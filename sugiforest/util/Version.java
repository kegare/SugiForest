package sugiforest.util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

import net.minecraft.util.MathHelper;
import net.minecraftforge.classloading.FMLForgePlugin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;

import sugiforest.core.SugiForest;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;

public class Version extends RecursiveAction
{
	private static Optional<String> CURRENT = Optional.absent();
	private static Optional<String> LATEST = Optional.absent();

	public static boolean DEV_DEBUG = false;

	private static Optional<Status> status = Optional.fromNullable(Status.PENDING);

	public enum Status
	{
		PENDING,
		FAILED,
		UP_TO_DATE,
		OUTDATED,
		AHEAD
	}

	private static void initialize()
	{
		CURRENT = Optional.of(Strings.nullToEmpty(SugiForest.metadata.version));
		LATEST = Optional.fromNullable(CURRENT.orNull());

		File file = SugiUtils.getModContainer().getSource();

		if (file != null && file.exists())
		{
			if (file.isFile())
			{
				if (StringUtils.endsWithIgnoreCase(FilenameUtils.getBaseName(file.getName()), "dev"))
				{
					DEV_DEBUG = true;
				}
			}
			else
			{
				DEV_DEBUG = true;
			}
		}
		else if (!FMLForgePlugin.RUNTIME_DEOBF)
		{
			DEV_DEBUG = true;
		}

		if (StringUtils.endsWithIgnoreCase(getCurrent(), "dev"))
		{
			DEV_DEBUG = true;
		}
		else if (DEV_DEBUG)
		{
			SugiForest.metadata.version += "-dev";
		}
	}

	public static void versionCheck()
	{
		if (!CURRENT.isPresent() || !LATEST.isPresent())
		{
			initialize();
		}

		SugiUtils.getPool().execute(new Version());
	}

	public static String getCurrent()
	{
		return CURRENT.orNull();
	}

	public static String getLatest()
	{
		return LATEST.or(getCurrent());
	}

	public static Status getStatus()
	{
		return status.orNull();
	}

	public static boolean isOutdated()
	{
		return getStatus() == Status.OUTDATED;
	}

	@Override
	protected void compute()
	{
		try
		{
			URL url = new URL(SugiForest.metadata.updateUrl);
			Map<String, Object> data = null;

			try (InputStream input = url.openStream())
			{
				byte[] dat = ByteStreams.toByteArray(input);

				if (dat != null && dat.length > 0)
				{
					data = new Gson().fromJson(new String(dat), Map.class);
				}
			}
			finally
			{
				if (data == null)
				{
					status = Optional.of(Status.FAILED);

					return;
				}
			}

			if (data.containsKey("homepage"))
			{
				SugiForest.metadata.url = (String)data.get("homepage");
			}

			Map<String, String> versions = Maps.newHashMap();

			if (data.containsKey("versions"))
			{
				versions = (Map<String, String>)data.get("versions");
			}

			String version = versions.get(MinecraftForge.MC_VERSION);
			ArtifactVersion current = new DefaultArtifactVersion(CURRENT.or("1.0.0"));

			if (!Strings.isNullOrEmpty(version))
			{
				ArtifactVersion latest = new DefaultArtifactVersion(version);

				LATEST = Optional.of(version);

				switch (MathHelper.clamp_int(latest.compareTo(current), -1, 1))
				{
					case 0:
						status = Optional.of(Status.UP_TO_DATE);
						return;
					case -1:
						status = Optional.of(Status.AHEAD);
						return;
					case 1:
						status = Optional.of(Status.OUTDATED);
						return;
					default:
						status = Optional.of(Status.FAILED);
						return;
				}
			}

			version = versions.get("latest");

			if (!Strings.isNullOrEmpty(version))
			{
				LATEST = Optional.of(version);
			}

			status = Optional.of(Status.FAILED);
		}
		catch (Exception e)
		{
			SugiLog.log(Level.WARN, e, "An error occurred trying to version check");

			status = Optional.of(Status.FAILED);
		}
	}
}
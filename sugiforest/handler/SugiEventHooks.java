package sugiforest.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sugiforest.core.Config;
import sugiforest.core.SugiForest;
import sugiforest.util.Version;
import sugiforest.world.BiomeSugiForest;

public class SugiEventHooks
{
	@SideOnly(Side.CLIENT)
	private float fogRender;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event)
	{
		if (event.getModID().equals(SugiForest.MODID))
		{
			Config.syncConfig();
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onFogDensity(FogDensity event)
	{
		Entity entity = event.getEntity();
		World world = entity.world;
		BlockPos pos = entity.getPosition();
		Biome biome = world.getBiome(pos);

		float f = (float)Math.pow(0.1D, 6);
		boolean flag = false;

		if (biome instanceof BiomeSugiForest)
		{
			long time = world.getWorldTime();
			int depth = 0;

			if (time >= 12000L && time < 13800 || time >= 22200 && time < 1000)
			{
				depth = 5;
			}
			else if (time >= 13800 && time < 22200)
			{
				depth = 4;
			}

			if (depth > 0)
			{
				float density = (float)Math.abs(Math.pow((entity.posY - 63) / (127 - 63), depth));

				if (fogRender < density)
				{
					fogRender += f;
				}
				else if (fogRender > density)
				{
					fogRender -= f;
				}

				flag = true;
			}
		}

		if (!flag && fogRender > 0.0F)
		{
			fogRender -= f;
		}

		if (fogRender > 0.0F)
		{
			GlStateManager.setFog(GlStateManager.FogMode.EXP);

			event.setDensity(fogRender);
			event.setCanceled(true);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onConnected(ClientConnectedToServerEvent event)
	{
		Minecraft mc = FMLClientHandler.instance().getClient();

		if (Config.versionNotify)
		{
			ITextComponent message;
			ITextComponent name = new TextComponentString(SugiForest.metadata.name);
			name.getStyle().setColor(TextFormatting.DARK_GREEN);

			if (Version.isOutdated())
			{
				ITextComponent latest = new TextComponentString(Version.getLatest().toString());
				latest.getStyle().setColor(TextFormatting.YELLOW);

				message = new TextComponentTranslation("sugiforest.version.message", name);
				message.appendText(" : ").appendSibling(latest);
				message.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, SugiForest.metadata.url));

				mc.ingameGUI.getChatGUI().printChatMessage(message);
			}

			message = null;

			if (Version.isBeta())
			{
				message = new TextComponentTranslation("sugiforest.version.message.beta", name);
			}
			else if (Version.isAlpha())
			{
				message = new TextComponentTranslation("sugiforest.version.message.alpha", name);
			}

			if (message != null)
			{
				mc.ingameGUI.getChatGUI().printChatMessage(message);
			}
		}
	}
}
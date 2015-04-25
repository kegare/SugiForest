package sugiforest.core;

import java.io.File;
import java.util.List;

import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.Loader;

import org.apache.logging.log4j.Level;

import sugiforest.util.SugiLog;

import com.google.common.collect.Lists;

public class Config
{
	public static Configuration config;

	public static boolean versionNotify;
	public static int sugiOnHills;

	public static int biomeID_SugiForest;
	public static int biomeGenWeight_SugiForest;
	public static int dimensionID_SugiForest;

	public static void syncConfig()
	{
		if (config == null)
		{
			File file = new File(Loader.instance().getConfigDir(), "SugiForest.cfg");
			config = new Configuration(file);

			try
			{
				config.load();
			}
			catch (Exception e)
			{
				File dest = new File(file.getParentFile(), file.getName() + ".bak");

				if (dest.exists())
				{
					dest.delete();
				}

				file.renameTo(dest);

				SugiLog.log(Level.ERROR, e, "A critical error occured reading the " + file.getName() + " file, defaults will be used - the invalid file is backed up at " + dest.getName());
			}
		}

		String category = Configuration.CATEGORY_GENERAL;
		Property prop;
		List<String> propOrder = Lists.newArrayList();

		prop = config.get(category, "versionNotify", true);
		prop.setLanguageKey("sugiforest.config." + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		versionNotify = prop.getBoolean(versionNotify);
		prop = config.get(category, "sugiOnHills", 2);
		prop.setMinValue(0).setMaxValue(100).setLanguageKey("sugiforest.config." + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [range: " + prop.getMinValue() + " ~ " + prop.getMaxValue() + ", default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		sugiOnHills = MathHelper.clamp_int(prop.getInt(sugiOnHills), Integer.parseInt(prop.getMinValue()), Integer.parseInt(prop.getMaxValue()));

		config.setCategoryPropertyOrder(category, propOrder);

		category = "sugiforest";
		propOrder = Lists.newArrayList();

		prop = config.get(category, "biomeID_SugiForest", 65).setRequiresMcRestart(true);
		prop.setMinValue(0).setMaxValue(255).setLanguageKey("sugiforest.config." + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [range: " + prop.getMinValue() + " ~ " + prop.getMaxValue() + ", default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		biomeID_SugiForest = MathHelper.clamp_int(prop.getInt(biomeID_SugiForest), Integer.parseInt(prop.getMinValue()), Integer.parseInt(prop.getMaxValue()));
		prop = config.get(category, "biomeGenWeight_SugiForest", 15).setRequiresMcRestart(true);
		prop.setMinValue(0).setMaxValue(100).setLanguageKey("sugiforest.config." + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [range: " + prop.getMinValue() + " ~ " + prop.getMaxValue() + ", default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		biomeGenWeight_SugiForest = MathHelper.clamp_int(prop.getInt(biomeGenWeight_SugiForest), Integer.parseInt(prop.getMinValue()), Integer.parseInt(prop.getMaxValue()));
		prop = config.get(category, "dimensionID_SugiForest", -7).setRequiresMcRestart(true);
		prop.setLanguageKey("sugiforest.config." + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [range: " + prop.getMinValue() + " ~ " + prop.getMaxValue() + ", default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		dimensionID_SugiForest = MathHelper.clamp_int(prop.getInt(dimensionID_SugiForest), Integer.parseInt(prop.getMinValue()), Integer.parseInt(prop.getMaxValue()));

		config.setCategoryPropertyOrder(category, propOrder);

		if (config.hasChanged())
		{
			config.save();
		}
	}
}
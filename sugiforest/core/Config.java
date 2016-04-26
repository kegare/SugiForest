package sugiforest.core;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;

import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;

public class Config
{
	public static final String LANG_KEY = "sugiforest.config.";
	public static final String CATEGORY_WORLD = "world";

	public static Configuration config;

	public static boolean versionNotify;
	public static boolean fallenSugiLeaves;
	public static int sugiOnHills;

	public static int biomeId;
	public static int biomeWeight;

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

				FMLLog.log(Level.ERROR, e, "A critical error occured reading the " + file.getName() + " file, defaults will be used - the invalid file is backed up at " + dest.getName());
			}
		}

		String category = Configuration.CATEGORY_GENERAL;
		Property prop;
		String comment;
		List<String> propOrder = Lists.newArrayList();

		prop = config.get(category, "fallenSugiLeaves", true);
		prop.setLanguageKey(LANG_KEY + category + "." + prop.getName());
		comment = I18n.translateToLocal(prop.getLanguageKey() + ".tooltip");
		comment += " [default: " + prop.getDefault() + "]";
		prop.setComment(comment);
		propOrder.add(prop.getName());
		fallenSugiLeaves = prop.getBoolean(fallenSugiLeaves);

		prop = config.get(category, "sugiOnHills", 2);
		prop.setMinValue(0).setMaxValue(100);
		prop.setLanguageKey(LANG_KEY + category + "." + prop.getName());
		comment = I18n.translateToLocal(prop.getLanguageKey() + ".tooltip");
		comment += " [range: " + prop.getMinValue() + " ~ " + prop.getMaxValue() + ", default: " + prop.getDefault() + "]";
		prop.setComment(comment);
		propOrder.add(prop.getName());
		sugiOnHills = prop.getInt(sugiOnHills);

		config.setCategoryPropertyOrder(category, propOrder);

		category = CATEGORY_WORLD;
		propOrder = Lists.newArrayList();

		prop = config.get(category, "biomeID_SugiForest", 65);
		prop.setMinValue(0).setMaxValue(255).setRequiresMcRestart(true);
		prop.setLanguageKey(LANG_KEY + category + "." + prop.getName());
		comment = I18n.translateToLocal(prop.getLanguageKey() + ".tooltip");
		comment += " [range: " + prop.getMinValue() + " ~ " + prop.getMaxValue() + ", default: " + prop.getDefault() + "]";
		prop.setComment(comment);
		propOrder.add(prop.getName());
		biomeId = prop.getInt(biomeId);

		prop = config.get(category, "biomeGenWeight_SugiForest", 20);
		prop.setMinValue(0).setMaxValue(100).setRequiresMcRestart(true);
		prop.setLanguageKey(LANG_KEY + category + "." + prop.getName());
		comment = I18n.translateToLocal(prop.getLanguageKey() + ".tooltip");
		comment += " [range: " + prop.getMinValue() + " ~ " + prop.getMaxValue() + ", default: " + prop.getDefault() + "]";
		prop.setComment(comment);
		propOrder.add(prop.getName());
		biomeWeight = prop.getInt(biomeWeight);

		config.setCategoryPropertyOrder(category, propOrder);

		if (config.hasChanged())
		{
			config.save();
		}
	}
}
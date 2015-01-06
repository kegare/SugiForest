/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.core;

import java.io.File;
import java.util.List;

import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Lists;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;

public class Config
{
	public static Configuration config;

	public static boolean versionNotify;
	public static int sugiOnHills;

	public static boolean woodSugi;
	public static boolean leavesSugi;
	public static boolean saplingSugi;
	public static boolean planksSugi;
	public static boolean woodSlabSugi;
	public static boolean stairsWoodSugi;

	public static boolean mystSap;

	public static int biomeSugiForest;
	public static int sugiForestGenWeight;
	public static int dimensionSugiForest;

	public static void syncConfig()
	{
		if (config == null)
		{
			File file = new File(Loader.instance().getConfigDir(), "SugiForest.cfg");
			config = new Configuration(file, true);

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
		List<String> propOrder = Lists.newArrayList();

		prop = config.get(category, "versionNotify", true);
		prop.setLanguageKey(SugiForest.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		versionNotify = prop.getBoolean(versionNotify);
		prop = config.get(category, "sugiOnHills", 10);
		prop.setMinValue(0).setMaxValue(300).setLanguageKey(SugiForest.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [range: " + prop.getMinValue() + " ~ " + prop.getMaxValue() + ", default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		sugiOnHills = prop.getInt(sugiOnHills);

		config.setCategoryPropertyOrder(category, propOrder);

		category = "blocks";
		propOrder = Lists.newArrayList();

		prop = config.get(category, "woodSugi", true).setRequiresMcRestart(true);
		prop.setLanguageKey(SugiForest.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		woodSugi = prop.getBoolean(woodSugi);
		prop = config.get(category, "leavesSugi", true).setRequiresMcRestart(true);
		prop.setLanguageKey(SugiForest.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		leavesSugi = prop.getBoolean(leavesSugi);
		prop = config.get(category, "saplingSugi", true).setRequiresMcRestart(true);
		prop.setLanguageKey(SugiForest.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		saplingSugi = prop.getBoolean(saplingSugi);
		prop = config.get(category, "planksSugi", true).setRequiresMcRestart(true);
		prop.setLanguageKey(SugiForest.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		planksSugi = prop.getBoolean(planksSugi);
		prop = config.get(category, "woodSlabSugi", true).setRequiresMcRestart(true);
		prop.setLanguageKey(SugiForest.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		woodSlabSugi = prop.getBoolean(woodSlabSugi);
		prop = config.get(category, "stairsWoodSugi", true).setRequiresMcRestart(true);
		prop.setLanguageKey(SugiForest.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		stairsWoodSugi = prop.getBoolean(stairsWoodSugi);

		config.addCustomCategoryComment(category, "If multiplayer, values must match on client-side and server-side.");
		config.setCategoryRequiresMcRestart(category, true);
		config.setCategoryPropertyOrder(category, propOrder);

		category = "items";
		propOrder = Lists.newArrayList();

		prop = config.get(category, "mystSap", true).setRequiresMcRestart(true);
		prop.setLanguageKey(SugiForest.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		mystSap = prop.getBoolean(mystSap);

		config.addCustomCategoryComment(category, "If multiplayer, values must match on client-side and server-side.");
		config.setCategoryRequiresMcRestart(category, true);
		config.setCategoryPropertyOrder(category, propOrder);

		category = "biomes";
		propOrder = Lists.newArrayList();

		prop = config.get(category, "Sugi Forest", 65).setRequiresMcRestart(true);
		prop.setMinValue(0).setMaxValue(BiomeGenBase.getBiomeGenArray().length).setLanguageKey(SugiForest.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [range: " + prop.getMinValue() + " ~ " + prop.getMaxValue() + ", default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		biomeSugiForest = prop.getInt(biomeSugiForest);
		prop = config.get(category, "sugiForestGenWeight", 15).setRequiresMcRestart(true);
		prop.setMinValue(0).setMaxValue(100).setLanguageKey(SugiForest.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [range: " + prop.getMinValue() + " ~ " + prop.getMaxValue() + ", default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		sugiForestGenWeight = prop.getInt(sugiForestGenWeight);

		config.setCategoryRequiresMcRestart(category, true);
		config.setCategoryPropertyOrder(category, propOrder);

		category = "dimension";
		propOrder = Lists.newArrayList();

		prop = config.get(category, "Sugi Forest", -7).setRequiresMcRestart(true);
		prop.setLanguageKey(SugiForest.CONFIG_LANG + category + "." + prop.getName());
		prop.comment = StatCollector.translateToLocal(prop.getLanguageKey() + ".tooltip");
		prop.comment += " [range: " + prop.getMinValue() + " ~ " + prop.getMaxValue() + ", default: " + prop.getDefault() + "]";
		propOrder.add(prop.getName());
		dimensionSugiForest = prop.getInt(dimensionSugiForest);

		config.setCategoryPropertyOrder(category, propOrder);

		if (config.hasChanged())
		{
			config.save();
		}
	}
}
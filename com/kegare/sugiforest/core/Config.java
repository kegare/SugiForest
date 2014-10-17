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

	public static int biomeSugiForest;
	public static int sugiForestGenWeight;

	public static void syncConfig()
	{
		if (config == null)
		{
			File file = new File(Loader.instance().getConfigDir(), "sugiforest.cfg");
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

		String category = "general";
		Property prop;
		List<String> propOrder = Lists.newArrayList();

		prop = config.get(category, "versionNotify", true);
		prop.comment = "Whether or not to notify when a new SugiForest version is available.";
		propOrder.add(prop.getName());
		versionNotify = prop.getBoolean(versionNotify);
		prop = config.get(category, "sugiOnHills", 10);
		prop.comment = "Specify the generation rate of sugi trees on hills.";
		prop.comment += Configuration.NEW_LINE;
		prop.comment += "If specify 0, sugi trees will be not generated in there.";
		prop.comment += Configuration.NEW_LINE;
		prop.comment += "NOTE: If multiplayer, server-side only.";
		propOrder.add(prop.getName());
		sugiOnHills = prop.getInt(sugiOnHills);

		config.setCategoryRequiresMcRestart(category, true);
		config.setCategoryPropertyOrder(category, propOrder);

		category = "blocks";

		prop = config.get(category, "woodSugi", true);
		prop.comment = "Whether or not to add Sugi Wood.";
		propOrder.add(prop.getName());
		woodSugi = prop.getBoolean(woodSugi);
		prop = config.get(category, "leavesSugi", true);
		prop.comment = "Whether or not to add Sugi Leaves.";
		propOrder.add(prop.getName());
		leavesSugi = prop.getBoolean(leavesSugi);
		prop = config.get(category, "saplingSugi", true);
		prop.comment = "Whether or not to add Sugi Sapling.";
		propOrder.add(prop.getName());
		saplingSugi = prop.getBoolean(saplingSugi);
		prop = config.get(category, "planksSugi", true);
		prop.comment = "Whether or not to add Sugi Wood Planks.";
		propOrder.add(prop.getName());
		planksSugi = prop.getBoolean(planksSugi);
		prop = config.get(category, "woodSlabSugi", true);
		prop.comment = "Whether or not to add Sugi Wood Slab.";
		propOrder.add(prop.getName());
		woodSlabSugi = prop.getBoolean(woodSlabSugi);
		prop = config.get(category, "stairsWoodSugi", true);
		prop.comment = "Whether or not to add Sugi Wood Stairs.";
		propOrder.add(prop.getName());
		stairsWoodSugi = prop.getBoolean(stairsWoodSugi);

		config.addCustomCategoryComment(category, "If multiplayer, values must match on client-side and server-side.");
		config.setCategoryRequiresMcRestart(category, true);
		config.setCategoryPropertyOrder(category, propOrder);

		category = "biomes";

		prop = config.get(category, "Sugi Forest", 65);
		prop.setMinValue(0).setMaxValue(BiomeGenBase.getBiomeGenArray().length);
		prop.comment = "Specify the biome ID for Sugi Forest.";
		prop.comment += Configuration.NEW_LINE;
		prop.comment += "NOTE: If multiplayer, values must match on client-side and server-side.";
		prop.comment += Configuration.NEW_LINE;
		prop.comment += "If specify 0 for ID, it will be disabled.";
		propOrder.add(prop.getName());
		biomeSugiForest = prop.getInt(biomeSugiForest);
		prop = config.get(category, "sugiForestGenWeight", 15);
		prop.comment = "Specify the generation weight for Sugi Forest.";
		prop.comment += Configuration.NEW_LINE;
		prop.comment += "NOTE: If multiplayer, server-side only.";
		propOrder.add(prop.getName());
		sugiForestGenWeight = prop.getInt(sugiForestGenWeight);

		config.setCategoryRequiresMcRestart(category, true);
		config.setCategoryPropertyOrder(category, propOrder);

		if (config.hasChanged())
		{
			config.save();
		}
	}
}
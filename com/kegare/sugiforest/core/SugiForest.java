/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.core;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.DimensionManager;

import org.apache.logging.log4j.Level;

import com.kegare.sugiforest.block.SugiBlocks;
import com.kegare.sugiforest.handler.SugiEventHooks;
import com.kegare.sugiforest.handler.SugiFuelHandler;
import com.kegare.sugiforest.handler.SugiWorldGenerator;
import com.kegare.sugiforest.item.SugiItems;
import com.kegare.sugiforest.plugin.bedrocklayer.BedrockLayerPlugin;
import com.kegare.sugiforest.plugin.mceconomy.MCEconomyPlugin;
import com.kegare.sugiforest.plugin.thaumcraft.ThaumcraftPlugin;
import com.kegare.sugiforest.util.Version;
import com.kegare.sugiforest.world.BiomeGenSugiForest;
import com.kegare.sugiforest.world.WorldProviderSugiForest;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod
(
	modid = SugiForest.MODID,
	acceptedMinecraftVersions = "[1.7.10,)",
	guiFactory = SugiForest.MOD_PACKAGE + ".client.config.SugiGuiFactory"
)
public class SugiForest
{
	public static final String
	MODID = "kegare.sugiforest",
	MOD_PACKAGE = "com.kegare.sugiforest",
	CONFIG_LANG = "sugiforest.config.";

	@Metadata(MODID)
	public static ModMetadata metadata;

	public static BiomeGenBase sugiForest = new BiomeGenSugiForest(65, false);

	@EventHandler
	public void construct(FMLConstructionEvent event)
	{
		Version.versionCheck();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Config.syncConfig();

		SugiBlocks.registerBlocks();
		SugiItems.registerItems();

		GameRegistry.registerFuelHandler(new SugiFuelHandler());
		GameRegistry.registerWorldGenerator(new SugiWorldGenerator(), 10);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		FMLCommonHandler.instance().bus().register(new SugiEventHooks());

		if (Config.biomeSugiForest > 0)
		{
			sugiForest = new BiomeGenSugiForest(Config.biomeSugiForest, true);

			BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(sugiForest, Config.sugiForestGenWeight));
			BiomeManager.addSpawnBiome(sugiForest);
			BiomeManager.addStrongholdBiome(sugiForest);

			BiomeGenBase.explorationBiomesList.add(sugiForest);

			BiomeDictionary.registerBiomeType(sugiForest, Type.FOREST, Type.HILLS);

			if (Config.dimensionSugiForest != 0)
			{
				int id = Config.dimensionSugiForest;

				DimensionManager.registerProviderType(id, WorldProviderSugiForest.class, true);
				DimensionManager.registerDimension(id, id);
			}
		}

		SugiBlocks.registerRecipes();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		try
		{
			if (BedrockLayerPlugin.enabled())
			{
				BedrockLayerPlugin.invoke();
			}
		}
		catch (Throwable e)
		{
			FMLLog.log(Level.WARN, e, "Failed to trying invoke plugin: BedrockLayerPlugin");
		}

		try
		{
			if (MCEconomyPlugin.enabled())
			{
				MCEconomyPlugin.invoke();
			}
		}
		catch (Throwable e)
		{
			FMLLog.log(Level.WARN, e, "Failed to trying invoke plugin: MCEconomyPlugin");
		}

		try
		{
			if (ThaumcraftPlugin.enabled())
			{
				ThaumcraftPlugin.invoke();
			}
		}
		catch (Throwable e)
		{
			FMLLog.log(Level.WARN, e, "Failed to trying invoke plugin: ThaumcraftPlugin");
		}
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		if (event.getSide().isServer() && (Version.DEV_DEBUG || Config.versionNotify && Version.isOutdated()))
		{
			event.getServer().logInfo("A new SugiForest version is available : " + Version.getLatest());
		}
	}
}
/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Metadata;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import org.apache.logging.log4j.Level;

import sugiforest.api.SugiForestAPI;
import sugiforest.block.SugiBlocks;
import sugiforest.handler.SugiEventHooks;
import sugiforest.handler.SugiForestAPIHandler;
import sugiforest.handler.SugiFuelHandler;
import sugiforest.handler.SugiWorldGenerator;
import sugiforest.item.SugiItems;
import sugiforest.plugin.bedrocklayer.BedrockLayerPlugin;
import sugiforest.util.SugiLog;
import sugiforest.util.Version;
import sugiforest.world.BiomeGenSugiForest;
import sugiforest.world.WorldProviderSugiForest;

@Mod(modid = "sugiforest", guiFactory = "sugiforest.client.config.SugiGuiFactory")
public class SugiForest
{
	@Metadata("sugiforest")
	public static ModMetadata metadata;

	public static final CreativeTabs tabSugiForest = new CreativeTabSugiForest();

	public static BiomeGenBase sugiForest;

	@EventHandler
	public void construct(FMLConstructionEvent event)
	{
		SugiForestAPI.instance = new SugiForestAPIHandler();

		Version.versionCheck();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Config.syncConfig();

		SugiBlocks.registerBlocks();
		SugiItems.registerItems();

		if (event.getSide().isClient())
		{
			SugiBlocks.registerModels();
			SugiItems.registerModels();
		}

		if (Config.biomeID_SugiForest > 0)
		{
			sugiForest = new BiomeGenSugiForest(Config.biomeID_SugiForest);
		}

		GameRegistry.registerFuelHandler(new SugiFuelHandler());
		GameRegistry.registerWorldGenerator(new SugiWorldGenerator(), 10);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		FMLCommonHandler.instance().bus().register(SugiEventHooks.instance);

		if (sugiForest != null)
		{
			BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(sugiForest, Config.biomeGenWeight_SugiForest));
			BiomeManager.addSpawnBiome(sugiForest);
			BiomeManager.addStrongholdBiome(sugiForest);

			BiomeGenBase.explorationBiomesList.add(sugiForest);

			BiomeDictionary.registerBiomeType(sugiForest, Type.FOREST, Type.HILLS);

			if (SugiForestAPI.getDimension() != 0)
			{
				int id = SugiForestAPI.getDimension();

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
			SugiLog.log(Level.WARN, e, "Failed to trying invoke plugin: BedrockLayerPlugin");
		}
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		if (event.getSide().isServer() && (Version.DEV_DEBUG || Config.versionNotify && Version.isOutdated()))
		{
			event.getServer().logInfo(StatCollector.translateToLocalFormatted("sugiforest.version.message", "SugiForest") + ": " + Version.getLatest());
		}
	}
}
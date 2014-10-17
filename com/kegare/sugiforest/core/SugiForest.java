/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.core;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.kegare.sugiforest.block.SugiBlocks;
import com.kegare.sugiforest.handler.SugiEventHooks;
import com.kegare.sugiforest.handler.SugiFuelHandler;
import com.kegare.sugiforest.handler.SugiWorldGenerator;
import com.kegare.sugiforest.util.Version;
import com.kegare.sugiforest.world.BiomeGenSugiForest;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLConstructionEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod
(
	modid = SugiForest.MODID,
	acceptedMinecraftVersions = "[1.7.10,)"
)
public class SugiForest
{
	public static final String
	MODID = "kegare.sugiforest";

	@Metadata(MODID)
	public static ModMetadata metadata;

	public static BiomeGenBase sugiForest = new BiomeGenSugiForest(65, false);

	public static ModContainer getModContainer()
	{
		ModContainer mod = Loader.instance().getIndexedModList().get(MODID);

		if (mod == null)
		{
			mod = Loader.instance().activeModContainer();

			if (mod == null || mod.getModId() != MODID)
			{
				return null;
			}
		}

		return mod;
	}

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

		registerRecipes();

		GameRegistry.registerFuelHandler(new SugiFuelHandler());
		GameRegistry.registerWorldGenerator(new SugiWorldGenerator(), 10);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (Config.biomeSugiForest > 0)
		{
			sugiForest = new BiomeGenSugiForest(Config.biomeSugiForest, true);

			BiomeManager.warmBiomes.add(new BiomeEntry(sugiForest, Config.sugiForestGenWeight));
			BiomeManager.addSpawnBiome(sugiForest);
			BiomeManager.addStrongholdBiome(sugiForest);

			BiomeGenBase.explorationBiomesList.add(sugiForest);

			BiomeDictionary.registerBiomeType(sugiForest, Type.FOREST, Type.HILLS);
		}
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		if (event.getSide().isServer() && (Version.DEV_DEBUG || Config.versionNotify && Version.isOutdated()))
		{
			event.getServer().logInfo("A new SugiForest version is available : " + Version.getLatest());
		}

		FMLCommonHandler.instance().bus().register(new SugiEventHooks());
	}

	protected static void registerRecipes()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(Blocks.noteblock, "###", "#X#", "###", '#', "planksSugi", 'X', Items.redstone));
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.bed, "###", "XXX", '#', Blocks.wool, 'X', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Blocks.piston, "###", "XYX", "XZX", '#', "planksSugi", 'X', "cobblestone", 'Y', Items.redstone, 'Z', Items.iron_ingot));
		GameRegistry.addRecipe(new ShapedOreRecipe(Blocks.bookshelf, "###", "XXX", "###", '#', "planksSugi", 'X', Items.book));
		GameRegistry.addRecipe(new ShapedOreRecipe(Blocks.chest, "###", "# #", "###", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Blocks.crafting_table, "##", "##", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.wooden_door, "##", "##", "##", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Blocks.wooden_pressure_plate, "##", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Blocks.jukebox, "###", "#X#", "###", '#', "planksSugi", 'X', Items.diamond));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.trapdoor, 2), "###", "###", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Blocks.fence_gate, "#X#", "#X#", '#', "stickWood", 'X', "planksSugi"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(Blocks.wooden_button, "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Blocks.tripwire_hook, 2), "#", "X", "Y", '#', Items.iron_ingot, 'X', "stickWood", 'Y', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Blocks.daylight_detector, "###", "XXX", "YYY", '#', Blocks.glass, 'X', Items.quartz, 'Y', "woodSlabSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.stick, 4), "#", "#", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.bowl, 4), "# #", " # ", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.sign, 3), "###", "###", " X ", '#', "planksSugi", 'X', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.boat, "# #", "###", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.wooden_sword, "#", "#", "X", '#', "planksSugi", 'X', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.wooden_pickaxe, "###", " X ", " X ", '#', "planksSugi", 'X', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.wooden_axe, "##", "#X", " X", '#', "planksSugi", 'X', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.wooden_shovel, "#", "X", "X", '#', "planksSugi", 'X', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.wooden_hoe, "##", " X", " X", '#', "planksSugi", 'X', "stickWood"));
	}
}
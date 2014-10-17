/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.block;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import com.kegare.sugiforest.core.Config;
import com.kegare.sugiforest.item.ItemWoodSlabSugi;

import cpw.mods.fml.common.registry.GameRegistry;

public class SugiBlocks
{
	public static final BlockLogSugi sugi_log = new BlockLogSugi("woodSugi");
	public static final BlockLeavesSugi sugi_leaves = new BlockLeavesSugi("leavesSugi");
	public static final BlockSaplingSugi sugi_sapling = new BlockSaplingSugi("saplingSugi");
	public static final BlockWoodSugi sugi_planks = new BlockWoodSugi("planksSugi");
	public static final BlockWoodSlabSugi sugi_slab = new BlockWoodSlabSugi("woodSlabSugi");
	public static final BlockStairsSugi sugi_stairs = new BlockStairsSugi("stairsWoodSugi");

	public static void registerBlocks()
	{
		if (Config.woodSugi)
		{
			GameRegistry.registerBlock(sugi_log, "sugi_log");

			FurnaceRecipes.smelting().func_151393_a(sugi_log, new ItemStack(Items.coal, 1, 1), 0.15F);

			OreDictionary.registerOre("woodSugi", sugi_log);

			Blocks.fire.setFireInfo(sugi_log, 5, 5);
		}

		if (Config.leavesSugi)
		{
			GameRegistry.registerBlock(sugi_leaves, "sugi_leaves");

			OreDictionary.registerOre("leavesSugi", sugi_leaves);

			Blocks.fire.setFireInfo(sugi_leaves, 30, 60);
		}

		if (Config.saplingSugi)
		{
			GameRegistry.registerBlock(sugi_sapling, "sugi_sapling");
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stick), new ItemStack(sugi_sapling));

			OreDictionary.registerOre("saplingSugi", sugi_sapling);

			Blocks.fire.setFireInfo(sugi_sapling, 20, 60);
		}

		if (Config.planksSugi)
		{
			GameRegistry.registerBlock(sugi_planks, "sugi_planks");
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(sugi_planks, 4), "woodSugi"));

			OreDictionary.registerOre("planksSugi", sugi_planks);

			Blocks.fire.setFireInfo(sugi_planks, 5, 20);
		}

		if (Config.woodSlabSugi)
		{
			GameRegistry.registerBlock(sugi_slab, ItemWoodSlabSugi.class, "sugi_slab");
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sugi_slab, 6), "###", '#', "planksSugi"));

			OreDictionary.registerOre("woodSlabSugi", sugi_slab);

			Blocks.fire.setFireInfo(sugi_slab, 5, 20);
		}

		if (Config.stairsWoodSugi)
		{
			GameRegistry.registerBlock(sugi_stairs, "sugi_stairs");
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sugi_stairs, 4), "  #", " ##", "###", '#', "planksSugi"));

			OreDictionary.registerOre("stairsWoodSugi", sugi_stairs);

			Blocks.fire.setFireInfo(sugi_stairs, 5, 20);
		}
	}
}
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
import com.kegare.sugiforest.entity.TileEntityChestSugi;
import com.kegare.sugiforest.item.ItemChestSugi;
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
	public static final BlockChestSugi sugi_chest = new BlockChestSugi("chestSugi");

	public static void registerBlocks()
	{
		if (Config.woodSugi)
		{
			GameRegistry.registerBlock(sugi_log, "sugi_log");

			FurnaceRecipes.smelting().func_151393_a(sugi_log, new ItemStack(Items.coal, 1, 1), 0.15F);

			OreDictionary.registerOre("logWood", new ItemStack(sugi_log, 1, OreDictionary.WILDCARD_VALUE));
			OreDictionary.registerOre("woodSugi", sugi_log);

			Blocks.fire.setFireInfo(sugi_log, 5, 5);
		}

		if (Config.leavesSugi)
		{
			GameRegistry.registerBlock(sugi_leaves, "sugi_leaves");

			OreDictionary.registerOre("treeLeaves", new ItemStack(sugi_leaves, 1, OreDictionary.WILDCARD_VALUE));
			OreDictionary.registerOre("leavesSugi", sugi_leaves);

			Blocks.fire.setFireInfo(sugi_leaves, 30, 60);
		}

		if (Config.saplingSugi)
		{
			GameRegistry.registerBlock(sugi_sapling, "sugi_sapling");

			OreDictionary.registerOre("treeSapling", new ItemStack(sugi_sapling, 1, OreDictionary.WILDCARD_VALUE));
			OreDictionary.registerOre("saplingSugi", sugi_sapling);

			Blocks.fire.setFireInfo(sugi_sapling, 20, 60);
		}

		if (Config.planksSugi)
		{
			GameRegistry.registerBlock(sugi_planks, "sugi_planks");

			OreDictionary.registerOre("plankWood", new ItemStack(sugi_planks, 1, OreDictionary.WILDCARD_VALUE));
			OreDictionary.registerOre("planksSugi", sugi_planks);

			Blocks.fire.setFireInfo(sugi_planks, 5, 20);
		}

		if (Config.woodSlabSugi)
		{
			GameRegistry.registerBlock(sugi_slab, ItemWoodSlabSugi.class, "sugi_slab");

			OreDictionary.registerOre("slabWood", new ItemStack(sugi_slab, 1, OreDictionary.WILDCARD_VALUE));
			OreDictionary.registerOre("woodSlabSugi", sugi_slab);

			Blocks.fire.setFireInfo(sugi_slab, 5, 20);
		}

		if (Config.stairsWoodSugi)
		{
			GameRegistry.registerBlock(sugi_stairs, "sugi_stairs");

			OreDictionary.registerOre("stairWood", sugi_slab);
			OreDictionary.registerOre("stairsWoodSugi", sugi_stairs);

			Blocks.fire.setFireInfo(sugi_stairs, 5, 20);
		}

		if (Config.chestSugi)
		{
			GameRegistry.registerBlock(sugi_chest, ItemChestSugi.class, "sugi_chest");
			GameRegistry.registerTileEntity(TileEntityChestSugi.class, "SugiChest");

			OreDictionary.registerOre("chestSugi", sugi_chest);
			OreDictionary.registerOre("sugiChest", sugi_chest);
			OreDictionary.registerOre("chest", sugi_chest);

			Blocks.fire.setFireInfo(sugi_chest, 5, 5);
		}
	}

	public static void registerRecipes()
	{
		if (Config.saplingSugi)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(Items.stick), new ItemStack(sugi_sapling));
		}

		if (Config.planksSugi)
		{
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(sugi_planks, 4), "woodSugi"));
			GameRegistry.addShapelessRecipe(new ItemStack(sugi_planks, 4), sugi_log);
		}

		if (Config.woodSlabSugi)
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sugi_slab, 6), "###", '#', "planksSugi"));
			GameRegistry.addRecipe(new ItemStack(sugi_slab, 6), "###", '#', sugi_planks);
		}

		if (Config.stairsWoodSugi)
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sugi_stairs, 4), "  #", " ##", "###", '#', "planksSugi"));
			GameRegistry.addRecipe(new ItemStack(sugi_stairs, 4), "  #", " ##", "###", '#', sugi_planks);
		}

		if (Config.chestSugi)
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(sugi_chest,
				"XXX", "X X", "XXX",
				'X', "planksSugi"
			));
			GameRegistry.addShapedRecipe(new ItemStack(sugi_chest),
				"XXX", "X X", "XXX",
				'X', sugi_planks
			);
		}
	}
}
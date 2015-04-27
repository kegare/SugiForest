/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.block;

import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import sugiforest.entity.TileEntitySugiChest;
import sugiforest.item.ItemSugiChest;
import sugiforest.item.ItemSugiFallenLeaves;
import sugiforest.item.ItemSugiLeaves;
import sugiforest.item.ItemSugiPortal;
import sugiforest.item.ItemSugiWoodSlab;
import sugiforest.util.SugiUtils;

public class SugiBlocks
{
	public static final BlockSugiLog sugi_log = new BlockSugiLog();
	public static final BlockSugiLeaves sugi_leaves = new BlockSugiLeaves();
	public static final BlockSugiFallenLeaves sugi_fallen_leaves = new BlockSugiFallenLeaves();
	public static final BlockSugiSapling sugi_sapling = new BlockSugiSapling();
	public static final BlockSugiWood sugi_planks = new BlockSugiWood();
	public static final BlockSugiWoodSlab sugi_slab = new BlockSugiWoodSlab();
	public static final BlockSugiStairs sugi_stairs = new BlockSugiStairs();
	public static final BlockSugiFence sugi_fence = new BlockSugiFence();
	public static final BlockSugiFenceGate sugi_fence_gate = new BlockSugiFenceGate();
	public static final BlockSugiChest sugi_chest = new BlockSugiChest();
	public static final BlockSugiPortal sugi_portal = new BlockSugiPortal();

	public static void registerBlocks()
	{
		GameRegistry.registerBlock(sugi_log, "sugi_log");
		GameRegistry.registerBlock(sugi_leaves, ItemSugiLeaves.class, "sugi_leaves");
		GameRegistry.registerBlock(sugi_fallen_leaves, ItemSugiFallenLeaves.class, "sugi_fallen_leaves");
		GameRegistry.registerBlock(sugi_sapling, "sugi_sapling");
		GameRegistry.registerBlock(sugi_planks, "sugi_planks");
		GameRegistry.registerBlock(sugi_slab, ItemSugiWoodSlab.class, "sugi_slab");
		GameRegistry.registerBlock(sugi_stairs, "sugi_stairs");
		GameRegistry.registerBlock(sugi_fence, "sugi_fence");
		GameRegistry.registerBlock(sugi_fence_gate, "sugi_fence_gate");
		GameRegistry.registerBlock(sugi_chest, ItemSugiChest.class, "sugi_chest");
		GameRegistry.registerBlock(sugi_portal, ItemSugiPortal.class, "sugi_portal");

		GameRegistry.registerTileEntity(TileEntitySugiChest.class, "SugiChest");

		OreDictionary.registerOre("logWood", new ItemStack(sugi_log, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("woodSugi", sugi_log);
		OreDictionary.registerOre("treeLeaves", new ItemStack(sugi_leaves, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("leavesSugi", sugi_leaves);
		OreDictionary.registerOre("fallenLeavesSugi", sugi_fallen_leaves);
		OreDictionary.registerOre("treeSapling", new ItemStack(sugi_sapling, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("saplingSugi", sugi_sapling);
		OreDictionary.registerOre("plankWood", new ItemStack(sugi_planks, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("planksSugi", sugi_planks);
		OreDictionary.registerOre("slabWood", new ItemStack(sugi_slab, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("woodSlabSugi", sugi_slab);
		OreDictionary.registerOre("stairWood", new ItemStack(sugi_stairs, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("stairsWoodSugi", sugi_stairs);
		OreDictionary.registerOre("fenceWood", new ItemStack(sugi_fence, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("fenceSugi", sugi_fence);
		OreDictionary.registerOre("fenceGateWood", new ItemStack(sugi_fence_gate, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("fenceGateSugi", sugi_fence_gate);
		SugiUtils.registerOreDict(sugi_chest, "chestSugi", "sugiChest", "chest");

		Blocks.fire.setFireInfo(sugi_log, 5, 5);
		Blocks.fire.setFireInfo(sugi_leaves, 30, 60);
		Blocks.fire.setFireInfo(sugi_fallen_leaves, 30, 60);
		Blocks.fire.setFireInfo(sugi_sapling, 20, 60);
		Blocks.fire.setFireInfo(sugi_planks, 5, 20);
		Blocks.fire.setFireInfo(sugi_slab, 5, 20);
		Blocks.fire.setFireInfo(sugi_stairs, 5, 20);
		Blocks.fire.setFireInfo(sugi_fence, 5, 20);
		Blocks.fire.setFireInfo(sugi_fence_gate, 5, 20);
		Blocks.fire.setFireInfo(sugi_chest, 5, 5);

		BlockSugiFallenLeaves.addFallenSeed(new ItemStack(sugi_sapling), 10);
		BlockSugiFallenLeaves.addFallenSeed(new ItemStack(Blocks.brown_mushroom), 12);
		BlockSugiFallenLeaves.addFallenSeed(new ItemStack(Blocks.red_mushroom), 8);
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels()
	{
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(sugi_log), 0, new ModelResourceLocation("sugiforest:sugi_log", "inventory"));
		ModelLoader.setCustomStateMapper(sugi_log, new StateMap.Builder().addPropertiesToIgnore(BlockSugiLog.MYST).build());
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(sugi_leaves), 0, new ModelResourceLocation("sugiforest:sugi_leaves", "inventory"));
		ModelLoader.setCustomStateMapper(sugi_leaves, new StateMap.Builder().addPropertiesToIgnore(BlockLeaves.DECAYABLE, BlockLeaves.CHECK_DECAY).build());
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(sugi_fallen_leaves), 0, new ModelResourceLocation("sugiforest:sugi_fallen_leaves", "inventory"));
		ModelLoader.setCustomStateMapper(sugi_fallen_leaves, new StateMap.Builder().addPropertiesToIgnore(BlockSugiFallenLeaves.CHANCE).build());
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(sugi_sapling), 0, new ModelResourceLocation("sugiforest:sugi_sapling", "inventory"));
		ModelLoader.setCustomStateMapper(sugi_sapling, new StateMap.Builder().addPropertiesToIgnore(BlockSapling.TYPE, BlockSapling.STAGE).build());
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(sugi_planks), 0, new ModelResourceLocation("sugiforest:sugi_planks", "inventory"));
		ModelLoader.setCustomStateMapper(sugi_planks, new StateMap.Builder().addPropertiesToIgnore(BlockSugiWood.DOUBLE).build());
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(sugi_slab), 0, new ModelResourceLocation("sugiforest:sugi_slab", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(sugi_stairs), 0, new ModelResourceLocation("sugiforest:sugi_stairs", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(sugi_fence), 0, new ModelResourceLocation("sugiforest:sugi_fence", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(sugi_fence_gate), 0, new ModelResourceLocation("sugiforest:sugi_fence_gate", "inventory"));
		ModelLoader.setCustomStateMapper(sugi_fence_gate, new StateMap.Builder().addPropertiesToIgnore(BlockFenceGate.POWERED).build());
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(sugi_chest), 0, new ModelResourceLocation("sugiforest:sugi_chest", "inventory"));
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(sugi_portal), 0, new ModelResourceLocation("sugiforest:sugi_portal", "inventory"));
	}

	public static void registerRecipes()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sugi_fallen_leaves, 6), "###", '#', "leavesSugi"));
		GameRegistry.addRecipe(new ItemStack(sugi_fallen_leaves, 6), "###", '#', sugi_leaves);

		GameRegistry.addShapelessRecipe(new ItemStack(Items.stick), new ItemStack(sugi_sapling));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(sugi_planks, 4), "woodSugi"));
		GameRegistry.addShapelessRecipe(new ItemStack(sugi_planks, 4), new ItemStack(sugi_log, 1, OreDictionary.WILDCARD_VALUE));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sugi_slab, 6), "###", '#', "planksSugi"));
		GameRegistry.addRecipe(new ItemStack(sugi_slab, 6), "###", '#', sugi_planks);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sugi_stairs, 4), "  #", " ##", "###", '#', "planksSugi"));
		GameRegistry.addRecipe(new ItemStack(sugi_stairs, 4), "  #", " ##", "###", '#', sugi_planks);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sugi_fence, 3), "#X#", "#X#", 'X', "stickWood", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sugi_fence, 3), "#X#", "#X#", 'X', "stickWood", '#', sugi_planks));
		GameRegistry.addRecipe(new ShapedOreRecipe(sugi_fence_gate, "X#X", "X#X", 'X', "stickWood", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(sugi_fence_gate, "X#X", "X#X", 'X', "stickWood", '#', sugi_planks));

		GameRegistry.addRecipe(new ShapedOreRecipe(sugi_chest, "XXX", "X X", "XXX", 'X', "planksSugi"));
		GameRegistry.addRecipe(new ItemStack(sugi_chest), "XXX", "X X", "XXX", 'X', sugi_planks);

		FurnaceRecipes.instance().addSmeltingRecipeForBlock(sugi_log, new ItemStack(Items.coal, 1, 1), 0.15F);
	}
}
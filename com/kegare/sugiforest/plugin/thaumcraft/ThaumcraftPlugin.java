/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.plugin.thaumcraft;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

import com.kegare.sugiforest.block.SugiBlocks;
import com.kegare.sugiforest.item.SugiItems;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional.Method;

public class ThaumcraftPlugin
{
	public static final String MODID = "Thaumcraft";

	public static boolean enabled()
	{
		return Loader.isModLoaded(MODID);
	}

	@Method(modid = MODID)
	public static void invoke()
	{
		ThaumcraftApi.registerObjectTag(new ItemStack(SugiBlocks.sugi_log, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.TREE, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(SugiBlocks.sugi_leaves, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.PLANT, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(SugiBlocks.sugi_sapling, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.PLANT, 1).add(Aspect.TREE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(SugiBlocks.sugi_planks, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.TREE, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(SugiBlocks.sugi_slab, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.TREE, 1));
		ThaumcraftApi.registerObjectTag(new ItemStack(SugiBlocks.sugi_stairs, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.TREE, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(SugiItems.myst_sap, 1, OreDictionary.WILDCARD_VALUE), new AspectList().add(Aspect.HEAL, 3).add(Aspect.TREE, 2).add(Aspect.ELDRITCH, 2));
	}
}
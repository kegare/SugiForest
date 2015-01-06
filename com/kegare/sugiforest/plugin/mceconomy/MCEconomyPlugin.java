/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.plugin.mceconomy;

import net.minecraft.item.ItemStack;
import shift.mceconomy2.api.MCEconomyAPI;

import com.kegare.sugiforest.block.SugiBlocks;
import com.kegare.sugiforest.item.SugiItems;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional.Method;

public class MCEconomyPlugin
{
	public static final String MODID = "mceconomy2";

	public static boolean enabled()
	{
		return Loader.isModLoaded(MODID);
	}

	@Method(modid = MODID)
	public static void invoke()
	{
		MCEconomyAPI.addPurchaseItem(new ItemStack(SugiBlocks.sugi_log), 1);
		MCEconomyAPI.addPurchaseItem(new ItemStack(SugiBlocks.sugi_leaves), 0);
		MCEconomyAPI.addPurchaseItem(new ItemStack(SugiBlocks.sugi_sapling), 0);
		MCEconomyAPI.addPurchaseItem(new ItemStack(SugiBlocks.sugi_planks), 0);
		MCEconomyAPI.addPurchaseItem(new ItemStack(SugiBlocks.sugi_slab), 0);
		MCEconomyAPI.addPurchaseItem(new ItemStack(SugiBlocks.sugi_stairs), 0);
		MCEconomyAPI.addPurchaseItem(new ItemStack(SugiItems.myst_sap), 50);
	}
}
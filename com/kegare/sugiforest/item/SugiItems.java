/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.item;

import net.minecraftforge.oredict.OreDictionary;

import com.kegare.sugiforest.core.Config;

import cpw.mods.fml.common.registry.GameRegistry;

public class SugiItems
{
	public static final ItemMystSap myst_sap = new ItemMystSap("mystSap");

	public static void registerItems()
	{
		if (Config.mystSap)
		{
			GameRegistry.registerItem(myst_sap, "myst_sap");

			OreDictionary.registerOre("mystSap", myst_sap);
			OreDictionary.registerOre("sapMyst", myst_sap);
		}
	}
}
/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.plugin.bedrocklayer;

import net.minecraft.init.Blocks;

import com.kegare.bedrocklayer.api.BedrockLayerAPI;
import com.kegare.sugiforest.core.Config;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional.Method;

public class BedrockLayerPlugin
{
	public static final String MODID = "kegare.bedrocklayer";

	public static boolean enabled()
	{
		return Loader.isModLoaded(MODID);
	}

	@Method(modid = MODID)
	public static void invoke()
	{
		if (Config.dimensionSugiForest != 0)
		{
			BedrockLayerAPI.registerFlatten(Config.dimensionSugiForest, 1, 5, Blocks.stone, 0, "sugiforest", false);
		}
	}
}
/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.plugin.bedrocklayer;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional.Method;
import sugiforest.core.Config;

import com.kegare.bedrocklayer.api.BedrockLayerAPI;

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
		if (Config.dimensionID_SugiForest != 0)
		{
			BedrockLayerAPI.registerFlatten(Config.dimensionID_SugiForest, 1, 5, Blocks.stone.getDefaultState(), "sugiforest", false);
		}
	}
}
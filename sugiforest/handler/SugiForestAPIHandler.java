/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.config.Configuration;
import sugiforest.api.ISugiForestAPI;
import sugiforest.block.BlockSugiFallenLeaves;
import sugiforest.core.Config;
import sugiforest.core.SugiForest;
import sugiforest.util.Version;

public class SugiForestAPIHandler implements ISugiForestAPI
{
	@Override
	public String getVersion()
	{
		return Version.getCurrent();
	}

	@Override
	public Configuration getConfig()
	{
		return Config.config;
	}

	@Override
	public BiomeGenBase getBiome()
	{
		return SugiForest.sugiForest;
	}

	@Override
	public int getDimension()
	{
		return Config.dimensionID_SugiForest;
	}

	@Override
	public void addFallenSeed(ItemStack stack, int weight)
	{
		BlockSugiFallenLeaves.addFallenSeed(stack, weight);
	}
}
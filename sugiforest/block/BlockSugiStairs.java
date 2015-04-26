/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.block;

import net.minecraft.block.BlockStairs;
import sugiforest.core.SugiForest;

public class BlockSugiStairs extends BlockStairs
{
	public BlockSugiStairs()
	{
		super(SugiBlocks.sugi_planks.getDefaultState());
		this.setUnlocalizedName("stairs.sugi");
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(SugiForest.tabSugiForest);
		this.useNeighborBrightness = true;
	}
}
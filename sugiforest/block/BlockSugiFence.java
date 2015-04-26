/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.block;

import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import sugiforest.core.SugiForest;

public class BlockSugiFence extends BlockFence
{
	public BlockSugiFence()
	{
		super(Material.wood);
		this.setUnlocalizedName("fence.sugi");
		this.setHardness(1.75F);
		this.setResistance(4.75F);
		this.setStepSound(soundTypeWood);
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(SugiForest.tabSugiForest);
	}
}
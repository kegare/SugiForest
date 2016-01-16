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
import net.minecraft.block.BlockPlanks;
import sugiforest.core.SugiForest;

public class BlockSugiFenceGate extends BlockFenceGate
{
	public BlockSugiFenceGate()
	{
		super(BlockPlanks.EnumType.SPRUCE);
		this.setUnlocalizedName("fenceGate.sugi");
		this.setHardness(1.75F);
		this.setResistance(4.75F);
		this.setStepSound(soundTypeWood);
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(SugiForest.tabSugiForest);
	}
}
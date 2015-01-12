/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.handler;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.kegare.sugiforest.block.BlockChestSugi;
import com.kegare.sugiforest.block.BlockLeavesSugi;
import com.kegare.sugiforest.block.BlockLogSugi;
import com.kegare.sugiforest.block.BlockSaplingSugi;
import com.kegare.sugiforest.block.BlockStairsSugi;
import com.kegare.sugiforest.block.BlockWoodSlabSugi;
import com.kegare.sugiforest.block.BlockWoodSugi;

import cpw.mods.fml.common.IFuelHandler;

public class SugiFuelHandler implements IFuelHandler
{
	@Override
	public int getBurnTime(ItemStack fuel)
	{
		Block block = Block.getBlockFromItem(fuel.getItem());

		if (block instanceof BlockLogSugi || block instanceof BlockWoodSugi || block instanceof BlockStairsSugi)
		{
			return 300;
		}
		else if (block instanceof BlockLeavesSugi || block instanceof BlockSaplingSugi)
		{
			return 100;
		}
		else if (block instanceof BlockWoodSlabSugi)
		{
			return 150;
		}
		else if (block instanceof BlockChestSugi)
		{
			return 2300;
		}

		return 0;
	}
}
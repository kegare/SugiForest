/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class BlockWoodSugi extends Block
{
	public BlockWoodSugi(String name)
	{
		super(Material.wood);
		this.setBlockName(name);
		this.setBlockTextureName("sugiforest:sugi_planks");
		this.setHardness(1.75F);
		this.setResistance(4.75F);
		this.setStepSound(soundTypeWood);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHarvestLevel("axe", 0);
	}

	@Override
	public int quantityDropped(int metadata, int fortune, Random random)
	{
		return metadata == 1 ? 2 : 1;
	}

	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		return metadata == 1 ? Item.getItemFromBlock(SugiBlocks.sugi_slab) : super.getItemDropped(metadata, random, fortune);
	}
}
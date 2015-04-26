/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import sugiforest.core.SugiForest;

public class BlockSugiWood extends Block
{
	public static final PropertyBool DOUBLE = PropertyBool.create("double");

	public BlockSugiWood()
	{
		super(Material.wood);
		this.setUnlocalizedName("wood.sugi");
		this.setHardness(1.75F);
		this.setResistance(4.75F);
		this.setStepSound(soundTypeWood);
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(SugiForest.tabSugiForest);
		this.setDefaultState(blockState.getBaseState().withProperty(DOUBLE, Boolean.valueOf(false)));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((Boolean)state.getValue(DOUBLE)).booleanValue() ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(DOUBLE, Boolean.valueOf(meta == 1));
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {DOUBLE});
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return ((Boolean)state.getValue(DOUBLE)).booleanValue() ? Item.getItemFromBlock(SugiBlocks.sugi_slab) : super.getItemDropped(state, rand, fortune);
	}

	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
	{
		return ((Boolean)state.getValue(DOUBLE)).booleanValue() ? 2 : super.quantityDropped(state, fortune, random);
	}
}
/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sugiforest.core.SugiForest;
import sugiforest.world.WorldGenSugiTree;

public class BlockSugiSapling extends BlockSapling
{
	public BlockSugiSapling()
	{
		super();
		this.setUnlocalizedName("sapling.sugi");
		this.setStepSound(soundTypeGrass);
		this.setCreativeTab(SugiForest.tabSugiForest);
		this.setDefaultState(blockState.getBaseState().withProperty(STAGE, Integer.valueOf(0)));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		byte b = 0;
		int meta = b | 0;

		meta |= ((Integer)state.getValue(STAGE)).intValue() << 3;

		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {TYPE, STAGE});
	}

	@Override
	public void generateTree(World world, BlockPos pos, IBlockState state, Random random)
	{
		if (!TerrainGen.saplingGrowTree(world, random, pos))
		{
			return;
		}

		world.setBlockState(pos, Blocks.air.getDefaultState(), 4);

		if (!new WorldGenSugiTree(true).generate(world, random, pos))
		{
			world.setBlockState(pos, state, 4);
		}
	}

	@Override
	public boolean isTypeAt(World world, BlockPos pos, EnumType type)
	{
		return world.getBlockState(pos).getBlock() == this;
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
	{
		list.add(new ItemStack(itemIn));
	}
}
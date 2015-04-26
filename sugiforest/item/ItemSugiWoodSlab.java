/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sugiforest.block.BlockSugiWood;
import sugiforest.block.SugiBlocks;

public class ItemSugiWoodSlab extends ItemBlock
{
	public ItemSugiWoodSlab(Block block)
	{
		super(block);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (stack.stackSize == 0)
		{
			return false;
		}
		else if (!player.canPlayerEdit(pos.offset(side), side, stack))
		{
			return false;
		}
		else
		{
			IBlockState state = world.getBlockState(pos);

			if (state.getBlock() == block)
			{
				BlockSlab.EnumBlockHalf half = (BlockSlab.EnumBlockHalf)state.getValue(BlockSlab.HALF);

				if (side == EnumFacing.UP && half == BlockSlab.EnumBlockHalf.BOTTOM || side == EnumFacing.DOWN && half == BlockSlab.EnumBlockHalf.TOP)
				{
					IBlockState blockstate = SugiBlocks.sugi_planks.getDefaultState().withProperty(BlockSugiWood.DOUBLE, Boolean.valueOf(true));

					if (world.checkNoEntityCollision(SugiBlocks.sugi_planks.getCollisionBoundingBox(world, pos, blockstate)) && world.setBlockState(pos, blockstate, 3))
					{
						world.playSoundEffect(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SugiBlocks.sugi_planks.stepSound.getPlaceSound(), (SugiBlocks.sugi_planks.stepSound.getVolume() + 1.0F) / 2.0F, SugiBlocks.sugi_planks.stepSound.getFrequency() * 0.8F);

						--stack.stackSize;
					}

					return true;
				}
			}

			return tryPlace(stack, world, pos.offset(side)) || super.onItemUse(stack, player, world, pos, side, hitX, hitY, hitZ);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack)
	{
		BlockPos blockpos = pos;
		IBlockState state = world.getBlockState(pos);

		if (state.getBlock() == block)
		{
			boolean flag = state.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP;

			if (side == EnumFacing.UP && !flag || side == EnumFacing.DOWN && flag)
			{
				return true;
			}
		}

		pos = pos.offset(side);
		state = world.getBlockState(pos);

		return state.getBlock() == block || super.canPlaceBlockOnSide(world, blockpos, side, player, stack);
	}

	private boolean tryPlace(ItemStack stack, World world, BlockPos pos)
	{
		if (world.getBlockState(pos).getBlock() == block)
		{
			IBlockState state = SugiBlocks.sugi_planks.getDefaultState().withProperty(BlockSugiWood.DOUBLE, Boolean.valueOf(true));

			if (world.checkNoEntityCollision(SugiBlocks.sugi_planks.getCollisionBoundingBox(world, pos, state)) && world.setBlockState(pos, state, 3))
			{
				world.playSoundEffect(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SugiBlocks.sugi_planks.stepSound.getPlaceSound(), (SugiBlocks.sugi_planks.stepSound.getVolume() + 1.0F) / 2.0F, SugiBlocks.sugi_planks.stepSound.getFrequency() * 0.8F);

				--stack.stackSize;
			}

			return true;
		}

		return false;
	}
}
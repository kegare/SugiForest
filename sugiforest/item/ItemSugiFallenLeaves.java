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
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import sugiforest.block.BlockSugiFallenLeaves;

public class ItemSugiFallenLeaves extends ItemSugiLeaves
{
	public ItemSugiFallenLeaves(Block block)
	{
		super(block);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (stack.stackSize == 0)
		{
			return false;
		}
		else if (!player.canPlayerEdit(pos, side, stack))
		{
			return false;
		}
		else
		{
			IBlockState state = world.getBlockState(pos);
			Block block = state.getBlock();

			if (block != this.block && side != EnumFacing.UP)
			{
				pos = pos.offset(side);
				state = world.getBlockState(pos);
				block = state.getBlock();
			}

			if (block == this.block)
			{
				int i = ((Integer)state.getValue(BlockSugiFallenLeaves.LAYERS)).intValue();

				if (i <= 7)
				{
					IBlockState blockstate = state.withProperty(BlockSugiFallenLeaves.LAYERS, Integer.valueOf(i + 1));

					if (world.checkNoEntityCollision(block.getCollisionBoundingBox(world, pos, blockstate)) && world.setBlockState(pos, blockstate, 2))
					{
						world.playSoundEffect(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getFrequency() * 0.8F);

						--stack.stackSize;

						return true;
					}
				}
			}

			return super.onItemUse(stack, player, world, pos, side, hitX, hitY, hitZ);
		}
	}

	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack)
	{
		IBlockState state = world.getBlockState(pos);

		return state.getBlock() != block || (Integer)state.getValue(BlockSugiFallenLeaves.LAYERS) > 7 ? super.canPlaceBlockOnSide(world, pos, side, player, stack) : true;
	}
}
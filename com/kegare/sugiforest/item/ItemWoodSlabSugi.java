/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.kegare.sugiforest.block.SugiBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWoodSlabSugi extends ItemBlock
{
	public ItemWoodSlabSugi(Block block)
	{
		super(block);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (itemstack.stackSize == 0)
		{
			return false;
		}
		else if (!player.canPlayerEdit(x, y, z, side, itemstack))
		{
			return false;
		}
		else
		{
			Block block = world.getBlock(x, y, z);
			int metadata = world.getBlockMetadata(x, y, z);
			boolean flag = (metadata & 8) != 0;

			if ((side == 1 && !flag || side == 0 && flag) && block == field_150939_a)
			{
				Block planks = SugiBlocks.sugi_planks;

				if (world.checkNoEntityCollision(planks.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlock(x, y, z, planks, 1, 3))
				{
					world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, planks.stepSound.func_150496_b(), (planks.stepSound.getVolume() + 1.0F) / 2.0F, planks.stepSound.getPitch() * 0.8F);

					--itemstack.stackSize;
				}

				return true;
			}

			int X = x;
			int Y = y;
			int Z = z;

			if (side == 0)
			{
				--y;
			}
			else if (side == 1)
			{
				++y;
			}
			else if (side == 2)
			{
				--z;
			}
			else if (side == 3)
			{
				++z;
			}
			else if (side == 4)
			{
				--x;
			}
			else if (side == 5)
			{
				++x;
			}

			block = world.getBlock(x, y, z);
			metadata = world.getBlockMetadata(x, y, z);
			flag = (metadata & 8) != 0;

			if (block == field_150939_a)
			{
				Block planks = SugiBlocks.sugi_planks;

				if (world.checkNoEntityCollision(planks.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlock(x, y, z, planks, 1, 3))
				{
					world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, planks.stepSound.func_150496_b(), (planks.stepSound.getVolume() + 1.0F) / 2.0F, planks.stepSound.getPitch() * 0.8F);

					--itemstack.stackSize;
				}

				return true;
			}

			return super.onItemUse(itemstack, player, world, X, Y, Z, side, hitX, hitY, hitZ);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean func_150936_a(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack itemstack)
	{
		Block block = world.getBlock(x, y, z);
		int metadata = world.getBlockMetadata(x, y, z);
		boolean flag = (metadata & 8) != 0;

		if ((side == 1 && !flag || side == 0 && flag) && block == field_150939_a)
		{
			return true;
		}

		int X = x;
		int Y = y;
		int Z = z;

		if (side == 0)
		{
			--y;
		}
		else if (side == 1)
		{
			++y;
		}
		else if (side == 2)
		{
			--z;
		}
		else if (side == 3)
		{
			++z;
		}
		else if (side == 4)
		{
			--x;
		}
		else if (side == 5)
		{
			++x;
		}

		block = world.getBlock(x, y, z);
		metadata = world.getBlockMetadata(x, y, z);
		flag = (metadata & 8) != 0;

		return block == field_150939_a || super.func_150936_a(world, X, Y, Z, side, player, itemstack);
	}
}
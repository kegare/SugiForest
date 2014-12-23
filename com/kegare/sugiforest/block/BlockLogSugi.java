/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.block;

import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.kegare.sugiforest.core.Config;
import com.kegare.sugiforest.item.SugiItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLogSugi extends BlockLog
{
	public BlockLogSugi(String name)
	{
		super();
		this.setBlockName(name);
		this.setBlockTextureName("sugiforest:sugi_log");
		this.setHarvestLevel("axe", 0);
		this.setTickRandomly(true);
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		if (world.getBlockMetadata(x, y, z) == 1)
		{
			return super.getBlockHardness(world, x, y, z) * 3.0F;
		}

		return super.getBlockHardness(world, x, y, z);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		if (world.getBlock(x, y, z) == this && world.getBlockMetadata(x, y, z) == 1)
		{
			return 7;
		}

		return super.getLightValue(world, x, y, z);
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
	{
		boolean flag = world.getBlockMetadata(x, y, z) == 1;
		boolean result = super.removedByPlayer(world, player, x, y, z, willHarvest);

		if (Config.mystSap && !world.isRemote && result && flag && player.inventory.hasItem(Items.bowl))
		{
			player.inventory.consumeInventoryItem(Items.bowl);

			EntityItem item = new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, new ItemStack(SugiItems.myst_sap));
			item.delayBeforeCanPickup = 20;

			world.spawnEntityInWorld(item);
		}

		return result;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon(getTextureName());
		field_150164_N = iconRegister.registerIcon(getTextureName() + "_top");
	}

	@SideOnly(Side.CLIENT)
	@Override
	protected IIcon getSideIcon(int metadata)
	{
		return blockIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	protected IIcon getTopIcon(int metadata)
	{
		return field_150164_N;
	}
}
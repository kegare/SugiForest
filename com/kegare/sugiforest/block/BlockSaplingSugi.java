/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;

import com.kegare.sugiforest.world.WorldGenSugiTree;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSaplingSugi extends BlockSapling
{
	public BlockSaplingSugi(String name)
	{
		super();
		this.setBlockName(name);
		this.setBlockTextureName("sugiforest:sugi_sapling");
		this.setStepSound(soundTypeGrass);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon(getTextureName());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata)
	{
		return blockIcon;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		if (!world.isRemote)
		{
			checkAndDropBlock(world, x, y, z);

			if (world.getBlockLightValue(x, y + 1, z) >= 9 && random.nextInt(7) == 0)
			{
				func_149879_c(world, x, y, z, random);
			}
		}
	}

	@Override
	public void func_149878_d(World world, int x, int y, int z, Random random)
	{
		if (!TerrainGen.saplingGrowTree(world, random, x, y, z))
		{
			return;
		}

		world.setBlock(x, y, z, Blocks.air, 0, 4);

		if (!new WorldGenSugiTree(true).generate(world, random, x, y, z))
		{
			world.setBlock(x, y, z, this, world.getBlockMetadata(x, y, z), 4);
		}
	}

	@Override
	public void func_149879_c(World world, int x, int y, int z, Random random)
	{
		int metadata = world.getBlockMetadata(x, y, z);

		if ((metadata & 8) == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, metadata | 8, 4);
		}
		else
		{
			func_149878_d(world, x, y, z, random);
		}
	}

	@Override
	public boolean func_149880_a(World world, int x, int y, int z, int metadata)
	{
		return world.getBlock(x, y, z) == this;
	}

	@Override
	public Item getItemDropped(int par1, Random random, int par3)
	{
		return Item.getItemFromBlock(SugiBlocks.sugi_sapling);
	}

	@Override
	public int damageDropped(int metadata)
	{
		return 0;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		list.add(new ItemStack(item, 1, 0));
	}
}
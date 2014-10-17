/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import com.google.common.collect.Lists;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLeavesSugi extends BlockLeavesBase implements IShearable
{
	private IIcon[] leavesIcon = new IIcon[2];

	private int[] adjacentTreeBlocks;

	public BlockLeavesSugi(String name)
	{
		super(Material.leaves, true);
		this.setBlockName(name);
		this.setBlockTextureName("sugiforest:sugi_leaves");
		this.setHardness(0.2F);
		this.setLightOpacity(1);
		this.setStepSound(soundTypeGrass);
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setHarvestLevel("axe", 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBlockColor()
	{
		return 6726755;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderColor(int metadata)
	{
		return getBlockColor();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int colorMultiplier(IBlockAccess world, int x, int y, int z)
	{
		int r = 0;
		int g = 0;
		int b = 0;

		for (int i = -1; i <= 1; ++i)
		{
			for (int j = -1; j <= 1; ++j)
			{
				int color = world.getBiomeGenForCoords(x + i, z + j).getBiomeFoliageColor(x, y, z);
				r += (color & 16711680) >> 16;
				g += (color & 65280) >> 8;
				b += color & 255;
			}
		}

		return (r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		leavesIcon[0] = iconRegister.registerIcon(getTextureName());
		leavesIcon[1] = iconRegister.registerIcon(getTextureName() + "_opacity");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata)
	{
		return FMLClientHandler.instance().getClient().gameSettings.fancyGraphics ? leavesIcon[0] : leavesIcon[1];
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		if (world.checkChunksExist(x - 2, y - 2, z - 2, x + 2, y + 2, z + 2))
		{
			for (int i = -1; i <= 1; i++)
			{
				for (int j = -1; j <= 1; j++)
				{
					for (int k = -1; k <= 1; k++)
					{
						world.getBlock(x + i, y + j, z + k).beginLeavesDecay(world, x + i, y + j, z + k);
					}
				}
			}
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		if (!world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);

			if ((metadata & 8) != 0)
			{
				int var1 = 4;
				int var2 = var1 + 1;
				byte var3 = 32;
				int var4 = var3 * var3;
				int var5 = var3 / 2;

				if (adjacentTreeBlocks == null)
				{
					adjacentTreeBlocks = new int[var3 * var3 * var3];
				}

				int var6;

				if (world.checkChunksExist(x - var2, y - var2, z - var2, x + var2, y + var2, z + var2))
				{
					int var7;
					int var8;
					int var9;

					for (var6 = -var1; var6 <= var1; ++var6)
					{
						for (var7 = -var1; var7 <= var1; ++var7)
						{
							for (var8 = -var1; var8 <= var1; ++var8)
							{
								Block block = world.getBlock(x + var6, y + var7, z + var8);

								if (block != null && block.canSustainLeaves(world, x + var6, y + var7, z + var8))
								{
									adjacentTreeBlocks[(var6 + var5) * var4 + (var7 + var5) * var3 + var8 + var5] = 0;
								}
								else if (block != null && block.isLeaves(world, x + var6, y + var7, z + var8))
								{
									adjacentTreeBlocks[(var6 + var5) * var4 + (var7 + var5) * var3 + var8 + var5] = -2;
								}
								else
								{
									adjacentTreeBlocks[(var6 + var5) * var4 + (var7 + var5) * var3 + var8 + var5] = -1;
								}
							}
						}
					}

					for (var6 = 1; var6 <= 4; ++var6)
					{
						for (var7 = -var1; var7 <= var1; ++var7)
						{
							for (var8 = -var1; var8 <= var1; ++var8)
							{
								for (var9 = -var1; var9 <= var1; ++var9)
								{
									if (adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5) * var3 + var9 + var5] == var6 - 1)
									{
										if (adjacentTreeBlocks[(var7 + var5 - 1) * var4 + (var8 + var5) * var3 + var9 + var5] == -2)
										{
											adjacentTreeBlocks[(var7 + var5 - 1) * var4 + (var8 + var5) * var3 + var9 + var5] = var6;
										}

										if (adjacentTreeBlocks[(var7 + var5 + 1) * var4 + (var8 + var5) * var3 + var9 + var5] == -2)
										{
											adjacentTreeBlocks[(var7 + var5 + 1) * var4 + (var8 + var5) * var3 + var9 + var5] = var6;
										}

										if (adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5 - 1) * var3 + var9 + var5] == -2)
										{
											adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5 - 1) * var3 + var9 + var5] = var6;
										}

										if (adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5 + 1) * var3 + var9 + var5] == -2)
										{
											adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5 + 1) * var3 + var9 + var5] = var6;
										}

										if (adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5) * var3 + var9 + var5 - 1] == -2)
										{
											adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5) * var3 + var9 + var5 - 1] = var6;
										}

										if (adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5) * var3 + var9 + var5 + 1] == -2)
										{
											adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5) * var3 + var9 + var5 + 1] = var6;
										}
									}
								}
							}
						}
					}
				}

				var6 = adjacentTreeBlocks[var5 * var4 + var5 * var3 + var5];

				if (var6 >= 0)
				{
					world.setBlockMetadataWithNotify(x, y, z, metadata & -9, 3);
				}
				else
				{
					dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);

					world.setBlockToAir(x, y, z);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		if (world.canLightningStrikeAt(x, y + 1, z) && !World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && random.nextInt(15) == 1)
		{
			double ptX = x + random.nextFloat();
			double ptY = y - 0.05D;
			double ptZ = z + random.nextFloat();

			world.spawnParticle("dripWater", ptX, ptY, ptZ, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public int quantityDropped(Random random)
	{
		return random.nextInt(30) == 0 ? 1 : 0;
	}

	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		return Item.getItemFromBlock(SugiBlocks.sugi_sapling);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getItem(World world, int x, int y, int z)
	{
		return Item.getItemFromBlock(SugiBlocks.sugi_sapling);
	}

	@Override
	public void beginLeavesDecay(World world, int x, int y, int z)
	{
		world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) | 8, 3);
	}

	@Override
	public boolean isShearable(ItemStack itemstack, IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack itemstack, IBlockAccess world, int x, int y, int z, int fortune)
	{
		return Lists.newArrayList(new ItemStack(this));
	}
}
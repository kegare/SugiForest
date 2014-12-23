/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenSwamp;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

import com.kegare.sugiforest.block.SugiBlocks;

public class WorldGenSugiTree extends WorldGenAbstractTree
{
	private final boolean doBlockNotify;

	private int minTreeHeight;
	private int maxTreeHeight;

	private int treeHeight;

	public WorldGenSugiTree(boolean doBlockNotify)
	{
		this(doBlockNotify, 17, 20);
	}

	public WorldGenSugiTree(boolean doBlockNotify, int minTreeHeight, int maxTreeHeight)
	{
		super(doBlockNotify);
		this.doBlockNotify = doBlockNotify;
		this.minTreeHeight = minTreeHeight;
		this.maxTreeHeight = maxTreeHeight;
	}

	private boolean isGeneratableTree(World world, int x, int y, int z)
	{
		for (int Y = y; Y <= y + 1 + treeHeight; ++Y)
		{
			if (Y >= 0 && Y < 256)
			{
				int checkedRange = Y == y ? 0 : Y >= y + 1 + treeHeight - 2 ? 2 : 1;

				for (int X = x - checkedRange; X <= x + checkedRange; ++X)
				{
					for (int Z = z - checkedRange; Z <= z + checkedRange; ++Z)
					{
						Block block = world.getBlock(X, Y, Z);

						if (!block.isAir(world, X, Y, Z) && !block.isLeaves(world, X, Y, Z) && !block.isReplaceable(world, X, Y, Z))
						{
							return false;
						}
					}
				}
			}
			else
			{
				return false;
			}
		}

		return true;
	}

	private void setTree(World world, Random random, int x, int y, int z, BiomeGenBase biome)
	{
		for (int woodHeight = 0; woodHeight < treeHeight; ++woodHeight)
		{
			Block block = world.getBlock(x, y + woodHeight, z);

			if (isReplaceable(world, x, y, z))
			{
				setBlockAndNotifyAdequately(world, x, y + woodHeight, z, SugiBlocks.sugi_log, random.nextInt(40) == 0 ? 1 : 0);

				if (!doBlockNotify)
				{
					if (woodHeight > 0)
					{
						if (biome instanceof BiomeGenSwamp || biome instanceof BiomeGenJungle)
						{
							if (random.nextInt(3) > 0 && world.isAirBlock(x - 1, y + woodHeight, z))
							{
								setBlockAndNotifyAdequately(world, x - 1, y + woodHeight, z, Blocks.vine, 8);
							}

							if (random.nextInt(3) > 0 && world.isAirBlock(x + 1, y + woodHeight, z))
							{
								setBlockAndNotifyAdequately(world, x + 1, y + woodHeight, z, Blocks.vine, 2);
							}

							if (random.nextInt(3) > 0 && world.isAirBlock(x, y + woodHeight, z - 1))
							{
								setBlockAndNotifyAdequately(world, x, y + woodHeight, z - 1, Blocks.vine, 1);
							}

							if (random.nextInt(3) > 0 && world.isAirBlock(x, y + woodHeight, z + 1))
							{
								setBlockAndNotifyAdequately(world, x, y + woodHeight, z + 1, Blocks.vine, 4);
							}
						}
					}
					else if (random.nextInt(10) == 0)
					{
						byte count = 0;

						for (int X = x - 2; count < 3 && X <= x + 2; ++X)
						{
							for (int Z = z - 2; count < 3 && Z <= z + 2; ++Z)
							{
								block = world.getBlock(X, y - 1, Z);

								if (block.canSustainPlant(world, X, y - 1, Z, ForgeDirection.UP, Blocks.brown_mushroom))
								{
									block = random.nextInt(30) == 0 ? Blocks.red_mushroom : Blocks.brown_mushroom;
									block.onPlantGrow(world, X, y - 1, Z, X, y, Z);

									if (random.nextInt(6) == 0 && world.getBlock(X, y, Z) == block)
									{
										++count;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void setLeaves(World world, Random random, int x, int y, int z, BiomeGenBase biome)
	{
		int leavesHeight = 12;

		if (treeHeight - leavesHeight >= leavesHeight - 3)
		{
			leavesHeight += 2;
		}

		for (int Y = y - leavesHeight + treeHeight; Y <= y + treeHeight; ++Y)
		{
			int leaveNum = Y - (y + treeHeight);
			int leaveRange = 1 - leaveNum / (leavesHeight / 2 - 1);

			for (int X = x - leaveRange; X <= x + leaveRange; ++X)
			{
				for (int Z = z - leaveRange; Z <= z + leaveRange; ++Z)
				{
					if ((Math.abs(X - x) != leaveRange || Math.abs(Z - z) != leaveRange || random.nextInt(2) != 0 && leaveNum != 0) &&
						(world.isAirBlock(X, Y, Z) || world.getBlock(X, Y, Z).canBeReplacedByLeaves(world, X, Y, Z)) && random.nextInt(12) != 0)
					{
						func_150515_a(world, X, Y, Z, SugiBlocks.sugi_leaves);
					}
				}
			}
		}
	}

	private void setVines(World world, Random random, int x, int y, int z)
	{
		int vinesHeight = 12;

		if (treeHeight - vinesHeight >= vinesHeight - 3)
		{
			vinesHeight += 2;
		}

		for (int Y = y - vinesHeight + treeHeight; Y <= y + treeHeight; ++Y)
		{
			int vineNum = Y - (y + treeHeight);
			int vineRange = 1 - vineNum / (vinesHeight / 2 - 1);

			for (int X = x - vineRange; X <= x + vineRange; ++X)
			{
				for (int Z = z - vineRange; Z <= z + vineRange; ++Z)
				{
					if (world.getBlock(X, Y, Z).isLeaves(world, X, Y, Z))
					{
						if (random.nextInt(4) == 0 && world.isAirBlock(X - 1, Y, Z))
						{
							growVines(world, X - 1, Y, Z, 8);
						}

						if (random.nextInt(4) == 0 && world.isAirBlock(X + 1, Y, Z))
						{
							growVines(world, X + 1, Y, Z, 2);
						}

						if (random.nextInt(4) == 0 && world.isAirBlock(X, Y, Z - 1))
						{
							growVines(world, X, Y, Z - 1, 1);
						}

						if (random.nextInt(4) == 0 && world.isAirBlock(X, Y, Z + 1))
						{
							growVines(world, X, Y, Z + 1, 4);
						}
					}
				}
			}
		}
	}

	private void growVines(World world, int x, int y, int z, int metadata)
	{
		setBlockAndNotifyAdequately(world, x, y, z, Blocks.vine, metadata);

		byte count = 4;

		while (true)
		{
			--y;

			if (!world.isAirBlock(x, y, z) || count <= 0)
			{
				return;
			}

			setBlockAndNotifyAdequately(world, x, y, z, Blocks.vine, metadata);

			--count;
		}
	}

	private void shiftTreeHeight(int height)
	{
		minTreeHeight += height;
		maxTreeHeight += height;
	}

	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(x, z);

		if (biome.isHighHumidity())
		{
			shiftTreeHeight(random.nextInt(3));
		}
		else if (biome.getFloatTemperature(x, y, z) >= 1.0F)
		{
			shiftTreeHeight(-(random.nextInt(2) + 3));
		}

		treeHeight = Math.min(random.nextInt(4) + minTreeHeight, maxTreeHeight);

		if (treeHeight != 0 && y > 0 && y + treeHeight + 1 <= 256 && isGeneratableTree(world, x, y, z))
		{
			Block block = world.getBlock(x, y - 1, z);

			if (block.canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, SugiBlocks.sugi_sapling) && y < 256 - treeHeight - 1)
			{
				block.onPlantGrow(world, x, y - 1, z, x, y, x);

				setLeaves(world, random, x, y, z, biome);
				setTree(world, random, x, y, z, biome);

				if (!doBlockNotify && (biome instanceof BiomeGenSwamp || biome instanceof BiomeGenJungle))
				{
					setVines(world, random, x, y, z);
				}

				return true;
			}
		}

		return false;
	}
}
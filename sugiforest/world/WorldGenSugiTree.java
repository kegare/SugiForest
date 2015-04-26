/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenSwamp;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import sugiforest.block.SugiBlocks;
import sugiforest.core.Config;

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

	private boolean isGeneratableTree(World world, BlockPos pos)
	{
		for (int y = pos.getY(); y <= pos.getY() + 1 + treeHeight; ++y)
		{
			if (y >= 0 && y < 256)
			{
				int checkedRange = y == pos.getY() ? 0 : y >= pos.getY() + 1 + treeHeight - 2 ? 2 : 1;

				for (int x = pos.getX() - checkedRange; x <= pos.getX() + checkedRange; ++x)
				{
					for (int z = pos.getZ() - checkedRange; z <= pos.getZ() + checkedRange; ++z)
					{
						if (!isReplaceable(world, new BlockPos(x, y, z)))
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

	private void setTree(World world, Random random, BlockPos pos, BiomeGenBase biome)
	{
		for (int woodHeight = 0; woodHeight < treeHeight; ++woodHeight)
		{
			BlockPos blockpos = pos.up(woodHeight);
			IBlockState state = world.getBlockState(blockpos);
			Block block = state.getBlock();

			if (block.isAir(world, blockpos) || block.isLeaves(world, blockpos) || block.getMaterial() == Material.vine)
			{
				func_175905_a(world, blockpos, SugiBlocks.sugi_log, random.nextInt(40) == 0 ? 1 : 0);

				if (!doBlockNotify)
				{
					if (woodHeight > 0)
					{
						if (biome instanceof BiomeGenSwamp || biome instanceof BiomeGenJungle)
						{
							if (random.nextInt(3) > 0 && world.isAirBlock(pos.add(-1, woodHeight, 0)))
							{
								func_175905_a(world, pos.add(-1, woodHeight, 0), Blocks.vine, BlockVine.EAST_FLAG);
							}

							if (random.nextInt(3) > 0 && world.isAirBlock(pos.add(1, woodHeight, 0)))
							{
								func_175905_a(world, pos.add(1, woodHeight, 0), Blocks.vine, BlockVine.WEST_FLAG);
							}

							if (random.nextInt(3) > 0 && world.isAirBlock(pos.add(0, woodHeight, -1)))
							{
								func_175905_a(world, pos.add(0, woodHeight, -1), Blocks.vine, BlockVine.SOUTH_FLAG);
							}

							if (random.nextInt(3) > 0 && world.isAirBlock(pos.add(0, woodHeight, 1)))
							{
								func_175905_a(world, pos.add(0, woodHeight, 1), Blocks.vine, BlockVine.NORTH_FLAG);
							}
						}
					}
					else if (random.nextInt(10) == 0)
					{
						byte count = 0;

						for (int x = pos.getX() - 2; count < 3 && x <= pos.getX() + 2; ++x)
						{
							for (int z = pos.getZ() - 2; count < 3 && z <= pos.getZ() + 2; ++z)
							{
								blockpos = new BlockPos(x, pos.getY() - 1, z);
								state = world.getBlockState(blockpos);
								block = state.getBlock();

								if (block.canSustainPlant(world, blockpos, EnumFacing.UP, Blocks.brown_mushroom))
								{
									block = random.nextInt(30) == 0 ? Blocks.red_mushroom : Blocks.brown_mushroom;
									block.onPlantGrow(world, blockpos, blockpos.up());

									if (random.nextInt(6) == 0 && world.getBlockState(blockpos).getBlock() == block)
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

	private void setLeaves(World world, Random random, BlockPos pos, BiomeGenBase biome)
	{
		int leavesHeight = 12;

		if (treeHeight - leavesHeight >= leavesHeight - 3)
		{
			leavesHeight += 2;
		}

		for (int y = pos.getY() - leavesHeight + treeHeight; y <= pos.getY() + treeHeight; ++y)
		{
			int leaveNum = y - (pos.getY() + treeHeight);
			int leaveRange = 1 - leaveNum / (leavesHeight / 2 - 1);

			for (int x = pos.getX() - leaveRange; x <= pos.getX() + leaveRange; ++x)
			{
				for (int z = pos.getZ() - leaveRange; z <= pos.getZ() + leaveRange; ++z)
				{
					if (Math.abs(x - pos.getX()) != leaveRange || Math.abs(z - pos.getZ()) != leaveRange || random.nextInt(2) != 0 && leaveNum != 0)
					{
						BlockPos blockpos = new BlockPos(x, y, z);
						IBlockState state = world.getBlockState(blockpos);
						Block block = state.getBlock();

						if ((block.isAir(world, blockpos) || block.isLeaves(world, blockpos) || block.getMaterial() == Material.vine) && random.nextInt(12) != 0)
						{
							func_175906_a(world, blockpos, SugiBlocks.sugi_leaves);
						}
					}
				}
			}
		}
	}

	private void setFallenLeaves(World world, Random random, BlockPos pos)
	{
		int leavesHeight = 12;

		if (treeHeight - leavesHeight >= leavesHeight - 3)
		{
			leavesHeight += 2;
		}

		int leaveRange = leavesHeight / 3 - 1;
		int y = pos.getY() + treeHeight - leavesHeight - 3;

		for (int x = pos.getX() - leaveRange; x <= pos.getX() + leaveRange; ++x)
		{
			for (int z = pos.getZ() - leaveRange; z <= pos.getZ() + leaveRange; ++z)
			{
				BlockPos blockpos = new BlockPos(x, y, z);

				if (!world.isAirBlock(blockpos))
				{
					continue;
				}

				do
				{
					blockpos = blockpos.down();
				}
				while (blockpos.getY() > 0 && world.isAirBlock(blockpos.down()));

				if (SugiBlocks.sugi_fallen_leaves.canPlaceBlockAt(world, blockpos) && random.nextInt(3) == 0)
				{
					func_175906_a(world, blockpos, SugiBlocks.sugi_fallen_leaves);
				}
			}
		}
	}

	private void setVines(World world, Random random, BlockPos pos)
	{
		int vinesHeight = 12;

		if (treeHeight - vinesHeight >= vinesHeight - 3)
		{
			vinesHeight += 2;
		}

		for (int y = pos.getY() - vinesHeight + treeHeight; y <= pos.getY() + treeHeight; ++y)
		{
			int vineNum = y - (pos.getY() + treeHeight);
			int vineRange = 1 - vineNum / (vinesHeight / 2 - 1);

			for (int x = pos.getX() - vineRange; x <= pos.getX() + vineRange; ++x)
			{
				for (int z = pos.getZ() - vineRange; z <= pos.getZ() + vineRange; ++z)
				{
					BlockPos blockpos = new BlockPos(x, y, z);

					if (world.getBlockState(blockpos).getBlock().isLeaves(world, blockpos))
					{
						blockpos = blockpos.add(-1, 0, 0);

						if (random.nextInt(4) == 0 && world.isAirBlock(blockpos))
						{
							growVines(world, blockpos, BlockVine.EAST_FLAG);
						}

						blockpos = blockpos.add(1, 0, 0);

						if (random.nextInt(4) == 0 && world.isAirBlock(blockpos))
						{
							growVines(world, blockpos, BlockVine.WEST_FLAG);
						}

						blockpos = blockpos.add(0, 0, -1);

						if (random.nextInt(4) == 0 && world.isAirBlock(blockpos))
						{
							growVines(world, blockpos, BlockVine.SOUTH_FLAG);
						}

						blockpos = blockpos.add(0, 0, 1);

						if (random.nextInt(4) == 0 && world.isAirBlock(blockpos))
						{
							growVines(world, blockpos, BlockVine.NORTH_FLAG);
						}
					}
				}
			}
		}
	}

	private void growVines(World world, BlockPos pos, int meta)
	{
		func_175905_a(world, pos, Blocks.vine, meta);

		byte count = 4;

		while (true)
		{
			pos = pos.down();

			if (!world.isAirBlock(pos) || count <= 0)
			{
				return;
			}

			func_175905_a(world, pos, Blocks.vine, meta);

			--count;
		}
	}

	private void shiftTreeHeight(int height)
	{
		minTreeHeight += height;
		maxTreeHeight += height;
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
		BiomeGenBase biome = world.getBiomeGenForCoords(pos);

		if (biome.isHighHumidity())
		{
			shiftTreeHeight(random.nextInt(3));
		}
		else if (biome.getFloatTemperature(pos) >= 1.0F)
		{
			shiftTreeHeight(-(random.nextInt(2) + 3));
		}

		treeHeight = Math.min(random.nextInt(4) + minTreeHeight, maxTreeHeight);

		if (treeHeight != 0 && pos.getY() > 0 && pos.getY() + treeHeight + 1 <= 256 && isGeneratableTree(world, pos))
		{
			BlockPos down = pos.down();
			IBlockState state = world.getBlockState(down);
			Block block = state.getBlock();

			if (block.canSustainPlant(world, down, EnumFacing.UP, SugiBlocks.sugi_sapling) && pos.getY() < 256 - treeHeight - 1)
			{
				block.onPlantGrow(world, down, pos);

				setLeaves(world, random, pos, biome);
				setTree(world, random, pos, biome);

				if (!doBlockNotify && (biome instanceof BiomeGenSwamp || biome instanceof BiomeGenJungle))
				{
					setVines(world, random, pos);
				}

				if (Config.fallenSugiLeaves)
				{
					setFallenLeaves(world, random, pos);
				}

				return true;
			}
		}

		return false;
	}
}
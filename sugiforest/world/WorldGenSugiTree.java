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
		for (int Y = pos.getY(); Y <= pos.getY() + 1 + treeHeight; ++Y)
		{
			if (Y >= 0 && Y < 256)
			{
				int checkedRange = Y == pos.getY() ? 0 : Y >= pos.getY() + 1 + treeHeight - 2 ? 2 : 1;

				for (int X = pos.getX() - checkedRange; X <= pos.getX() + checkedRange; ++X)
				{
					for (int Z = pos.getZ() - checkedRange; Z <= pos.getZ() + checkedRange; ++Z)
					{
						if (!isReplaceable(world, new BlockPos(X, Y, Z)))
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

						for (int X = pos.getX() - 2; count < 3 && X <= pos.getX() + 2; ++X)
						{
							for (int Z = pos.getZ() - 2; count < 3 && Z <= pos.getZ() + 2; ++Z)
							{
								blockpos = new BlockPos(X, pos.getY() - 1, Z);
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

		for (int Y = pos.getY() - leavesHeight + treeHeight; Y <= pos.getY() + treeHeight; ++Y)
		{
			int leaveNum = Y - (pos.getY() + treeHeight);
			int leaveRange = 1 - leaveNum / (leavesHeight / 2 - 1);

			for (int X = pos.getX() - leaveRange; X <= pos.getX() + leaveRange; ++X)
			{
				for (int Z = pos.getZ() - leaveRange; Z <= pos.getZ() + leaveRange; ++Z)
				{
					if (Math.abs(X - pos.getX()) != leaveRange || Math.abs(Z - pos.getZ()) != leaveRange || random.nextInt(2) != 0 && leaveNum != 0)
					{
						BlockPos blockpos = new BlockPos(X, Y, Z);
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

	private void setVines(World world, Random random, BlockPos pos)
	{
		int vinesHeight = 12;

		if (treeHeight - vinesHeight >= vinesHeight - 3)
		{
			vinesHeight += 2;
		}

		for (int Y = pos.getY() - vinesHeight + treeHeight; Y <= pos.getY() + treeHeight; ++Y)
		{
			int vineNum = Y - (pos.getY() + treeHeight);
			int vineRange = 1 - vineNum / (vinesHeight / 2 - 1);

			for (int X = pos.getX() - vineRange; X <= pos.getX() + vineRange; ++X)
			{
				for (int Z = pos.getZ() - vineRange; Z <= pos.getZ() + vineRange; ++Z)
				{
					BlockPos blockpos = new BlockPos(X, Y, Z);

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

				return true;
			}
		}

		return false;
	}
}
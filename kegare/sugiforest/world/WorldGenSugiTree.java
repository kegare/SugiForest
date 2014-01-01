package kegare.sugiforest.world;

import kegare.sugiforest.block.SugiBlock;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenJungle;
import net.minecraft.world.biome.BiomeGenSwamp;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenSugiTree extends WorldGenerator
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
				int checkedRange = Y == y ? 0 : (Y >= y + 1 + treeHeight - 2 ? 2 : 1);

				for (int X = x - checkedRange; X <= x + checkedRange; ++X)
				{
					for (int Z = z - checkedRange; Z <= z + checkedRange; ++Z)
					{
						int blockId = world.getBlockId(X, Y, Z);
						Block block = Block.blocksList[blockId];

						if (blockId != 0 && block != null && !block.isLeaves(world, X, Y, Z))
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
			int blockId = world.getBlockId(x, y + woodHeight, z);
			Block block = Block.blocksList[blockId];

			if (blockId == 0 || block == null || block.isLeaves(world, x, y + woodHeight, z))
			{
				setBlock(world, x, y + woodHeight, z, SugiBlock.woodSugi.or(Block.wood).blockID);

				if (!doBlockNotify)
				{
					if (woodHeight > 0)
					{
						if (biome instanceof BiomeGenSwamp || biome instanceof BiomeGenJungle)
						{
							if (random.nextInt(3) > 0 && world.isAirBlock(x - 1, y + woodHeight, z))
							{
								setBlockAndMetadata(world, x - 1, y + woodHeight, z, Block.vine.blockID, 8);
							}

							if (random.nextInt(3) > 0 && world.isAirBlock(x + 1, y + woodHeight, z))
							{
								setBlockAndMetadata(world, x + 1, y + woodHeight, z, Block.vine.blockID, 2);
							}

							if (random.nextInt(3) > 0 && world.isAirBlock(x, y + woodHeight, z - 1))
							{
								setBlockAndMetadata(world, x, y + woodHeight, z - 1, Block.vine.blockID, 1);
							}

							if (random.nextInt(3) > 0 && world.isAirBlock(x, y + woodHeight, z + 1))
							{
								setBlockAndMetadata(world, x, y + woodHeight, z + 1, Block.vine.blockID, 4);
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
								blockId = world.getBlockId(X, y - 1, Z);

								if (world.isAirBlock(X, y, Z) && (blockId == Block.grass.blockID || blockId == Block.dirt.blockID))
								{
									block = random.nextInt(30) == 0 ? Block.mushroomRed : Block.mushroomBrown;

									if (block.canBlockStay(world, X, y, Z) && random.nextInt(6) == 0 && world.setBlock(X, y, Z, block.blockID, 0, 2))
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
					Block block = Block.blocksList[world.getBlockId(X, Y, Z)];

					if ((Math.abs(X - x) != leaveRange || Math.abs(Z - z) != leaveRange || (random.nextInt(2) != 0 && leaveNum != 0)) && (block == null || block.canBeReplacedByLeaves(world, X, Y, Z)) && random.nextInt(12) != 0)
					{
						setBlock(world, X, Y, Z, SugiBlock.leavesSugi.or(Block.leaves).blockID);
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
					Block block = Block.blocksList[world.getBlockId(X, Y, Z)];

					if (block != null && block.isLeaves(world, X, Y, Z))
					{
						if (random.nextInt(4) == 0 && world.getBlockId(X - 1, Y, Z) == 0)
						{
							growVines(world, X - 1, Y, Z, 8);
						}

						if (random.nextInt(4) == 0 && world.getBlockId(X + 1, Y, Z) == 0)
						{
							growVines(world, X + 1, Y, Z, 2);
						}

						if (random.nextInt(4) == 0 && world.getBlockId(X, Y, Z - 1) == 0)
						{
							growVines(world, X, Y, Z - 1, 1);
						}

						if (random.nextInt(4) == 0 && world.getBlockId(X, Y, Z + 1) == 0)
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
		setBlockAndMetadata(world, x, y, z, Block.vine.blockID, metadata);

		byte count = 4;

		while (true)
		{
			--y;

			if (world.getBlockId(x, y, z) != 0 || count <= 0)
			{
				return;
			}

			setBlockAndMetadata(world, x, y, z, Block.vine.blockID, metadata);

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
		else if (biome.getFloatTemperature() >= 1.0F)
		{
			shiftTreeHeight(-(random.nextInt(2) + 3));
		}

		treeHeight = Math.min(random.nextInt(4) + minTreeHeight, maxTreeHeight);

		if (treeHeight != 0 && y > 0 && y + treeHeight + 1 <= 256 && isGeneratableTree(world, x, y, z))
		{
			int blockId = world.getBlockId(x, y - 1, z);

			if ((blockId == Block.grass.blockID || blockId == Block.dirt.blockID) && y < 256 - treeHeight - 1)
			{
				setBlock(world, x, y - 1, z, Block.dirt.blockID);
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
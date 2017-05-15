package sugiforest.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.biome.BiomeSwamp;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.BiomeDictionary;
import sugiforest.block.BlockSugiFallenLeaves;
import sugiforest.block.BlockSugiLog;
import sugiforest.block.SugiBlocks;
import sugiforest.core.Config;

public class WorldGenSugiTree extends WorldGenAbstractTree
{
	public static final WorldGenSugiTree TREE_GEN = new WorldGenSugiTree(true);
	public static final WorldGenSugiTree NATURAL_GEN = new WorldGenSugiTree(false);

	private final boolean doBlockNotify;

	private int minTreeHeight;
	private int maxTreeHeight;

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

	private boolean isGeneratableTree(World world, BlockPos pos, int treeHeight)
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

	private void setTree(World world, Random random, BlockPos pos, Biome biome, int treeHeight)
	{
		BlockPos blockpos = pos.down();

		for (int woodHeight = 0; woodHeight < treeHeight; ++woodHeight)
		{
			blockpos = blockpos.up();

			IBlockState state = world.getBlockState(blockpos);
			Block block = state.getBlock();

			if (block.isAir(state, world, blockpos) || block.isLeaves(state, world, blockpos) || state.getMaterial() == Material.VINE)
			{
				IBlockState blockstate = SugiBlocks.SUGI_LOG.getDefaultState();

				if (random.nextInt(40) == 0)
				{
					blockstate = blockstate.withProperty(BlockSugiLog.VARIANT, BlockSugiLog.EnumType.MYST);
				}

				setBlockAndNotifyAdequately(world, blockpos, blockstate);

				if (!doBlockNotify)
				{
					if (woodHeight > 0)
					{
						if (biome instanceof BiomeSwamp || biome instanceof BiomeJungle)
						{
							if (random.nextInt(3) > 0 && world.isAirBlock(pos.add(-1, woodHeight, 0)))
							{
								setVineBlock(world, pos.add(-1, woodHeight, 0), BlockVine.EAST);
							}

							if (random.nextInt(3) > 0 && world.isAirBlock(pos.add(1, woodHeight, 0)))
							{
								setVineBlock(world, pos.add(1, woodHeight, 0), BlockVine.WEST);
							}

							if (random.nextInt(3) > 0 && world.isAirBlock(pos.add(0, woodHeight, -1)))
							{
								setVineBlock(world, pos.add(0, woodHeight, -1), BlockVine.SOUTH);
							}

							if (random.nextInt(3) > 0 && world.isAirBlock(pos.add(0, woodHeight, 1)))
							{
								setVineBlock(world, pos.add(0, woodHeight, 1), BlockVine.NORTH);
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
								BlockPos pos1 = new BlockPos(x, pos.getY() - 1, z);

								state = world.getBlockState(pos1);
								block = state.getBlock();

								if (block.canSustainPlant(state, world, pos1, EnumFacing.UP, Blocks.BROWN_MUSHROOM))
								{
									block = random.nextInt(30) == 0 ? Blocks.RED_MUSHROOM : Blocks.BROWN_MUSHROOM;
									block.onPlantGrow(state, world, pos1, pos1.up());

									if (random.nextInt(6) == 0 && world.getBlockState(pos1).getBlock() == block)
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

	private void setLeaves(World world, Random random, BlockPos pos, Biome biome, int treeHeight)
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

						if ((block.isAir(state, world, blockpos) || block.isLeaves(state, world, blockpos) || state.getMaterial() == Material.VINE) && random.nextInt(12) != 0)
						{
							setBlockAndNotifyAdequately(world, blockpos, SugiBlocks.SUGI_LEAVES.getDefaultState());
						}
					}
				}
			}
		}
	}

	private void setFallenLeaves(World world, Random random, BlockPos pos, int treeHeight)
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

				blockpos = blockpos.down();

				while (blockpos.getY() > 0 && world.isAirBlock(blockpos))
				{
					blockpos = blockpos.down();
				}

				blockpos = blockpos.up();

				if (SugiBlocks.SUGI_FALLEN_LEAVES.canPlaceBlockAt(world, blockpos) && random.nextInt(3) == 0)
				{
					setBlockAndNotifyAdequately(world, blockpos, SugiBlocks.SUGI_FALLEN_LEAVES.getDefaultState().withProperty(BlockSugiFallenLeaves.CHANCE, Boolean.valueOf(true)));
				}
				else
				{
					blockpos = blockpos.down();

					IBlockState state = world.getBlockState(blockpos);

					if (state.getBlock() instanceof BlockSugiFallenLeaves)
					{
						int layers = state.getValue(BlockSugiFallenLeaves.LAYERS).intValue();

						setBlockAndNotifyAdequately(world, blockpos, SugiBlocks.SUGI_FALLEN_LEAVES.getDefaultState().withProperty(BlockSugiFallenLeaves.LAYERS, (layers & 7) + 1).withProperty(BlockSugiFallenLeaves.CHANCE, Boolean.valueOf(true)));
					}
				}
			}
		}
	}

	private void setVines(World world, Random random, BlockPos pos, int treeHeight)
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
					IBlockState state = world.getBlockState(blockpos);

					if (state.getBlock().isLeaves(state, world, blockpos))
					{
						blockpos = blockpos.west();

						if (random.nextInt(4) == 0 && world.isAirBlock(blockpos))
						{
							growVines(world, blockpos, BlockVine.EAST);
						}

						blockpos = blockpos.east();

						if (random.nextInt(4) == 0 && world.isAirBlock(blockpos))
						{
							growVines(world, blockpos, BlockVine.WEST);
						}

						blockpos = blockpos.north();

						if (random.nextInt(4) == 0 && world.isAirBlock(blockpos))
						{
							growVines(world, blockpos, BlockVine.SOUTH);
						}

						blockpos = blockpos.south();

						if (random.nextInt(4) == 0 && world.isAirBlock(blockpos))
						{
							growVines(world, blockpos, BlockVine.NORTH);
						}
					}
				}
			}
		}
	}

	private void growVines(World world, BlockPos pos, PropertyBool prop)
	{
		setVineBlock(world, pos, prop);

		byte count = 4;

		while (true)
		{
			pos = pos.down();

			if (!world.isAirBlock(pos) || count <= 0)
			{
				return;
			}

			setVineBlock(world, pos, prop);

			--count;
		}
	}

	private void setVineBlock(World world, BlockPos pos, PropertyBool prop)
	{
		setBlockAndNotifyAdequately(world, pos, Blocks.VINE.getDefaultState().withProperty(prop, Boolean.valueOf(true)));
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
		Biome biome = world.getBiome(pos);
		int i = 0;

		if (biome.isHighHumidity())
		{
			i = random.nextInt(3);
		}
		else if (biome.getFloatTemperature(pos) >= 1.0F)
		{
			i = -(random.nextInt(2) + 3);
		}

		int treeHeight = Math.min(random.nextInt(4) + minTreeHeight + i, maxTreeHeight + i);

		if (treeHeight != 0 && pos.getY() > 0 && pos.getY() + treeHeight + 1 <= 256 && isGeneratableTree(world, pos, treeHeight))
		{
			BlockPos down = pos.down();
			IBlockState state = world.getBlockState(down);
			Block block = state.getBlock();

			if (block.canSustainPlant(state, world, down, EnumFacing.UP, SugiBlocks.SUGI_SAPLING) && pos.getY() < 256 - treeHeight - 1)
			{
				block.onPlantGrow(state, world, down, pos);

				setLeaves(world, random, pos, biome, treeHeight);
				setTree(world, random, pos, biome, treeHeight);

				if (!doBlockNotify && (BiomeDictionary.hasType(biome, BiomeDictionary.Type.JUNGLE) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.SWAMP)))
				{
					setVines(world, random, pos, treeHeight);
				}

				if (Config.fallenSugiLeaves)
				{
					setFallenLeaves(world, random, pos, treeHeight);
				}

				return true;
			}
		}

		return false;
	}
}
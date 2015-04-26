/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.world;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenRavine;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import sugiforest.world.gen.MapGenSugiForestCaves;

import com.google.common.base.Strings;

public class ChunkProviderSugiForest implements IChunkProvider
{
	private final World worldObj;
	private final boolean mapFeaturesEnabled;
	private final WorldType terrainType;
	private final Random rand;

	private final MapGenBase caveGenerator = new MapGenSugiForestCaves();
	private MapGenBase ravineGenerator = new MapGenRavine();
	private MapGenVillage villageGenerator = new MapGenVillage();

	private ChunkProviderSettings settings;
	private Block liquid = Blocks.water;

	private final double[] noiseField = new double[825];
	private final float[] parabolicField = new float[25];
	private double[] stoneNoise = new double[256];

	private final NoiseGeneratorOctaves noiseGen1;
	private final NoiseGeneratorOctaves noiseGen2;
	private final NoiseGeneratorOctaves noiseGen3;
	private final NoiseGeneratorPerlin noiseGen4;
	private final NoiseGeneratorOctaves noiseGen5;

	private BiomeGenBase[] biomesForGeneration;
	private double[] noise1;
	private double[] noise2;
	private double[] noise3;
	private double[] noise5;

	public ChunkProviderSugiForest(World world, long seed, boolean features, String options)
	{
		this.worldObj = world;
		this.mapFeaturesEnabled = features;
		this.terrainType = world.getWorldInfo().getTerrainType();
		this.rand = new Random(seed);
		this.ravineGenerator = TerrainGen.getModdedMapGen(ravineGenerator, InitMapGenEvent.EventType.RAVINE);
		this.villageGenerator = (MapGenVillage)TerrainGen.getModdedMapGen(villageGenerator, InitMapGenEvent.EventType.VILLAGE);
		this.noiseGen1 = new NoiseGeneratorOctaves(rand, 16);
		this.noiseGen2 = new NoiseGeneratorOctaves(rand, 16);
		this.noiseGen3 = new NoiseGeneratorOctaves(rand, 8);
		this.noiseGen4 = new NoiseGeneratorPerlin(rand, 4);
		this.noiseGen5 = new NoiseGeneratorOctaves(rand, 16);

		for (int j = -2; j <= 2; ++j)
		{
			for (int k = -2; k <= 2; ++k)
			{
				this.parabolicField[j + 2 + (k + 2) * 5] = 10.0F / MathHelper.sqrt_float(j * j + k * k + 0.2F);
			}
		}

		if (options != null)
		{
			this.settings = ChunkProviderSettings.Factory.func_177865_a(options).func_177864_b();
			this.liquid = settings.useLavaOceans ? Blocks.lava : Blocks.water;
		}
	}

	private void setBlocksInChunk(int chunkX, int chunkZ, ChunkPrimer primer)
	{
		biomesForGeneration = worldObj.getWorldChunkManager().getBiomesForGeneration(biomesForGeneration, chunkX * 4 - 2, chunkZ * 4 - 2, 10, 10);
		initializeNoiseField(chunkX * 4, 0, chunkZ * 4);

		for (int k = 0; k < 4; ++k)
		{
			int l = k * 5;
			int i1 = (k + 1) * 5;

			for (int j1 = 0; j1 < 4; ++j1)
			{
				int k1 = (l + j1) * 33;
				int l1 = (l + j1 + 1) * 33;
				int i2 = (i1 + j1) * 33;
				int j2 = (i1 + j1 + 1) * 33;

				for (int k2 = 0; k2 < 32; ++k2)
				{
					double d0 = 0.125D;
					double d1 = noiseField[k1 + k2];
					double d2 = noiseField[l1 + k2];
					double d3 = noiseField[i2 + k2];
					double d4 = noiseField[j2 + k2];
					double d5 = (noiseField[k1 + k2 + 1] - d1) * d0;
					double d6 = (noiseField[l1 + k2 + 1] - d2) * d0;
					double d7 = (noiseField[i2 + k2 + 1] - d3) * d0;
					double d8 = (noiseField[j2 + k2 + 1] - d4) * d0;

					for (int l2 = 0; l2 < 8; ++l2)
					{
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i3 = 0; i3 < 4; ++i3)
						{
							double d14 = 0.25D;
							double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;

							for (int j3 = 0; j3 < 4; ++j3)
							{
								if ((d15 += d16) > 0.0D)
								{
									primer.setBlockState(k * 4 + i3, k2 * 8 + l2, j1 * 4 + j3, Blocks.stone.getDefaultState());
								}
								else if (k2 * 8 + l2 < settings.seaLevel)
								{
									primer.setBlockState(k * 4 + i3, k2 * 8 + l2, j1 * 4 + j3, liquid.getDefaultState());
								}
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	private void initializeNoiseField(int posX, int posY, int posZ)
	{
		noise5 = noiseGen5.generateNoiseOctaves(noise5, posX, posZ, 5, 5, settings.depthNoiseScaleX, settings.depthNoiseScaleZ, settings.depthNoiseScaleExponent);
		float f = settings.coordinateScale;
		float f1 = settings.heightScale;
		noise1 = noiseGen3.generateNoiseOctaves(noise1, posX, posY, posZ, 5, 33, 5, f / settings.mainNoiseScaleX, f1 / settings.mainNoiseScaleY, f / settings.mainNoiseScaleZ);
		noise2 = noiseGen1.generateNoiseOctaves(noise2, posX, posY, posZ, 5, 33, 5, f, f1, f);
		noise3 = noiseGen2.generateNoiseOctaves(noise3, posX, posY, posZ, 5, 33, 5, f, f1, f);
		int i = 0;
		int j = 0;

		for (int j1 = 0; j1 < 5; ++j1)
		{
			for (int k1 = 0; k1 < 5; ++k1)
			{
				float f2 = 0.0F;
				float f3 = 0.0F;
				float f4 = 0.0F;
				byte b0 = 2;
				BiomeGenBase biome = biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

				for (int l1 = -b0; l1 <= b0; ++l1)
				{
					for (int i2 = -b0; i2 <= b0; ++i2)
					{
						BiomeGenBase biome1 = biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
						float f5 = settings.biomeDepthOffSet + biome1.minHeight * settings.biomeDepthWeight;
						float f6 = settings.biomeScaleOffset + biome1.maxHeight * settings.biomeScaleWeight;

						if (terrainType == WorldType.AMPLIFIED && f5 > 0.0F)
						{
							f5 = 1.0F + f5 * 2.0F;
							f6 = 1.0F + f6 * 4.0F;
						}

						float f7 = parabolicField[l1 + 2 + (i2 + 2) * 5] / (f5 + 2.0F);

						if (biome1.minHeight > biome.minHeight)
						{
							f7 /= 2.0F;
						}

						f2 += f6 * f7;
						f3 += f5 * f7;
						f4 += f7;
					}
				}

				f2 /= f4;
				f3 /= f4;
				f2 = f2 * 0.9F + 0.1F;
				f3 = (f3 * 4.0F - 1.0F) / 8.0F;
				double d7 = noise5[j] / 8000.0D;

				if (d7 < 0.0D)
				{
					d7 = -d7 * 0.3D;
				}

				d7 = d7 * 3.0D - 2.0D;

				if (d7 < 0.0D)
				{
					d7 /= 2.0D;

					if (d7 < -1.0D)
					{
						d7 = -1.0D;
					}

					d7 /= 1.4D;
					d7 /= 2.0D;
				}
				else
				{
					if (d7 > 1.0D)
					{
						d7 = 1.0D;
					}

					d7 /= 8.0D;
				}

				++j;
				double d8 = f3;
				double d9 = f2;
				d8 += d7 * 0.2D;
				d8 = d8 * settings.baseSize / 8.0D;
				double d0 = settings.baseSize + d8 * 4.0D;

				for (int j2 = 0; j2 < 33; ++j2)
				{
					double d1 = (j2 - d0) * settings.stretchY * 128.0D / 256.0D / d9;

					if (d1 < 0.0D)
					{
						d1 *= 4.0D;
					}

					double d2 = noise2[i] / settings.lowerLimitScale;
					double d3 = noise3[i] / settings.upperLimitScale;
					double d4 = (noise1[i] / 10.0D + 1.0D) / 2.0D;
					double d5 = MathHelper.denormalizeClamp(d2, d3, d4) - d1;

					if (j2 > 29)
					{
						double d6 = (j2 - 29) / 3.0F;
						d5 = d5 * (1.0D - d6) + -10.0D * d6;
					}

					noiseField[i] = d5;
					++i;
				}
			}
		}
	}

	private void replaceBlocksForBiome(int chunkX, int chunkZ, ChunkPrimer primer, BiomeGenBase[] biomes)
	{
		ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, chunkX, chunkZ, primer, worldObj);
		MinecraftForge.EVENT_BUS.post(event);

		if (event.getResult() == Result.DENY)
		{
			return;
		}

		double noise = 0.03125D;
		stoneNoise = noiseGen4.func_151599_a(stoneNoise, chunkX * 16, chunkZ * 16, 16, 16, noise * 2.0D, noise * 2.0D, 1.0D);

		for (int i = 0; i < 16; ++i)
		{
			for (int j = 0; j < 16; ++j)
			{
				biomes[j + i * 16].genTerrainBlocks(worldObj, rand, primer, chunkX * 16 + i, chunkZ * 16 + j, stoneNoise[j + i * 16]);
			}
		}
	}

	@Override
	public Chunk provideChunk(BlockPos pos)
	{
		return provideChunk(pos.getX() >> 4, pos.getZ() >> 4);
	}

	@Override
	public Chunk provideChunk(int chunkX, int chunkZ)
	{
		rand.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);

		ChunkPrimer primer = new ChunkPrimer();
		setBlocksInChunk(chunkX, chunkZ, primer);
		biomesForGeneration = worldObj.getWorldChunkManager().loadBlockGeneratorData(biomesForGeneration, chunkX * 16, chunkZ * 16, 16, 16);
		replaceBlocksForBiome(chunkX, chunkZ, primer, biomesForGeneration);

		if (settings.useCaves)
		{
			caveGenerator.func_175792_a(this, worldObj, chunkX, chunkZ, primer);
		}

		if (settings.useRavines)
		{
			ravineGenerator.func_175792_a(this, worldObj, chunkX, chunkZ, primer);
		}

		if (settings.useVillages && mapFeaturesEnabled)
		{
			villageGenerator.func_175792_a(this, worldObj, chunkX, chunkZ, primer);
		}

		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				for (int y = 10; y < 100; ++y)
				{
					if (primer.getBlockState(x, y, z).getBlock() == Blocks.stone && primer.getBlockState(x, y + 1, z).getBlock() == Blocks.air)
					{
						primer.setBlockState(x, y, z, Blocks.grass.getDefaultState());

						if (primer.getBlockState(x, y - 1, z).getBlock().getMaterial().isSolid())
						{
							primer.setBlockState(x, y - 1, z, Blocks.dirt.getDefaultState());
						}
					}
				}
			}
		}

		Chunk chunk = new Chunk(worldObj, primer, chunkX, chunkZ);
		byte[] biomes = chunk.getBiomeArray();

		for (int k = 0; k < biomes.length; ++k)
		{
			biomes[k] = (byte)biomesForGeneration[k].biomeID;
		}

		chunk.generateSkylightMap();

		return chunk;
	}

	@Override
	public boolean chunkExists(int chunkX, int chunkZ)
	{
		return true;
	}

	@Override
	public void populate(IChunkProvider provider, int chunkX, int chunkZ)
	{
		BlockFalling.fallInstantly = true;

		int x = chunkX * 16;
		int z = chunkZ * 16;
		BlockPos blockpos = new BlockPos(x, 0, z);
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(blockpos.add(16, 0, 16));
		rand.setSeed(worldObj.getSeed());
		long xSeed = rand.nextLong() / 2L * 2L + 1L;
		long zSeed = rand.nextLong() / 2L * 2L + 1L;
		rand.setSeed(chunkX * xSeed + chunkZ * zSeed ^ worldObj.getSeed());
		boolean flag = false;
		ChunkCoordIntPair coord = new ChunkCoordIntPair(chunkX, chunkZ);

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(provider, worldObj, rand, chunkX, chunkZ, flag));

		if (settings.useVillages && mapFeaturesEnabled)
		{
			flag = villageGenerator.func_175794_a(worldObj, rand, coord);
		}

		int i;
		int j;
		int k;

		if (settings.useWaterLakes && !flag && rand.nextInt(settings.waterLakeChance) == 0 && TerrainGen.populate(provider, worldObj, rand, chunkX, chunkZ, flag, EventType.LAKE))
		{
			i = rand.nextInt(16) + 8;
			j = rand.nextInt(256);
			k = rand.nextInt(16) + 8;
			new WorldGenLakes(Blocks.water).generate(worldObj, rand, blockpos.add(i, j, k));
		}

		if (TerrainGen.populate(provider, worldObj, rand, chunkX, chunkZ, flag, EventType.LAVA) && !flag && rand.nextInt(settings.lavaLakeChance / 10) == 0 && settings.useLavaLakes)
		{
			i = rand.nextInt(16) + 8;
			j = rand.nextInt(rand.nextInt(248) + 8);
			k = rand.nextInt(16) + 8;

			if (j < 63 || rand.nextInt(settings.lavaLakeChance / 8) == 0)
			{
				new WorldGenLakes(Blocks.lava).generate(worldObj, rand, blockpos.add(i, j, k));
			}
		}

		if (settings.useDungeons)
		{
			boolean doGen = TerrainGen.populate(provider, worldObj, rand, chunkX, chunkZ, flag, EventType.DUNGEON);

			for (i = 0; doGen && i < settings.dungeonChance; ++i)
			{
				j = rand.nextInt(16) + 8;
				k = rand.nextInt(256);
				int j2 = rand.nextInt(16) + 8;
				new WorldGenDungeons().generate(worldObj, rand, blockpos.add(j, k, j2));
			}
		}

		biome.decorate(worldObj, rand, new BlockPos(x, 0, z));

		if (TerrainGen.populate(provider, worldObj, rand, chunkX, chunkZ, flag, EventType.ANIMALS))
		{
			SpawnerAnimals.performWorldGenSpawning(worldObj, biome, x + 8, z + 8, 16, 16, rand);
		}

		blockpos = blockpos.add(8, 0, 8);

		boolean doGen = TerrainGen.populate(provider, worldObj, rand, chunkX, chunkZ, flag, EventType.ICE);

		for (i = 0; doGen && i < 16; ++i)
		{
			for (j = 0; j < 16; ++j)
			{
				BlockPos blockpos1 = worldObj.getPrecipitationHeight(blockpos.add(i, 0, j));
				BlockPos blockpos2 = blockpos1.down();

				if (worldObj.func_175675_v(blockpos2))
				{
					worldObj.setBlockState(blockpos2, Blocks.ice.getDefaultState(), 2);
				}

				if (worldObj.canSnowAt(blockpos1, true))
				{
					worldObj.setBlockState(blockpos1, Blocks.snow_layer.getDefaultState(), 2);
				}
			}
		}

		MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(provider, worldObj, rand, chunkX, chunkZ, flag));

		BlockFalling.fallInstantly = false;
	}

	@Override
	public boolean func_177460_a(IChunkProvider provider, Chunk chunk, int x, int z)
	{
		return false;
	}

	@Override
	public boolean saveChunks(boolean flag, IProgressUpdate progress)
	{
		return true;
	}

	@Override
	public void saveExtraData() {}

	@Override
	public boolean unloadQueuedChunks()
	{
		return false;
	}

	@Override
	public boolean canSave()
	{
		return true;
	}

	@Override
	public String makeString()
	{
		return "SugiForestRandomLevelSource";
	}

	@Override
	public List func_177458_a(EnumCreatureType type, BlockPos pos)
	{
		return worldObj.getBiomeGenForCoords(pos).getSpawnableList(type);
	}

	@Override
	public BlockPos getStrongholdGen(World world, String name, BlockPos pos)
	{
		if (Strings.isNullOrEmpty(name))
		{
			return null;
		}

		switch (name)
		{
			case "Village":
				return villageGenerator.getClosestStrongholdPos(world, pos);
			default:
				return null;
		}
	}

	@Override
	public int getLoadedChunkCount()
	{
		return 0;
	}

	@Override
	public void recreateStructures(Chunk chunk, int x, int z)
	{
		if (settings.useVillages && mapFeaturesEnabled)
		{
			villageGenerator.func_175792_a(this, worldObj, x, z, (ChunkPrimer)null);
		}
	}
}
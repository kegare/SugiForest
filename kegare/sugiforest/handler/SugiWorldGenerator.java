package kegare.sugiforest.handler;

import cpw.mods.fml.common.IWorldGenerator;
import kegare.sugiforest.core.Config;
import kegare.sugiforest.world.WorldGenSugiTree;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenHills;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

public class SugiWorldGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		if (world.provider instanceof WorldProviderSurface)
		{
			generateSurface(world, random, chunkX << 4, chunkZ << 4);
		}
	}

	private void generateSurface(World world, Random random, int chunkX, int chunkZ)
	{
		BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(chunkX, chunkZ);
		WorldGenerator worldGenSugiTree = new WorldGenSugiTree(false);

		if (Config.sugiTreesOnHills && biome instanceof BiomeGenHills)
		{
			if (random.nextInt(20) == 0)
			{
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;
				int y = world.getHeightValue(x, z);

				if (y > 63 && y <= 96 && TerrainGen.decorate(world, random, chunkX, chunkZ, EventType.TREE))
				{
					worldGenSugiTree.setScale(1.0D, 1.0D, 1.0D);
					worldGenSugiTree.generate(world, random, x, y, z);
				}
			}
		}
	}
}
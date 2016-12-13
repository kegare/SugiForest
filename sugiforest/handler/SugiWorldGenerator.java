package sugiforest.handler;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.IWorldGenerator;
import sugiforest.core.Config;
import sugiforest.world.BiomeSugiForest;
import sugiforest.world.gen.WorldGenSugiTree;

public class SugiWorldGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		BlockPos pos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
		Biome biome = world.getBiome(pos);

		if (biome instanceof BiomeSugiForest)
		{
			return;
		}

		if (Config.sugiOnHills > 0 && BiomeDictionary.hasType(biome, Type.HILLS))
		{
			for (int i = 0; i < Config.sugiOnHills; ++i)
			{
				if (random.nextInt(10) == 0)
				{
					BlockPos blockpos = world.getHeight(pos.add(random.nextInt(16), 0, random.nextInt(16)));

					if (TerrainGen.decorate(world, random, blockpos, EventType.TREE))
					{
						WorldGenSugiTree.NATURAL_GEN.generate(world, random, blockpos);
					}
				}
			}
		}
	}
}
/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.handler;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType;
import net.minecraftforge.event.terraingen.TerrainGen;

import com.kegare.sugiforest.core.Config;
import com.kegare.sugiforest.core.SugiForest;
import com.kegare.sugiforest.world.WorldGenSugiTree;

import cpw.mods.fml.common.IWorldGenerator;

public class SugiWorldGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		BiomeGenBase biome = world.getWorldChunkManager().getBiomeGenAt(chunkX, chunkZ);

		if (biome == SugiForest.sugiForest)
		{
			return;
		}

		if (Config.sugiOnHills > 0 && BiomeDictionary.isBiomeOfType(biome, Type.HILLS))
		{
			WorldGenerator worldGenSugiTree = new WorldGenSugiTree(false);

			for (int i = 0; i < Config.sugiOnHills; ++i)
			{
				int x = chunkX + random.nextInt(16) + 8;
				int z = chunkZ + random.nextInt(16) + 8;
				int y = world.getChunkFromChunkCoords(chunkX, chunkZ).getHeightValue(x & 15, z & 15);

				if (TerrainGen.decorate(world, random, chunkX, chunkZ, EventType.TREE))
				{
					worldGenSugiTree.setScale(1.0D, 1.0D, 1.0D);
					worldGenSugiTree.generate(world, random, x, y, z);
				}
			}
		}
	}
}
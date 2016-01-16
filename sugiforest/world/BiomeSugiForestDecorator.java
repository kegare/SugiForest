/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.world;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.*;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

public class BiomeSugiForestDecorator extends BiomeDecorator
{
	@Override
	protected void genDecorations(BiomeGenBase biome)
	{
		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(currentWorld, randomGenerator, field_180294_c));
		generateOres();

		int i;
		int j;
		int k;

		boolean doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, SAND);
		for (i = 0; doGen && i < sandPerChunk2; ++i)
		{
			j = randomGenerator.nextInt(16) + 8;
			k = randomGenerator.nextInt(16) + 8;
			sandGen.generate(currentWorld, randomGenerator, currentWorld.getTopSolidOrLiquidBlock(field_180294_c.add(j, 0, k)));
		}

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, CLAY);
		for (i = 0; doGen && i < clayPerChunk; ++i)
		{
			j = randomGenerator.nextInt(16) + 8;
			k = randomGenerator.nextInt(16) + 8;
			clayGen.generate(currentWorld, randomGenerator, currentWorld.getTopSolidOrLiquidBlock(field_180294_c.add(j, 0, k)));
		}

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, SAND_PASS2);
		for (i = 0; doGen && i < sandPerChunk; ++i)
		{
			j = randomGenerator.nextInt(16) + 8;
			k = randomGenerator.nextInt(16) + 8;
			gravelAsSandGen.generate(currentWorld, randomGenerator, currentWorld.getTopSolidOrLiquidBlock(field_180294_c.add(j, 0, k)));
		}

		i = treesPerChunk;

		if (randomGenerator.nextInt(10) == 0)
		{
			++i;
		}

		int l;
		BlockPos blockpos;

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, TREE);
		for (j = 0; doGen && j < i; ++j)
		{
			k = randomGenerator.nextInt(16) + 8;
			l = randomGenerator.nextInt(16) + 8;
			WorldGenAbstractTree treeGen = biome.genBigTreeChance(randomGenerator);
			treeGen.func_175904_e();
			blockpos = currentWorld.getHeight(field_180294_c.add(k, 0, l));

			if (treeGen.generate(currentWorld, randomGenerator, blockpos))
			{
				treeGen.func_180711_a(currentWorld, randomGenerator, blockpos);
			}
		}

		int m;

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, FLOWERS);
		for (j = 0; doGen && j < flowersPerChunk; ++j)
		{
			k = randomGenerator.nextInt(16) + 8;
			l = randomGenerator.nextInt(16) + 8;
			m = nextInt(currentWorld.getHeight(field_180294_c.add(k, 0, l)).getY() + 32);
			blockpos = field_180294_c.add(k, m, l);
			BlockFlower.EnumFlowerType type = biome.pickRandomFlower(randomGenerator, blockpos);
			BlockFlower blockflower = type.getBlockType().getBlock();

			if (blockflower.getMaterial() != Material.air)
			{
				yellowFlowerGen.setGeneratedBlock(blockflower, type);
				yellowFlowerGen.generate(currentWorld, randomGenerator, blockpos);
			}
		}

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, GRASS);
		for (j = 0; doGen && j < grassPerChunk; ++j)
		{
			k = randomGenerator.nextInt(16) + 8;
			l = randomGenerator.nextInt(16) + 8;
			m = nextInt(currentWorld.getHeight(field_180294_c.add(k, 0, l)).getY() * 2);
			biome.getRandomWorldGenForGrass(randomGenerator).generate(currentWorld, randomGenerator, field_180294_c.add(k, m, l));
		}

		j = 0;

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, LILYPAD);
		while (doGen && j < waterlilyPerChunk)
		{
			k = randomGenerator.nextInt(16) + 8;
			l = randomGenerator.nextInt(16) + 8;
			m = nextInt(currentWorld.getHeight(field_180294_c.add(k, 0, l)).getY() * 2);
			blockpos = field_180294_c.add(k, m, l);

			while (true)
			{
				if (blockpos.getY() > 0)
				{
					BlockPos blockpos3 = blockpos.down();

					if (currentWorld.isAirBlock(blockpos3))
					{
						blockpos = blockpos3;
						continue;
					}
				}

				waterlilyGen.generate(currentWorld, randomGenerator, blockpos);
				++j;
				break;
			}
		}

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, SHROOM);
		for (j = 0; doGen && j < mushroomsPerChunk; ++j)
		{
			if (randomGenerator.nextInt(4) == 0)
			{
				k = randomGenerator.nextInt(16) + 8;
				l = randomGenerator.nextInt(16) + 8;
				BlockPos blockpos2 = currentWorld.getHeight(field_180294_c.add(k, 0, l));
				mushroomBrownGen.generate(currentWorld, randomGenerator, blockpos2);
			}

			if (randomGenerator.nextInt(8) == 0)
			{
				k = randomGenerator.nextInt(16) + 8;
				l = randomGenerator.nextInt(16) + 8;
				m = nextInt(currentWorld.getHeight(field_180294_c.add(k, 0, l)).getY() * 2);
				blockpos = field_180294_c.add(k, m, l);
				mushroomRedGen.generate(currentWorld, randomGenerator, blockpos);
			}
		}

		if (doGen && randomGenerator.nextInt(4) == 0)
		{
			j = randomGenerator.nextInt(16) + 8;
			k = randomGenerator.nextInt(16) + 8;
			l = nextInt(currentWorld.getHeight(field_180294_c.add(j, 0, k)).getY() * 2);
			mushroomBrownGen.generate(currentWorld, randomGenerator, field_180294_c.add(j, l, k));
		}

		if (doGen && randomGenerator.nextInt(8) == 0)
		{
			j = randomGenerator.nextInt(16) + 8;
			k = randomGenerator.nextInt(16) + 8;
			l = nextInt(currentWorld.getHeight(field_180294_c.add(j, 0, k)).getY() * 2);
			mushroomRedGen.generate(currentWorld, randomGenerator, field_180294_c.add(j, l, k));
		}

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, REED);
		for (j = 0; doGen && j < reedsPerChunk; ++j)
		{
			k = randomGenerator.nextInt(16) + 8;
			l = randomGenerator.nextInt(16) + 8;
			m = nextInt(currentWorld.getHeight(field_180294_c.add(k, 0, l)).getY() * 2);
			reedGen.generate(currentWorld, randomGenerator, field_180294_c.add(k, m, l));
		}

		for (j = 0; doGen && j < 10; ++j)
		{
			k = randomGenerator.nextInt(16) + 8;
			l = randomGenerator.nextInt(16) + 8;
			m = nextInt(currentWorld.getHeight(field_180294_c.add(k, 0, l)).getY() * 2);
			reedGen.generate(currentWorld, randomGenerator, field_180294_c.add(k, m, l));
		}

		doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, PUMPKIN);
		if (doGen && randomGenerator.nextInt(32) == 0)
		{
			j = randomGenerator.nextInt(16) + 8;
			k = randomGenerator.nextInt(16) + 8;
			l = nextInt(currentWorld.getHeight(field_180294_c.add(j, 0, k)).getY() * 2);
			new WorldGenPumpkin().generate(currentWorld, randomGenerator, field_180294_c.add(j, l, k));
		}

		if (generateLakes)
		{
			BlockPos blockpos1;

			doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, LAKE_WATER);
			for (j = 0; doGen && j < 50; ++j)
			{
				blockpos1 = field_180294_c.add(randomGenerator.nextInt(16) + 8, randomGenerator.nextInt(randomGenerator.nextInt(248) + 8), randomGenerator.nextInt(16) + 8);
				new WorldGenLiquids(Blocks.flowing_water).generate(currentWorld, randomGenerator, blockpos1);
			}

			doGen = TerrainGen.decorate(currentWorld, randomGenerator, field_180294_c, LAKE_LAVA);
			for (j = 0; doGen && j < 20; ++j)
			{
				blockpos1 = field_180294_c.add(randomGenerator.nextInt(16) + 8, randomGenerator.nextInt(randomGenerator.nextInt(randomGenerator.nextInt(240) + 8) + 8), randomGenerator.nextInt(16) + 8);
				new WorldGenLiquids(Blocks.flowing_lava).generate(currentWorld, randomGenerator, blockpos1);
			}
		}

		MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(currentWorld, randomGenerator, field_180294_c));
	}

	private int nextInt(int i)
	{
		if (i <= 1)
		{
			return 0;
		}

		return randomGenerator.nextInt(i);
	}
}
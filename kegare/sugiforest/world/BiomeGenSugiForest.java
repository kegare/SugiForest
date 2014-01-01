package kegare.sugiforest.world;

import kegare.sugiforest.core.Config;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BiomeGenSugiForest extends BiomeGenBase
{
	public BiomeGenSugiForest(int biomeID)
	{
		super(biomeID);
		this.setBiomeName("Sugi Forest");
		this.setTemperatureRainfall(0.25F, 0.9F);
		this.setMinMaxHeight(0.05F, 0.65F);
		this.func_76733_a(5159473);
		this.setColor(6726755);
		this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));
		this.theBiomeDecorator.treesPerChunk = Config.sugiTreesOnSugiForest;
		this.theBiomeDecorator.grassPerChunk = 6;
		this.theBiomeDecorator.mushroomsPerChunk = 2;
		this.theBiomeDecorator.reedsPerChunk = 5;
		this.theBiomeDecorator.clayPerChunk = 3;
	}

	@Override
	public WorldGenerator getRandomWorldGenForTrees(Random random)
	{
		return new WorldGenSugiTree(false);
	}

	@Override
	public float getSpawningChance()
	{
		return 0.15F;
	}
}
package sugiforest.world;

import java.util.Random;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import sugiforest.world.gen.WorldGenSugiTree;

public class BiomeSugiForest extends Biome
{
	public BiomeSugiForest()
	{
		super(new BiomeProperties("Sugi Forest").setTemperature(0.25F).setRainfall(0.9F).setBaseHeight(0.05F).setHeightVariation(0.65F));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));
		this.decorator.treesPerChunk = 16;
		this.decorator.grassPerChunk = 12;
		this.decorator.mushroomsPerChunk = 10;
		this.decorator.reedsPerChunk = 5;
		this.decorator.clayPerChunk = 6;
	}

	@Override
	public BiomeDecorator createBiomeDecorator()
	{
		return new BiomeSugiForestDecorator();
	}

	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand)
	{
		return WorldGenSugiTree.NATURAL_GEN;
	}

	@Override
	public float getSpawningChance()
	{
		return 0.075F;
	}
}
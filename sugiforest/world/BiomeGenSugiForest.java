package sugiforest.world;

import java.util.Random;

import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import sugiforest.world.gen.WorldGenSugiTree;

public class BiomeGenSugiForest extends BiomeGenBase
{
	public BiomeGenSugiForest()
	{
		super(new BiomeProperties("Sugi Forest").setTemperature(0.25F).setRainfall(0.9F).setBaseHeight(0.05F).setHeightVariation(0.65F));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 4, 4));
		this.theBiomeDecorator.treesPerChunk = 16;
		this.theBiomeDecorator.grassPerChunk = 12;
		this.theBiomeDecorator.mushroomsPerChunk = 10;
		this.theBiomeDecorator.reedsPerChunk = 5;
		this.theBiomeDecorator.clayPerChunk = 6;
	}

	@Override
	public BiomeDecorator createBiomeDecorator()
	{
		return new BiomeSugiForestDecorator();
	}

	@Override
	public WorldGenAbstractTree genBigTreeChance(Random rand)
	{
		return WorldGenSugiTree.naturalGen;
	}

	@Override
	public float getSpawningChance()
	{
		return 0.075F;
	}
}
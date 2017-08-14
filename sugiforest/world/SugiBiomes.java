package sugiforest.world;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.registries.IForgeRegistry;
import sugiforest.core.Config;

public class SugiBiomes
{
	public static final BiomeSugiForest SUGI_FOREST = new BiomeSugiForest();

	public static void registerBiomes(IForgeRegistry<Biome> registry)
	{
		registry.register(SUGI_FOREST.setRegistryName("sugi_forest"));
	}

	public static void registerBiomeTypes()
	{
		BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(SUGI_FOREST, Config.biomeWeight));
		BiomeManager.addSpawnBiome(SUGI_FOREST);
		BiomeManager.addStrongholdBiome(SUGI_FOREST);

		BiomeDictionary.addTypes(SUGI_FOREST, Type.FOREST, Type.HILLS, Type.WET);
	}
}
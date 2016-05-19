package sugiforest.world;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import sugiforest.core.Config;

public class SugiBiomes
{
	public static final BiomeSugiForest sugiForest = new BiomeSugiForest();

	public static void registerBiomes()
	{
		Biome.registerBiome(Config.biomeId, "sugi_forest", sugiForest);

		BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(sugiForest, Config.biomeWeight));
		BiomeManager.addSpawnBiome(sugiForest);
		BiomeManager.addStrongholdBiome(sugiForest);

		Biome.EXPLORATION_BIOMES_LIST.add(sugiForest);

		BiomeDictionary.registerBiomeType(sugiForest, Type.FOREST, Type.HILLS);
	}
}
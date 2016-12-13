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
	public static final BiomeSugiForest SUGIFOREST = new BiomeSugiForest();

	public static void registerBiomes()
	{
		Biome.registerBiome(Config.biomeId, "sugi_forest", SUGIFOREST);

		BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(SUGIFOREST, Config.biomeWeight));
		BiomeManager.addSpawnBiome(SUGIFOREST);
		BiomeManager.addStrongholdBiome(SUGIFOREST);

		Biome.EXPLORATION_BIOMES_LIST.add(SUGIFOREST);

		BiomeDictionary.addTypes(SUGIFOREST, Type.FOREST, Type.HILLS, Type.WET);
	}
}
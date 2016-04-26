package sugiforest.api;

import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;

public interface ISugiForestAPI
{
	public BiomeGenBase getBiome();

	public void addFallenSeed(ItemStack stack, int weight);
}
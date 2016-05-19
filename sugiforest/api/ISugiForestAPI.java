package sugiforest.api;

import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;

public interface ISugiForestAPI
{
	public Biome getBiome();

	public void addFallenSeed(ItemStack stack, int weight);
}
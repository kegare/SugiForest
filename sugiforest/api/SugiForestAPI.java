package sugiforest.api;

import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;

public final class SugiForestAPI
{
	public static ISugiForestAPI apiHandler;

	/**
	 * Returns the Sugi Forest biome.
	 */
	public static Biome getBiome()
	{
		if (apiHandler != null)
		{
			return apiHandler.getBiome();
		}

		return null;
	}

	/**
	 * Adds a fallen seed for Sugi Fallen Leaves.
	 * @param stack The seed
	 * @param weight The weight
	 */
	public static void addFallenSeed(ItemStack stack, int weight)
	{
		if (apiHandler != null)
		{
			apiHandler.addFallenSeed(stack, weight);
		}
	}
}
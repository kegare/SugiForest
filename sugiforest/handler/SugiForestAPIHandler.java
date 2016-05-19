package sugiforest.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import sugiforest.api.ISugiForestAPI;
import sugiforest.block.BlockSugiFallenLeaves;
import sugiforest.world.SugiBiomes;

public class SugiForestAPIHandler implements ISugiForestAPI
{
	@Override
	public Biome getBiome()
	{
		return SugiBiomes.sugiForest;
	}

	@Override
	public void addFallenSeed(ItemStack stack, int weight)
	{
		BlockSugiFallenLeaves.addFallenSeed(stack, weight);
	}
}
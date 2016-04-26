package sugiforest.handler;

import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import sugiforest.api.ISugiForestAPI;
import sugiforest.block.BlockSugiFallenLeaves;
import sugiforest.world.SugiBiomes;

public class SugiForestAPIHandler implements ISugiForestAPI
{
	@Override
	public BiomeGenBase getBiome()
	{
		return SugiBiomes.sugiForest;
	}

	@Override
	public void addFallenSeed(ItemStack stack, int weight)
	{
		BlockSugiFallenLeaves.addFallenSeed(stack, weight);
	}
}
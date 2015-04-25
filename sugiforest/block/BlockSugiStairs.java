package sugiforest.block;

import net.minecraft.block.BlockStairs;
import sugiforest.core.SugiForest;

public class BlockSugiStairs extends BlockStairs
{
	public BlockSugiStairs()
	{
		super(SugiBlocks.sugi_planks.getDefaultState());
		this.setUnlocalizedName("stairs.sugi");
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(SugiForest.tabSugiForest);
		this.useNeighborBrightness = true;
	}
}
package sugiforest.block;

import net.minecraft.block.BlockStairs;
import sugiforest.core.SugiForest;

public class BlockSugiStairs extends BlockStairs
{
	public BlockSugiStairs()
	{
		super(SugiBlocks.SUGI_PLANKS.getDefaultState());
		this.setUnlocalizedName("stairs.sugi");
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(SugiForest.TAB_SUGI);
		this.useNeighborBrightness = true;
	}
}
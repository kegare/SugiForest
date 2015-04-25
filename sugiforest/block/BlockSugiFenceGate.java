package sugiforest.block;

import net.minecraft.block.BlockFenceGate;
import sugiforest.core.SugiForest;

public class BlockSugiFenceGate extends BlockFenceGate
{
	public BlockSugiFenceGate()
	{
		super();
		this.setUnlocalizedName("fenceGate.sugi");
		this.setHardness(1.75F);
		this.setResistance(4.75F);
		this.setStepSound(soundTypeWood);
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(SugiForest.tabSugiForest);
	}
}
package sugiforest.block;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import sugiforest.core.SugiForest;

public class BlockSugiWoodSlab extends BlockSlab
{
	public BlockSugiWoodSlab()
	{
		super(Material.WOOD);
		this.setUnlocalizedName("woodSlab.sugi");
		this.setHardness(1.75F);
		this.setResistance(4.75F);
		this.setSoundType(SoundType.WOOD);
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(SugiForest.TAB_SUGI);
		this.setDefaultState(blockState.getBaseState().withProperty(HALF, EnumBlockHalf.BOTTOM));
		this.useNeighborBrightness = true;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, HALF);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(HALF, meta == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(HALF) == EnumBlockHalf.TOP ? 1 : 0;
	}

	@Override
	public String getUnlocalizedName(int meta)
	{
		return getUnlocalizedName();
	}

	@Override
	public boolean isDouble()
	{
		return false;
	}

	@Override
	public IProperty<?> getVariantProperty()
	{
		return null;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack)
	{
		return null;
	}
}
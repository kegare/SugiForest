package sugiforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sugiforest.core.SugiForest;

import com.google.common.collect.Lists;

public class BlockSugiLeaves extends BlockLeaves
{
	public BlockSugiLeaves()
	{
		super();
		this.fancyGraphics = true;
		this.setUnlocalizedName("leaves.sugi");
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(SugiForest.tabSugiForest);
		this.setDefaultState(blockState.getBaseState().withProperty(DECAYABLE, Boolean.valueOf(true)).withProperty(CHECK_DECAY, Boolean.valueOf(true)));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		byte b = 0;
		int meta = b | 0;

		if (!((Boolean)state.getValue(DECAYABLE)).booleanValue())
		{
			meta |= 4;
		}

		if (((Boolean)state.getValue(CHECK_DECAY)).booleanValue())
		{
			meta |= 8;
		}

		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {DECAYABLE, CHECK_DECAY});
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBlockColor()
	{
		return 6726755;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderColor(IBlockState state)
	{
		return getBlockColor();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return random.nextInt(30) == 0 ? 1 : 0;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(SugiBlocks.sugi_sapling);
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
	{
		return Lists.newArrayList(new ItemStack(this));
	}

	@Override
	public EnumType getWoodType(int meta)
	{
		return null;
	}
}
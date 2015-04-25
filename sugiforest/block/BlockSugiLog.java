package sugiforest.block;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sugiforest.core.SugiForest;
import sugiforest.item.SugiItems;

public class BlockSugiLog extends BlockLog
{
	public static final PropertyBool MYST = PropertyBool.create("myst");

	public BlockSugiLog()
	{
		super();
		this.setUnlocalizedName("log.sugi");
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(SugiForest.tabSugiForest);
		this.setDefaultState(blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.Y).withProperty(MYST, Boolean.valueOf(false)));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		if (((Boolean)state.getValue(MYST)).booleanValue())
		{
			return 1;
		}

		byte b = 0;
		int meta = b | 0;

		switch (SwitchEnumAxis.AXIS_LOOKUP[((EnumAxis)state.getValue(LOG_AXIS)).ordinal()])
		{
			case 1:
				meta |= 4;
				break;
			case 2:
				meta |= 8;
				break;
			case 3:
				meta |= 12;
		}

		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState state = getDefaultState();

		switch (meta)
		{
			case 0:
				state = state.withProperty(LOG_AXIS, EnumAxis.Y);
				break;
			case 1:
				state = state.withProperty(LOG_AXIS, EnumAxis.Y).withProperty(MYST, Boolean.valueOf(true));
				break;
			case 4:
				state = state.withProperty(LOG_AXIS, EnumAxis.X);
				break;
			case 8:
				state = state.withProperty(LOG_AXIS, EnumAxis.Z);
				break;
			default:
				state = state.withProperty(LOG_AXIS, EnumAxis.NONE);
		}

		return state;
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {LOG_AXIS, MYST});
	}

	@Override
	public int getLightValue(IBlockAccess world, BlockPos pos)
	{
		if (((Boolean)world.getBlockState(pos).getValue(MYST)).booleanValue())
		{
			return 7;
		}

		return super.getLightValue(world, pos);
	}

	@Override
	public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		boolean flag = ((Boolean)world.getBlockState(pos).getValue(MYST)).booleanValue();
		boolean result = super.removedByPlayer(world, pos, player, willHarvest);

		if (!world.isRemote && result && flag && player.inventory.hasItem(Items.bowl))
		{
			player.inventory.consumeInventoryItem(Items.bowl);

			spawnAsEntity(world, pos, new ItemStack(SugiItems.myst_sap));
		}

		return result;
	}

	static final class SwitchEnumAxis
	{
		static final int[] AXIS_LOOKUP = new int[EnumAxis.values().length];

		static
		{
			try
			{
				AXIS_LOOKUP[EnumAxis.X.ordinal()] = 1;
			}
			catch (NoSuchFieldError e) {}

			try
			{
				AXIS_LOOKUP[EnumAxis.Z.ordinal()] = 2;
			}
			catch (NoSuchFieldError e) {}

			try
			{
				AXIS_LOOKUP[EnumAxis.NONE.ordinal()] = 3;
			}
			catch (NoSuchFieldError e) {}
		}
	}
}
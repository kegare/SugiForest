package sugiforest.block;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sugiforest.core.SugiForest;

public class BlockSugiFallenLeaves extends Block implements IShearable
{
	public static final PropertyInteger LAYERS = PropertyInteger.create("layers", 1, 8);
	public static final PropertyBool CHANCE = PropertyBool.create("chance");

	public static final AxisAlignedBB[] FALLEN_AABB = new AxisAlignedBB[]
	{
		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D),
		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D),
		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D),
		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D),
		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D),
		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D),
		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D),
		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D),
		new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)
	};

	protected static class SeedEntry extends WeightedRandom.Item
	{
		public final ItemStack stack;

		public SeedEntry(ItemStack stack, int weight)
		{
			super(weight);
			this.stack = stack;
		}
	}

	protected static final List<SeedEntry> ENTRIES = Lists.newArrayList();

	public static void addFallenSeed(ItemStack stack, int weight)
	{
		ENTRIES.add(new SeedEntry(stack, weight));
	}

	public static ItemStack getFallenSeed(Random rand)
	{
		SeedEntry entry = WeightedRandom.getRandomItem(rand, ENTRIES);

		if (entry == null || entry.stack == null)
		{
			return null;
		}

		return entry.stack.copy();
	}

	public BlockSugiFallenLeaves()
	{
		super(Material.LEAVES);
		this.setUnlocalizedName("fallenLeaves.sugi");
		this.setHardness(0.1F);
		this.setLightOpacity(1);
		this.setSoundType(SoundType.PLANT);
		this.setCreativeTab(SugiForest.TAB_SUGI);
		this.setDefaultState(blockState.getBaseState().withProperty(LAYERS, Integer.valueOf(1)).withProperty(CHANCE, Boolean.valueOf(false)));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {LAYERS, CHANCE});
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if (meta >= 8)
		{
			return getDefaultState().withProperty(LAYERS, Integer.valueOf((meta - 8 & 7) + 1)).withProperty(CHANCE, Boolean.valueOf(true));
		}

		return getDefaultState().withProperty(LAYERS, Integer.valueOf((meta & 7) + 1));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = state.getValue(LAYERS).intValue() - 1;

		if (state.getValue(CHANCE).booleanValue())
		{
			meta += 8;
		}

		return meta;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return FALLEN_AABB[state.getValue(LAYERS).intValue()];
	}

	@Override
	public boolean isFullyOpaque(IBlockState state)
	{
		return state.getValue(LAYERS).intValue() == 7;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess source, BlockPos pos, EnumFacing side)
	{
		if (side == EnumFacing.UP)
		{
			return true;
		}
		else
		{
			IBlockState blockState = source.getBlockState(pos.offset(side));

			return blockState.getBlock() == this && blockState.getValue(LAYERS).intValue() >= state.getValue(LAYERS).intValue() || super.shouldSideBeRendered(state, source, pos, side);
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean causesSuffocation(IBlockState state)
	{
		return false;
	}

	@Override
	public float getBlockHardness(IBlockState state, World world, BlockPos pos)
	{
		float hardness = super.getBlockHardness(state, world, pos);

		if (!state.getProperties().containsKey(LAYERS))
		{
			return hardness;
		}

		int layers = state.getValue(LAYERS).intValue();

		if (layers >= 6)
		{
			return hardness * 2.0F;
		}

		if (layers >= 3)
		{
			return hardness * 1.5F;
		}

		return hardness;
	}

	@Override
	public boolean isReplaceable(IBlockAccess source, BlockPos pos)
	{
		return source.getBlockState(pos).getValue(LAYERS).intValue() == 1;
	}

	@Override
	public boolean isPassable(IBlockAccess world, BlockPos pos)
	{
		return world.getBlockState(pos).getValue(LAYERS).intValue() < 5;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos.down());
		Block block = state.getBlock();

		return block != this && block.isLeaves(state, world, pos.down()) && state.isFullCube() || block == this && state.getValue(LAYERS).intValue() == 8 || state.isOpaqueCube() && state.getMaterial().blocksMovement();
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
		checkAndDropBlock(world, pos, state);
	}

	private boolean checkAndDropBlock(World world, BlockPos pos, IBlockState state)
	{
		if (!canPlaceBlockAt(world, pos))
		{
			world.setBlockToAir(pos);

			return false;
		}

		return true;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tile, ItemStack stack)
	{
		super.harvestBlock(world, player, pos, state, tile, stack);

		world.setBlockToAir(pos);
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
	{
		return !item.isEmpty();
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
	{
		return Lists.newArrayList(new ItemStack(this, world.getBlockState(pos).getValue(LAYERS).intValue()));
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		List<ItemStack> ret = Lists.newArrayList();

		if (!state.getValue(CHANCE).booleanValue() || RANDOM.nextInt(5) != 0)
		{
			return ret;
		}

		ItemStack stack = getFallenSeed(RANDOM);

		if (stack != null)
		{
			ret.add(stack);
		}

		return ret;
	}
}
package sugiforest.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sugiforest.core.SugiForest;
import sugiforest.item.ItemSugiChest;
import sugiforest.tileentity.TileEntitySugiChest;

public class BlockSugiChest extends BlockContainer
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockSugiChest()
	{
		super(Material.wood);
		this.setUnlocalizedName("chest.sugi");
		this.setHardness(3.0F);
		this.setResistance(5.5F);
		this.setStepSound(SoundType.WOOD);
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(SugiForest.tabSugiForest);
		this.setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing facing = EnumFacing.getFront(meta);

		if (facing.getAxis() == EnumFacing.Axis.Y)
		{
			facing = EnumFacing.NORTH;
		}

		return getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirror)
	{
		return state.withRotation(mirror.toRotation(state.getValue(FACING)));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return new TileEntitySugiChest();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		EnumFacing facing = EnumFacing.getHorizontal(MathHelper.floor_double(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3).getOpposite();
		state = state.withProperty(FACING, facing);
		BlockPos north = pos.north();
		BlockPos south = pos.south();
		BlockPos west = pos.west();
		BlockPos east = pos.east();
		boolean flag = this == world.getBlockState(north).getBlock();
		boolean flag1 = this == world.getBlockState(south).getBlock();
		boolean flag2 = this == world.getBlockState(west).getBlock();
		boolean flag3 = this == world.getBlockState(east).getBlock();

		if (!flag && !flag1 && !flag2 && !flag3)
		{
			world.setBlockState(pos, state, 3);
		}
		else if (facing.getAxis() == EnumFacing.Axis.X && (flag || flag1))
		{
			if (flag)
			{
				world.setBlockState(north, state, 3);
			}
			else
			{
				world.setBlockState(south, state, 3);
			}

			world.setBlockState(pos, state, 3);
		}
		else if (facing.getAxis() == EnumFacing.Axis.Z && (flag2 || flag3))
		{
			if (flag2)
			{
				world.setBlockState(west, state, 3);
			}
			else
			{
				world.setBlockState(east, state, 3);
			}

			world.setBlockState(pos, state, 3);
		}

		if (stack.hasDisplayName())
		{
			TileEntity tileentity = world.getTileEntity(pos);

			if (tileentity instanceof TileEntitySugiChest)
			{
				((TileEntitySugiChest)tileentity).setCustomName(stack.getDisplayName());
			}
		}

		if (stack.hasTagCompound())
		{
			NBTTagCompound nbt = stack.getTagCompound();

			if (nbt.hasKey("Chest"))
			{
				TileEntity tileentity = world.getTileEntity(pos);
				NBTTagCompound data = nbt.getCompoundTag("Chest");

				data.setInteger("x", pos.getX());
				data.setInteger("y", pos.getY());
				data.setInteger("z", pos.getZ());

				tileentity.readFromNBT(data);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		super.onNeighborBlockChange(world, pos, state, neighborBlock);

		TileEntity tileentity = world.getTileEntity(pos);

		if (tileentity instanceof TileEntitySugiChest)
		{
			tileentity.updateContainingBlockInfo();
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			player.displayGUIChest((IInventory)world.getTileEntity(pos));
		}

		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		TileEntity tileentity = world.getTileEntity(pos);

		if (tileentity instanceof IInventory)
		{
			InventoryHelper.dropInventoryItems(world, pos, (IInventory)tileentity);

			world.updateComparatorOutputLevel(pos, this);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		if (player.capabilities.isCreativeMode)
		{
			return super.removedByPlayer(state, world, pos, player, willHarvest);
		}

		if (EnchantmentHelper.getEnchantmentLevel(Enchantments.silkTouch, player.getHeldItemMainhand()) > 0)
		{
			TileEntitySugiChest tileentity = (TileEntitySugiChest)world.getTileEntity(pos);
			ItemStack stack;
			boolean flag = false;

			for (int i = 0; i < tileentity.getSizeInventory(); ++i)
			{
				stack = tileentity.getStackInSlot(i);

				if (stack != null)
				{
					flag = true;

					if (stack.getItem() instanceof ItemSugiChest)
					{
						if (((ItemSugiChest)stack.getItem()).isContained(stack))
						{
							flag = false;
							break;
						}
					}
				}
			}

			if (!flag)
			{
				spawnAsEntity(world, pos, new ItemStack(this));

				return super.removedByPlayer(state, world, pos, player, willHarvest);
			}

			if (!world.isRemote)
			{
				stack = new ItemStack(this);
				NBTTagCompound data = new NBTTagCompound();
				tileentity.writeToNBT(data);
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setTag("Chest", data);
				stack.setTagCompound(nbt);

				spawnAsEntity(world, pos, stack);
			}

			super.breakBlock(world, pos, world.getBlockState(pos));
		}
		else
		{
			spawnAsEntity(world, pos, new ItemStack(this));
		}

		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	public int getWeakPower(IBlockState state, IBlockAccess source, BlockPos pos, EnumFacing side)
	{
		if (!canProvidePower(state))
		{
			return 0;
		}

		int i = 0;
		TileEntity tileentity = source.getTileEntity(pos);

		if (tileentity instanceof TileEntitySugiChest)
		{
			i = ((TileEntitySugiChest)tileentity).numUsingPlayers;
		}

		return MathHelper.clamp_int(i, 0, 15);
	}

	@Override
	public int getStrongPower(IBlockState state, IBlockAccess source, BlockPos pos, EnumFacing side)
	{
		return side == EnumFacing.UP ? getWeakPower(state, source, pos, side) : 0;
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
	{
		return Container.calcRedstone(world.getTileEntity(pos));
	}
}
package sugiforest.block;

import java.util.Random;

import javax.annotation.Nullable;

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
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import sugiforest.core.SugiForest;
import sugiforest.item.ItemSugiChest;
import sugiforest.tileentity.TileEntitySugiChest;

public class BlockSugiChest extends BlockContainer
{
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockSugiChest()
	{
		super(Material.WOOD);
		this.setUnlocalizedName("chest.sugi");
		this.setHardness(3.0F);
		this.setResistance(5.5F);
		this.setSoundType(SoundType.WOOD);
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(SugiForest.TAB_SUGI);
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

	public EnumFacing getFacingForPlacement(EntityLivingBase placer)
	{
		return EnumFacing.getHorizontal(MathHelper.floor(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3).getOpposite();
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return getDefaultState().withProperty(FACING, getFacingForPlacement(placer));
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		world.setBlockState(pos, state.withProperty(FACING, getFacingForPlacement(placer)), 2);

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
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
		super.neighborChanged(state, world, pos, block, fromPos);

		TileEntity tileentity = world.getTileEntity(pos);

		if (tileentity instanceof TileEntitySugiChest)
		{
			tileentity.updateContainingBlockInfo();
		}
	}

	@Override
	public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return base_state.getValue(FACING) != side;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			ILockableContainer container = getLockableContainer(world, pos);

			if (container != null)
			{
				player.displayGUIChest(container);
			}

			return true;
		}
	}

	@Nullable
	public ILockableContainer getLockableContainer(World world, BlockPos pos)
	{
		return getContainer(world, pos, false);
	}

	@Nullable
	public ILockableContainer getContainer(World world, BlockPos pos, boolean flag)
	{
		TileEntity tileentity = world.getTileEntity(pos);

		if (!(tileentity instanceof TileEntitySugiChest))
		{
			return null;
		}
		else
		{
			ILockableContainer container = (TileEntitySugiChest)tileentity;

			return container;
		}
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntitySugiChest();
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

	public ItemStack getContainedChest(TileEntitySugiChest chest)
	{
		ItemStack ret = ItemStack.EMPTY;
		boolean flag = false;

		for (int i = 0; i < chest.getSizeInventory(); ++i)
		{
			ItemStack stack = chest.getStackInSlot(i);

			if (!stack.isEmpty())
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
			return ret;
		}

		ret = new ItemStack(this);
		NBTTagCompound data = new NBTTagCompound();
		chest.writeToNBT(data);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setTag("Chest", data);
		ret.setTagCompound(nbt);

		if (chest.hasCustomName())
		{
			ret.setStackDisplayName(chest.getName());
		}

		return ret;
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		if (willHarvest)
		{
			ItemStack chest = ItemStack.EMPTY;
			ItemStack heldMain = player.getHeldItemMainhand();

			if (player.isSneaking() && heldMain.isEmpty() || EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, heldMain) > 0)
			{
				chest = getContainedChest((TileEntitySugiChest)world.getTileEntity(pos));
			}

			if (chest.isEmpty())
			{
				chest = new ItemStack(this);
			}
			else super.breakBlock(world, pos, state);

			spawnAsEntity(world, pos, chest);
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

		return MathHelper.clamp(i, 0, 15);
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
		return Container.calcRedstoneFromInventory(getLockableContainer(world, pos));
	}
}
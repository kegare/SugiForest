/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sugiforest.core.SugiForest;

import com.google.common.collect.Lists;

public class BlockSugiFallenLeaves extends Block implements IShearable
{
	public static final PropertyInteger LAYERS = PropertyInteger.create("layers", 1, 8);
	public static final PropertyBool CHANCE = PropertyBool.create("chance");

	static class SeedEntry extends WeightedRandom.Item
	{
		public final ItemStack stack;

		public SeedEntry(ItemStack stack, int weight)
		{
			super(weight);
			this.stack = stack;
		}
	}

	static final List<SeedEntry> seedList = Lists.newArrayList();

	public static void addFallenSeed(ItemStack stack, int weight)
	{
		seedList.add(new SeedEntry(stack, weight));
	}

	public static ItemStack getFallenSeed(Random rand)
	{
		SeedEntry entry = (SeedEntry)WeightedRandom.getRandomItem(rand, seedList);

		if (entry == null || entry.stack == null)
		{
			return null;
		}

		return entry.stack.copy();
	}

	public BlockSugiFallenLeaves()
	{
		super(Material.leaves);
		this.setUnlocalizedName("fallenLeaves.sugi");
		this.setHardness(0.1F);
		this.setLightOpacity(1);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		this.setBlockBoundsForItemRender();
		this.setStepSound(soundTypeGrass);
		this.setCreativeTab(SugiForest.tabSugiForest);
		this.setDefaultState(blockState.getBaseState().withProperty(LAYERS, Integer.valueOf(1)).withProperty(CHANCE, Boolean.valueOf(false)));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = ((Integer)state.getValue(LAYERS)).intValue() - 1;

		if (((Boolean)state.getValue(CHANCE)).booleanValue())
		{
			meta += 8;
		}

		return meta;
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
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {LAYERS, CHANCE});
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
	public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass)
	{
		return BiomeColorHelper.getFoliageColorAtPos(world, pos);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state)
	{
		int i = ((Integer)state.getValue(LAYERS)).intValue() - 1;
		float f = 0.125F;

		return new AxisAlignedBB(pos.getX() + minX, pos.getY() + minY, pos.getZ() + minZ, pos.getX() + maxX, pos.getY() + i * f, pos.getZ() + maxZ);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return side == EnumFacing.UP || super.shouldSideBeRendered(world, pos, side);
	}

	@Override
	public void setBlockBoundsForItemRender()
	{
		getBoundsForLayers(0);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
	{
		getBoundsForLayers(((Integer)world.getBlockState(pos).getValue(LAYERS)).intValue());
	}

	protected void getBoundsForLayers(int layer)
	{
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, layer / 8.0F, 1.0F);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public boolean isVisuallyOpaque()
	{
		return false;
	}

	@Override
	public float getBlockHardness(World world, BlockPos pos)
	{
		float hardness = super.getBlockHardness(world, pos);
		int layers = ((Integer)world.getBlockState(pos).getValue(LAYERS)).intValue();

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
	public boolean isReplaceable(World world, BlockPos pos)
	{
		return ((Integer)world.getBlockState(pos).getValue(LAYERS)).intValue() == 1;
	}

	@Override
	public boolean isPassable(IBlockAccess world, BlockPos pos)
	{
		return ((Integer)world.getBlockState(pos).getValue(LAYERS)).intValue() < 5;
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

		return block != this && block.isLeaves(world, pos.down()) && block.isFullCube() || block == this && ((Integer)state.getValue(LAYERS)).intValue() == 8 || block.isOpaqueCube() && block.getMaterial().blocksMovement();
	}

	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
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
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te)
	{
		super.harvestBlock(world, player, pos, state, te);

		world.setBlockToAir(pos);
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
	{
		return Lists.newArrayList(new ItemStack(this, ((Integer)world.getBlockState(pos).getValue(LAYERS)).intValue()));
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		List<ItemStack> ret = Lists.newArrayList();

		if (!((Boolean)state.getValue(CHANCE)).booleanValue() || RANDOM.nextInt(5) != 0)
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
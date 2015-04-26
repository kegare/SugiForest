/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.block;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import sugiforest.core.SugiForest;

public class BlockSugiWoodSlab extends BlockSlab
{
	public BlockSugiWoodSlab()
	{
		super(Material.wood);
		this.setUnlocalizedName("woodSlab.sugi");
		this.setHardness(1.75F);
		this.setResistance(4.75F);
		this.setStepSound(soundTypeWood);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(SugiForest.tabSugiForest);
		this.setDefaultState(blockState.getBaseState().withProperty(HALF, EnumBlockHalf.BOTTOM));
		this.useNeighborBrightness = true;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (EnumBlockHalf)state.getValue(HALF) == EnumBlockHalf.TOP ? 1 : 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(HALF, meta == 0 ? EnumBlockHalf.BOTTOM : EnumBlockHalf.TOP);
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {HALF});
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
	public IProperty getVariantProperty()
	{
		return null;
	}

	@Override
	public Object getVariant(ItemStack stack)
	{
		return null;
	}
}
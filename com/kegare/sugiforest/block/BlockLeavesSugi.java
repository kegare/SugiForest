/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.block;

import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLeavesSugi extends BlockLeaves
{
	@SideOnly(Side.CLIENT)
	private IIcon opacityIcon;

	public BlockLeavesSugi(String name)
	{
		super();
		this.field_150121_P = true;
		this.setBlockName(name);
		this.setBlockTextureName("sugiforest:sugi_leaves");
		this.setHarvestLevel("axe", 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getBlockColor()
	{
		return 6726755;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderColor(int metadata)
	{
		return getBlockColor();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		super.registerBlockIcons(iconRegister);

		opacityIcon = iconRegister.registerIcon(getTextureName() + "_opacity");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int metadata)
	{
		GameSettings options = RenderManager.instance.options;

		if (options != null)
		{
			field_150121_P = options.fancyGraphics;
		}

		return field_150121_P ? blockIcon : opacityIcon;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return random.nextInt(30) == 0 ? 1 : 0;
	}

	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		return Item.getItemFromBlock(SugiBlocks.sugi_sapling);
	}

	@Override
	public String[] func_150125_e()
	{
		return null;
	}
}
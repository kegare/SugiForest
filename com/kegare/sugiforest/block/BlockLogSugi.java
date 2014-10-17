/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.block;

import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLogSugi extends BlockLog
{
	public BlockLogSugi(String name)
	{
		super();
		this.setBlockName(name);
		this.setBlockTextureName("sugiforest:sugi_log");
		this.setHarvestLevel("axe", 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon(getTextureName());
		field_150164_N = iconRegister.registerIcon(getTextureName() + "_top");
	}

	@SideOnly(Side.CLIENT)
	@Override
	protected IIcon getSideIcon(int metadata)
	{
		return blockIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	protected IIcon getTopIcon(int metadata)
	{
		return field_150164_N;
	}
}
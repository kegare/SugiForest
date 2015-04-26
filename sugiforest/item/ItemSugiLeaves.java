/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSugiLeaves extends ItemBlock
{
	public ItemSugiLeaves(Block block)
	{
		super(block);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getColorFromItemStack(ItemStack stack, int renderPass)
	{
		return block.getBlockColor();
	}
}
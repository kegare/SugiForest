package sugiforest.handler;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import sugiforest.block.BlockSugiChest;
import sugiforest.block.BlockSugiFallenLeaves;
import sugiforest.block.BlockSugiLeaves;
import sugiforest.block.BlockSugiLog;
import sugiforest.block.BlockSugiSapling;
import sugiforest.block.BlockSugiStairs;
import sugiforest.block.BlockSugiWood;
import sugiforest.block.BlockSugiWoodSlab;
import sugiforest.item.ItemMystSap;
import sugiforest.item.ItemSugiAxe;
import sugiforest.item.ItemSugiHoe;
import sugiforest.item.ItemSugiPickaxe;
import sugiforest.item.ItemSugiShovel;
import sugiforest.item.ItemSugiSword;

public class SugiFuelHandler implements IFuelHandler
{
	@Override
	public int getBurnTime(ItemStack fuel)
	{
		Item item = fuel.getItem();

		if (item instanceof ItemMystSap)
		{
			return 200;
		}

		if (item instanceof ItemSugiSword || item instanceof ItemSugiPickaxe || item instanceof ItemSugiAxe || item instanceof ItemSugiShovel || item instanceof ItemSugiHoe)
		{
			return 100;
		}

		Block block = Block.getBlockFromItem(item);

		if (block == null)
		{
			return 0;
		}

		if (block instanceof BlockSugiLog || block instanceof BlockSugiWood || block instanceof BlockSugiStairs)
		{
			return 250;
		}

		if (block instanceof BlockSugiLeaves || block instanceof BlockSugiSapling)
		{
			return 100;
		}

		if (block instanceof BlockSugiFallenLeaves)
		{
			return 50;
		}

		if (block instanceof BlockSugiWoodSlab)
		{
			return 150;
		}

		if (block instanceof BlockSugiChest)
		{
			return 2300;
		}

		return 0;
	}
}
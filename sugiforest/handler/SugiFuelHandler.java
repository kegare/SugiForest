package sugiforest.handler;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import sugiforest.block.BlockSugiChest;
import sugiforest.block.BlockSugiLeaves;
import sugiforest.block.BlockSugiLog;
import sugiforest.block.BlockSugiSapling;
import sugiforest.block.BlockSugiStairs;
import sugiforest.block.BlockSugiWood;
import sugiforest.block.BlockSugiWoodSlab;

public class SugiFuelHandler implements IFuelHandler
{
	@Override
	public int getBurnTime(ItemStack fuel)
	{
		Block block = Block.getBlockFromItem(fuel.getItem());

		if (block instanceof BlockSugiLog || block instanceof BlockSugiWood || block instanceof BlockSugiStairs)
		{
			return 300;
		}
		else if (block instanceof BlockSugiLeaves || block instanceof BlockSugiSapling)
		{
			return 100;
		}
		else if (block instanceof BlockSugiWoodSlab)
		{
			return 150;
		}
		else if (block instanceof BlockSugiChest)
		{
			return 2300;
		}

		return 0;
	}
}
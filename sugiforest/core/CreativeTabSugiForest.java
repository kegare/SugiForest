package sugiforest.core;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sugiforest.block.SugiBlocks;
import sugiforest.item.SugiItems;

public class CreativeTabSugiForest extends CreativeTabs
{
	public CreativeTabSugiForest()
	{
		super(SugiForest.MODID);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(SugiBlocks.SUGI_LEAVES);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> list)
	{
		List<Item> sugiItems = SugiItems.getItems();

		for (Item item : sugiItems)
		{
			for (CreativeTabs tab : item.getCreativeTabs())
			{
				if (tab == this)
				{
					item.getSubItems(item, this, list);
				}
			}
		}

		for (Item item : Item.REGISTRY)
		{
			if (item == null || sugiItems.contains(item))
			{
				continue;
			}

			for (CreativeTabs tab : item.getCreativeTabs())
			{
				if (tab == this)
				{
					item.getSubItems(item, this, list);
				}
			}
		}
	}
}
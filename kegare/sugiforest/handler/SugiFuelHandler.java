package kegare.sugiforest.handler;

import cpw.mods.fml.common.IFuelHandler;
import kegare.sugiforest.block.SugiBlock;
import net.minecraft.item.ItemStack;

public class SugiFuelHandler implements IFuelHandler
{
	@Override
	public int getBurnTime(ItemStack fuel)
	{
		if (SugiBlock.woodSugi.isPresent() &&fuel.itemID == SugiBlock.woodSugi.get().blockID)
		{
			return 300;
		}
		else if (SugiBlock.saplingSugi.isPresent() && fuel.itemID == SugiBlock.saplingSugi.get().blockID)
		{
			return 100;
		}
		else if (SugiBlock.planksSugi.isPresent() && fuel.itemID == SugiBlock.planksSugi.get().blockID)
		{
			return 300;
		}
		else if (SugiBlock.woodSlabSugi.isPresent() && fuel.itemID == SugiBlock.woodSlabSugi.get().blockID)
		{
			return 150;
		}
		else if (SugiBlock.stairsWoodSugi.isPresent() && fuel.itemID == SugiBlock.stairsWoodSugi.get().blockID)
		{
			return 300;
		}

		return 0;
	}
}
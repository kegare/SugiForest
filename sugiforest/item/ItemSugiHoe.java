package sugiforest.item;

import net.minecraft.item.ItemHoe;
import sugiforest.core.SugiForest;

public class ItemSugiHoe extends ItemHoe
{
	public ItemSugiHoe()
	{
		super(SugiItems.SUGI);
		this.setUnlocalizedName("hoeSugi");
		this.setCreativeTab(SugiForest.TAB_SUGI);
	}
}
package sugiforest.item;

import net.minecraft.item.ItemSpade;
import sugiforest.core.SugiForest;

public class ItemSugiShovel extends ItemSpade
{
	public ItemSugiShovel()
	{
		super(SugiItems.SUGI);
		this.setUnlocalizedName("shovelSugi");
		this.setCreativeTab(SugiForest.TAB_SUGI);
	}
}
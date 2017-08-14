package sugiforest.item;

import net.minecraft.item.ItemSword;
import sugiforest.core.SugiForest;

public class ItemSugiSword extends ItemSword
{
	public ItemSugiSword()
	{
		super(SugiItems.SUGI);
		this.setUnlocalizedName("swordSugi");
		this.setCreativeTab(SugiForest.TAB_SUGI);
	}
}
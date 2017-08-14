package sugiforest.item;

import net.minecraft.item.ItemPickaxe;
import sugiforest.core.SugiForest;

public class ItemSugiPickaxe extends ItemPickaxe
{
	public ItemSugiPickaxe()
	{
		super(SugiItems.SUGI);
		this.setUnlocalizedName("pickaxeSugi");
		this.setCreativeTab(SugiForest.TAB_SUGI);
	}
}
package sugiforest.item;

import net.minecraft.item.ItemAxe;
import sugiforest.core.SugiForest;

public class ItemSugiAxe extends ItemAxe
{
	protected ItemSugiAxe()
	{
		super(SugiItems.SUGI, 7.0F, -3.2F);
		this.setUnlocalizedName("axeSugi");
		this.setCreativeTab(SugiForest.TAB_SUGI);
	}
}
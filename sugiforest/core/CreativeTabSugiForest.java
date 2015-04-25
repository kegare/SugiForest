package sugiforest.core;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sugiforest.block.SugiBlocks;

public class CreativeTabSugiForest extends CreativeTabs
{
	public CreativeTabSugiForest()
	{
		super("sugiforest");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getTabLabel()
	{
		return "SugiForest";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public String getTranslatedTabLabel()
	{
		return getTabLabel();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getTabIconItem()
	{
		return Item.getItemFromBlock(SugiBlocks.sugi_sapling);
	}
}
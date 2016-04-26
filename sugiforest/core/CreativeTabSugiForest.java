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
		super(SugiForest.MODID);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getTabIconItem()
	{
		return Item.getItemFromBlock(SugiBlocks.sugi_leaves);
	}
}
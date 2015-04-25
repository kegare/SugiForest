package sugiforest.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemSugiChest extends ItemBlock
{
	public ItemSugiChest(Block block)
	{
		super(block);
	}

	public boolean isContained(ItemStack itemstack)
	{
		return itemstack != null && itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("Chest");
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		String name = super.getUnlocalizedName(itemstack);

		return isContained(itemstack) ? name + ".contained" : name;
	}

	@Override
	public int getItemStackLimit(ItemStack itemstack)
	{
		return isContained(itemstack) ? 1 : super.getItemStackLimit(itemstack);
	}
}
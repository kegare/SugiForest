package sugiforest.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import sugiforest.core.SugiForest;

public class ItemMystSap extends ItemSoup
{
	public ItemMystSap()
	{
		super(0);
		this.setUnlocalizedName("mystSap");
		this.setAlwaysEdible();
		this.setCreativeTab(SugiForest.TAB_SUGI);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
	{
		player.extinguish();

		if (!world.isRemote)
		{
			int size = itemRand.nextInt(3) + 1;

			for (int i = 0; i < size; ++i)
			{
				Potion potion = null;

				while (potion == null || potion.isBadEffect() || potion == MobEffects.GLOWING || player.isPotionActive(potion))
				{
					potion = Potion.REGISTRY.getRandomObject(itemRand);
				}

				if (potion != null)
				{
					player.addPotionEffect(new PotionEffect(potion, itemRand.nextInt(500) + 150, 0, false, false));
				}
			}
		}

		player.getFoodStats().addStats((20 - player.getFoodStats().getFoodLevel()) / 2, 0.5F);
		player.heal((player.getMaxHealth() - player.getHealth()) * 0.5F);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.DRINK;
	}

	@Override
	public EnumRarity getRarity(ItemStack stack)
	{
		return EnumRarity.UNCOMMON;
	}
}
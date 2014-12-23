/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.item;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemMystSap extends ItemSoup
{
	public ItemMystSap(String name)
	{
		super(0);
		this.setUnlocalizedName(name);
		this.setTextureName("sugiforest:myst_sap");
		this.setAlwaysEdible();
	}

	@Override
	protected void onFoodEaten(ItemStack itemstack, World world, EntityPlayer player)
	{
		player.extinguish();

		if (!world.isRemote)
		{
			Random random = player.getRNG();
			int max = random.nextInt(3) + 1;

			for (int i = 0; i < max; ++i)
			{
				Potion potion = null;

				while (potion == null || potion.getEffectiveness() <= 0.5D || player.isPotionActive(potion))
				{
					potion = Potion.potionTypes[random.nextInt(Potion.potionTypes.length)];
				}

				if (potion != null)
				{
					player.addPotionEffect(new PotionEffect(potion.getId(), random.nextInt(500) + 150));
				}
			}
		}

		player.getFoodStats().addStats((20 - player.getFoodStats().getFoodLevel()) / 2, 0.5F);
		player.heal((player.getMaxHealth() - player.getHealth()) * 0.5F);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack itemstack)
	{
		return EnumAction.drink;
	}

	@Override
	public EnumRarity getRarity(ItemStack itemstack)
	{
		return EnumRarity.uncommon;
	}
}
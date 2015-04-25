package sugiforest.item;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import sugiforest.block.SugiBlocks;
import sugiforest.core.SugiForest;

public class ItemMystSap extends ItemSoup
{
	public ItemMystSap()
	{
		super(0);
		this.setUnlocalizedName("mystSap");
		this.setAlwaysEdible();
		this.setCreativeTab(SugiForest.tabSugiForest);
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			BlockPos pos1 = pos.offset(side);

			if (SugiBlocks.sugi_portal.func_176548_d(world, pos1))
			{
				world.playSoundEffect(pos1.getX() + 0.5D, pos1.getY() + 0.5D, pos1.getZ() + 0.5D, SugiBlocks.sugi_portal.stepSound.getPlaceSound(), 1.0F, 2.0F);

				if (!player.capabilities.isCreativeMode && --stack.stackSize <= 0)
				{
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
				}

				return true;
			}
		}

		return super.onItemUseFirst(stack, player, world, pos, side, hitX, hitY, hitZ);
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
		return EnumAction.DRINK;
	}

	@Override
	public EnumRarity getRarity(ItemStack itemstack)
	{
		return EnumRarity.UNCOMMON;
	}
}
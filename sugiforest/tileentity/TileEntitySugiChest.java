package sugiforest.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import sugiforest.block.SugiBlocks;
import sugiforest.core.SugiForest;
import sugiforest.core.SugiSounds;

public class TileEntitySugiChest extends TileEntityLockableLoot
{
	private NonNullList<ItemStack> chestContents = NonNullList.withSize(36, ItemStack.EMPTY);

	public int numUsingPlayers;

	@Override
	public int getSizeInventory()
	{
		return 36;
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack stack : chestContents)
		{
			if (!stack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public String getName()
	{
		return hasCustomName() ? customName : SugiBlocks.SUGI_CHEST.getUnlocalizedName() + ".name";
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);

		chestContents = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);

		if (!checkLootAndRead(compound))
		{
			ItemStackHelper.loadAllItems(compound, chestContents);
		}

		if (compound.hasKey("CustomName", 8))
		{
			customName = compound.getString("CustomName");
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);

		if (!checkLootAndWrite(compound))
		{
			ItemStackHelper.saveAllItems(compound, chestContents);
		}

		if (hasCustomName())
		{
			compound.setString("CustomName", customName);
		}

		return compound;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean receiveClientEvent(int channel, int type)
	{
		if (channel == 1)
		{
			numUsingPlayers = type;

			return true;
		}

		return super.receiveClientEvent(channel, type);
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
		if (!player.isSpectator())
		{
			if (numUsingPlayers < 0)
			{
				numUsingPlayers = 0;
			}

			if (++numUsingPlayers <= 1)
			{
				world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SugiSounds.SUGI_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, 1.0F);
			}

			world.addBlockEvent(pos, getBlockType(), 1, numUsingPlayers);
			world.notifyNeighborsOfStateChange(pos, getBlockType(), false);
		}
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		if (!player.isSpectator())
		{
			if (--numUsingPlayers <= 0)
			{
				world.playSound(null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SugiSounds.SUGI_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, 1.0F);
			}

			world.addBlockEvent(pos, getBlockType(), 1, numUsingPlayers);
			world.notifyNeighborsOfStateChange(pos, getBlockType(), false);
		}
	}

	@Override
	public void invalidate()
	{
		super.invalidate();

		updateContainingBlockInfo();
	}

	@Override
	public String getGuiID()
	{
		return SugiForest.MODID + ":sugi_chest";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer player)
	{
		fillWithLoot(player);

		return new ContainerChest(playerInventory, this, player);
	}

	@Override
	protected NonNullList<ItemStack> getItems()
	{
		return chestContents;
	}
}
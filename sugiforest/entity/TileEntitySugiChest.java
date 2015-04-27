/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.common.util.Constants.NBT;
import sugiforest.block.SugiBlocks;

public class TileEntitySugiChest extends TileEntity implements IInventory
{
	private final ItemStack[] chestContents = new ItemStack[36];

	public int numUsingPlayers;

	private String customName;

	@Override
	public int getSizeInventory()
	{
		return chestContents.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		if (slot < 0 || slot >= chestContents.length)
		{
			return null;
		}

		return chestContents[slot];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot)
	{
		ItemStack itemstack = getStackInSlot(slot);

		if (itemstack != null)
		{
			chestContents[slot] = null;
		}

		return itemstack;
	}

	@Override
	public ItemStack decrStackSize(int slot, int size)
	{
		ItemStack itemstack = getStackInSlot(slot);

		if (itemstack != null)
		{
			if (itemstack.stackSize <= size)
			{
				chestContents[slot] = null;
				markDirty();

				return itemstack;
			}

			ItemStack item = itemstack.splitStack(size);

			if (itemstack.stackSize <= 0)
			{
				chestContents[slot] = null;
			}

			markDirty();

			return item;
		}

		return itemstack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack)
	{
		if (slot < 0 || slot >= chestContents.length)
		{
			return;
		}

		chestContents[slot] = itemstack;

		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}

		markDirty();
	}

	@Override
	public String getName()
	{
		return hasCustomName() ? customName : SugiBlocks.sugi_chest.getUnlocalizedName() + ".name";
	}

	@Override
	public IChatComponent getDisplayName()
	{
		return hasCustomName() ? new ChatComponentText(getName()) : new ChatComponentTranslation(getName(), new Object[0]);
	}

	@Override
	public boolean hasCustomName()
	{
		return customName != null && customName.length() > 0;
	}

	public void setCustomName(String name)
	{
		customName = name;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return worldObj.getTileEntity(pos) == this && player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
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
				worldObj.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "sugiforest:sugichest.open", 0.5F, 1.0F);
			}

			worldObj.addBlockEvent(pos, getBlockType(), 1, numUsingPlayers);
			worldObj.notifyNeighborsOfStateChange(pos, getBlockType());
			worldObj.notifyNeighborsOfStateChange(pos.down(), getBlockType());
		}
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		if (!player.isSpectator())
		{
			if (--numUsingPlayers <= 0)
			{
				worldObj.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, "sugiforest:sugichest.close", 0.5F, 1.0F);
			}

			worldObj.addBlockEvent(pos, getBlockType(), 1, numUsingPlayers);
			worldObj.notifyNeighborsOfStateChange(pos, getBlockType());
			worldObj.notifyNeighborsOfStateChange(pos.down(), getBlockType());
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return true;
	}

	@Override
	public boolean receiveClientEvent(int channel, int value)
	{
		if (channel == 1)
		{
			numUsingPlayers = value;

			return true;
		}

		return super.receiveClientEvent(channel, value);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		clear();

		NBTTagList list = nbt.getTagList("Items", NBT.TAG_COMPOUND);

		for (int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagCompound data = list.getCompoundTagAt(i);
			int slot = data.getByte("Slot") & 255;

			if (slot >= 0 && slot < chestContents.length)
			{
				chestContents[slot] = ItemStack.loadItemStackFromNBT(data);
			}
		}

		if (nbt.hasKey("CustomName", NBT.TAG_STRING))
		{
			customName = nbt.getString("CustomName");
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		NBTTagList list = new NBTTagList();

		for (int i = 0; i < chestContents.length; ++i)
		{
			if (chestContents[i] != null)
			{
				NBTTagCompound data = new NBTTagCompound();

				data.setByte("Slot", (byte)i);
				chestContents[i].writeToNBT(data);

				list.appendTag(data);
			}
		}

		nbt.setTag("Items", list);

		if (hasCustomName())
		{
			nbt.setString("CustomName", customName);
		}
	}

	@Override
	public void invalidate()
	{
		updateContainingBlockInfo();

		super.invalidate();
	}

	@Override
	public int getField(int id)
	{
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount()
	{
		return 0;
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < chestContents.length; ++i)
		{
			chestContents[i] = null;
		}
	}
}
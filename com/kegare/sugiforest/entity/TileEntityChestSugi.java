/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants.NBT;

import com.kegare.sugiforest.block.SugiBlocks;

public class TileEntityChestSugi extends TileEntity implements IInventory
{
	private final ItemStack[] chestContents = new ItemStack[36];

	private int updateEntityTick, numUsingPlayers;

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
	public String getInventoryName()
	{
		return SugiBlocks.sugi_chest.getUnlocalizedName() + ".name";
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory()
	{
		if (++numUsingPlayers <= 1)
		{
			worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "sugiforest:sugichest.open", 0.5F, 1.0F);
		}
	}

	@Override
	public void closeInventory()
	{
		if (--numUsingPlayers <= 0)
		{
			numUsingPlayers = 0;

			worldObj.playSoundEffect(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, "sugiforest:sugichest.close", 0.5F, 1.0F);
		}
	}

	public void clearInventory()
	{
		for (int i = 0; i < chestContents.length; ++i)
		{
			chestContents[i] = null;
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack)
	{
		return true;
	}

	@Override
	public void updateEntity()
	{
		super.updateEntity();

		if (++updateEntityTick % 20 * 4 == 0)
		{
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, worldObj.getBlock(xCoord, yCoord, zCoord), 1, numUsingPlayers);
		}
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
		clearInventory();

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
	}

	@Override
	public void invalidate()
	{
		updateContainingBlockInfo();

		super.invalidate();
	}
}
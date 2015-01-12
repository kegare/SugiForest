/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.kegare.sugiforest.entity.TileEntityChestSugi;
import com.kegare.sugiforest.item.ItemChestSugi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockChestSugi extends BlockContainer
{
	@SideOnly(Side.CLIENT)
	private IIcon sideIcon, topIcon;

	private final Random rand = new Random();

	public BlockChestSugi(String name)
	{
		super(Material.wood);
		this.setBlockName(name);
		this.setBlockTextureName("sugiforest:sugi_chest");
		this.setHardness(3.0F);
		this.setResistance(5.5F);
		this.setStepSound(soundTypeWood);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHarvestLevel("axe", 0);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack)
	{
		byte meta = 0;

		switch (MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3)
		{
			case 0:
				meta = 2;
				break;
			case 1:
				meta = 5;
				break;
			case 2:
				meta = 3;
				break;
			case 3:
				meta = 4;
				break;
		}

		world.setBlockMetadataWithNotify(x, y, z, meta, 2);

		if (itemstack.hasTagCompound())
		{
			NBTTagCompound nbt = itemstack.getTagCompound();

			if (nbt.hasKey("Chest"))
			{
				TileEntity tile = world.getTileEntity(x, y, z);

				if (tile != null)
				{
					NBTTagCompound data = nbt.getCompoundTag("Chest");

					data.setInteger("x", x);
					data.setInteger("y", y);
					data.setInteger("z", z);

					tile.readFromNBT(data);
				}
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			player.displayGUIChest((IInventory)world.getTileEntity(x, y, z));
		}

		return true;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		TileEntityChestSugi tile = (TileEntityChestSugi)world.getTileEntity(x, y, z);

		if (tile != null)
		{
			EntityItem item = new EntityItem(world, x + 0.5D, y + 0.5D, z + 0.5D, new ItemStack(this));
			item.delayBeforeCanPickup = 10;

			world.spawnEntityInWorld(item);

			for (int i = 0; i < tile.getSizeInventory(); ++i)
			{
				ItemStack itemstack = tile.getStackInSlot(i);

				if (itemstack != null)
				{
					float f = rand.nextFloat() * 0.8F + 0.1F;
					float f1 = rand.nextFloat() * 0.8F + 0.1F;

					for (float f2 = rand.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world.spawnEntityInWorld(item))
					{
						int j = rand.nextInt(21) + 10;

						if (j > itemstack.stackSize)
						{
							j = itemstack.stackSize;
						}

						itemstack.stackSize -= j;
						item = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));
						float f3 = 0.05F;
						item.motionX = (float)rand.nextGaussian() * f3;
						item.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
						item.motionZ = (float)rand.nextGaussian() * f3;

						if (itemstack.hasTagCompound())
						{
							item.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
						}
					}
				}
			}
		}

		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
	{
		if (EnchantmentHelper.getSilkTouchModifier(player))
		{
			TileEntityChestSugi tile = (TileEntityChestSugi)world.getTileEntity(x, y, z);

			if (tile != null)
			{
				ItemStack itemstack;
				boolean flag = false;

				for (int i = 0; i < tile.getSizeInventory(); ++i)
				{
					itemstack = tile.getStackInSlot(i);

					if (itemstack != null)
					{
						flag = true;

						if (itemstack.getItem() instanceof ItemChestSugi)
						{
							if (((ItemChestSugi)itemstack.getItem()).isContained(itemstack))
							{
								flag = false;
								break;
							}
						}
					}
				}

				if (!flag)
				{
					return super.removedByPlayer(world, player, x, y, z, willHarvest);
				}

				itemstack = new ItemStack(this);
				NBTTagCompound data = new NBTTagCompound();
				tile.writeToNBT(data);
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setTag("Chest", data);
				itemstack.setTagCompound(nbt);

				float f1 = rand.nextFloat() * 0.8F + 0.1F;
				float f2 = rand.nextFloat() * 0.8F + 0.1F;
				float f3 = rand.nextFloat() * 0.8F + 0.1F;
				EntityItem item = new EntityItem(world, x + f1, y + f2, z + f3, itemstack);
				float f4 = 0.05F;

				item.motionX = (float)rand.nextGaussian() * f4;
				item.motionY = (float)rand.nextGaussian() * f4 + 0.2F;
				item.motionZ = (float)rand.nextGaussian() * f4;

				world.spawnEntityInWorld(item);

				super.breakBlock(world, x, y, z, world.getBlock(x, y, z), world.getBlockMetadata(x, y, z));
			}
		}

		return super.removedByPlayer(world, player, x, y, z, willHarvest);
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 0;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TileEntityChestSugi();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		super.registerBlockIcons(iconRegister);

		sideIcon = iconRegister.registerIcon(getTextureName() + "_side");
		topIcon = iconRegister.registerIcon(getTextureName() + "_top");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (side == 0 || side == 1)
		{
			return topIcon;
		}

		return side == 3 ? blockIcon : sideIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side)
	{
		if (side == 0 || side == 1)
		{
			return topIcon;
		}

		return blockAccess.getBlockMetadata(x, y, z) == side ? blockIcon : sideIcon;
	}
}
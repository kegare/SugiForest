package kegare.sugiforest.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kegare.sugiforest.block.SugiBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWoodSlabSugi extends ItemBlock
{
	public ItemWoodSlabSugi(int blockID)
	{
		super(blockID);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (itemstack.stackSize == 0)
		{
			return false;
		}
		else if (!player.canPlayerEdit(x, y, z, side, itemstack))
		{
			return false;
		}
		else
		{
			int blockId = world.getBlockId(x, y, z);
			int metadata = world.getBlockMetadata(x, y, z);
			boolean flag = (metadata & 8) != 0;

			if ((side == 1 && !flag || side == 0 && flag) && blockId == itemID)
			{
				Block block = SugiBlock.planksSugi.or(Block.planks);

				if (world.checkNoEntityCollision(block.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlock(x, y, z, block.blockID, 1, 3))
				{
					world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);

					--itemstack.stackSize;
				}

				return true;
			}
			else
			{
				int X = x;
				int Y = y;
				int Z = z;

				if (side == 0)
				{
					--y;
				}
				else if (side == 1)
				{
					++y;
				}
				else if (side == 2)
				{
					--z;
				}
				else if (side == 3)
				{
					++z;
				}
				else if (side == 4)
				{
					--x;
				}
				else if (side == 5)
				{
					++x;
				}

				blockId = world.getBlockId(x, y, z);
				metadata = world.getBlockMetadata(x, y, z);
				flag = (metadata & 8) != 0;

				if (blockId == itemID)
				{
					Block block = SugiBlock.planksSugi.or(Block.planks);

					if (world.checkNoEntityCollision(block.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlock(x, y, z, block.blockID, 1, 3))
					{
						world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);

						--itemstack.stackSize;
					}

					return true;
				}
				else
				{
					return super.onItemUse(itemstack, player, world, X, Y, Z, side, hitX, hitY, hitZ);
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canPlaceItemBlockOnSide(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack itemstack)
	{
		int block = world.getBlockId(x, y, z);
		int metadata = world.getBlockMetadata(x, y, z);
		boolean flag = (metadata & 8) != 0;

		if ((side == 1 && !flag || side == 0 && flag) && block == itemID)
		{
			return true;
		}
		else
		{
			int X = x;
			int Y = y;
			int Z = z;

			if (side == 0)
			{
				--y;
			}
			else if (side == 1)
			{
				++y;
			}
			else if (side == 2)
			{
				--z;
			}
			else if (side == 3)
			{
				++z;
			}
			else if (side == 4)
			{
				--x;
			}
			else if (side == 5)
			{
				++x;
			}

			block = world.getBlockId(x, y, z);
			metadata = world.getBlockMetadata(x, y, z);
			flag = (metadata & 8) != 0;

			return block == itemID || super.canPlaceItemBlockOnSide(world, X, Y, Z, side, player, itemstack);
		}
	}
}
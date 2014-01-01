package kegare.sugiforest.block;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kegare.sugiforest.core.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import java.util.ArrayList;
import java.util.Random;

public class BlockLeavesSugi extends BlockLeavesBase implements IShearable
{
	private Icon[] leavesIcon = new Icon[2];

	private int[] adjacentTreeBlocks;

	public BlockLeavesSugi(int blockID, String name)
	{
		super(blockID, Material.leaves, true);
		this.setUnlocalizedName(name);
		this.setTextureName("sugiforest:leaves_sugi");
		this.setHardness(0.2F);
		this.setLightOpacity(1);
		this.setStepSound(soundGrassFootstep);
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		Block.setBurnProperties(blockID, 30, 60);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor()
	{
		return 6726755;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int metadata)
	{
		return getBlockColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z)
	{
		int r = 0;
		int g = 0;
		int b = 0;

		for (int i = -1; i <= 1; ++i)
		{
			for (int j = -1; j <= 1; ++j)
			{
				int color = world.getBiomeGenForCoords(x + i, z + j).getBiomeFoliageColor();
				r += (color & 16711680) >> 16;
				g += (color & 65280) >> 8;
				b += color & 255;
			}
		}

		return (r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister)
	{
		leavesIcon[0] = iconRegister.registerIcon(getTextureName());
		leavesIcon[1] = iconRegister.registerIcon(getTextureName() + "_opacity");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata)
	{
		if (Config.leavesSugiGraphicsLevel >= 0 && Config.leavesSugiGraphicsLevel < 2)
		{
			return leavesIcon[Config.leavesSugiGraphicsLevel];
		}

		return leaves.graphicsLevel ? leavesIcon[0] : leavesIcon[1];
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int blockID, int metadata)
	{
		if (world.checkChunksExist(x - 2, y - 2, z - 2, x + 2, y + 2, z + 2))
		{
			for (int i = -1; i <= 1; i++)
			{
				for (int j = -1; j <= 1; j++)
				{
					for (int k = -1; k <= 1; k++)
					{
						int block = world.getBlockId(x + i, y + j, z + k);

						if (blocksList[block] != null)
						{
							blocksList[block].beginLeavesDecay(world, x + i, y + j, z + k);
						}
					}
				}
			}
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		if (!world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);

			if ((metadata & 8) != 0)
			{
				int var1 = 4;
				int var2 = var1 + 1;
				byte var3 = 32;
				int var4 = var3 * var3;
				int var5 = var3 / 2;

				if (adjacentTreeBlocks == null)
				{
					adjacentTreeBlocks = new int[var3 * var3 * var3];
				}

				int var6;

				if (world.checkChunksExist( x - var2, y - var2, z - var2, x + var2, y + var2, z + var2))
				{
					int var7;
					int var8;
					int var9;

					for (var6 = -var1; var6 <= var1; ++var6)
					{
						for (var7 = -var1; var7 <= var1; ++var7)
						{
							for (var8 = -var1; var8 <= var1; ++var8)
							{
								var9 = world.getBlockId(x + var6, y + var7, z + var8);
								Block block = blocksList[var9];

								if (block != null && block.canSustainLeaves(world, x + var6, y + var7, z + var8))
								{
									adjacentTreeBlocks[(var6 + var5) * var4 + (var7 + var5) * var3 + var8 + var5] = 0;
								}
								else if (block != null && block.isLeaves(world, x + var6, y + var7, z + var8))
								{
									adjacentTreeBlocks[(var6 + var5) * var4 + (var7 + var5) * var3 + var8 + var5] = -2;
								}
								else
								{
									adjacentTreeBlocks[(var6 + var5) * var4 + (var7 + var5) * var3 + var8 + var5] = -1;
								}
							}
						}
					}

					for (var6 = 1; var6 <= 4; ++var6)
					{
						for (var7 = -var1; var7 <= var1; ++var7)
						{
							for (var8 = -var1; var8 <= var1; ++var8)
							{
								for (var9 = -var1; var9 <= var1; ++var9)
								{
									if (adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5) * var3 + var9 + var5] == var6 - 1)
									{
										if (adjacentTreeBlocks[(var7 + var5 - 1) * var4 + (var8 + var5) * var3 + var9 + var5] == -2)
										{
											adjacentTreeBlocks[(var7 + var5 - 1) * var4 + (var8 + var5) * var3 + var9 + var5] = var6;
										}

										if (adjacentTreeBlocks[(var7 + var5 + 1) * var4 + (var8 + var5) * var3 + var9 + var5] == -2)
										{
											adjacentTreeBlocks[(var7 + var5 + 1) * var4 + (var8 + var5) * var3 + var9 + var5] = var6;
										}

										if (adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5 - 1) * var3 + var9 + var5] == -2)
										{
											adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5 - 1) * var3 + var9 + var5] = var6;
										}

										if (adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5 + 1) * var3 + var9 + var5] == -2)
										{
											adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5 + 1) * var3 + var9 + var5] = var6;
										}

										if (adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5) * var3 + (var9 + var5 - 1)] == -2)
										{
											adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5) * var3 + (var9 + var5 - 1)] = var6;
										}

										if (adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5) * var3 + var9 + var5 + 1] == -2)
										{
											adjacentTreeBlocks[(var7 + var5) * var4 + (var8 + var5) * var3 + var9 + var5 + 1] = var6;
										}
									}
								}
							}
						}
					}
				}

				var6 = adjacentTreeBlocks[var5 * var4 + var5 * var3 + var5];

				if (var6 >= 0)
				{
					world.setBlockMetadataWithNotify(x, y, z, metadata & -9, 3);
				}
				else
				{
					dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);

					world.setBlockToAir(x, y, z);
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		if (world.canLightningStrikeAt(x, y + 1, z) && !world.doesBlockHaveSolidTopSurface(x, y - 1, z) && random.nextInt(15) == 1)
		{
			double ptX = (double)((float)x + random.nextFloat());
			double ptY = (double)y - 0.05D;
			double ptZ = (double)((float)z + random.nextFloat());

			world.spawnParticle("dripWater", ptX, ptY, ptZ, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public int quantityDropped(Random random)
	{
		return random.nextInt(30) == 0 ? 1 : 0;
	}

	@Override
	public int idDropped(int metadata, Random random, int fortune)
	{
		return SugiBlock.saplingSugi.or(sapling).blockID;
	}

	@Override
	public void beginLeavesDecay(World world, int x, int y, int z)
	{
		world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) | 8, 3);
	}

	@Override
	public boolean isLeaves(World world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public boolean isShearable(ItemStack itemstack, World world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack itemstack, World world, int x, int y, int z, int fortune)
	{
		return Lists.newArrayList(new ItemStack(blockID, 1, world.getBlockMetadata(x, y, z)));
	}
}
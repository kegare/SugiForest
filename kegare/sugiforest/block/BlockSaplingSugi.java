package kegare.sugiforest.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kegare.sugiforest.world.WorldGenSugiTree;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.List;
import java.util.Random;

public class BlockSaplingSugi extends BlockSapling
{
	public BlockSaplingSugi(int blockID, String name)
	{
		super(blockID);
		this.setUnlocalizedName(name);
		this.setTextureName("sugiforest:sapling_sugi");
		this.setStepSound(soundGrassFootstep);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon(getTextureName());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int metadata)
	{
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int blockID, CreativeTabs creativeTabs, List list)
	{
		list.add(new ItemStack(blockID, 1, 0));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		if (!world.isRemote)
		{
			super.updateTick(world, x, y, z, random);

			if (world.getBlockLightValue(x, y + 1, z) >= 9 && random.nextInt(7) == 0)
			{
				markOrGrowMarked(world, x, y, z, random);
			}
		}
	}

	@Override
	public void growTree(World world, int x, int y, int z, Random random)
	{
		if (!TerrainGen.saplingGrowTree(world, random, x, y, z))
		{
			return;
		}

		world.setBlockToAir(x, y, z);

		if (!(new WorldGenSugiTree(true)).generate(world, random, x, y, z))
		{
			world.setBlock(x, y, z, blockID, world.getBlockMetadata(x, y, z), 3);
		}
	}

	@Override
	public void markOrGrowMarked(World world, int x, int y, int z, Random random)
	{
		int metadata = world.getBlockMetadata(x, y, z);

		if ((metadata & 8) == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, metadata | 8, 3);
		}
		else
		{
			growTree(world, x, y, z, random);
		}
	}

	@Override
	public boolean isSameSapling(World world, int x, int y, int z, int metadata)
	{
		return world.getBlockId(x, y, z) == blockID;
	}

	@Override
	public int damageDropped(int metadata)
	{
		return 0;
	}
}
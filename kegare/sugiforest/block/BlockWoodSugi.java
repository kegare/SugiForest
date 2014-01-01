package kegare.sugiforest.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

import java.util.Random;

public class BlockWoodSugi extends Block
{
	public BlockWoodSugi(int blockID, String name)
	{
		super(blockID, Material.wood);
		this.setUnlocalizedName(name);
		this.setTextureName("sugiforest:planks_sugi");
		this.setHardness(1.75F);
		this.setResistance(4.75F);
		this.setStepSound(soundWoodFootstep);
		this.setCreativeTab(CreativeTabs.tabBlock);
		Block.setBurnProperties(blockID, 5, 20);
	}

	@Override
	public int quantityDropped(int metadata, int fortune, Random random)
	{
		return metadata == 1 ? 2 : 1;
	}

	@Override
	public int idDropped(int metadata, Random random, int fortune)
	{
		return metadata == 1 ? SugiBlock.woodSlabSugi.or(this).blockID : blockID;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z) == 1 ? SugiBlock.woodSlabSugi.or(this).blockID : blockID;
	}
}
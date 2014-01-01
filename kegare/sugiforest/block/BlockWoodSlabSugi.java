package kegare.sugiforest.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHalfSlab;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;

public class BlockWoodSlabSugi extends BlockHalfSlab
{
	public BlockWoodSlabSugi(int blockID, String name)
	{
		super(blockID, false, Material.wood);
		this.setUnlocalizedName(name);
		this.setTextureName("sugiforest:planks_sugi");
		this.setHardness(1.75F);
		this.setResistance(4.75F);
		this.setStepSound(soundWoodFootstep);
		this.setCreativeTab(CreativeTabs.tabBlock);
		Block.useNeighborBrightness[blockID] = true;
		Block.setBurnProperties(blockID, 5, 20);
	}

	@Override
	public String getFullSlabName(int metadata)
	{
		return getUnlocalizedName();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int idPicked(World world, int x, int y, int z)
	{
		return SugiBlock.woodSlabSugi.or(woodSingleSlab).blockID;
	}
}
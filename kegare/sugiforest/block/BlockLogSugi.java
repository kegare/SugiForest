package kegare.sugiforest.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

import java.util.List;
import java.util.Random;

public class BlockLogSugi extends BlockLog
{
	public BlockLogSugi(int blockID, String name)
	{
		super(blockID);
		this.setUnlocalizedName(name);
		this.setTextureName("sugiforest:log_sugi");
		this.setHardness(2.0F);
		this.setStepSound(soundWoodFootstep);
		Block.setBurnProperties(blockID, 5, 5);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister)
	{
		blockIcon = iconRegister.registerIcon(getTextureName());
		field_111051_a = iconRegister.registerIcon(getTextureName() + "_top");
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected Icon getSideIcon(int metadata)
	{
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected Icon getEndIcon(int metadata)
	{
		return field_111051_a;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int blockID, CreativeTabs creativeTabs, List list)
	{
		list.add(new ItemStack(blockID, 1, 0));
	}

	@Override
	public int idDropped(int metadata, Random random, int fortune)
	{
		return blockID;
	}
}
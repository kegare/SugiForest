package kegare.sugiforest.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

public class BlockStairsSugi extends BlockStairs
{
	public BlockStairsSugi(int blockID, String name)
	{
		super(blockID, SugiBlock.planksSugi.or(Block.planks), 0);
		this.setUnlocalizedName(name);
		this.setTextureName("sugiforest:planks_sugi");
		Block.useNeighborBrightness[blockID] = true;
		Block.setBurnProperties(blockID, 5, 20);
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
}
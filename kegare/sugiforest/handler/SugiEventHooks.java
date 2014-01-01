package kegare.sugiforest.handler;

import kegare.sugiforest.block.BlockLeavesSugi;
import kegare.sugiforest.block.BlockSaplingSugi;
import kegare.sugiforest.block.SugiBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;

public class SugiEventHooks
{
	@ForgeSubscribe
	public void onBreakSpeed(BreakSpeed event)
	{
		ItemStack itemstack = event.entityPlayer.getCurrentEquippedItem();

		if (itemstack != null && itemstack.getItem() instanceof ItemShears && event.block instanceof BlockLeavesSugi)
		{
			event.newSpeed = itemstack.getStrVsBlock(Block.leaves);
		}
	}

	@ForgeSubscribe
	public void onBonemealUsed(BonemealEvent event)
	{
		World world = event.world;

		if (!world.isRemote && SugiBlock.saplingSugi.isPresent() && event.ID == SugiBlock.saplingSugi.get().blockID)
		{
			if ((double)world.rand.nextFloat() < 0.45D)
			{
				((BlockSaplingSugi)SugiBlock.saplingSugi.get()).markOrGrowMarked(world, event.X, event.Y, event.Z, world.rand);
			}

			event.setResult(Result.ALLOW);
		}
	}
}
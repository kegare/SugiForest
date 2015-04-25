package sugiforest.handler;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sugiforest.block.SugiBlocks;
import sugiforest.core.Config;
import sugiforest.core.SugiForest;
import sugiforest.util.Version;

public class SugiEventHooks
{
	public static final SugiEventHooks instance = new SugiEventHooks();

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event)
	{
		if (event.modID.equals("sugiforest"))
		{
			Config.syncConfig();
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientConnected(ClientConnectedToServerEvent event)
	{
		if (Version.DEV_DEBUG || Config.versionNotify && Version.isOutdated())
		{
			IChatComponent component = new ChatComponentTranslation("sugiforest.version.message", EnumChatFormatting.DARK_GREEN + "SugiForest" + EnumChatFormatting.RESET);
			component.appendText(" : " + EnumChatFormatting.YELLOW + Version.getLatest());
			component.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, SugiForest.metadata.url));

			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(component);
		}
	}

	@SubscribeEvent
	public void onItemPickup(ItemPickupEvent event)
	{
		EntityPlayer player = event.player;
		EntityItem entity = event.pickedUp;
		ItemStack itemstack = entity.getEntityItem();

		if (itemstack != null && itemstack.getItem() == Item.getItemFromBlock(SugiBlocks.sugi_log))
		{
			player.triggerAchievement(AchievementList.mineWood);
		}
	}
}
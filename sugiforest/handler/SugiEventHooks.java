package sugiforest.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.common.ForgeVersion.Status;
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
import sugiforest.world.BiomeGenSugiForest;

public class SugiEventHooks
{
	@SideOnly(Side.CLIENT)
	private float fogRender;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event)
	{
		if (event.getModID().equals(SugiForest.MODID))
		{
			Config.syncConfig();
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onFogDensity(FogDensity event)
	{
		Entity entity = event.getEntity();
		World world = entity.worldObj;
		BlockPos pos = entity.getPosition();
		BiomeGenBase biome = world.getBiomeGenForCoords(pos);

		float f = (float)Math.pow(0.1D, 6);
		boolean flag = false;

		if (biome instanceof BiomeGenSugiForest)
		{
			long time = world.getWorldTime();
			int depth = 0;

			if (time >= 12000L && time < 13800 || time >= 22200 && time < 1000)
			{
				depth = 5;
			}
			else if (time >= 13800 && time < 22200)
			{
				depth = 4;
			}

			if (depth > 0)
			{
				float density = (float)Math.abs(Math.pow((entity.posY - 63) / (127 - 63), depth));

				if (fogRender < density)
				{
					fogRender += f;
				}
				else if (fogRender > density)
				{
					fogRender -= f;
				}

				flag = true;
			}
		}

		if (!flag && fogRender > 0.0F)
		{
			fogRender -= f;
		}

		if (fogRender > 0.0F)
		{
			GlStateManager.setFog(GlStateManager.FogMode.EXP);

			event.setDensity(fogRender);
			event.setCanceled(true);
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onConnected(ClientConnectedToServerEvent event)
	{
		Minecraft mc = FMLClientHandler.instance().getClient();

		if (Version.DEV_DEBUG || Version.getStatus() == Status.AHEAD || Version.getStatus() == Status.BETA || Config.versionNotify && Version.isOutdated())
		{
			ITextComponent name = new TextComponentString(SugiForest.metadata.name);
			name.getChatStyle().setColor(TextFormatting.DARK_GREEN);
			ITextComponent latest = new TextComponentString(Version.getLatest().toString());
			latest.getChatStyle().setColor(TextFormatting.YELLOW);

			ITextComponent message;

			message = new TextComponentTranslation("sugiforest.version.message", name);
			message.appendText(" : ").appendSibling(latest);
			message.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, SugiForest.metadata.url));

			mc.ingameGUI.getChatGUI().printChatMessage(message);
			message = null;

			if (Version.isBeta())
			{
				message = new TextComponentTranslation("sugiforest.version.message.beta", name);
			}
			else if (Version.isAlpha())
			{
				message = new TextComponentTranslation("sugiforest.version.message.alpha", name);
			}

			if (message != null)
			{
				mc.ingameGUI.getChatGUI().printChatMessage(message);
			}
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
			player.addStat(AchievementList.mineWood);
		}
	}
}
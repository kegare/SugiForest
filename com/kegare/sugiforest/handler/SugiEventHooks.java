/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.handler;

import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

import com.kegare.sugiforest.core.Config;
import com.kegare.sugiforest.core.SugiForest;
import com.kegare.sugiforest.util.Version;
import com.kegare.sugiforest.util.Version.Status;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SugiEventHooks
{
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event)
	{
		if (event.modID.equals(SugiForest.MODID))
		{
			Config.syncConfig();
		}
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientConnected(ClientConnectedToServerEvent event)
	{
		if (Version.getStatus() == Status.PENDING || Version.getStatus() == Status.FAILED)
		{
			Version.versionCheck();
		}
		else if (Version.DEV_DEBUG || Config.versionNotify && Version.isOutdated())
		{
			IChatComponent component = new ChatComponentTranslation("sugiforest.version.message", EnumChatFormatting.DARK_GREEN + "SugiForest" + EnumChatFormatting.RESET);
			component.appendText(" : " + EnumChatFormatting.YELLOW + Version.getLatest());
			component.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, SugiForest.metadata.url));

			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(component);
		}
	}
}
/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.client.config;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sugiforest.core.Config;

import com.google.common.collect.Lists;

@SideOnly(Side.CLIENT)
public class SugiConfigGui extends GuiConfig
{
	public SugiConfigGui(GuiScreen parent)
	{
		super(parent, getConfigElements(), "sugiforest", false, false, I18n.format("sugiforest.config.title"));
	}

	private static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = Lists.newArrayList();

		list.addAll(new ConfigElement(Config.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements());
		list.addAll(new ConfigElement(Config.config.getCategory("sugiforest")).getChildElements());

		return list;
	}
}
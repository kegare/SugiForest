/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.item;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sugiforest.util.SugiUtils;

public class SugiItems
{
	public static final ItemMystSap myst_sap = new ItemMystSap();

	public static void registerItems()
	{
		GameRegistry.registerItem(myst_sap, "myst_sap");

		SugiUtils.registerOreDict(myst_sap, "mystSap", "sapMyst");
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels()
	{
		ModelLoader.setCustomModelResourceLocation(myst_sap, 0, new ModelResourceLocation("sugiforest:myst_sap", "inventory"));
	}
}
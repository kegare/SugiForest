package sugiforest.client.config;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sugiforest.core.Config;
import sugiforest.core.SugiForest;

@SideOnly(Side.CLIENT)
public class SugiConfigGui extends GuiConfig
{
	public SugiConfigGui(GuiScreen parent)
	{
		super(parent, getConfigElements(), SugiForest.MODID, false, false, I18n.format(Config.LANG_KEY + "title"));
	}

	private static List<IConfigElement> getConfigElements()
	{
		List<IConfigElement> list = Lists.newArrayList();

		for (String category : Config.config.getCategoryNames())
		{
			list.addAll(new ConfigElement(Config.config.getCategory(category)).getChildElements());
		}

		return list;
	}
}
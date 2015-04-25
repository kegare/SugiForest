package sugiforest.item;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class SugiItems
{
	public static final ItemMystSap myst_sap = new ItemMystSap();

	public static void registerItems()
	{
		GameRegistry.registerItem(myst_sap, "myst_sap");

		OreDictionary.registerOre("mystSap", myst_sap);
		OreDictionary.registerOre("sapMyst", myst_sap);
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels()
	{
		ModelLoader.setCustomModelResourceLocation(myst_sap, 0, new ModelResourceLocation("sugiforest:myst_sap", "inventory"));
	}
}
package sugiforest.item;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sugiforest.core.SugiForest;

public class SugiItems
{
	public static final ItemMystSap myst_sap = new ItemMystSap();

	public static void registerItems()
	{
		myst_sap.setRegistryName("myst_sap");

		GameRegistry.register(myst_sap);
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels()
	{
		registerModel(myst_sap);
	}

	@SideOnly(Side.CLIENT)
	public static void registerModel(Item item, String modelName)
	{
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(SugiForest.MODID + ":" + modelName, "inventory"));
	}

	@SideOnly(Side.CLIENT)
	public static void registerModel(Item item)
	{
		registerModel(item, item.getRegistryName().getResourcePath());
	}

	@SideOnly(Side.CLIENT)
	public static void registerModelWithMeta(Item item, String... modelName)
	{
		List<ModelResourceLocation> models = Lists.newArrayList();

		for (String model : modelName)
		{
			models.add(new ModelResourceLocation(SugiForest.MODID + ":" + model, "inventory"));
		}

		ModelBakery.registerItemVariants(item, models.toArray(new ResourceLocation[models.size()]));

		for (int i = 0; i < models.size(); ++i)
		{
			ModelLoader.setCustomModelResourceLocation(item, i, models.get(i));
		}
	}
}
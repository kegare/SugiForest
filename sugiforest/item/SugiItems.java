package sugiforest.item;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sugiforest.core.SugiForest;

public class SugiItems
{
	private static final List<Item> ITEMS = NonNullList.create();

	public static final ItemMystSap MYST_SAP = new ItemMystSap();

	public static List<Item> getItems()
	{
		return Collections.unmodifiableList(ITEMS);
	}

	public static void register(IForgeRegistry<Item> registry, Item item)
	{
		ITEMS.add(item);

		if (item instanceof ItemBlock && item.getRegistryName() == null)
		{
			item.setRegistryName(((ItemBlock)item).getBlock().getRegistryName());
		}

		registry.register(item);
	}

	public static void registerItems(IForgeRegistry<Item> registry)
	{
		register(registry, MYST_SAP.setRegistryName("myst_sap"));
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels()
	{
		registerModel(MYST_SAP);
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
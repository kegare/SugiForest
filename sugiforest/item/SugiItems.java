package sugiforest.item;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
import sugiforest.core.SugiForest;

public class SugiItems
{
	private static final NonNullList<Item> ITEMS = NonNullList.create();

	public static final ToolMaterial SUGI = EnumHelper.addToolMaterial("SUGI", 0, 43, 3.0F, 0.5F, 10);

	public static final ItemMystSap MYST_SAP = new ItemMystSap();
	public static final ItemSugiSword SUGI_SWORD = new ItemSugiSword();
	public static final ItemSugiPickaxe SUGI_PICKAXE = new ItemSugiPickaxe();
	public static final ItemSugiAxe SUGI_AXE = new ItemSugiAxe();
	public static final ItemSugiShovel SUGI_SHOVEL = new ItemSugiShovel();
	public static final ItemSugiHoe SUGI_HOE = new ItemSugiHoe();

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
		register(registry, SUGI_SWORD.setRegistryName("sugi_sword"));
		register(registry, SUGI_PICKAXE.setRegistryName("sugi_pickaxe"));
		register(registry, SUGI_AXE.setRegistryName("sugi_axe"));
		register(registry, SUGI_SHOVEL.setRegistryName("sugi_shovel"));
		register(registry, SUGI_HOE.setRegistryName("sugi_hoe"));
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels()
	{
		registerModel(MYST_SAP);
		registerModel(SUGI_SWORD);
		registerModel(SUGI_PICKAXE);
		registerModel(SUGI_AXE);
		registerModel(SUGI_SHOVEL);
		registerModel(SUGI_HOE);
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
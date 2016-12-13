package sugiforest.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Metadata;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import sugiforest.api.SugiForestAPI;
import sugiforest.block.SugiBlocks;
import sugiforest.handler.SugiEventHooks;
import sugiforest.handler.SugiForestAPIHandler;
import sugiforest.handler.SugiFuelHandler;
import sugiforest.handler.SugiWorldGenerator;
import sugiforest.item.SugiItems;
import sugiforest.util.Version;
import sugiforest.world.SugiBiomes;

@Mod
(
	modid = SugiForest.MODID,
	guiFactory = "sugiforest.client.config.SugiGuiFactory",
	updateJSON = "https://dl.dropboxusercontent.com/u/51943112/versions/sugiforest.json"
)
@EventBusSubscriber
public class SugiForest
{
	public static final String MODID = "sugiforest";

	@Metadata(MODID)
	public static ModMetadata metadata;

	public static final CreativeTabSugiForest TAB_SUGI = new CreativeTabSugiForest();

	@EventHandler
	public void construct(FMLConstructionEvent event)
	{
		SugiForestAPI.apiHandler = new SugiForestAPIHandler();

		Version.initVersion();
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		IForgeRegistry<Block> registry = event.getRegistry();

		SugiBlocks.registerBlocks(registry);
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		IForgeRegistry<Item> registry = event.getRegistry();

		SugiBlocks.registerItemBlocks(registry);
		SugiItems.registerItems(registry);
	}

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event)
	{
		IForgeRegistry<SoundEvent> registry = event.getRegistry();

		SugiSounds.registerSounds(registry);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Config.syncConfig();

		if (event.getSide().isClient())
		{
			SugiBlocks.registerModels();
			SugiItems.registerModels();
		}

		SugiBlocks.registerTileEntities();
		SugiBlocks.registerOreDicts();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (event.getSide().isClient())
		{
			SugiBlocks.registerBlockColors();
			SugiBlocks.registerItemBlockColors();
		}

		SugiBlocks.registerRecipes();

		SugiBiomes.registerBiomes();

		MinecraftForge.EVENT_BUS.register(new SugiEventHooks());

		GameRegistry.registerWorldGenerator(new SugiWorldGenerator(), 10);
		GameRegistry.registerFuelHandler(new SugiFuelHandler());
	}
}
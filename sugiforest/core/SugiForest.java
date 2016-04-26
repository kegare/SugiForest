package sugiforest.core;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Metadata;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
public class SugiForest
{
	public static final String MODID = "sugiforest";

	@Metadata(MODID)
	public static ModMetadata metadata;

	public static final CreativeTabSugiForest tabSugiForest = new CreativeTabSugiForest();

	@EventHandler
	public void construct(FMLConstructionEvent event)
	{
		SugiForestAPI.apiHandler = new SugiForestAPIHandler();

		Version.initVersion();
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Config.syncConfig();

		SugiBlocks.registerBlocks();
		SugiItems.registerItems();

		if (event.getSide().isClient())
		{
			SugiBlocks.registerModels();
			SugiItems.registerModels();
		}

		SugiSounds.registerSounds();
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
package sugiforest.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Metadata;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;
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
	updateJSON = "https://raw.githubusercontent.com/kegare/SugiForest/master/sugiforest.json"
)
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

		MinecraftForge.EVENT_BUS.register(this);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Config.syncConfig();
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event)
	{
		IForgeRegistry<Block> registry = event.getRegistry();

		SugiBlocks.registerBlocks(registry);
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		IForgeRegistry<Item> registry = event.getRegistry();

		SugiBlocks.registerItemBlocks(registry);
		SugiItems.registerItems(registry);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event)
	{
		SugiBlocks.registerModels();
		SugiItems.registerModels();
	}

	@SubscribeEvent
	public void registerBiomes(RegistryEvent.Register<Biome> event)
	{
		IForgeRegistry<Biome> registry = event.getRegistry();

		SugiBiomes.registerBiomes(registry);
	}

	@SubscribeEvent
	public void registerSounds(RegistryEvent.Register<SoundEvent> event)
	{
		IForgeRegistry<SoundEvent> registry = event.getRegistry();

		SugiSounds.registerSounds(registry);
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		if (event.getSide().isClient())
		{
			SugiBlocks.registerBlockColors();
			SugiBlocks.registerItemBlockColors();
		}

		SugiBlocks.registerSmeltingRecipes();

		SugiBlocks.registerTileEntities();
		SugiBlocks.registerOreDicts();

		SugiBiomes.registerBiomeTypes();

		MinecraftForge.EVENT_BUS.register(new SugiEventHooks());

		GameRegistry.registerWorldGenerator(new SugiWorldGenerator(), 10);
		GameRegistry.registerFuelHandler(new SugiFuelHandler());
	}
}
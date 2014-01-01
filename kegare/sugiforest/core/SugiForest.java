package kegare.sugiforest.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import kegare.sugiforest.block.SugiBlock;
import kegare.sugiforest.handler.*;
import kegare.sugiforest.util.Version;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.classloading.FMLForgePlugin;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@Mod
(
	modid = "kegare.sugiforest"
)
@NetworkMod
(
	clientSideRequired = true,
	serverSideRequired = false,
	channels = {"sugiforest.sync"},
	packetHandler = SugiPacketHandler.class,
	connectionHandler = SugiConnectionHandler.class
)
public class SugiForest
{
	public static BiomeGenBase sugiForest;

	@Metadata("kegare.sugiforest")
	public static ModMetadata metadata;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Version.versionCheck();

		Config.buildConfig();
		Config.initialize();

		SugiBlock.configure();

		registerRecipes();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		GameRegistry.registerWorldGenerator(new SugiWorldGenerator());
		GameRegistry.registerFuelHandler(new SugiFuelHandler());

		if (sugiForest != null)
		{
			GameRegistry.addBiome(sugiForest);

			BiomeManager.addSpawnBiome(sugiForest);
			BiomeManager.addStrongholdBiome(sugiForest);

			BiomeDictionary.registerBiomeType(sugiForest, Type.FOREST);
		}

		MinecraftForge.EVENT_BUS.register(new SugiEventHooks());
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		Config.saveConfig(false);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandSugiForest());

		if (event.getSide().isServer() && (!FMLForgePlugin.RUNTIME_DEOBF || Config.versionNotify && Version.isOutdated()))
		{
			event.getServer().logInfo("A new SugiForest version is available : " + Version.LATEST.or(Version.CURRENT.orNull()));
		}
	}

	protected static void registerRecipes()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(Block.music, "###", "#X#", "###", '#', "planksSugi", 'X', Item.redstone));
		GameRegistry.addRecipe(new ShapedOreRecipe(Block.bed, "###", "XXX", '#', Block.cloth, 'X', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Block.pistonBase, "###", "XYX", "XZX", '#', "planksSugi", 'X', "cobblestone", 'Y', Item.redstone, 'Z', Item.ingotIron));
		GameRegistry.addRecipe(new ShapedOreRecipe(Block.bookShelf, "###", "XXX", "###", '#', "planksSugi", 'X', Item.book));
		GameRegistry.addRecipe(new ShapedOreRecipe(Block.chest, "###", "# #", "###", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Block.workbench, "##", "##", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Item.doorWood, "##", "##", "##", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Block.pressurePlatePlanks, "##", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Block.jukebox, "###", "#X#", "###", '#', "planksSugi", 'X', Item.diamond));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.trapdoor, 2), "###", "###", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Block.fenceGate, "#X#", "#X#", '#', "stickWood", 'X', "planksSugi"));
		GameRegistry.addRecipe(new ShapelessOreRecipe(Block.woodenButton, "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Block.tripWire, 2), "#", "X", "Y", '#', Item.ingotIron, 'X', "stickWood", 'Y', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Block.daylightSensor, "###", "XXX", "YYY", '#', Block.glass, 'X', Item.netherQuartz, 'Y', "woodSlabSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.stick, 4), "#", "#", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.bowlEmpty, 4), "# #", " # ", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Item.sign, 3), "###", "###", " X ", '#', "planksSugi", 'X', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Item.boat, "# #", "###", '#', "planksSugi"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Item.swordWood, "#", "#", "X", '#', "planksSugi", 'X', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Item.pickaxeWood, "###", " X ", " X ", '#', "planksSugi", 'X', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Item.axeWood, "##", "#X", " X", '#', "planksSugi", 'X', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Item.shovelWood, "#", "X", "X", '#', "planksSugi", 'X', "stickWood"));
		GameRegistry.addRecipe(new ShapedOreRecipe(Item.hoeWood, "##", " X", " X", '#', "planksSugi", 'X', "stickWood"));
	}
}
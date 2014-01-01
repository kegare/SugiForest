package kegare.sugiforest.core;

import com.google.common.base.Optional;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import kegare.sugiforest.block.*;
import kegare.sugiforest.util.SugiLog;
import kegare.sugiforest.world.BiomeGenSugiForest;
import net.minecraft.block.Block;
import net.minecraftforge.common.Configuration;

import java.io.File;

public class Config
{
	private static File cfgFile;
	private static Configuration config;

	@SideOnly(Side.CLIENT)
	public static int leavesSugiGraphicsLevel;

	public static boolean versionNotify;
	public static boolean saplingSugiCraftRecipe;

	public static int woodSugi;
	public static int leavesSugi;
	public static int saplingSugi;
	public static int planksSugi;
	public static int woodSlabSugi;
	public static int stairsWoodSugi;

	public static int biomeSugiForest;

	public static boolean sugiTreesOnHills;
	public static int sugiTreesOnSugiForest;

	public static void buildConfig()
	{
		cfgFile = new File(Loader.instance().getConfigDir(), "SugiForest.cfg");
		config = new Configuration(cfgFile);

		try
		{
			config.load();
		}
		catch (Exception e)
		{
			File dest = new File(cfgFile.getParentFile(),"SugiForest.cfg.bak");

			if (dest.exists())
			{
				dest.delete();
			}

			cfgFile.renameTo(dest);

			SugiLog.severe("A critical error occured reading the SugiForest.cfg file, defaults will be used - the invalid file is backed up at SugiForest.cfg.bak", e);
		}

		boolean isClient = FMLCommonHandler.instance().getSide().isClient();

		if (isClient)
		{
			config.addCustomCategoryComment("client", "Client-side only settings.");
		}

		config.addCustomCategoryComment(Configuration.CATEGORY_BLOCK, "If multiplayer, values must match on client-side and server-side.\nIf specify 0 for ID, it will be disabled.");
		config.addCustomCategoryComment("biome", "If multiplayer, server-side only.\nIf specify 0 for ID, it will be disabled.");
		config.addCustomCategoryComment("generate", "If multiplayer, server-side only.");

		if (isClient)
		{
			leavesSugiGraphicsLevel = config.get("client", "leavesSugiGraphicsLevel", 2, "Graphics level of Sugi Leaves texture. [0-2]\n0=FANCY fixation, 1=FAST fixation, 2=Video Settings").getInt(2);
		}

		versionNotify = config.get("general", "versionNotify", true, "Whether or not to notify when a new SugiForest version is available. [true/false]").getBoolean(true);
		saplingSugiCraftRecipe = config.get("general", "saplingSugiCraftRecipe", false, "Whether or not to add a craft recipe of Sugi Sapling. [true/false]\nNote: If multiplayer, values must match on client-side and server-side.").getBoolean(false);

		woodSugi = config.getBlock("woodSugi", 650, "BlockID - Sugi Wood").getInt(650);
		leavesSugi = config.getBlock("leavesSugi", 651, "BlockID - Sugi Leaves").getInt(651);
		saplingSugi = config.getBlock("saplingSugi", 652, "BlockID - Sugi Sapling").getInt(652);
		planksSugi = config.getBlock("planksSugi", 653, "BlockID - Sugi Wood Planks").getInt(653);
		woodSlabSugi = config.getBlock("woodSlabSugi", 654, "BlockID - Sugi Wood Slab").getInt(654);
		stairsWoodSugi = config.getBlock("stairsWoodSugi", 655, "BlockID - Sugi Wood Stairs").getInt(655);

		biomeSugiForest = config.get("biome", "sugiForest", 65, "BiomeID - Sugi Forest").getInt(65);

		sugiTreesOnHills = config.get("generate", "sugiTreesOnHills", true, "Whether or not to generate sugi trees on hills. [true/false]").getBoolean(true);
		sugiTreesOnSugiForest = config.get("generate", "sugiTreesOnSugiForest", 15, "Generation rate of sugi trees on Sugi Forest. [0-]").getInt(15);
	}

	public static boolean saveConfig(boolean forced)
	{
		if (cfgFile != null && config != null)
		{
			if (forced || !cfgFile.exists() || config.hasChanged())
			{
				config.save();

				return true;
			}
		}

		return false;
	}

	public static void initialize()
	{
		if (woodSugi > 0) SugiBlock.woodSugi = Optional.of((Block)new BlockLogSugi(woodSugi, "woodSugi"));
		if (leavesSugi > 0) SugiBlock.leavesSugi = Optional.of((Block)new BlockLeavesSugi(leavesSugi, "leavesSugi"));
		if (saplingSugi > 0) SugiBlock.saplingSugi = Optional.of((Block)new BlockSaplingSugi(saplingSugi, "saplingSugi"));
		if (planksSugi > 0) SugiBlock.planksSugi = Optional.of((Block)new BlockWoodSugi(planksSugi, "planksSugi"));
		if (woodSlabSugi > 0) SugiBlock.woodSlabSugi = Optional.of((Block)new BlockWoodSlabSugi(woodSlabSugi, "woodSlabSugi"));
		if (stairsWoodSugi > 0) SugiBlock.stairsWoodSugi = Optional.of((Block)new BlockStairsSugi(stairsWoodSugi, "stairsWoodSugi"));

		if (biomeSugiForest > 0) SugiForest.sugiForest = new BiomeGenSugiForest(biomeSugiForest);
	}
}
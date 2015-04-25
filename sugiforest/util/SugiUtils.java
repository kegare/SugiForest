package sugiforest.util;

import java.util.concurrent.ForkJoinPool;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.oredict.OreDictionary;

public class SugiUtils
{
	private static ForkJoinPool pool;

	public static ForkJoinPool getPool()
	{
		if (pool == null || pool.isShutdown())
		{
			pool = new ForkJoinPool();
		}

		return pool;
	}

	public static ModContainer getModContainer()
	{
		ModContainer mod = Loader.instance().getIndexedModList().get("sugiforest");

		if (mod == null)
		{
			mod = Loader.instance().activeModContainer();

			if (mod == null || !mod.getModId().equals("sugiforest"))
			{
				return new DummyModContainer("sugiforest");
			}
		}

		return mod;
	}

	public static void registerOreDict(Item item, String... names)
	{
		for (String name : names)
		{
			OreDictionary.registerOre(name, item);
		}
	}

	public static void registerOreDict(Block block, String... names)
	{
		for (String name : names)
		{
			OreDictionary.registerOre(name, block);
		}
	}

	public static void registerOreDict(ItemStack item, String... names)
	{
		for (String name : names)
		{
			OreDictionary.registerOre(name, item);
		}
	}

	public static WorldInfo getWorldInfo(World world)
	{
		WorldInfo worldInfo = world.getWorldInfo();

		if (worldInfo instanceof DerivedWorldInfo)
		{
			worldInfo = ObfuscationReflectionHelper.getPrivateValue(DerivedWorldInfo.class, (DerivedWorldInfo)worldInfo, "theWorldInfo", "field_76115_a");
		}

		return worldInfo;
	}
}
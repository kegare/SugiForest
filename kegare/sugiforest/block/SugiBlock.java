package kegare.sugiforest.block;

import com.google.common.base.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import kegare.sugiforest.core.Config;
import kegare.sugiforest.item.ItemWoodSlabSugi;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class SugiBlock
{
	public static Optional<Block> woodSugi = Optional.absent();
	public static Optional<Block> leavesSugi = Optional.absent();
	public static Optional<Block> saplingSugi = Optional.absent();
	public static Optional<Block> planksSugi = Optional.absent();
	public static Optional<Block> woodSlabSugi = Optional.absent();
	public static Optional<Block> stairsWoodSugi = Optional.absent();

	public static void configure()
	{
		Block block;

		if (woodSugi.isPresent())
		{
			block = woodSugi.get();

			OreDictionary.registerOre("woodSugi", block);

			GameRegistry.registerBlock(block, "woodSugi");

			MinecraftForge.setBlockHarvestLevel(block, "axe", 0);
		}

		if (leavesSugi.isPresent())
		{
			block = leavesSugi.get();

			OreDictionary.registerOre("leavesSugi", block);

			GameRegistry.registerBlock(block, "leavesSugi");
		}

		if (saplingSugi.isPresent())
		{
			block = saplingSugi.get();

			OreDictionary.registerOre("saplingSugi", block);

			GameRegistry.registerBlock(block, "saplingSugi");
			GameRegistry.addShapelessRecipe(new ItemStack(Item.stick), new ItemStack(block));

			if (Config.saplingSugiCraftRecipe)
			{
				GameRegistry.addShapelessRecipe(new ItemStack(block), new ItemStack(Block.sapling, 1, 0), new ItemStack(Block.sapling, 1, 1), new ItemStack(Item.dyePowder, 1, 15));
			}
		}

		if (planksSugi.isPresent())
		{
			block = planksSugi.get();

			OreDictionary.registerOre("planksSugi", block);

			GameRegistry.registerBlock(block, "planksSugi");
			GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(block, 4), "woodSugi"));

			MinecraftForge.setBlockHarvestLevel(block, "axe", 0);
		}

		if (woodSlabSugi.isPresent())
		{
			block = woodSlabSugi.get();

			OreDictionary.registerOre("woodSlabSugi", block);

			GameRegistry.registerBlock(block, ItemWoodSlabSugi.class, "woodSlabSugi");
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block, 6), "###", '#', "planksSugi"));

			MinecraftForge.setBlockHarvestLevel(block, "axe", 0);
		}

		if (stairsWoodSugi.isPresent())
		{
			block = stairsWoodSugi.get();

			OreDictionary.registerOre("stairsWoodSugi", block);

			GameRegistry.registerBlock(block, "stairsWoodSugi");
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block, 4), "  #", " ##", "###", '#', "planksSugi"));

			MinecraftForge.setBlockHarvestLevel(block, "axe", 0);
		}
	}
}
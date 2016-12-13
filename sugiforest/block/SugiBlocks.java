package sugiforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import sugiforest.item.ItemSugiChest;
import sugiforest.item.ItemSugiFallenLeaves;
import sugiforest.item.ItemSugiWoodSlab;
import sugiforest.item.SugiItems;
import sugiforest.tileentity.TileEntitySugiChest;

public class SugiBlocks
{
	public static final BlockSugiLog SUGI_LOG = new BlockSugiLog();
	public static final BlockSugiLeaves SUGI_LEAVES = new BlockSugiLeaves();
	public static final BlockSugiFallenLeaves SUGI_FALLEN_LEAVES = new BlockSugiFallenLeaves();
	public static final BlockSugiSapling SUGI_SAPLING = new BlockSugiSapling();
	public static final BlockSugiWood SUGI_PLANKS = new BlockSugiWood();
	public static final BlockSugiWoodSlab SUGI_SLAB = new BlockSugiWoodSlab();
	public static final BlockSugiStairs SUGI_STAIRS = new BlockSugiStairs();
	public static final BlockSugiFence SUGI_FENCE = new BlockSugiFence();
	public static final BlockSugiFenceGate SUGI_FENCE_GATE = new BlockSugiFenceGate();
	public static final BlockSugiChest SUGI_CHEST = new BlockSugiChest();

	public static void registerBlocks(IForgeRegistry<Block> registry)
	{
		registry.register(SUGI_LOG.setRegistryName("sugi_log"));
		registry.register(SUGI_LEAVES.setRegistryName("sugi_leaves"));
		registry.register(SUGI_FALLEN_LEAVES.setRegistryName("sugi_fallen_leaves"));
		registry.register(SUGI_SAPLING.setRegistryName("sugi_sapling"));
		registry.register(SUGI_PLANKS.setRegistryName("sugi_planks"));
		registry.register(SUGI_SLAB.setRegistryName("sugi_slab"));
		registry.register(SUGI_STAIRS.setRegistryName("sugi_stairs"));
		registry.register(SUGI_FENCE.setRegistryName("sugi_fence"));
		registry.register(SUGI_FENCE_GATE.setRegistryName("sugi_fence_gate"));
		registry.register(SUGI_CHEST.setRegistryName("sugi_chest"));

		Blocks.FIRE.setFireInfo(SUGI_LOG, 5, 5);
		Blocks.FIRE.setFireInfo(SUGI_LEAVES, 30, 60);
		Blocks.FIRE.setFireInfo(SUGI_FALLEN_LEAVES, 30, 60);
		Blocks.FIRE.setFireInfo(SUGI_SAPLING, 20, 60);
		Blocks.FIRE.setFireInfo(SUGI_PLANKS, 5, 20);
		Blocks.FIRE.setFireInfo(SUGI_SLAB, 5, 20);
		Blocks.FIRE.setFireInfo(SUGI_STAIRS, 5, 20);
		Blocks.FIRE.setFireInfo(SUGI_FENCE, 5, 20);
		Blocks.FIRE.setFireInfo(SUGI_FENCE_GATE, 5, 20);
		Blocks.FIRE.setFireInfo(SUGI_CHEST, 5, 5);

		BlockSugiFallenLeaves.addFallenSeed(new ItemStack(SUGI_SAPLING), 10);
		BlockSugiFallenLeaves.addFallenSeed(new ItemStack(Blocks.BROWN_MUSHROOM), 12);
		BlockSugiFallenLeaves.addFallenSeed(new ItemStack(Blocks.RED_MUSHROOM), 8);
		BlockSugiFallenLeaves.addFallenSeed(new ItemStack(Items.STICK), 10);
	}

	public static void registerItemBlocks(IForgeRegistry<Item> registry)
	{
		SugiItems.register(registry, new ItemBlock(SUGI_LOG));
		SugiItems.register(registry, new ItemBlock(SUGI_LEAVES));
		SugiItems.register(registry, new ItemSugiFallenLeaves(SUGI_FALLEN_LEAVES));
		SugiItems.register(registry, new ItemBlock(SUGI_SAPLING));
		SugiItems.register(registry, new ItemBlock(SUGI_PLANKS));
		SugiItems.register(registry, new ItemSugiWoodSlab(SUGI_SLAB));
		SugiItems.register(registry, new ItemBlock(SUGI_STAIRS));
		SugiItems.register(registry, new ItemBlock(SUGI_FENCE));
		SugiItems.register(registry, new ItemBlock(SUGI_FENCE_GATE));
		SugiItems.register(registry, new ItemSugiChest(SUGI_CHEST));
	}

	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntitySugiChest.class, "SugiChest");
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels()
	{
		registerModel(SUGI_LOG);
		registerModel(SUGI_LEAVES);
		registerModel(SUGI_FALLEN_LEAVES);
		registerModel(SUGI_SAPLING);
		registerModel(SUGI_PLANKS);
		registerModel(SUGI_SLAB);
		registerModel(SUGI_STAIRS);
		registerModel(SUGI_FENCE);
		registerModel(SUGI_FENCE_GATE);
		registerModel(SUGI_CHEST);

		ModelLoader.setCustomStateMapper(SUGI_LOG, new StateMap.Builder().ignore(BlockSugiLog.VARIANT).build());
		ModelLoader.setCustomStateMapper(SUGI_LEAVES, new StateMap.Builder().ignore(BlockLeaves.DECAYABLE, BlockLeaves.CHECK_DECAY).build());
		ModelLoader.setCustomStateMapper(SUGI_FALLEN_LEAVES, new StateMap.Builder().ignore(BlockSugiFallenLeaves.CHANCE).build());
		ModelLoader.setCustomStateMapper(SUGI_SAPLING, new StateMap.Builder().ignore(BlockSapling.TYPE, BlockSapling.STAGE).build());
		ModelLoader.setCustomStateMapper(SUGI_PLANKS, new StateMap.Builder().ignore(BlockSugiWood.DOUBLE).build());
		ModelLoader.setCustomStateMapper(SUGI_FENCE_GATE, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
	}

	@SideOnly(Side.CLIENT)
	public static void registerModel(Block block, String modelName)
	{
		SugiItems.registerModel(Item.getItemFromBlock(block), modelName);
	}

	@SideOnly(Side.CLIENT)
	public static void registerModel(Block block)
	{
		SugiItems.registerModel(Item.getItemFromBlock(block));
	}

	@SideOnly(Side.CLIENT)
	public static void registerModelWithMeta(Block block, String... modelName)
	{
		SugiItems.registerModelWithMeta(Item.getItemFromBlock(block), modelName);
	}

	@SideOnly(Side.CLIENT)
	public static void registerBlockColors()
	{
		final Minecraft mc = FMLClientHandler.instance().getClient();
		final BlockColors colors = mc.getBlockColors();

		colors.registerBlockColorHandler(new IBlockColor()
		{
			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex)
			{
				return world != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(world, pos) : 6726755;
			}
		}, new Block[] {SUGI_LEAVES, SUGI_FALLEN_LEAVES});
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemBlockColors()
	{
		final Minecraft mc = FMLClientHandler.instance().getClient();
		final BlockColors blockColors = mc.getBlockColors();
		final ItemColors colors = mc.getItemColors();

		colors.registerItemColorHandler((stack, tintIndex) ->
		{
			IBlockState state = ((ItemBlock)stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());

			return blockColors.colorMultiplier(state, null, null, tintIndex);
		}, new Block[] {SUGI_LEAVES, SUGI_FALLEN_LEAVES});
	}

	public static void registerOreDicts()
	{
		OreDictionary.registerOre("logWood", new ItemStack(SUGI_LOG, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("woodSugi", SUGI_LOG);
		OreDictionary.registerOre("treeLeaves", new ItemStack(SUGI_LEAVES, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("leavesSugi", SUGI_LEAVES);
		OreDictionary.registerOre("fallenLeavesSugi", SUGI_FALLEN_LEAVES);
		OreDictionary.registerOre("treeSapling", new ItemStack(SUGI_SAPLING, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("saplingSugi", SUGI_SAPLING);
		OreDictionary.registerOre("plankWood", new ItemStack(SUGI_PLANKS, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("planksSugi", SUGI_PLANKS);
		OreDictionary.registerOre("slabWood", new ItemStack(SUGI_SLAB, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("woodSlabSugi", SUGI_SLAB);
		OreDictionary.registerOre("stairWood", new ItemStack(SUGI_STAIRS, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("stairsWoodSugi", SUGI_STAIRS);
		OreDictionary.registerOre("fenceWood", new ItemStack(SUGI_FENCE, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("fenceSugi", SUGI_FENCE);
		OreDictionary.registerOre("fenceGateWood", new ItemStack(SUGI_FENCE_GATE, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("fenceGateSugi", SUGI_FENCE_GATE);
	}

	public static void registerRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(SUGI_FALLEN_LEAVES, 6), "###", '#', SUGI_LEAVES);

		GameRegistry.addShapelessRecipe(new ItemStack(Items.STICK), new ItemStack(SUGI_SAPLING));

		GameRegistry.addShapelessRecipe(new ItemStack(SUGI_PLANKS, 4), new ItemStack(SUGI_LOG, 1, OreDictionary.WILDCARD_VALUE));

		GameRegistry.addRecipe(new ItemStack(SUGI_SLAB, 6), "###", '#', SUGI_PLANKS);

		GameRegistry.addRecipe(new ItemStack(SUGI_STAIRS, 4), "  #", " ##", "###", '#', SUGI_PLANKS);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(SUGI_FENCE, 3), "#S#", "#S#", 'S', "stickWood", '#', SUGI_PLANKS));
		GameRegistry.addRecipe(new ShapedOreRecipe(SUGI_FENCE_GATE, "S#S", "S#S", 'S', "stickWood", '#', SUGI_PLANKS));

		GameRegistry.addRecipe(new ItemStack(SUGI_CHEST), "###", "# #", "###", '#', SUGI_PLANKS);

		GameRegistry.addSmelting(SUGI_LOG, new ItemStack(Items.COAL, 1, 1), 0.15F);
	}
}
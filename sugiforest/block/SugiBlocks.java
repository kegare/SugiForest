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
import net.minecraft.client.renderer.color.IItemColor;
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
	public static final BlockSugiLog sugi_log = new BlockSugiLog();
	public static final BlockSugiLeaves sugi_leaves = new BlockSugiLeaves();
	public static final BlockSugiFallenLeaves sugi_fallen_leaves = new BlockSugiFallenLeaves();
	public static final BlockSugiSapling sugi_sapling = new BlockSugiSapling();
	public static final BlockSugiWood sugi_planks = new BlockSugiWood();
	public static final BlockSugiWoodSlab sugi_slab = new BlockSugiWoodSlab();
	public static final BlockSugiStairs sugi_stairs = new BlockSugiStairs();
	public static final BlockSugiFence sugi_fence = new BlockSugiFence();
	public static final BlockSugiFenceGate sugi_fence_gate = new BlockSugiFenceGate();
	public static final BlockSugiChest sugi_chest = new BlockSugiChest();

	public static void registerBlocks()
	{
		sugi_log.setRegistryName("sugi_log");
		sugi_leaves.setRegistryName("sugi_leaves");
		sugi_fallen_leaves.setRegistryName("sugi_fallen_leaves");
		sugi_sapling.setRegistryName("sugi_sapling");
		sugi_planks.setRegistryName("sugi_planks");
		sugi_slab.setRegistryName("sugi_slab");
		sugi_stairs.setRegistryName("sugi_stairs");
		sugi_fence.setRegistryName("sugi_fence");
		sugi_fence_gate.setRegistryName("sugi_fence_gate");
		sugi_chest.setRegistryName("sugi_chest");

		GameRegistry.register(sugi_log);
		GameRegistry.register(new ItemBlock(sugi_log), sugi_log.getRegistryName());

		GameRegistry.register(sugi_leaves);
		GameRegistry.register(new ItemBlock(sugi_leaves), sugi_leaves.getRegistryName());

		GameRegistry.register(sugi_fallen_leaves);
		GameRegistry.register(new ItemSugiFallenLeaves(sugi_fallen_leaves), sugi_fallen_leaves.getRegistryName());

		GameRegistry.register(sugi_sapling);
		GameRegistry.register(new ItemBlock(sugi_sapling), sugi_sapling.getRegistryName());

		GameRegistry.register(sugi_planks);
		GameRegistry.register(new ItemBlock(sugi_planks), sugi_planks.getRegistryName());

		GameRegistry.register(sugi_slab);
		GameRegistry.register(new ItemSugiWoodSlab(sugi_slab), sugi_slab.getRegistryName());

		GameRegistry.register(sugi_stairs);
		GameRegistry.register(new ItemBlock(sugi_stairs), sugi_stairs.getRegistryName());

		GameRegistry.register(sugi_fence);
		GameRegistry.register(new ItemBlock(sugi_fence), sugi_fence.getRegistryName());

		GameRegistry.register(sugi_fence_gate);
		GameRegistry.register(new ItemBlock(sugi_fence_gate), sugi_fence_gate.getRegistryName());

		GameRegistry.register(sugi_chest);
		GameRegistry.register(new ItemSugiChest(sugi_chest), sugi_chest.getRegistryName());

		GameRegistry.registerTileEntity(TileEntitySugiChest.class, "SugiChest");

		OreDictionary.registerOre("logWood", new ItemStack(sugi_log, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("woodSugi", sugi_log);
		OreDictionary.registerOre("treeLeaves", new ItemStack(sugi_leaves, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("leavesSugi", sugi_leaves);
		OreDictionary.registerOre("fallenLeavesSugi", sugi_fallen_leaves);
		OreDictionary.registerOre("treeSapling", new ItemStack(sugi_sapling, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("saplingSugi", sugi_sapling);
		OreDictionary.registerOre("plankWood", new ItemStack(sugi_planks, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("planksSugi", sugi_planks);
		OreDictionary.registerOre("slabWood", new ItemStack(sugi_slab, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("woodSlabSugi", sugi_slab);
		OreDictionary.registerOre("stairWood", new ItemStack(sugi_stairs, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("stairsWoodSugi", sugi_stairs);
		OreDictionary.registerOre("fenceWood", new ItemStack(sugi_fence, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("fenceSugi", sugi_fence);
		OreDictionary.registerOre("fenceGateWood", new ItemStack(sugi_fence_gate, 1, OreDictionary.WILDCARD_VALUE));
		OreDictionary.registerOre("fenceGateSugi", sugi_fence_gate);

		Blocks.FIRE.setFireInfo(sugi_log, 5, 5);
		Blocks.FIRE.setFireInfo(sugi_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(sugi_fallen_leaves, 30, 60);
		Blocks.FIRE.setFireInfo(sugi_sapling, 20, 60);
		Blocks.FIRE.setFireInfo(sugi_planks, 5, 20);
		Blocks.FIRE.setFireInfo(sugi_slab, 5, 20);
		Blocks.FIRE.setFireInfo(sugi_stairs, 5, 20);
		Blocks.FIRE.setFireInfo(sugi_fence, 5, 20);
		Blocks.FIRE.setFireInfo(sugi_fence_gate, 5, 20);
		Blocks.FIRE.setFireInfo(sugi_chest, 5, 5);

		BlockSugiFallenLeaves.addFallenSeed(new ItemStack(sugi_sapling), 10);
		BlockSugiFallenLeaves.addFallenSeed(new ItemStack(Blocks.BROWN_MUSHROOM), 12);
		BlockSugiFallenLeaves.addFallenSeed(new ItemStack(Blocks.RED_MUSHROOM), 8);
		BlockSugiFallenLeaves.addFallenSeed(new ItemStack(Items.STICK), 10);
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels()
	{
		registerModel(sugi_log);
		registerModel(sugi_leaves);
		registerModel(sugi_fallen_leaves);
		registerModel(sugi_sapling);
		registerModel(sugi_planks);
		registerModel(sugi_slab);
		registerModel(sugi_stairs);
		registerModel(sugi_fence);
		registerModel(sugi_fence_gate);
		registerModel(sugi_chest);

		ModelLoader.setCustomStateMapper(sugi_log, new StateMap.Builder().ignore(BlockSugiLog.VARIANT).build());
		ModelLoader.setCustomStateMapper(sugi_leaves, new StateMap.Builder().ignore(BlockLeaves.DECAYABLE, BlockLeaves.CHECK_DECAY).build());
		ModelLoader.setCustomStateMapper(sugi_fallen_leaves, new StateMap.Builder().ignore(BlockSugiFallenLeaves.CHANCE).build());
		ModelLoader.setCustomStateMapper(sugi_sapling, new StateMap.Builder().ignore(BlockSapling.TYPE, BlockSapling.STAGE).build());
		ModelLoader.setCustomStateMapper(sugi_planks, new StateMap.Builder().ignore(BlockSugiWood.DOUBLE).build());
		ModelLoader.setCustomStateMapper(sugi_fence_gate, new StateMap.Builder().ignore(BlockFenceGate.POWERED).build());
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
		}, new Block[] {sugi_leaves, sugi_fallen_leaves});
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemBlockColors()
	{
		final Minecraft mc = FMLClientHandler.instance().getClient();
		final BlockColors blockColors = mc.getBlockColors();
		final ItemColors colors = mc.getItemColors();

		colors.registerItemColorHandler(new IItemColor()
		{
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex)
			{
				IBlockState state = ((ItemBlock)stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());

				return blockColors.colorMultiplier(state, null, null, tintIndex);
			}
		}, new Block[] {sugi_leaves, sugi_fallen_leaves});
	}

	public static void registerRecipes()
	{
		GameRegistry.addRecipe(new ItemStack(sugi_fallen_leaves, 6), "###", '#', sugi_leaves);

		GameRegistry.addShapelessRecipe(new ItemStack(Items.STICK), new ItemStack(sugi_sapling));

		GameRegistry.addShapelessRecipe(new ItemStack(sugi_planks, 4), new ItemStack(sugi_log, 1, OreDictionary.WILDCARD_VALUE));

		GameRegistry.addRecipe(new ItemStack(sugi_slab, 6), "###", '#', sugi_planks);

		GameRegistry.addRecipe(new ItemStack(sugi_stairs, 4), "  #", " ##", "###", '#', sugi_planks);

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sugi_fence, 3), "#S#", "#S#", 'S', "stickWood", '#', sugi_planks));
		GameRegistry.addRecipe(new ShapedOreRecipe(sugi_fence_gate, "S#S", "S#S", 'S', "stickWood", '#', sugi_planks));

		GameRegistry.addRecipe(new ItemStack(sugi_chest), "###", "# #", "###", '#', sugi_planks);

		GameRegistry.addSmelting(sugi_log, new ItemStack(Items.COAL, 1, 1), 0.15F);
	}
}
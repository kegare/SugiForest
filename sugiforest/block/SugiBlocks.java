package sugiforest.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
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
		TileEntity.register(SUGI_CHEST.getRegistryName().toString(), TileEntitySugiChest.class);
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
		Minecraft mc = FMLClientHandler.instance().getClient();
		BlockColors colors = mc.getBlockColors();

		colors.registerBlockColorHandler((state, world, pos, tintIndex) -> world != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(world, pos) : 6726755, new Block[] {SUGI_LEAVES, SUGI_FALLEN_LEAVES});
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemBlockColors()
	{
		Minecraft mc = FMLClientHandler.instance().getClient();
		BlockColors blockColors = mc.getBlockColors();
		ItemColors colors = mc.getItemColors();

		colors.registerItemColorHandler((stack, tintIndex) ->
		{
			IBlockState state = ((ItemBlock)stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());

			return blockColors.colorMultiplier(state, null, null, tintIndex);
		},
		new Block[] {SUGI_LEAVES, SUGI_FALLEN_LEAVES});
	}

	public static void registerOreDicts()
	{
		OreDictionary.registerOre("logWood", SUGI_LOG);
		OreDictionary.registerOre("treeLeaves", SUGI_LEAVES);
		OreDictionary.registerOre("treeSapling", SUGI_SAPLING);
		OreDictionary.registerOre("plankWood", SUGI_PLANKS);
		OreDictionary.registerOre("slabWood", SUGI_SLAB);
		OreDictionary.registerOre("stairWood", SUGI_STAIRS);
	}

	public static void registerSmeltingRecipes()
	{
		GameRegistry.addSmelting(SUGI_LOG, new ItemStack(Items.COAL, 1, 1), 0.15F);
	}
}
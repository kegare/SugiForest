/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.world.gen;

import net.minecraft.block.BlockSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenCaves;

public class MapGenSugiForestCaves extends MapGenCaves
{
	@Override
	protected void func_180701_a(World worldIn, int x, int z, int chunkX, int chunkZ, ChunkPrimer primer)
	{
		int i = rand.nextInt(rand.nextInt(rand.nextInt(20) + 1) + 1);

		if (rand.nextInt(3) != 0)
		{
			i = 0;
		}

		for (int j = 0; j < i; ++j)
		{
			double d0 = x * 16 + rand.nextInt(16);
			double d1 = rand.nextInt(rand.nextInt(100) + 8);
			double d2 = z * 16 + rand.nextInt(16);
			int count = 1;

			if (rand.nextInt(3) == 0)
			{
				func_180703_a(rand.nextLong(), chunkX, chunkZ, primer, d0, d1, d2);

				count += rand.nextInt(5);
			}

			for (int k = 0; k < count; ++k)
			{
				float f = rand.nextFloat() * (float)Math.PI * 2.0F;
				float f1 = (rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float f2 = rand.nextFloat() * 6.0F + rand.nextFloat();

				if (rand.nextInt(6) == 0)
				{
					f2 *= rand.nextFloat() * rand.nextFloat() * 4.0F + 1.0F;
				}

				func_180702_a(rand.nextLong(), chunkX, chunkZ, primer, d0, d1, d2, f2, f, f1, 0, 0, 1.0D);
			}
		}
	}

	@Override
	protected void digBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop, IBlockState state, IBlockState up)
	{
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
		IBlockState top = biome.topBlock;
		IBlockState filler = biome.fillerBlock;

		if (func_175793_a(state, up) || state.getBlock() == top.getBlock() || state.getBlock() == filler.getBlock())
		{
			if (y < 10)
			{
				data.setBlockState(x, y, z, Blocks.water.getDefaultState());
			}
			else
			{
				data.setBlockState(x, y, z, Blocks.air.getDefaultState());

				if (up.getBlock() == Blocks.sand)
				{
					data.setBlockState(x, y + 1, z, up.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND ? Blocks.red_sandstone.getDefaultState() : Blocks.sandstone.getDefaultState());
				}

				if (foundTop && data.getBlockState(x, y - 1, z).getBlock() == filler.getBlock())
				{
					data.setBlockState(x, y - 1, z, top.getBlock().getDefaultState());
				}
			}
		}
	}
}
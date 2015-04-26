/*
 * SugiForest
 *
 * Copyright (c) 2015 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package sugiforest.world;

import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sugiforest.core.Config;
import sugiforest.core.SugiForest;
import sugiforest.util.SugiUtils;

public class WorldProviderSugiForest extends WorldProviderSurface
{
	@Override
	protected void registerWorldChunkManager()
	{
		dimensionId = Config.dimensionID_SugiForest;
		worldChunkMgr = new WorldChunkManagerHell(SugiForest.sugiForest, SugiForest.sugiForest.rainfall);
	}

	@Override
	public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderSugiForest(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled(), worldObj.getWorldInfo().getGeneratorOptions());
	}

	@Override
	public boolean canCoordinateBeSpawn(int x, int z)
	{
		return worldObj.getBlockState(worldObj.getHorizon(new BlockPos(x, 0, z))).getBlock().getMaterial().isSolid();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getCloudHeight()
	{
		return 150.0F;
	}

	@Override
	public String getDimensionName()
	{
		return "Sugi Forest";
	}

	@Override
	public String getInternalNameSuffix()
	{
		return "_sugiforest";
	}

	@Override
	public String getWelcomeMessage()
	{
		return "Entering the " + getDimensionName();
	}

	@Override
	public String getDepartMessage()
	{
		return "Leaving the " + getDimensionName();
	}

	@Override
	public boolean shouldMapSpin(String entity, double x, double y, double z)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getSunBrightness(float ticks)
	{
		return super.getSunBrightness(ticks) * 1.5F;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getStarBrightness(float ticks)
	{
		return super.getStarBrightness(ticks) * 1.25F;
	}

	@Override
	public void resetRainAndThunder()
	{
		super.resetRainAndThunder();

		if (worldObj.getGameRules().getGameRuleBooleanValue("doDaylightCycle"))
		{
			WorldInfo worldInfo = SugiUtils.getWorldInfo(worldObj);
			long i = worldInfo.getWorldTime() + 24000L;

			worldInfo.setWorldTime(i - i % 24000L);
		}
	}
}
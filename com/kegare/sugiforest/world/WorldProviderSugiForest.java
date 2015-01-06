/*
 * SugiForest
 *
 * Copyright (c) 2014 kegare
 * https://github.com/kegare
 *
 * This mod is distributed under the terms of the Minecraft Mod Public License Japanese Translation, or MMPL_J.
 */

package com.kegare.sugiforest.world;

import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.storage.WorldInfo;

import com.kegare.sugiforest.core.Config;
import com.kegare.sugiforest.core.SugiForest;
import com.kegare.sugiforest.util.SugiUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldProviderSugiForest extends WorldProviderSurface
{
	@Override
	protected void registerWorldChunkManager()
	{
		dimensionId = Config.dimensionSugiForest;
		worldChunkMgr = new WorldChunkManagerHell(SugiForest.sugiForest, SugiForest.sugiForest.rainfall);
	}

	@Override
	public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderGenerate(worldObj, worldObj.getSeed(), worldObj.getWorldInfo().isMapFeaturesEnabled());
	}

	@Override
	public boolean canCoordinateBeSpawn(int x, int z)
	{
		return worldObj.getTopBlock(x, z).getMaterial().isSolid();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public float getCloudHeight()
	{
		return 150.0F;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean getWorldHasVoidParticles()
	{
		return false;
	}

	@Override
	public String getDimensionName()
	{
		return "Sugi Forest";
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
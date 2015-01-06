package com.kegare.sugiforest.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.DimensionManager;

import com.kegare.sugiforest.core.SugiForest;
import com.kegare.sugiforest.world.TeleporterDummy;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class SugiUtils
{
	public static ModContainer getModContainer()
	{
		ModContainer mod = Loader.instance().getIndexedModList().get(SugiForest.MODID);

		if (mod == null)
		{
			mod = Loader.instance().activeModContainer();

			if (mod == null || mod.getModId() != SugiForest.MODID)
			{
				return null;
			}
		}

		return mod;
	}

	public static void setPlayerLocation(EntityPlayerMP player, double posX, double posY, double posZ)
	{
		setPlayerLocation(player, posX, posY, posZ, player.rotationYaw, player.rotationPitch);
	}

	public static void setPlayerLocation(EntityPlayerMP player, double posX, double posY, double posZ, float yaw, float pitch)
	{
		int x = MathHelper.floor_double(posX);
		int z = MathHelper.floor_double(posZ);
		IChunkProvider provider = player.getServerForPlayer().getChunkProvider();

		provider.loadChunk(x - 3 >> 4, z - 3 >> 4);
		provider.loadChunk(x + 3 >> 4, z - 3 >> 4);
		provider.loadChunk(x - 3 >> 4, z + 3 >> 4);
		provider.loadChunk(x + 3 >> 4, z + 3 >> 4);

		player.mountEntity(null);
		player.playerNetServerHandler.setPlayerLocation(posX, posY, posZ, yaw, pitch);
	}

	public static boolean transferPlayer(EntityPlayerMP player, int dim)
	{
		if (dim != player.dimension)
		{
			if (!DimensionManager.isDimensionRegistered(dim))
			{
				return false;
			}

			player.isDead = false;
			player.forceSpawn = true;
			player.timeUntilPortal = player.getPortalCooldown();
			player.mcServer.getConfigurationManager().transferPlayerToDimension(player, dim, new TeleporterDummy(player.mcServer.worldServerForDimension(dim)));
			player.addExperienceLevel(0);

			return true;
		}

		return false;
	}

	public static boolean teleportPlayer(EntityPlayerMP player, int dim)
	{
		transferPlayer(player, dim);

		WorldServer world = player.getServerForPlayer();
		int originX = MathHelper.floor_double(player.posX);
		int originZ = MathHelper.floor_double(player.posZ);
		int range = 128;

		for (int x = originX - range; x < originX + range; ++x)
		{
			for (int z = originZ - range; z < originZ + range; ++z)
			{
				for (int y = world.getActualHeight(); y > 1; --y)
				{
					if (world.isAirBlock(x, y, z) && world.isAirBlock(x, y + 1, z))
					{
						while (y > 1 && (world.isAirBlock(x, y - 1, z) || world.getBlock(x, y - 1, z).isLeaves(world, x, y - 1, z)))
						{
							--y;
						}

						if (!world.isAirBlock(x, y - 1, z) && !world.getBlock(x, y - 1, z).getMaterial().isLiquid())
						{
							setPlayerLocation(player, x + 0.5D, y + 0.5D, z + 0.5D);

							return true;
						}
					}
				}
			}
		}

		return respawnPlayer(player, 0);
	}

	public static boolean respawnPlayer(EntityPlayerMP player, int dim)
	{
		transferPlayer(player, dim);

		WorldServer world = player.getServerForPlayer();
		ChunkCoordinates spawn = player.getBedLocation(player.dimension);

		if (spawn != null)
		{
			spawn = EntityPlayer.verifyRespawnCoordinates(world, spawn, true);
		}

		if (spawn == null)
		{
			spawn = world.getSpawnPoint();
		}

		int x = spawn.posX;
		int y = spawn.posY;
		int z = spawn.posZ;

		if (world.isAirBlock(x, y, z) && world.isAirBlock(x, y + 1, z))
		{
			while (y > 1 && world.isAirBlock(x, y - 1, z))
			{
				--y;
			}

			if (!world.isAirBlock(x, y - 1, z) && !world.getBlock(x, y - 1, z).getMaterial().isLiquid())
			{
				setPlayerLocation(player, x + 0.5D, y + 0.5D, z + 0.5D);

				return true;
			}
		}

		return teleportPlayer(player, dim);
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
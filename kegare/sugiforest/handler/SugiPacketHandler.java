package kegare.sugiforest.handler;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import kegare.sugiforest.core.Config;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

public class SugiPacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		if ("sugiforest.sync".equals(packet.channel))
		{
			ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
			Config.biomeSugiForest = dat.readInt();
			Config.sugiTreesOnHills = dat.readBoolean();
			Config.sugiTreesOnSugiForest = dat.readInt();
		}
	}

	public static Packet250CustomPayload getPacketDataSync()
	{
		ByteArrayDataOutput dat = ByteStreams.newDataOutput();
		dat.writeInt(Config.biomeSugiForest);
		dat.writeBoolean(Config.sugiTreesOnHills);
		dat.writeInt(Config.sugiTreesOnSugiForest);

		return new Packet250CustomPayload("sugiforest.sync", dat.toByteArray());
	}
}
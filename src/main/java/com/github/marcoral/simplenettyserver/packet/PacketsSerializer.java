package com.github.marcoral.simplenettyserver.packet;

import com.github.marcoral.nettyencodingutil.BytesEncoder;
import com.github.marcoral.simplenettyserver.api.packet.InvalidPacketError;
import com.github.marcoral.simplenettyserver.api.packet.PacketOut;
import com.github.marcoral.simplenettyserver.api.packet.PacketSerializer;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;

public class PacketsSerializer {
    private IntObjectMap<PacketSerializer> packetSerializers = new IntObjectHashMap<>();

    public PacketsSerializer() {
        addPacketSerializer(InvalidPacketError.PACKET_ID, new InvalidPacketErrorSerializer());
    }

    public void addPacketSerializer(int id, PacketSerializer packetSerializer) {
        PacketSerializer oldSerializer = packetSerializers.get(id);
        if(oldSerializer != null)
            throw new IllegalArgumentException("Packet serializer for packet id " + id + " already defined!");
        packetSerializers.put(id, packetSerializer);
    }

    public void serialize(PacketOut packet, BytesEncoder encoderUtil) {
        PacketSerializer serializer = packetSerializers.get(packet.getPacketID());
        if(serializer == null)
            throw new IllegalArgumentException("No eligible packet serializer found for packet " + packet + "!");
        serializer.serialize(packet, encoderUtil);
    }
}
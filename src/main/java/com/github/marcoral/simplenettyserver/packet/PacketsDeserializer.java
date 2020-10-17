package com.github.marcoral.simplenettyserver.packet;

import com.github.marcoral.nettyencodingutil.BytesDecoder;
import com.github.marcoral.simplenettyserver.api.client.ConnectedClient;
import com.github.marcoral.simplenettyserver.api.exception.InvalidPacketException;
import com.github.marcoral.simplenettyserver.api.packet.PacketDeserializer;
import com.github.marcoral.simplenettyserver.api.packet.PacketIn;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;

public class PacketsDeserializer {
    private IntObjectMap<PacketDeserializer> packetDeserializers = new IntObjectHashMap<>();

    public void addPacketDeserializer(int id, PacketDeserializer packetDeserializer) {
        PacketDeserializer oldDeserializer = packetDeserializers.get(id);
        if(oldDeserializer != null)
            throw new IllegalArgumentException("Packet deserializer for packet id " + id + " already defined!");
        packetDeserializers.put(id, packetDeserializer);
    }

    public PacketIn deserialize(BytesDecoder decoderUtil, ConnectedClient client) throws InvalidPacketException {
        long packetID = decoderUtil.readVarInt();
        if(packetID < Integer.MIN_VALUE || packetID > Integer.MAX_VALUE)
            throw new InvalidPacketException(String.format("Sent PacketID (%d) out of range", packetID));
        PacketDeserializer packetDeserializer = packetDeserializers.get((int) packetID);
        if(packetDeserializer == null)
            throw new InvalidPacketException(String.format("Server can not handle PacketId %d.", packetID));
        return packetDeserializer.deserialize(decoderUtil, client);
    }
}
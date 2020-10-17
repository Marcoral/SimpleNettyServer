package com.github.marcoral.simplenettyserver.api.packet;

import com.github.marcoral.nettyencodingutil.BytesDecoder;
import com.github.marcoral.simplenettyserver.api.client.ConnectedClient;
import com.github.marcoral.simplenettyserver.api.exception.InvalidPacketException;

public interface PacketDeserializer {
    PacketIn deserialize(BytesDecoder decoderUtil, ConnectedClient client) throws InvalidPacketException;
}
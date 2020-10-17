package com.github.marcoral.simplenettyserver.packet;

import com.github.marcoral.nettyencodingutil.BytesEncoder;
import com.github.marcoral.simplenettyserver.api.packet.InvalidPacketError;
import com.github.marcoral.simplenettyserver.api.packet.PacketSerializer;

public class InvalidPacketErrorSerializer implements PacketSerializer<InvalidPacketError> {
    @Override
    public void serialize(InvalidPacketError packet, BytesEncoder encoderUtil) {
        encoderUtil.writeCharSequence(packet.getCause());
    }
}

package com.github.marcoral.simplenettyserver.api.packet;

import com.github.marcoral.nettyencodingutil.BytesEncoder;

public interface PacketSerializer<T extends PacketOut> {
    void serialize(T packet, BytesEncoder encoderUtil);
}
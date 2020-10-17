package com.github.marcoral.simplenettyserver.api.packet;

import com.github.marcoral.simplenettyserver.api.annotation.Castable;
import com.github.marcoral.simplenettyserver.api.client.ConnectedClient;
import com.github.marcoral.simplenettyserver.api.exception.InvalidPacketException;
import io.netty.channel.ChannelHandlerContext;

public interface PacketIn extends Packet {
    void handle(ChannelHandlerContext context, @Castable ConnectedClient client) throws InvalidPacketException;
}
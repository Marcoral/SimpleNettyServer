package com.github.marcoral.simplenettyserver.api.client;

import com.github.marcoral.simplenettyserver.api.packet.PacketOut;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.SocketChannel;

public interface ConnectedClient {
    SocketChannel getSocketChannel();

    default ChannelFuture sendPacket(PacketOut packet) {
        return getSocketChannel().writeAndFlush(packet);
    }
}
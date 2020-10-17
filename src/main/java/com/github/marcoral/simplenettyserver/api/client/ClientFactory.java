package com.github.marcoral.simplenettyserver.api.client;

import io.netty.channel.socket.SocketChannel;

public interface ClientFactory {
    ConnectedClient create(SocketChannel channel);
}
package com.github.marcoral.simplenettyserver.api.client;

import io.netty.channel.socket.SocketChannel;

public class DefaultConnectedClient implements ConnectedClient {
    private final SocketChannel socketChannel;
    public DefaultConnectedClient(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
}
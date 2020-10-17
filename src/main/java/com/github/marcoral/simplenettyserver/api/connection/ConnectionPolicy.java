package com.github.marcoral.simplenettyserver.api.connection;

import io.netty.channel.socket.SocketChannel;

import java.util.function.Predicate;

public interface ConnectionPolicy extends Predicate<SocketChannel> {
    default void onTestFailed(SocketChannel channel) {}
}

package com.github.marcoral.simplenettyserver.api.connection;

import io.netty.channel.socket.SocketChannel;

import java.net.InetSocketAddress;
import java.util.HashSet;

public class BlacklistConnectionPolicy extends HashSet<InetSocketAddress> implements ConnectionPolicy {
    @Override
    public boolean test(SocketChannel channel) {
        return !contains(channel.remoteAddress().getAddress());
    }
}
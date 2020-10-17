package com.github.marcoral.simplenettyserver.api.util;

import com.github.marcoral.simplenettyserver.api.client.ConnectedClient;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.AttributeKey;

public class SocketChannelUtils {
    public static final AttributeKey<ConnectedClient> USER_OBJECT = AttributeKey.valueOf("userobject");

    public static void setUserObject(SocketChannel channel, ConnectedClient client) {
        channel.attr(USER_OBJECT).set(client);
    }
}

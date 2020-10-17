package com.github.marcoral.simplenettyserver.util;

import com.github.marcoral.simplenettyserver.api.client.ConnectedClient;
import com.github.marcoral.simplenettyserver.api.util.SocketChannelUtils;
import io.netty.channel.ChannelHandlerContext;

public class InternalSocketChannelUtils {
    public static ConnectedClient getClient(ChannelHandlerContext ctx) {
        return ctx.channel().attr(SocketChannelUtils.USER_OBJECT).get();
    }
}

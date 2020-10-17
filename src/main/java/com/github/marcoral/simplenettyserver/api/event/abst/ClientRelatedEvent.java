package com.github.marcoral.simplenettyserver.api.event.abst;

import com.github.marcoral.simplenettyserver.api.annotation.Castable;
import com.github.marcoral.simplenettyserver.api.client.ConnectedClient;
import io.netty.channel.ChannelHandlerContext;

public interface ClientRelatedEvent extends Event {
    ChannelHandlerContext getChannelHandlerContext();
    @Castable <T extends ConnectedClient> T getClient();
}

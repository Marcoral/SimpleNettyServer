package com.github.marcoral.simplenettyserver.event.concrete;

import com.github.marcoral.simplenettyserver.api.client.ConnectedClient;
import com.github.marcoral.simplenettyserver.api.event.abst.ConsumableEventBase;
import com.github.marcoral.simplenettyserver.api.event.abst.Event;
import com.github.marcoral.simplenettyserver.api.event.concrete.NewConnectionEvent;
import io.netty.channel.ChannelHandlerContext;

public class NewConnectionEventImpl extends ConsumableEventBase implements NewConnectionEvent {
    private final ConnectedClient client;
    private final ChannelHandlerContext channelHandlerContext;
    public NewConnectionEventImpl(ConnectedClient client, ChannelHandlerContext channelHandlerContext) {
        this.client = client;
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public Class<? extends Event> getEventIdentifier() {
        return NewConnectionEvent.class;
    }

    @Override
    public <T extends ConnectedClient> T getClient() {
        return (T) client;
    }

    @Override
    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }
}

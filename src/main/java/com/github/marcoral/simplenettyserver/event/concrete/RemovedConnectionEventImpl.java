package com.github.marcoral.simplenettyserver.event.concrete;

import com.github.marcoral.simplenettyserver.api.client.ConnectedClient;
import com.github.marcoral.simplenettyserver.api.event.abst.ConsumableEventBase;
import com.github.marcoral.simplenettyserver.api.event.abst.Event;
import com.github.marcoral.simplenettyserver.api.event.concrete.NewConnectionEvent;
import com.github.marcoral.simplenettyserver.api.event.concrete.RemovedConnectionEvent;
import io.netty.channel.ChannelHandlerContext;

public class RemovedConnectionEventImpl extends ConsumableEventBase implements RemovedConnectionEvent {
    private final ConnectedClient client;
    private final ChannelHandlerContext channelHandlerContext;
    public RemovedConnectionEventImpl(ConnectedClient client, ChannelHandlerContext channelHandlerContext) {
        this.client = client;
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public Class<? extends Event> getEventIdentifier() {
        return RemovedConnectionEvent.class;
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
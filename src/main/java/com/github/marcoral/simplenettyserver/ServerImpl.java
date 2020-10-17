package com.github.marcoral.simplenettyserver;

import com.github.marcoral.simplenettyserver.api.Server;
import com.github.marcoral.simplenettyserver.api.event.EventManager;
import com.github.marcoral.simplenettyserver.event.concrete.ServerShutdownEventImpl;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerImpl implements Server {
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final Channel channel;
    private final EventManager eventManager;
    public ServerImpl(EventLoopGroup bossGroup, EventLoopGroup workerGroup, Channel channel, EventManager eventManager) {
        this.bossGroup = bossGroup;
        this.workerGroup = workerGroup;
        this.channel = channel;
        this.eventManager = eventManager;
    }

    @Override
    public Future<?> shutdown() {
        return Executors.newSingleThreadExecutor().submit(() -> {
            eventManager.invokeEvent(new ServerShutdownEventImpl());
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            channel.close();
        });
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }
}

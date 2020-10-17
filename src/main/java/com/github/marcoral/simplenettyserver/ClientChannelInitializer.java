package com.github.marcoral.simplenettyserver;

import com.github.marcoral.simplenettyserver.api.client.ClientFactory;
import com.github.marcoral.simplenettyserver.api.client.ConnectedClient;
import com.github.marcoral.simplenettyserver.api.config.ServerConfig;
import com.github.marcoral.simplenettyserver.api.event.EventManager;
import com.github.marcoral.simplenettyserver.api.util.SocketChannelUtils;
import com.github.marcoral.simplenettyserver.handler.PacketDecodingHandler;
import com.github.marcoral.simplenettyserver.handler.PacketEncodingHandler;
import com.github.marcoral.simplenettyserver.handler.PacketProcessingHandler;
import com.github.marcoral.simplenettyserver.packet.PacketsDeserializer;
import com.github.marcoral.simplenettyserver.packet.PacketsSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.util.Collection;


public class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final ClientFactory clientFactory;
    private final Collection<ConnectedClient> connectedClients;
    private final EventManager eventManager;
    private final PacketsSerializer packetsSerializer;
    private final PacketsDeserializer packetsDeserializer;
    private final ServerConfig serverConfig;

    public ClientChannelInitializer(ClientFactory clientFactory,
                                    Collection<ConnectedClient> connectedClients,
                                    EventManager eventManager,
                                    PacketsSerializer packetsSerializer,
                                    PacketsDeserializer packetsDeserializer,
                                    ServerConfig serverConfig) {
        this.clientFactory = clientFactory;
        this.connectedClients = connectedClients;
        this.eventManager = eventManager;
        this.packetsSerializer = packetsSerializer;
        this.packetsDeserializer = packetsDeserializer;
        this.serverConfig = serverConfig;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        if(!serverConfig.getConnectionPolicy().test(socketChannel)) {
            socketChannel.close().addListener(f -> serverConfig.getConnectionPolicy().onTestFailed(socketChannel));
            return;
        }
        ChannelPipeline pipeline = socketChannel.pipeline();
        SocketChannelUtils.setUserObject(socketChannel, clientFactory.create(socketChannel));
        pipeline.addLast(new PacketDecodingHandler(connectedClients, eventManager, packetsDeserializer));
        pipeline.addLast(new PacketProcessingHandler(serverConfig));
        pipeline.addLast(new PacketEncodingHandler(packetsSerializer));
    }
}
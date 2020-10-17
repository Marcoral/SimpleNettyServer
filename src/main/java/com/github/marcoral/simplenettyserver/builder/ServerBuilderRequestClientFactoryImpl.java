package com.github.marcoral.simplenettyserver.builder;

import com.github.marcoral.simplenettyserver.ClientChannelInitializer;
import com.github.marcoral.simplenettyserver.ServerImpl;
import com.github.marcoral.simplenettyserver.api.Server;
import com.github.marcoral.simplenettyserver.api.builder.ServerBuilder;
import com.github.marcoral.simplenettyserver.api.builder.ServerBuilderRequestClientFactory;
import com.github.marcoral.simplenettyserver.api.client.ClientFactory;
import com.github.marcoral.simplenettyserver.api.config.ServerConfig;
import com.github.marcoral.simplenettyserver.api.event.EventManager;
import com.github.marcoral.simplenettyserver.api.packet.PacketDeserializer;
import com.github.marcoral.simplenettyserver.api.packet.PacketSerializer;
import com.github.marcoral.simplenettyserver.event.EventManagerImpl;
import com.github.marcoral.simplenettyserver.packet.PacketsDeserializer;
import com.github.marcoral.simplenettyserver.packet.PacketsSerializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class ServerBuilderRequestClientFactoryImpl implements ServerBuilderRequestClientFactory {
    private ClientFactory clientFactory;
    private PacketsSerializer packetsSerializer = new PacketsSerializer();
    private PacketsDeserializer packetsDeserializer = new PacketsDeserializer();
    private AtomicBoolean invalid = new AtomicBoolean();

    private ServerBuilder serverBuilder = new ServerBuilderImpl();

    @Override
    public ServerBuilder clientFactory(ClientFactory clientFactory) {
        validateBuilder();
        this.clientFactory = clientFactory;
        return serverBuilder;
    }

    class ServerBuilderImpl implements ServerBuilder {
        private ServerConfig serverConfig = new ServerConfig(){};
        private final EventManager eventManager = new EventManagerImpl();

        @Override
        public ServerBuilder addPacketSerializer(int packetID, PacketSerializer<?> packetSerializer) {
            validateBuilder();
            packetsSerializer.addPacketSerializer(packetID, packetSerializer);
            return this;
        }

        @Override
        public ServerBuilder addPacketDeserializer(int packetID, PacketDeserializer packetDeserializer) {
            validateBuilder();
            packetsDeserializer.addPacketDeserializer(packetID, packetDeserializer);
            return this;
        }

        @Override
        public ServerBuilder overrideDefaultConfig(ServerConfig config) {
            validateBuilder();
            this.serverConfig = config;
            return this;
        }

        @Override
        public ServerBuilder adjustEventManager(Consumer<EventManager> eventManagerConsumer) {
            eventManagerConsumer.accept(eventManager);
            return this;
        }

        @Override
        public Future<Server> launchOnPort(int port) {
            return launch(new InetSocketAddress(port));
        }

        @Override
        public Future<Server> launchLocally(int port) {
            return launch(new InetSocketAddress(InetAddress.getLoopbackAddress(), port));
        }

        private Future<Server> launch(InetSocketAddress address) {
            validateBuilder();
            invalid.set(true);

            return Executors.newSingleThreadExecutor().submit(() -> runServer(address));
        }

        private Server runServer(InetSocketAddress address) {
            try {
                EventLoopGroup bossGroup = new NioEventLoopGroup();
                EventLoopGroup workerGroup = new NioEventLoopGroup();
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                        .childHandler(new ClientChannelInitializer(clientFactory, ConcurrentHashMap.newKeySet(), eventManager, packetsSerializer, packetsDeserializer, serverConfig))
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);

                ChannelFuture result = b.bind(address);
                result.channel().closeFuture().addListener(f -> {
                    workerGroup.shutdownGracefully();
                    bossGroup.shutdownGracefully();
                });
                result.sync();
                return new ServerImpl(bossGroup, workerGroup, result.channel(), eventManager);
            } catch (Exception e) {
                throw new RuntimeException("Failed to run the server on port " + address.getPort(), e);
            }
        }
    }

    private void validateBuilder() {
        if(invalid.get())
            throw new IllegalStateException("ServerImpl already built!");
    }
}
package com.github.marcoral.simplenettyserver.api.builder;

import com.github.marcoral.simplenettyserver.api.Server;
import com.github.marcoral.simplenettyserver.api.config.ServerConfig;
import com.github.marcoral.simplenettyserver.api.event.EventManager;
import com.github.marcoral.simplenettyserver.api.packet.PacketDeserializer;
import com.github.marcoral.simplenettyserver.api.packet.PacketSerializer;

import java.util.concurrent.Future;
import java.util.function.Consumer;

public interface ServerBuilder {
    ServerBuilder addPacketSerializer(int packetID, PacketSerializer<?> packetSerializer);
    ServerBuilder addPacketDeserializer(int packetID, PacketDeserializer packetDeserializer);

    ServerBuilder overrideDefaultConfig(ServerConfig config);
    ServerBuilder adjustEventManager(Consumer<EventManager> eventManagerConsumer);

    Future<Server> launchOnPort(int port) throws Exception;
    Future<Server> launchLocally(int port) throws Exception;
}
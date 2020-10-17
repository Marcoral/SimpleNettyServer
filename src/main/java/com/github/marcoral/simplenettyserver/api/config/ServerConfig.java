package com.github.marcoral.simplenettyserver.api.config;

import com.github.marcoral.simplenettyserver.api.annotation.Castable;
import com.github.marcoral.simplenettyserver.api.client.ConnectedClient;
import com.github.marcoral.simplenettyserver.api.connection.ConnectionPolicy;
import com.github.marcoral.simplenettyserver.api.connection.DummyConnectionPolicy;
import com.github.marcoral.simplenettyserver.api.packet.InvalidPacketError;

public interface ServerConfig {
    default ConnectionPolicy getConnectionPolicy() {
        return new DummyConnectionPolicy();
    }

    default void onInvalidPacketSent(@Castable ConnectedClient client, String cause) {
        client.sendPacket(new InvalidPacketError(cause));
        client.getSocketChannel().close();
    }
}
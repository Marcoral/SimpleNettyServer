package com.github.marcoral.simplenettyserver.handler;

import com.github.marcoral.nettyencodingutil.ByteBufBytesDecoderVarIntWrapper;
import com.github.marcoral.simplenettyserver.api.client.ConnectedClient;
import com.github.marcoral.simplenettyserver.api.config.ServerConfig;
import com.github.marcoral.simplenettyserver.api.event.EventManager;
import com.github.marcoral.simplenettyserver.api.exception.InvalidPacketException;
import com.github.marcoral.simplenettyserver.api.packet.PacketIn;
import com.github.marcoral.simplenettyserver.event.concrete.NewConnectionEventImpl;
import com.github.marcoral.simplenettyserver.event.concrete.RemovedConnectionEventImpl;
import com.github.marcoral.simplenettyserver.packet.PacketsDeserializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.Collection;
import java.util.List;

import static com.github.marcoral.simplenettyserver.util.InternalSocketChannelUtils.getClient;

public class PacketDecodingHandler extends ByteToMessageDecoder {
    private final Collection<ConnectedClient> connectedClients;
    private final EventManager eventManager;
    private final PacketsDeserializer packetsDeserializer;

    public PacketDecodingHandler(Collection<ConnectedClient> connectedClients, EventManager eventManager, PacketsDeserializer packetsDeserializer) {
        this.connectedClients = connectedClients;
        this.eventManager = eventManager;
        this.packetsDeserializer = packetsDeserializer;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws InvalidPacketException {
        if(byteBuf.readableBytes() == 0)
            return;
        ByteBufBytesDecoderVarIntWrapper bytesDecoder = new ByteBufBytesDecoderVarIntWrapper(byteBuf);
        PacketIn packet = packetsDeserializer.deserialize(bytesDecoder, getClient(channelHandlerContext));

        list.add(packet);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ConnectedClient client = getClient(ctx);
        connectedClients.add(client);
        eventManager.invokeEvent(new NewConnectionEventImpl(client, ctx));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        ConnectedClient client = getClient(ctx);
        connectedClients.remove(client);
        eventManager.invokeEvent(new RemovedConnectionEventImpl(client, ctx));
    }
}
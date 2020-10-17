package com.github.marcoral.simplenettyserver.handler;

import com.github.marcoral.simplenettyserver.api.config.ServerConfig;
import com.github.marcoral.simplenettyserver.api.exception.InvalidPacketException;
import com.github.marcoral.simplenettyserver.api.packet.PacketIn;
import com.github.marcoral.simplenettyserver.api.util.SocketChannelUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.github.marcoral.simplenettyserver.util.InternalSocketChannelUtils.getClient;

public class PacketProcessingHandler extends SimpleChannelInboundHandler<PacketIn> {
    private final ServerConfig serverConfig;
    public PacketProcessingHandler(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PacketIn packet) throws InvalidPacketException {
        packet.handle(channelHandlerContext, channelHandlerContext.channel().attr(SocketChannelUtils.USER_OBJECT).get());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable throwable) {
        String cause = throwable.getMessage();
        if(cause == null)
            cause = "Internal server error";
        serverConfig.onInvalidPacketSent(getClient(ctx), cause);
    }
}
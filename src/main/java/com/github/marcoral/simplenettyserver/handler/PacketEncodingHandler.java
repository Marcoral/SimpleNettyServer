package com.github.marcoral.simplenettyserver.handler;

import com.github.marcoral.nettyencodingutil.ByteBufBytesEncoderVarIntWrapper;
import com.github.marcoral.nettyencodingutil.BytesEncoder;
import com.github.marcoral.simplenettyserver.api.packet.PacketOut;
import com.github.marcoral.simplenettyserver.packet.PacketsSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncodingHandler extends MessageToByteEncoder<PacketOut> {
    private final PacketsSerializer packetsSerializer;
    public PacketEncodingHandler(PacketsSerializer packetsSerializer) {
        this.packetsSerializer = packetsSerializer;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, PacketOut packetOut, ByteBuf byteBuf) {
        BytesEncoder bytesEncoder = new ByteBufBytesEncoderVarIntWrapper(byteBuf);
        bytesEncoder.writeVarInt(packetOut.getPacketID());
        packetsSerializer.serialize(packetOut, bytesEncoder);
    }
}
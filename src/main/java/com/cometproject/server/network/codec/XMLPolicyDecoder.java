package com.cometproject.server.network.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class XMLPolicyDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();
        byte delimiter = in.readByte();
        in.resetReaderIndex();

        if (delimiter == 0x3C) {
            ctx.writeAndFlush(
                    "<?xml version=\"1.0\"?>\r\n"
                            + "<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\r\n"
                            + "<cross-domain-policy>\r\n"
                            + "<allow-access-from domain=\"*\" to-ports=\"*\" />\r\n"
                            + "</cross-domain-policy>\0"
            ).addListener(ChannelFutureListener.CLOSE);
        } else {
            ctx.pipeline().remove(this);
            out.add(ctx.alloc().buffer().writeBytes(in));
        }
    }
}

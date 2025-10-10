package com.github.nettybook.ch4;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handler implementation for the echo server.
 */
public class EchoServerV4FirstHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf readMessage = (ByteBuf) msg;
		System.out.println("FirstHandler channelRead : " + readMessage.toString(Charset.defaultCharset()));
		ctx.write(msg);
		ctx.fireChannelRead(msg); // 다음핸들러의 channelRead메서드도 실행하고싶을때 설정
	}
}

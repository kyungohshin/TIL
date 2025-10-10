package com.github.nettybook.ch6;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handler implementation for the echo server.
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ByteBuf readMessage = (ByteBuf) msg; // 네티 바이트버퍼. 풀에 관리되므로 channelRead가 실행된이후 풀로 돌아감
		System.out.println("channelRead : " + readMessage.toString(Charset.defaultCharset()));

		// alloc메서드를 통하여 네티 프레임워크에서 초기화된 ByteBufAllocator로 접근가능. 다이렉트 버퍼풀이 디폴트. 개발자의 필요에따라 힙버퍼 생성가능
		ByteBufAllocator byteBufAllocator = ctx.alloc();
		// buffer메서드로 풀에서 네티 바이트버퍼를 생성할수있음. release메서드가 호출되거나 버이트 버퍼를 채널에 기록하면 버퍼풀로 돌아감
		ByteBuf newBuffer = byteBufAllocator.buffer();

		ctx.write(msg); // 바이트 버퍼가 입력되면, 채널에 기록하고 버퍼풀로 돌아감
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}

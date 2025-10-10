package com.github.nettybook.ch5;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handler implementation for the echo server.
 */
@Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ChannelFuture channelFuture = ctx.writeAndFlush(msg);
		// ChannelFuture 객체가 완료 이벤트를 수신할때 채널을 닫음(성공유무 관계없음)
		// 퓨처객체가 while문으로 작업의 완료유무를 계속 확인하지않고 작업이 완료되면 수행할 리스너를 설정
		channelFuture.addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}

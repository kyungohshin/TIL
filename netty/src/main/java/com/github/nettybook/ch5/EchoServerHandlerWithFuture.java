package com.github.nettybook.ch5;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Handler implementation for the echo server.
 */
@Sharable
public class EchoServerHandlerWithFuture extends ChannelInboundHandlerAdapter {
	// 데이터가 들어왔을때, 클라이언트 소켓채널에 데이터 기록을 완료하고, 기록완료메시지와 기록된 메시지의 크기를 출력하고 소켓채널 닫는 이벤트코드

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		ChannelFuture channelFuture = ctx.writeAndFlush(msg); // 클라이언트 소켓버퍼에 데이터 기록완료하고 채널로 전송(클라이언트에게 되돌려줌)

		final int writeMessageSize = ((ByteBuf) msg).readableBytes(); // 네티가 수신한 msg객체는 ByteBuf객체

		channelFuture.addListener(new ChannelFutureListener() { // 사용자 정의 리스너사용
			@Override
			// ChannelFuture 객체에서 발생하는 작업 완료 이벤트 메서드
			public void operationComplete(ChannelFuture future) throws Exception {
				System.out.println("전송한 Byte : " + writeMessageSize);
				future.channel().close(); // 채널 닫기 이벤트 발생
			}
		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}

package com.github.nettybook.ch4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServerV1 {
	public static void main(String[] args) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					// 연결된 클라이언트 소켓채널이 사용할 채널 파이프라인 설정
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						// ch는 연결된 클라이언트 소켓채널을 의미
						public void initChannel(SocketChannel ch) { // 클라이언트 소켓 채널이 생성될때 자동호출
							ChannelPipeline p = ch.pipeline(); // 네티 내부에서 클라이언트 소켓 채널을 생성할때
															   // 빈채널파이프라인객체를 생성및 할당하기때문에 꺼내쓸수있는것
							p.addLast(new EchoServerV1Handler()); // 이벤트를 다룰 핸들러를 연결
						}
					});

			ChannelFuture f = b.bind(8888).sync();
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
}

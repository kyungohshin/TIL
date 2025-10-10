package com.github.nettybook.ch5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;

public class EchoServer {
	public static void main(String[] args) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) {
							ChannelPipeline p = ch.pipeline();
							p.addLast(new EchoServerHandlerWithFuture());
						}
					});

			ChannelFuture f = b.bind(8888).sync(); // 퓨처객체 사용

			f.channel().closeFuture().sync();

//			ChannelFuture future = b.bind(8888); // 포트 바인딩이 완료되기전에 ChannelFuture객체를 돌려줌
//			future.sync(); // sync메서드는 주어진 ChannelFuture 객체의 작업이 완료될때까지 블로킹하는 메서드. bind메서드 처리가 완료되면
//						   // sync도 같이완료됨
//			Channel channel = future.channel(); // future를 통해서 채널을 얻어온다. 여기서 얻어진 채널은 8888번 포트에 바인딩된
//												// 서버채널
//			ChannelFuture closeFuture = channel.closeFuture(); // 바인드가 완료된 서버채널의 ChannelFuture객체를
//															   // 돌려줌
//			closeFuture.sync();
		} finally {
			Future f1 = workerGroup.shutdownGracefully();
			f1.sync();
			Future f2 = bossGroup.shutdownGracefully();
			f2.sync();
		}
	}
}

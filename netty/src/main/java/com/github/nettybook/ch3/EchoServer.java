package com.github.nettybook.ch3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {
	public static void main(String[] args) throws Exception {
		// NioEventLoopGroup 논블로킹
		EventLoopGroup bossGroup = new NioEventLoopGroup(1); // 그룹내에서 생성할 최대 쓰레드 갯수. 1이니까 단일쓰레드
		EventLoopGroup workerGroup = new NioEventLoopGroup(); // 값이 없다면 cpu코어수x2
		try {
			ServerBootstrap b = new ServerBootstrap(); // 빌더패턴이기때문에 .로 나중에 추가하는것이 가능
			// 클라이언트의 연결을 수락하는 부모스레드그룹, 연결된 클라이언트의 소켓으로부터 데이터 입출력 및 이벤트 처리를 담당하는 자식스레드그룹
			b.group(bossGroup, workerGroup) // 이벤트 루프설정
					.channel(NioServerSocketChannel.class) // 서버소켓(부모쓰레드)이 사용할 네트워크 입출력모드 설정,
														   // 소켓입출력모드설정
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) {
							ChannelPipeline p = ch.pipeline();
							p.addLast(new EchoServerHandler()); // 클라이언트연결이 생성되었을때 데이터처리를 담당할 클래스 연결
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

package com.github.nettybook.ch6;

import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

public class NettyBufferCreate {
	public void createBuffer() {
		ByteBuf buf1 = Unpooled.buffer(11); // 버퍼풀 사용하지않는 11바이트 크기의 힙 버퍼 생성

		ByteBuf buf2 = Unpooled.directBuffer(11); // 버퍼풀 사용하지않는 11바이트 크기의 다이렉트 버퍼 생성

		ByteBuf buf3 = PooledByteBufAllocator.DEFAULT.heapBuffer(11); // 버퍼풀 사용하는 11바이트 크기의 힙 버퍼 생성

		ByteBuf buf4 = PooledByteBufAllocator.DEFAULT.directBuffer(11); // 버퍼풀 사용하는 11바이트 크기의 다이렉트
																		// 버퍼 생성

		testBuffer(buf2);
	}

	private void testBuffer(ByteBuf buf) {
		System.out.println(buf.capacity());

		System.out.println(buf.isDirect());

		buf.writeInt(1234); // 정수형 4바이트
		System.out.println(buf.readableBytes());
		System.out.println(buf.writableBytes());

		System.out.println(buf.readShort()); // 2바이트를 읽고
		System.out.println(buf.readableBytes()); // 4바이트중 2바이트를 읽었으니 2바이트가 남음
		System.out.println(buf.writableBytes());

		buf.capacity(20); // 동적으로 크기 변경
		System.out.println(buf.capacity());

		buf.clear(); // 읽기 쓰기 인덱스 모두 0으로 초기화
	}

	public void convertNettyBufferToJavaBuffer() {
		ByteBuf buf = Unpooled.buffer(11);
		buf.writeBytes("abcd".getBytes());
		ByteBuffer nioByteBuffer = buf.nioBuffer(); // 네티 -> 자바
	}

	public void convertJavaBufferToNettyBuffer() {
		ByteBuffer byteBuffer = ByteBuffer.wrap("abcd".getBytes());
		ByteBuf buf = Unpooled.wrappedBuffer(byteBuffer); // 자바 -> 네티
	}

	public static void main(String args[]) {
		NettyBufferCreate n = new NettyBufferCreate();
		n.createBuffer();
	}
}

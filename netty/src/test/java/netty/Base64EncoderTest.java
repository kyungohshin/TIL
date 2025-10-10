package netty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.base64.Base64Decoder;
import io.netty.handler.codec.base64.Base64Encoder;

public class Base64EncoderTest {
	@Test
	public void testEncoder() {
		String writeData = "안녕하세요";
		ByteBuf request = Unpooled.wrappedBuffer(writeData.getBytes()); // 문자열을 네티의 바이트버퍼로 변환

		Base64Encoder encoder = new Base64Encoder();
		EmbeddedChannel embeddedChannel = new EmbeddedChannel(encoder); // 테스트할 핸들러 등록

		boolean result = embeddedChannel.writeOutbound(request); // 바이트버퍼를 아웃바운드에 기록 = 클라이언트로부터 데이터를 수신한것과 동일
		assertTrue(result);

		ByteBuf response = (ByteBuf) embeddedChannel.readOutbound(); // 아웃바운드 데이터를 읽는다.

		String expect = "7JWI64WV7ZWY7IS47JqU";
		assertEquals(expect, response.toString(Charset.defaultCharset())); 
		
		embeddedChannel.finish(); // 종료
	}
	
	@Test
	public void testDecoder() {
		String writeData = "7JWI64WV7ZWY7IS47JqU";
		ByteBuf request = Unpooled.wrappedBuffer(writeData.getBytes()); // 문자열을 네티의 바이트버퍼로 변환

		Base64Decoder decoder = new Base64Decoder();
		EmbeddedChannel embeddedChannel = new EmbeddedChannel(decoder); // 테스트할 핸들러 등록

		boolean result = embeddedChannel.writeInbound(request); // 바이트버퍼를 인바운드에 기록 = 클라이언트로부터 데이터를 수신한것과 동일
		assertTrue(result);

		ByteBuf response = (ByteBuf) embeddedChannel.readInbound(); // 인바운드 데이터를 읽는다.

		String expect = "안녕하세요";
		assertEquals(expect, response.toString(Charset.defaultCharset())); 
		
		embeddedChannel.finish(); // 종료
	}
}

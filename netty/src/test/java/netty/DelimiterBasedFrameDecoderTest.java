package netty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;

public class DelimiterBasedFrameDecoderTest {
	@Test
	public void testDecoder() {
		String writeData = "안녕하세요\r\n반갑습니다\r\n";
		String first = "안녕하세요\r\n";
		String second = "반갑습니다\r\n";

		// 최대 8192바이트 데이터를 줄바꿈 문자 기준으로 잘라서 디코딩, 2번재 인수는 디코딩된 데이터에 구분자 포함여부를 뜻함.
		DelimiterBasedFrameDecoder decoder = new DelimiterBasedFrameDecoder(8192, false, Delimiters.lineDelimiter());
		EmbeddedChannel embeddedChannel = new EmbeddedChannel(decoder); // 테스트할 핸들러 등록

		ByteBuf request = Unpooled.wrappedBuffer(writeData.getBytes());  // 문자열을 네티의 바이트버퍼로 변환
		boolean result = embeddedChannel.writeInbound(request); // 바이트버퍼를 인바운드에 기록 = 클라이언트로부터 데이터를 수신한것과 동일
		assertTrue(result);
		
		ByteBuf response = null;
		response = (ByteBuf) embeddedChannel.readInbound(); // 인바운드 데이터를 읽는다. 
		assertEquals(first, response.toString(Charset.defaultCharset())); // 안녕하세요/r/n를 돌려줌
		
		response = (ByteBuf) embeddedChannel.readInbound(); // 인바운드 데이터를 읽는다. 
		assertEquals(second, response.toString(Charset.defaultCharset())); // 반갑습니다/r/n를 돌려줌
		
		embeddedChannel.finish(); // 종료
	}
}

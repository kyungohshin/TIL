package tacos;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest // 스프링부트기능으로 테스트하라고 junit에게 알려줌
class TacoCloudApplicationTests {

	@Test
	void contextLoads() {
		// 내용은 없어도 테스트메서드가 존재한다면 스프링 컨텍스트가 로드됨.
	}

}

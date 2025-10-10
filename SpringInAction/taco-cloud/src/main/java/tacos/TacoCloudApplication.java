package tacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * 아래의 3개의 어노테이션이 결합된 효과
 *
 * @SpringBootConfiguration - applicationContext.xml같은 구성클래스를 자바로 작성한클래스
 *
 * @EnableAutoConfiguration - 스프링부트 자동-구성을 활성화
 *
 * @ComponentScan - 컴포넌트 검색 활성화
 */
@SpringBootApplication // 스프링부트 애플리케이션임을 나타냄 (이걸로 시작)
public class TacoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}

}

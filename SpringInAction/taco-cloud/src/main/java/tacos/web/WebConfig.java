package tacos.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * 원래는 보통의 컨트롤러처럼 작성하는게 맞지만,
 * 사용자 입력을 처리하지않는 건단한 컨트롤러같은경우는 아래처럼 WebMvcConfigurer를 구현하여 정의해도 무방.
 * WebMvcConfigurer는 인터페이스임에도 불구하고 정의된 모든 메서드의 기본적인 구현 제공. <- 사용자가 필요한것만 골라서 오버라이딩하면 됨.
 * 꼭 따로 클래스를 안만들어도 되고, 어떤 클래스던간에 WebMvcConfigurer를 구현하기만하면됨.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	/*
	 * "/"라는 url을 처리하여 home이라는 뷰네임을 반환하는 컨트롤러 (GET메소드만 가능)
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
	}
}

//@Controller
//public class HomeController {
//	@GetMapping("/")
//	public String home() {
//		return "home"; // 뷰의 명칭
//	}
//}

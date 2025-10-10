package beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

// @Component == <bean class="beans.TestBean" />
@Component("java500") // <bean id='java500' class="beans.TestBean" />
public class TestBean {
	public TestBean() {
		System.out.println("TestBean 생성자");
	}

	@PostConstruct // 생성자 호출 이후 자동으로 호출
	public void bean_init() {
		System.out.println("TestBean init 메서드");
	}

	@PreDestroy // 객체가 소멸되기 전에 자동으로 호출
	public void bean_destory() {
		System.out.println("TestBean destory 메서드");
	}

	public void default_init() {
		System.out.println("default init 메서드");
	}

	public void default_destroy() {
		System.out.println("default destory 메서드");
	}
}

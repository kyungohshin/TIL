package config;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import beans.Bean1;
import beans.DataBean1;
import beans.TestBean;
import beans.TestBean2;

@Configuration
public class BeanConfig {

//	@Bean(initMethod = "bean_init", destroyMethod = "bean_destory")
	@Bean
	@Lazy // xml생성할때 객체가 생성되지않고 최초 getBean할때 생성됨
	@Scope("prototype") // getBean될때마다 객체 생성
	@Primary
	public TestBean testBean() {
		TestBean t1 = new TestBean();
		return t1;
	}

	@Bean(name = "java500")
	public TestBean java600() {
		TestBean t1 = new TestBean();
		return t1;
	}

	@Bean
	@Primary // 같은 클래스타입이 여러개라도 이클래스를 우선적으로 넣음
	public DataBean1 dataBean1() {
		DataBean1 d1 = new DataBean1();
		return d1;
	}

	@Bean
	public DataBean1 dataBean2() {
		DataBean1 d1 = new DataBean1();
		return d1;
	}

	// 5.1부터는 deprecated되었으므로 config파일이 아니라 빈에 직접 붙이는것을 권장
	@Bean(autowire = Autowire.BY_NAME)
	public Bean1 bean() {
		return new Bean1();
	}

	@Bean
	@Lazy
	@Scope("prototype")
	public TestBean2 testBean2() {
		return new TestBean2();
	}
}

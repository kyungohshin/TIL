package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import beans.Bean1;
import beans.DataBean1;
import beans.TestBean;

@Configuration
@ComponentScan(basePackages = { "beans", "advisor" })
@EnableAspectJAutoProxy
public class BeanConfig_aspectJ {

	@Bean
	@Lazy // xml생성할때 객체가 생성되지않고 최초 getBean할때 생성됨
	@Scope("prototype") // getBean될때마다 객체 생성
	@Primary
	public TestBean testBean() {
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

//  @Component로 해결불가능 dataBean1이 여러개의 이름으로 넣어야하기때문.
	@Bean
	public Bean1 bean() {
		return new Bean1();
	}

}

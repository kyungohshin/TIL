package jwt.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jwt.filter.Filter1;
import jwt.filter.Filter2;

@Configuration
// SecurityFilterChain에서 내가 원하는 필터를 껴넣을수도있고, 애초에 SecurityFilterChain를 안쓰고 따로 만들수도있다.
// 이렇게만 등록해주면 요청이 오면 알아서 순서에 따라 알아서 실행됨. 다만 SecurityFilterChain보다 뒤에 실행
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<Filter1> filter1() {
		FilterRegistrationBean<Filter1> bean = new FilterRegistrationBean<>(new Filter1());
		bean.addUrlPatterns("/*"); // 모든 요청
		bean.setOrder(0); // 낮은 번호가 필터중에서 가장먼저 실행됨.
		return bean;
	}

	@Bean
	public FilterRegistrationBean<Filter2> filter2() {
		FilterRegistrationBean<Filter2> bean = new FilterRegistrationBean<>(new Filter2());
		bean.addUrlPatterns("/*"); // 모든 요청
		bean.setOrder(1); // 낮은 번호가 필터중에서 가장먼저 실행됨.
		return bean;
	}

}

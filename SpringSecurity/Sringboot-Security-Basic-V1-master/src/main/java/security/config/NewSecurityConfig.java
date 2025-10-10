//package security.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
///*
// * 1. 사용자 프로필 정보를 가져오고, 그 정보로 만족한다면 회원가입을 자동으로 시킴.
// * 2. 코드받기(인증)
// * 3. 엑세스토큰받기(권한)
// */
//
//@Configuration
//@EnableWebSecurity // 스프링 시큐리티 설정을 해줌 (debug = true)
//// @Secured 활성화 - 특정메서드에 간단하게 권한을 부여
//// @preAuthorize, postAuthorize 활성화
////@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//public class NewSecurityConfig {
//
////	@Bean
////	public BCryptPasswordEncoder encodePwd() {
////		return new BCryptPasswordEncoder();
////	}
//
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http
//				.authorizeRequests(
//						authroize -> authroize.antMatchers("/api/v1/user/**")// 이쪽 경로로 들어오면
//								.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // 이런
//																													// 권한만
//																													// 들어올수있고
//								.antMatchers("/api/v1/manager/**") // 이쪽 경로로 들어오면
//								.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // 이런
//																							// 권한만
//																							// 들어올수있고
//								.antMatchers("/api/v1/admin/**").access("hasRole('ROLE_ADMIN')")
//								.anyRequest().permitAll())
//				.httpBasic();
//		return http.build();
//
//	}
//
//}

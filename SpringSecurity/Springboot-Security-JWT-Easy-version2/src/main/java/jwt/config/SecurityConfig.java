package jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

import jwt.config.jwt.JwtAuthenticationFilter;
import jwt.config.jwt.JwtAuthorizationFilter;
import jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 활성화
@RequiredArgsConstructor
// 최신버전은 extends WebSecurityConfigurerAdapter 방식은 Deprecate 되었기때문에 변경해야함.
public class SecurityConfig {

	private final UserRepository userRepository;

	private final CorsFilter corsFilter;

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.csrf().disable() // http csfr 사용안함
				// 세션을 사용하지않는다.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.formLogin().disable() // 폼로그인 사용안함
				.httpBasic().disable() // 기본적인 http로그인 방식 사용안함
				.apply(new MyCustomDsl()) // 커스텀 필터 등록
				.and()
				.authorizeRequests(
						authroize ->
						authroize.antMatchers("/api/v1/user/**")// 이쪽 경로로 들어오면
						.access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // 이런 권한만 들어올수있고
						.antMatchers("/api/v1/manager/**") // 이쪽 경로로 들어오면
						.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')") // 이런 권한만 들어올수있고
						.antMatchers("/api/v1/admin/**").access("hasRole('ROLE_ADMIN')")
						.anyRequest().permitAll()) // 나머지경로는 누구나 접근가능
				.build();
	}

	public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
			http
					// SecurityFilterChain에 내가 특정필터실행전에 필터3을 껴넣는다. - SecurityFilterChain실행 전에 실행됨.
					// 직접커스터마이징해도 SecurityFilterChain가 먼저실행되고 그다음에 내가 커스터마이징한 필터들이 실행된다.
					// 순서가 우선임 3 1 2순
//					.addFilterBefore(new Filter3(), BasicAuthenticationFilter.class)
					// 모든요청에 관하여 필터가 적용됨. (컨트롤러쪽에 @CrossOrigin은 인증이 필요없을것들만 보여주게되는것, 인증이 필요하다면 필터를
					// 걸어야함)
					.addFilter(corsFilter)
					// formLogin().disable()때문에 작동하지않았던 /login을 다시 활성화시키기 위해서 필터적용
					.addFilter(new JwtAuthenticationFilter(authenticationManager)) // 인증
					.addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository)); // 인가
		}
	}

}

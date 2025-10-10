package security.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;

import security.config.auth.oauth.PrincipalOauth2UserService;
import security.filter.Filter3;

/*
 * 1. 사용자 프로필 정보를 가져오고, 그 정보로 만족한다면 회원가입을 자동으로 시킴.
 * 2. 코드받기(인증)
 * 3. 엑세스토큰받기(권한)
 */

@Configuration
@EnableWebSecurity // 스프링 시큐리티 설정을 해줌 (debug = true)
// @Secured 활성화 - 특정메서드에 간단하게 권한을 부여
// @preAuthorize, postAuthorize 활성화
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;

	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
		return authConfiguration.getAuthenticationManager();
	}

	// 글로벌하게 해당 경로에해당하는 설정을 걸어준다.
	// 어떤 특정한것만 권한을 걸어주고싶을때 @Secured를 사용
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable();
		http.sessionManagement() // 세션 관리 기능이 작동함
//				.invalidSessionUrl("/invalidSession") // 세션이 유효하지 않을 때 이동 할 페이지
				.maximumSessions(1) // 최대 허용 가능 세션 수, (-1: 무제한)
				.maxSessionsPreventsLogin(false) // 동시 로그인 차단함, false: 기존 세션 만료(default)
				.expiredUrl("/expried"); // 세션이 만료된 경우 이동할 페이지

		http
				.addFilterAt(new Filter3(), RequestCacheAwareFilter.class)
//				.addFilter(new TestAuthenticationFilter())
				.authorizeRequests()
				.antMatchers("/user/**").authenticated() // 인증만 되면 들어갈수있음
				// admin이나 manager권한이 있어야만 들어올수있고
				.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
				// admin권한이 있어야 들어올수있다.
				.antMatchers("/manager/tt").access("hasRole('ROLE_ADMIN')")
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
				.anyRequest().permitAll() // 위 3개 경로가 아닌것은 권한이 다있음
				.and()
				// 위 3개 경로는 인증이 필요하다면 무조건 로그인페이지(/loginForm)를 먼저띄움
				.formLogin().loginPage("/loginForm.html")
				// .usernameParameter("username2") // form에서 넘어온 username2값을 username으로 받아들임
				// /login 주소가 호출되면 시큐리티가 낚아채서 대신 로그인 진행 *
				.loginProcessingUrl("/login")
				// loginPage(/loginForm)을 이용해서 로그인이 되면 /로 이동하도록함 (true면 전에 어떤페이지에있었는지무관하게 무조건 /로 이동)
				.defaultSuccessUrl("/", true)
//				.successHandler(null)
//				.failureHandler(null)
				.and()
				.logout()
				.invalidateHttpSession(true) // 로그아웃 세션정보 제거 여부
				.logoutSuccessUrl("/loginForm")
				.logoutSuccessHandler(new LogoutSuccessHandler() {

					@Override
					public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication authentication) throws IOException, ServletException {
						System.out.println("로그아웃 완료");
						response.sendRedirect("/loginForm");
					}

				})
				.and()
				.oauth2Login().loginPage("/loginForm")
				// 구글 로그인이 완료된 뒤의 후처리가 필요함. (엑세스토큰+사용자프로필정보)
				.userInfoEndpoint().userService(principalOauth2UserService);
	}

}

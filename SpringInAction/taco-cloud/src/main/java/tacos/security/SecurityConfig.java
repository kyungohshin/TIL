package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation
					.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation
					.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web
					.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//import javax.sql.DataSource; JDBC 기반 사용자 스토어에 필요함


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	@Autowired
//	DataSource dataSource;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	// http 보안을 구성하는 메서드
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/design", "/orders")
		.access("hasRole('ROLE_USER')")
		.antMatchers("/", "/**").access("permitAll")
				.and().httpBasic();
	}

	@Bean // 이 객체는 스프링에 등록, 관리된다.
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	// 사용자 인증 정보를 구성하는 메서드 대부분 jdbc기반으로 많이함
	public void configure(AuthenticationManagerBuilder auth) throws Exception{

		/* UserDetailsService를 구현한 객체를 등록. 구현하여 커스터마이징함 */
		auth
				.userDetailsService(userDetailsService)
				.passwordEncoder(encoder());


		/* 아래 코드는 책의 JDBC 기반 사용자 스토어 예제 코드. */
//		auth
//				.jdbcAuthentication()
//				.dataSource(dataSource) // 유저, 권한 등의 디폴트 쿼리가 정해져있고 자동으로 실행됨.
//				.usersByUsernameQuery(
//						"select username, password, enabled from users " +
//								"where username=?") // 하지만, 대부분의경우는 테이블명 등이 다르므로 별도의 쿼리작성. 하지만 열타입,길이같은건 일치해야함
//				.authoritiesByUsernameQuery(
//						"select username, authority from authorities " +
//								"where username=?")
//				.passwordEncoder(new NoEncodingPasswordEnconder()); // DB에는 암호화되어있지않는값이 들어가있으므로,
//																	// 일치를 위해 암호화하지않는 인코더를 구현해서 넣음.

		/* 아래 코드는 책의 LDAP 기반 사용자 스토어 예제 코드. */
//		auth
//				.ldapAuthentication()
//				.userSearchBase("ou=people")
//				.userSearchFilter("(uid={0})")
//				.groupSearchBase("ou=groups")
//				.groupSearchFilter("member={0}")
//				.contextSource()
//				.root("dc=tacocloud,dc=com")
//				.ldif("classpath:users.ldif")
//				.and()
//				.passwordCompare()
//				.passwordEncoder(new BCryptPasswordEncoder())
//				.passwordAttribute("userPasscode");

		/*
		 * 아래 코드는 책의 인메모리 기반 사용자 스토어 예제 코드.
		 * 간단한 테스트는 편하나, 수정이 번거로워서 잘 안씀
		 */
//		auth.inMemoryAuthentication()
//		.withUser("user1")
//				.password("{noop}password1") // 스프링5부터는 비밀번호를 반드시암호화해야함. 암호화하지않을시 403 or 500에러발생.
//											 // noop으로 비암호화가능
//		.roles("USER") // authorities or roles 둘중에 하나 쓰면됨/
//		.and()
//		.withUser("user2")
//		.password("{noop}password2")
//				.authorities("ROLE_USER");
	}
}
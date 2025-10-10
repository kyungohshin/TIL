package jwt.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jwt.config.auth.PrincipalDetails;
import jwt.model.User;
import jwt.repository.UserRepository;

// 시큐리티가 filter를 가지고있는데 그 필터중에 BasicAuthenticationFilter라는 것이 있음.
// 권한이나 인증이 필요한 특정 주소를 요청했을때 위 필터를 무조건 타게 되어있음.
// 만약 권한이나 인증이 필요한 주소가 아니라면 이 필터를 안탐
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private UserRepository userRepository;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}

	// 권한이나 인증이 필요한 특정 주소를 요청했을때 해당 필터를 무조건 타게 되어있음.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("인증이나 권한이 필요한 주소가 요청됨");

		String jwtHeader = request.getHeader("Authorization");
		System.out.println("jwtHeader : " + jwtHeader);

		// 원하는 토큰값이 존재하는지 확인
		if (jwtHeader == null) {
			chain.doFilter(request, response);
			return;
		}

		// JWT 토큰을 검증하여 유효한지 확인
		String token = request.getHeader("Authorization").replace("Bearer: ", "");
		String username = JWT.require(Algorithm.HMAC512("cos")).build().verify(token).getClaim("username").asString();

		// 토큰이 유효하다.
		if (username != null) {
			User user = userRepository.findByUsername(username);

			PrincipalDetails principalDetails = new PrincipalDetails(user);
			// 정상적인 방식 - PrincipalDetailsService의 loadUserByUsername(username)으로 Authentication만들지만,
			// 지금은 로그인을 통해서 만든게아니라 토큰으로 들어온것을 통해 Authentication객체를 생성.
			Authentication authentication =
					new UsernamePasswordAuthenticationToken(principalDetails, principalDetails.getPassword(),
							principalDetails.getAuthorities());

			System.out.println(authentication + "@@@@@@@@@@@@@@@");
			// 강제로 시큐리티 세션에 접근하여 Authentication 객체를 저장.
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		// 다음필터로 넘어가도록 설정
		chain.doFilter(request, response);
	}

}

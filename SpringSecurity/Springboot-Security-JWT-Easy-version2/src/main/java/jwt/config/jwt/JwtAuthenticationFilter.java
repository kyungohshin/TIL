package jwt.config.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import jwt.config.auth.PrincipalDetails;
import jwt.model.User;
import lombok.RequiredArgsConstructor;

/*
 * 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter가 존재.
 * /login을 요청해서 username, password를 전송하면(post) UsernamePasswordAuthenticationFilter가 동작
 * 하지만 formLogin().disable()때문에 작동하지 않는 상황.
 * 작동을 위해서는 config에 다시 등록해주어야한다.
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;

	// /login 요청을 하면 로그인 시도를 위해서 실행되는 함수
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("로그인 시도중");

		// 1. username, password를 받아서 정상인지 확인하려고 authenticationManager로 로그인 시도
//		try {
//			BufferedReader br = request.getReader();
//			String input = null;
//			while ((input = br.readLine()) != null) {
//				System.out.println(input); // 이렇게 노가다방식으로도 할수있는데, json이면 mapper를 쓰는게 편함
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		try {
			ObjectMapper om = new ObjectMapper();
			User user = null;
			user = om.readValue(request.getInputStream(), User.class);

			// 토큰 생성
			UsernamePasswordAuthenticationToken token =
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

			// 2. PrincipalDetailsService가 호출되어 loadUserByUsername메서드가 실행됨
			// PrincipalDetailsService의 loadUserByUsername(첫번째파라미터)가 실행됨.
			// 넘겨받은 토큰으로 db에 접근하여 있다면 UserDetails객체가 리턴.
			// 그 객체의 비밀번호와 두번째파라미터가 일치하는지 검증
			// 일치하면 Authentication객체로 만들어서 필터체인으로 리턴 => 이때 로그인이 됨.
			Authentication authentication = authenticationManager.authenticate(token);

			PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
			System.out.println(principalDetails.getUser().getUsername() + "로그인완료");

			// 3. 정상일시 PrincipalDetails가 리턴될꺼니까 그걸 세션에 넣고 (권한관리때문에 세션에 넣는다)
			// authentication객체가 session영역에 저장됨 - 그 방법은 return
			// 리턴의 이유는 단지 권한 관리를 security가 대신 해주기떄문에 편하게 사용하려고 세션을 이용. 원래 jwt일때는 세션이 굳이 필요없다.
			return authentication;
//			return super.attemptAuthentication(request, response); 커스터마이징안했으면 이게 실행되는 따로 커스텀해준것
//			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	// attemptAuthentication 실행후 인증이 정상적으로 되었으면 해당 함수 실행
	// 4. JWT토큰을 만들어서 응답하면됨.
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("successfulAuthentication 실행됨");
		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

		// rsa는 아니고 hash암호방식
		String jwtToken = JWT.create()
				.withSubject(principalDetails.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 60000 * 5))
				.withClaim("id", principalDetails.getUser().getId())
				.withClaim("username", principalDetails.getUser().getUsername())
				.sign(Algorithm.HMAC512("cos")); // 서버만 알고있는 고유키로 사인

		response.addHeader("Authorization", jwtToken);

		// 커스터마이징안했으면 이게 실행되는 따로 커스텀해준것
//		super.successfulAuthentication(request, response, chain, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		System.out.println("인증 실패");
		super.unsuccessfulAuthentication(request, response, failed);
	}
}

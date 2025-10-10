package security.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import security.model.User;
import security.repository.UserRepository;

// 일반 로그인 관련
// 시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 UserDetailsService타입으로 빈등록되어있는 loadUserByUsername메서드가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	/*
	 * 시큐리티 session(내부Authecntication(내부UserDetails))
	 * 여기서 리턴된 값은 Authecntication내부로 들어간다.
	 * 그리고 Authecntication는 시큐리티 session내부로 들어감.
	 * 오버라이딩 하지않아도 기본적으로 작동함. PrincipalDetails객체를 리턴하고싶어서 오버라이딩한것
	 * 해당 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
	 *
	 * 유저네임만 받으면 패스워드는 스프링내부에서 일치하는지 알아서 처리해줌
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username + "@@@@");
		User user = userRepository.findByUsername(username);
		if (user != null) {

			// 리턴 객체는 authentication안으로 들어간다.
			return new PrincipalDetails(user);
		}
		throw new UsernameNotFoundException("User '" + username + "' not found");
	}


}

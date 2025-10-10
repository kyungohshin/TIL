package jwt.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jwt.model.User;
import jwt.repository.UserRepository;


// 일반 로그인 관련
/*
 * 원래라면 http://localhost:8080/login 로 요청이 들어왔을때 동작함. => 현재는 동작 x
 * 기본적으로 스프링시큐리티는 loginProcessingUrl()로 바꾸지않았을경우 /login 이 디폴트값이므로 가로채기때문.
 * 그런데 여기서는 /login으로 쏘면 404가 나온다.
 * 이유는 formLogin().disable()때문에 그렇다.
 */
@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println(username + "@@@@");
		User user = userRepository.findByUsername(username);
		if (user != null) {

			// 리턴 객체는 authentication안으로 들어간다.
			return new PrincipalDetails(user);
		}
		return null;
	}

}

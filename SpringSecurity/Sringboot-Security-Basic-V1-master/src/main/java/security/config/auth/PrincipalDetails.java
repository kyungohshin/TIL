package security.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Data;
import security.model.User;

/*
 * 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킴
 * 로그인 진행이 완료되면 시큐리티session을 만들어준다 (Security ContextHolder 안에다가 저장)
 * 시큐리티session에 들어갈수있는 객체는 정해져있음 (Authentication객체)
 * Authentication 객체 안에는 User정보가 있어야됨.
 * User객체도 타입이 정해져있음 (UserDetails타입)
 *
 * 쉽게말하면, 시큐리티가 가지고있는 시큐리티 세션 영역이 존재.
 * Security Session 여기에 들어갈 객체는 정해져있음
 * -> Authentication 이 객체고 이객체도 User정보가 있어야하는데
 * -> UserDetails 이 객체를 구현한 User가 필요함
 */
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

	private User user;
	private Map<String, Object> attributes;

	// 일반 로그인
	public PrincipalDetails(User user) {
		this.user = user;
	}

	// oauth 로그인
	public PrincipalDetails(User user, Map<String, Object> attributes) {
		this.user = user;
		this.attributes = attributes;
	}

	// 해당 User의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return user.getRole();
			}
		});
		return collect;
	}

	@Override
	public String getPassword() { // 이값을 들어온 비밀번호와 일치하는지 비교 bCryptPasswordEncoder가 디폴트
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	// 계정이 만료 안되었는지
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	// 계정이 안 잠겼는지
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	// 계정이 만료가 안되었는지
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	// 활성화 되었는지
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return null;
	}

}

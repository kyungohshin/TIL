package security.config.auth.oauth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import security.config.auth.PrincipalDetails;
import security.config.auth.oauth.provider.FacebookUserInfo;
import security.config.auth.oauth.provider.GoogleUserInfo;
import security.config.auth.oauth.provider.NaverUserInfo;
import security.config.auth.oauth.provider.OAuth2UserInfo;
import security.model.User;
import security.repository.UserRepository;

//oauth 로그인 관련

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	/*
	 * 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
	 * 오버라이딩 하지않아도 기본적으로 작동함. PrincipalDetails객체를 리턴 & 회원가입 하고 싶어서 오버라이딩한것
	 * 해당 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
	 */
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		// registrationId로 어떤 OAuth로 로그인되었는지
		System.out.println("getClientRegistration : " + userRequest.getClientRegistration());
		System.out.println("getAccessToken : " + userRequest.getAccessToken().getTokenValue());

		OAuth2User oauth2User = super.loadUser(userRequest);
		// 구글로그인버튼 클릭 ->  구글로그인창 -> 로그인완료 -> code를 리턴(oauth-client라이브러리) -> 엑세스토큰 요청
		// userRequest 정보 -> loadUser함수 -> 구글로부터 회원프로필을 받음
		System.out.println("getAttributes : " + super.loadUser(userRequest).getAttributes());

		OAuth2UserInfo oAuth2UserInfo = null;
		if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청");
			oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
		} else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
			System.out.println("페이스북 로그인 요청");
			oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
		} else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
			System.out.println("네이버 로그인 요청");
			oAuth2UserInfo = new NaverUserInfo((Map) oauth2User.getAttributes().get("response"));
		} else {
			System.out.println("지원불가");
		}

		// oauth로 부터 얻어온 유저정보를 꺼내올수있음.
		String provider = oAuth2UserInfo.getProvider();
		String providerId = oAuth2UserInfo.getProviderId();
		String username = provider + "_" + providerId;
		String password = bCryptPasswordEncoder.encode("겟인데어"); // 그냥 필요없는데 만드는거
		String email = oAuth2UserInfo.getEmail();
		String role = "ROLE_USER";

		User userEntity = userRepository.findByUsername(username);

		// 회원가입을 강제로 진행했음
		if (userEntity == null) {
			userEntity = User.builder().username(username).password(password).email(email).role(role)
					.provider(provider).providerId(providerId).build();
			userRepository.save(userEntity);
		}

		// 리턴 객체는 authentication안으로 들어간다.
		return new PrincipalDetails(userEntity, oauth2User.getAttributes());
	}

}

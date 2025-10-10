package security.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import security.config.auth.PrincipalDetails;
import security.model.User;
import security.repository.UserRepository;

@Controller
public class IndexController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/*
	 * 세션정보에 접근하는 방법 2가지 - 일반 로그인
	 * 1. 로그인 한다음에 여길들어오면 authentication객체를 통해 세션정보를 확인할 수 있다. (UserDetails객체로 다운캐스팅)
	 * 2. @AuthenticationPrincipal를 통해서도 세션정보에 접근할 수 있다.
	 */
	@GetMapping("/test/login")
	public @ResponseBody String testLogin(Authentication authentication,
			@AuthenticationPrincipal PrincipalDetails userDetails) {
		System.out.println("==================");

		PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("authentication : " + principalDetails.getUser());

		System.out.println("userDetails : " + userDetails.getUser());

		Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("context : " + authentication2.getPrincipal());
		return "세션 정보 확인하기";
	}

	/*
	 * 세션정보에 접근하는 방법 2가지 - oauth 로그인
	 * 1. 로그인 한다음에 여길들어오면 authentication객체를 통해 세션정보를 확인할 수 있다. (OAuth2User객체로 다운캐스팅)
	 * 2. @AuthenticationPrincipal를 통해서도 세션정보에 접근할 수 있다.
	 */
	@GetMapping("/test/oauth/login")
	public @ResponseBody String testOAuthLogin(Authentication authentication,
			@AuthenticationPrincipal OAuth2User oauth) {
		System.out.println("==================");

		// oauth로 로그인하면 PrincipalDetails가 아닌 OAuth2User로 캐스팅해야함
		OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
		System.out.println("authentication : " + oauth2User.getAttributes());
		System.out.println("oauth : " + oauth.getAttributes());

		return "oauth 세션 정보 확인하기";
	}

	// 머스테치 기본폴더 src/main/resources/
	// 부리졸버 설정 : templates(pre), .mustache(suf) 생략가능!
	@GetMapping({ "", "/" })
	public String index() {
		return "index"; // 실제 src/main/resources/templates/index.html 페이지를 찾아감
	}

	// 일반로그인, oauth로그인 둘다 PrincipalDetails타입으로 받을 수 있어짐. 분기할필요가없어서 굉장히 편함
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("principalDetails : " + principalDetails.getUser());
		return "user";
	}

	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}

	@GetMapping("/invalidSession")
	public @ResponseBody String invalidSession() {
		return "세션이 유효하지않습니다.";
	}

	@GetMapping("/expried")
	public @ResponseBody String expried() {
		return "세션이 만료되었습니다.";
	}

	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}

	@GetMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm() {
		return "joinForm";
	}

	@PostMapping("/join")
	public String join(User user) {
		// 스프링5부터는 비밀번호를 암호화해서 DB에 넣어야한다.
		// 그게 싫으면 config쪽에 jdbc.passwordEncoder(비암호화)를 따로 설정하면됨.
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole("ROLE_USER");
		System.out.println("회원가입 진행 : " + user);
		userRepository.save(user);
		return "redirect:/loginForm";
	}

	@Secured("ROLE_ADMIN") // 이 메서드는 ROLE_ADMIN권한을 가진사람만 접근이 가능하도록 간단히 설정
	@GetMapping("/info")
	public @ResponseBody String info() {
		return "개인정보";
	}

	@PreAuthorize("hasRole('ROLE_MANAGER') OR hasRole('ROLE_ADMIN')") // 2개의 권한이있으면 실행됨. secured랑 비슷
	@GetMapping("/data")
	public @ResponseBody String data() {
		return "데이터정보";
	}
}

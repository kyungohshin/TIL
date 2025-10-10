package tacos.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class NoEncodingPasswordEnconder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// rawPassword encode()결과값
		// encodedPassword DB에서 가져온 값
		return rawPassword.toString().equals(encodedPassword);
	}

}

package jwt.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//SecurityFilterChain 실행전에 실행됨
public class Filter3 implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		// ID,PWS이 정상적이라서 로그인이 되면 토큰 값을 만들고 리턴해주면됨.
		// 그럼 요청할때마다 들어오는 토큰이 내가 만든 토큰인지(유효한지) 검증만 하면됨. (HS256 or RSA로 복호화를해서..)
		if (req.getMethod().equals("GET")) {
			String headerAuth = req.getHeader("Authorization");
			System.out.println(headerAuth);

			if (headerAuth.equals("cos")) {
				// 계속 체인으로 옮겨다닐수있도록 설정해줘야함
				chain.doFilter(request, response);
			} else {
				PrintWriter out = res.getWriter();
				out.println("인증안됨");
			}
		}
	}

}

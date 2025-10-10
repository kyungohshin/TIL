package advisor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AdvisorClass {

	// 모든반환타입, 모든클래스의 aopTest()메서드를 호출하기 전에 실행하라
	@Before("execution(* aopTest())")
	public void beforeMethod() {
		System.out.println("aop_before 메서드 호출");
	}

	@After("execution(* aopTest())")
	public void afterMethod() {
		System.out.println("aop_after 메서드 호출");
	}

	// around는 관심사 호출 전후로 호출하기떄문에 중간에 관심사메서드를 호출해야할 일이 있다.
	// 그때를 위해서 pjp를 만들어둔것.
	@Around("execution(* aopTest())")
	public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("aop_around 메서드 호출");

		// 원래 메서드 호출
		// 어떤값이 리턴될지 모르므로 object타입이여야만함.
		Object obj = pjp.proceed();

		// 반환값이 있을수도 있으므로..
		return obj;
	}

	// 오류없이 정상적으로 끝났을때만
	@AfterReturning("execution(* aopTest())")
	public void afterReturningMethod() {
		System.out.println("aop_afterReturn 메서드 호출");
	}

	@AfterThrowing("execution(* aopTest())")
	public void afterThrowingMethod() {
		System.out.println("aop_afterThrow 메서드 호출");
	}
}

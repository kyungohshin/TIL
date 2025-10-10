package beans;

import org.springframework.stereotype.Component;

/**
 *
 *
 * @author shin
 * @history
 *          2021. 9. 21. initial creation
 */
@Component
public class DataBean1 {
	public void aopTest() {
//		int e = 10 / 0;
		System.out.println("[AOP 메서드 호출]");
	}

	public void aopTest(int i) {
		System.out.println("[AOP 메서드 호출int]");
	}

	public void aopTest(String i) {
		System.out.println("[AOP 메서드 호출String]");
	}

	public void aopTest(String i, int a1, int a2) {
		System.out.println("[AOP 메서드 호출 여러매개변수]");
	}
}

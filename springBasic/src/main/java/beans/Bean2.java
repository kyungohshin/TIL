package beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class Bean2 {
	private int data1;
	private String data2;
	private TestBean testBean;
	private TestBean2 testBean2;

	// 생성자를 통하면 반드시 주입을 받아야하기 때문에, @required를 쓸필요가 없다
	// 컴포넌트되어있는 클래스는 알아서 주입시켜주기때문에 @Autowired가 필요없음
	public Bean2(@Value("100") int data1, @Value("abcd") String data2,
			TestBean testBean, TestBean2 testBean2) {
		this.data1 = data1;
		this.data2 = data2;
		this.testBean = testBean;
		this.testBean2 = testBean2;
	}

	public void printData() {
		System.out.printf("data1 : %s\n", getData1());
		System.out.printf("data2 : %s\n", getData2());
		System.out.printf("testBean : %s\n", getTestBean());
		System.out.printf("testBean2 : %s\n", getTestBean2());
	}
}

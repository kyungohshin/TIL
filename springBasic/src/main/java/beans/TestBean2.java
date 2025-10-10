package beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component("tb2")
@Lazy // Lazy = true
@Scope("prototype")
public class TestBean2 {
	private int data1;
	private double data2;
	private String data3;

	@Autowired
	@Qualifier("java500")
//	@Resource(name = "java500")
	private TestBean tb;

	public TestBean2() {
		System.out.println("TestBean2의 기본생성자");
		this.data1 = 0;
		this.data2 = 0.0;
		this.data3 = null;
		this.tb = null;
	}

	// 기본타입은 자동주입이 당연히 안되므로 직접 넣어줘야
	public TestBean2(@Value("100") int data1, @Value("13.2") double data2,
			@Value("abcd") String data3, TestBean tb) {
		System.out.println("TestBean2의 매개변수생성자");
		this.data1 = data1;
		this.data2 = data2;
		this.data3 = data3;
		this.tb = tb;
	}

	public void printData() {
		System.out.printf("Data1 : %d\n", data1);
		System.out.printf("Data2 : %f\n", data2);
		System.out.printf("Data3 : %s\n", data3);
		System.out.printf("tb : %s", tb);
	}

	@Required
	public void setData1(int data1) {
		this.data1 = data1;
	}

//	@Autowired 변수에 @거는거랑 똑같음
	public void setTestBean(TestBean tb) {
		this.tb = tb;
	}
}

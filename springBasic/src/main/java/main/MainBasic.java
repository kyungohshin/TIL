package main;

import java.util.Map;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import beans.HelloWorld;
import beans.ListBean;
import beans.TestBean;
import beans.TestBean2;

public class MainBasic {

	public static void main(String[] args) {
//		 ApplicationContext ctx = new ClassPathXmlApplicationContext("/config/beans.xml");
		// 이 어플리케이션을 쓸때 모든 bean을 생성함.
//		ApplicationContext ctx = new GenericXmlApplicationContext("/config/beans.xml");
//		callMethod(ctx.getBean("hello", HelloWorld.class));

		// beanFactoryInternal();
		// beanFactoryExternal();
		// applicationContextInternal();
		// applicationContextExternal();
		// initTest();
		// postTest();
		// di();
		// setterDi();
		collectionDi();
	}

	public static void callMethod(HelloWorld hello) {
		hello.sayHello();
	}

	// 빈팩토리 - 패키지 내부 (폐기됨)
	public static void beanFactoryInternal() {
		ClassPathResource res = new ClassPathResource("/config/beans.xml");
		XmlBeanFactory factory = new XmlBeanFactory(res); // xml빈팩토리는 로딩할때 기본적으로 빈을 생성안한다(디폴트)

		TestBean t1 = factory.getBean("t1", TestBean.class); // 빈팩토리는 실제로 getBean했을때 빈을 생성
		System.out.printf("t1 : %s\n", t1);
		TestBean t2 = factory.getBean("t1", TestBean.class); // 빈은 싱글톤 (매번생성하도록 할수도있음)
		System.out.printf("t2 : %s\n", t2);
	}

	// 빈팩토리 - 패키지 외부 (폐기됨)
	public static void beanFactoryExternal() {
		FileSystemResource res = new FileSystemResource("beans.xml");
		XmlBeanFactory factory = new XmlBeanFactory(res); // xml빈팩토리는 로딩할때 기본적으로 빈을 생성안한다(디폴트)

		TestBean t1 = factory.getBean("t1", TestBean.class); // 빈팩토리는 실제로 getBean했을때 빈을 생성
		System.out.printf("t1 : %s\n", t1);
		TestBean t2 = factory.getBean("t1", TestBean.class); // 빈은 싱글톤 (매번생성하도록 할수도있음)
		System.out.printf("t1 : %s\n", t2);
	}

	// 어플리케이션컨택스트 - 패키지 내부
	public static void applicationContextInternal() {
		// ClassPathXmlApplicationContext는 로딩할때 기본적으로 빈을 생성한다(디폴트)
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/config/beans.xml");

		TestBean t1 = ctx.getBean("t1", TestBean.class);
		System.out.printf("t1 : %s\n", t1);
		TestBean t2 = ctx.getBean("t1", TestBean.class); // 빈은 싱글톤 (매번생성하도록 할수도있음)
		System.out.printf("t2 : %s\n", t2);
		TestBean t3 = ctx.getBean("t2", TestBean.class); // 빈은 싱글톤 (매번생성하도록 할수도있음)
		System.out.printf("t3 : %s\n", t3);
		TestBean t4 = ctx.getBean("t2", TestBean.class); // 빈은 싱글톤 (매번생성하도록 할수도있음)
		System.out.printf("t4 : %s\n", t4);

		ctx.close();
	}

	// 어플리케이션컨택스트 - 패키지 외부
	public static void applicationContextExternal() {
		// ClassPathXmlApplicationContext는 로딩할때 기본적으로 빈을 생성한다(디폴트)
		FileSystemXmlApplicationContext ctx = new FileSystemXmlApplicationContext("beans.xml");

		TestBean t1 = ctx.getBean("t1", TestBean.class);
		System.out.printf("t1 : %s\n", t1);
		TestBean t2 = ctx.getBean("t1", TestBean.class);
		System.out.printf("t2 : %s\n", t2);

		ctx.close();
	}

	// 어플리케이션컨택스트 - 패키지 내부
	public static void initTest() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/config/beans.xml");

		TestBean t3 = ctx.getBean("t3", TestBean.class); // 객체생성 - init메서드 호출
		System.out.printf("t3 : %s\n", t3);

		ctx.close(); // 객체소멸 - destroy메서드 호출
	}

	// 어플리케이션컨택스트 - 패키지 내부
	public static void postTest() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/config/beans.xml");

		TestBean t4 = ctx.getBean("t4", TestBean.class);
		System.out.printf("t4 : %s\n", t4);

		ctx.close();
	}

	// 생성자 주입
	public static void constructorDi() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/config/beans.xml");

		TestBean2 obj1 = ctx.getBean("obj1", TestBean2.class);
		System.out.printf("obj1 : %s\n", obj1);
		obj1.printData();

		ctx.close();
	}

	// setter 주입
	public static void setterDi() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/config/beans.xml");

		TestBean2 t5 = ctx.getBean("t5", TestBean2.class);
		System.out.printf("t5.data1 : %s\n", t5);
		t5.printData();

		ctx.close();
	}

	// 컬렉션 주입
	public static void collectionDi() {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/config/beans.xml");

		ListBean bean = ctx.getBean("listBean", ListBean.class);
		Map<String, Object> map = bean.getMap();

		for (String key : map.keySet()) {
			System.out.println(String.format("키 : " + key + ", 값 : " + map.get(key)));
		}

//		Properties prop1 = bean.getProperty();
//		System.out.printf("p1 : %s\n", prop1.getProperty("p1"));
//		System.out.printf("p2 : %s\n", prop1.getProperty("p2"));
//		System.out.printf("p3 : %s\n", prop1.getProperty("p3"));

		ctx.close();
	}
}

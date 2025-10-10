package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import beans.Bean1;
import beans.TestBean2;

public class MainAutowired {

	public static void main(String[] args) {
		ApplicationContext ctx = new GenericXmlApplicationContext("/config/beans-autowired.xml");
		Bean1 obj1 = ctx.getBean("obj1", Bean1.class);
		System.out.println(obj1.getDataBean1());
		System.out.println(obj1.getDataBean2());

		System.out.println("================================");
		Bean1 obj2 = ctx.getBean("obj2", Bean1.class);
		System.out.println(obj2.getDataBean1());
		System.out.println(obj2.getDataBean2());

		System.out.println("================================");
		TestBean2 obj4 = ctx.getBean("obj4", TestBean2.class);
		obj4.printData();

		System.out.println();
		System.out.println("================================");
		Bean1 obj5 = ctx.getBean("obj5", Bean1.class);
		System.out.println(obj5.getDataBean1());
		System.out.println(obj5.getDataBean2());
	}
}

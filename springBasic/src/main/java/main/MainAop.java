package main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import beans.Bean1;
import beans.Bean2;
import beans.DataBean1;
import beans.TestBean;
import beans.TestBean2;
import config.BeanConfig_component;

public class MainAop {

	public static void main(String[] args) {
		// xml을 이용하는방법
		ApplicationContext xml = new GenericXmlApplicationContext("/config/annotataionBean.xml");
		System.out.println("=======================");
		TestBean2 xml2 = xml.getBean("tb2", TestBean2.class);
		System.out.printf("xml2 : %s\n", xml2.getTb());

		DataBean1 aop = xml.getBean(DataBean1.class);
		aop.aopTest("문자열");
		aop.aopTest("문자열", 1, 2);

		System.out.println("=======================");

		// java를 이용하는방법
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig_component.class);
		TestBean tb1 = ctx.getBean("testBean", TestBean.class);
		TestBean tb2 = ctx.getBean("testBean", TestBean.class);
		System.out.printf("tb1 : %s\n", tb1);
		System.out.printf("tb2 : %s\n", tb2);

		TestBean java500 = ctx.getBean("java500", TestBean.class);
		System.out.printf("java500 : %s\n", java500);

		DataBean1 dt1 = ctx.getBean(DataBean1.class);
		System.out.printf("dt1 : %s\n", dt1);

		Bean1 b1 = ctx.getBean(Bean1.class);
		System.out.printf("b1.getDataBean1 : %s\n", b1.getDataBean1());
		System.out.printf("b1.getDataBean2 : %s\n", b1.getDataBean2());

		TestBean2 tb22 = ctx.getBean(TestBean2.class);
		tb22.printData();

		System.out.println();
		System.out.println("=======================");
		Bean2 b2 = ctx.getBean(Bean2.class);
		b2.printData();

		ctx.close();
	}
}

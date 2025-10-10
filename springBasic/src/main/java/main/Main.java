package main;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import beans.DataBean1;
import beans.JdbcTableDto;
import config.BeanConfig_component;
import config.DbConfig;
import dao.JdbcDao;
import mapper.MapperInterface;

public class Main {

	public static void main(String[] args) {
		// xml을 이용하는방법
		ApplicationContext xml = new GenericXmlApplicationContext("/config/aspectJBean.xml");
		System.out.println("=======================");

		DataBean1 aop = xml.getBean(DataBean1.class);
		try {
			aop.aopTest();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("=======================");

		// java를 이용하는방법
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BeanConfig_component.class);

		ctx.close();

		System.out.println("============[JDBC]===========");

		AnnotationConfigApplicationContext db = new AnnotationConfigApplicationContext(DbConfig.class);
		JdbcDao dao = db.getBean(JdbcDao.class);
//		JdbcTableDto dto = new JdbcTableDto();
//		dto.setInt_data(1);
//		dto.setStr_data("abc");
//		dao.insert_data(dto);

		List<JdbcTableDto> list = dao.select_data();
		for (JdbcTableDto dto : list) {
			System.out.println(dto.getInt_data());
			System.out.println(dto.getStr_data());
		}

		System.out.println("저장완료");

		System.out.println("============[Mybatis]===========");
		MapperInterface mapper = db.getBean("getMapper", MapperInterface.class);

		db.close();
	}
}

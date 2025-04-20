package jpaStudy.dbTest;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/resources/*.xml" })
public class DataSourceTest {

	@Autowired
	private DataSource ds;

	@Autowired
	private SqlSessionFactory sqlFactory;

	@Test
	public void testFactory() {
		System.out.println(sqlFactory);
	}

	@Test
	public void testSession() throws Exception {
		try (SqlSession session = sqlFactory.openSession()) {
			System.out.println(session);
		} catch (Exception e) {

		}
	}

	@Test
	public void testConnection() throws Exception {

		try (Connection con = ds.getConnection()) {
			System.out.println("@" + con);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

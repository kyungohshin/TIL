package config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import mapper.MapperInterface;

@Configuration
@ComponentScan(basePackages = { "beans", "dao" })
public class DbConfig {

	@Bean
	public BasicDataSource source() {
		BasicDataSource source = new BasicDataSource();
		source.setDriverClassName("org.h2.Driver");
		source.setUrl("jdbc:h2:file:~/testdb");
		source.setUsername("shin");
		source.setPassword("shin123");

		return source;
	}

	// 외부라이브러리를 빈등록할때는 @Bean 등록하고
	// 내가 만든 클래스를 빈등록할때는 해당클래스에 @Component
	// Component되어있는 클래스는 알아서 집어넣어주기떄문에 @Autowired할필요가없음.
	@Bean
	public JdbcTemplate db(BasicDataSource source) {
		JdbcTemplate db = new JdbcTemplate(source);
		return db;
	}

	// SqlSessionFactory : jdbc를 처리하는 객체
	@Bean
	public SqlSessionFactory factory(BasicDataSource source) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(source);
		SqlSessionFactory factory = bean.getObject();
		return factory;
	}

	// Mapper
	@Bean
	public MapperFactoryBean<MapperInterface> getMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<MapperInterface> bean = new MapperFactoryBean<MapperInterface>(MapperInterface.class);
		bean.setSqlSessionFactory(factory);
		return bean;
	}
}

package mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import beans.JdbcTableDto;

public interface MapperInterface {
	/*
	 * @Results({
	 * 
	 * @Results(column = "int_data", property = "int_data"),
	 * 
	 * @Results(column = "str_data", property = "str_data")
	 * })
	 */
	@Select("select * from jdbc_table")
	List<JdbcTableDto> select();

	@Insert("insert into jdbc_tabel (int_data, str_data) values (#{int_data}, #{str_data})")
	void insert_data(JdbcTableDto bean);
}

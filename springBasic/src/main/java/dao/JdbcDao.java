package dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import beans.JdbcTableDto;

@Component
public class JdbcDao {

	@Autowired
	private JdbcTemplate jt;

	@Autowired
	private MapperClass mapper;

	public void insert_data(JdbcTableDto dto) {
		String sql = "insert into jdbc_table values(?, ?)";
		jt.update(sql, dto.getInt_data(), dto.getStr_data());
	}

	public List<JdbcTableDto> select_data() {
		String sql = "select int_data, str_data from jdbc_table";
		List<JdbcTableDto> list = jt.query(sql, mapper);
		return list;
	}

	public void update_data(JdbcTableDto dto) {
		String sql = "update jdbc_table set str_date = ? where int_data = ?";
		jt.update(sql, dto.getStr_data(), dto.getInt_data());
	}

	public void delete_data(int int_data) {
		String sql = "delete from jdbc_table where int_data = ?";
		jt.update(sql, int_data);
	}

}

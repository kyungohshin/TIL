package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import beans.JdbcTableDto;

@Component
public class MapperClass implements org.springframework.jdbc.core.RowMapper<JdbcTableDto> {
	@Override
	public JdbcTableDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		JdbcTableDto dto = new JdbcTableDto();
		dto.setInt_data(rs.getInt("int_data"));
		dto.setStr_data(rs.getString("str_data"));
		return dto;
	}
}

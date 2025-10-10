package tacos.repo;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import tacos.dto.Ingredient;
import tacos.dto.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository {
	private final JdbcTemplate jdbc;

	@Autowired // 생성자 주입 - 스프링에서는 생성자주입을 권장하고있기때문에 생성자가 1개일경우 생략해도 알아서 주입해줌
	public JdbcTacoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
		for (Ingredient ingredient : taco.getIngredients()) {
			saveIngredientToTaco(ingredient, tacoId);
		}
		return taco;
	}

	private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
		jdbc.update("insert into Taco_Ingredients (taco, ingredient) values (?, ?)",
		tacoId, ingredient.getId());

	}

	private long saveTacoInfo(Taco taco) {
		taco.setCreateAt(new Date());

		PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
				"insert into Taco (name, createdAt) values (?, ?)",
				Types.VARCHAR, Types.TIMESTAMP);
		preparedStatementCreatorFactory.setReturnGeneratedKeys(true);

		PreparedStatementCreator psc =
				preparedStatementCreatorFactory.newPreparedStatementCreator(
						Arrays.asList(
								taco.getName(),
								new Timestamp(taco.getCreateAt().getTime())));

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(psc, keyHolder);
		return keyHolder.getKey().longValue();
	}

}

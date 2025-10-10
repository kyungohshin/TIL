package tacos.repo;

import tacos.dto.Ingredient;

public interface IngredientRepository {
	Iterable<Ingredient> findAll();

	Ingredient findById(String id);

	Ingredient save(Ingredient Ingredient);

}

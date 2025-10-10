package tacos.repo;

import org.springframework.data.repository.CrudRepository;

import tacos.dto.Ingredient;

/*
 * JDBC는 레포지토리가 제공할 메서드를 우리가 직접 선언 ex) save, findById..
 * 하지만, JPA서는 미리 만들어놓은 레포를 상속받기만하면됨.
 * CrudRepository<레포에 저장되는 개체 타입, 개체 ID속성 타입>
 */
public interface IngredientRepository extends CrudRepository<Ingredient, String> {
//	Iterable<Ingredient> findAll(); 이렇게 우리가 안써도된다는뜻.
//	Ingredient findById(String id);
//	Ingredient save(Ingredient Ingredient);

}

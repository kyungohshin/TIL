package tacos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import tacos.dto.Ingredient;
import tacos.dto.Ingredient.Type;
import tacos.repo.IngredientRepository;

/*
 * 아래의 3개의 어노테이션이 결합된 효과
 *
 * @SpringBootConfiguration - applicationContext.xml같은 구성클래스를 자바로 작성한클래스
 *
 * @EnableAutoConfiguration - 스프링부트 자동-구성을 활성화
 *
 * @ComponentScan - 컴포넌트 검색 활성화
 */
@SpringBootApplication // 스프링부트 애플리케이션임을 나타냄 (이걸로 시작)
public class TacoCloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacoCloudApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(IngredientRepository repo) { // 스프링부트 시작할때 실행되는 메서드
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				repo.save(new Ingredient("FLTO", "Flour Tortilla", Type.WRAP));
				repo.save(new Ingredient("COTO", "Corn Tortilla", Type.WRAP));
				repo.save(new Ingredient("GRBF", "Ground Beef", Type.PROTEIN));
				repo.save(new Ingredient("CARN", "Carnitas", Type.PROTEIN));
				repo.save(new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES));
				repo.save(new Ingredient("LETC", "Lettuce", Type.VEGGIES));
				repo.save(new Ingredient("CHED", "Cheddar", Type.CHEESE));
				repo.save(new Ingredient("JACK", "Monterrey Jack", Type.CHEESE));
				repo.save(new Ingredient("SLSA", "Salsa", Type.SAUCE));
				repo.save(new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
			}
		};
	}

}

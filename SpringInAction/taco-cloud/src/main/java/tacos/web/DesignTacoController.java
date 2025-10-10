package tacos.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import tacos.dto.Ingredient;
import tacos.dto.Ingredient.Type;
import tacos.dto.Order;
import tacos.dto.Taco;
import tacos.repo.IngredientRepository;
import tacos.repo.TacoRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

	private final IngredientRepository ingredientRepository;
	private final TacoRepository tacoRepository;

	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
		this.ingredientRepository = ingredientRepository;
		this.tacoRepository = tacoRepository;
	}

	@ModelAttribute(name = "order") // 객체를 생성해서 모델에 넣음
	public Order order() {
		return new Order();
	}

	@ModelAttribute(name = "taco")
	public Taco toco() {
		return new Taco();
	}

	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepository.findAll().forEach(i -> ingredients.add(i));

		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(),
					filterByType(ingredients, type));
		}

		model.addAttribute("taco", new Taco());

		return "design"; // 뷰네임
	}

	private List<Ingredient> filterByType(
			List<Ingredient> ingredients, Type type) {
		return ingredients
				.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}

	// @Valid 해당 객체의 유효성검사를 실행하라는 의미. 유효성검사 실패시 Errors객체에 저장
	@PostMapping
	public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order/*모델로부터 전달된 객체*/) {
		if (errors.hasErrors()) {
			return "design";
		}

		Taco saved = tacoRepository.save(design);
		order.addDesign(saved);

		return "redirect:/orders/current";
	}
}
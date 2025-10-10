package tacos.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
/*
 * JPA는 인자없는 생성자를 가져야만하기때문에 지정. 하지만 외부에서 이 생성자를 사용하는것을 원치않기때문에 access추가
 * 초기화가 필요한 final 속성때문에 force설정. lombok이 자동 생성한 생성자에서 속성들은 null로 박히게됨.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity // JPA사용시 반드시 지정
public class Ingredient {

	@Id // pk임을 나타냄
	private final String id;
	private final String name;
	private final Type type;

	public static enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}
}

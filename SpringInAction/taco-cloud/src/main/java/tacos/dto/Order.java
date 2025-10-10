package tacos.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Data
public class Order {
	private Long id;
	private Date placeAt;
	private List<Taco> tacos = new ArrayList<>();

	@NotBlank(message="Name is required")
	private String deliveryName;

	@NotBlank(message="Street is required")
	private String deliveryStreet;

	@NotBlank(message="City is required")
	private String deliveryCity;

	@NotBlank(message="State is required")
	private String deliveryState;

	@NotBlank(message="Zip code is required")
	private String deliveryZip;

	@CreditCardNumber(message="Not a valid credit card number")
	private String ccNumber;

	// 기존에 만들어진게 맞는게 없어서 정규표현식으로 유효성 검사
	@Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$",
			message="Must be formatted MM/YY")
	private String ccExpiration;

	// 3자리 숫자인지 확인
	@Digits(integer=3, fraction=0, message="Invalid CVV")
	private String ccCVV;

	public void addDesign(Taco design) {
		this.tacos.add(design);

	}
}
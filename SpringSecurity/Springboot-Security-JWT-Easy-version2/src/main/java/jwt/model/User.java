package jwt.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String username;
	private String password;
	private String role;

//	public List<String> getRoleList() {
//		if (this.role.length() > 0) {
//			return Arrays.asList(this.role.split(","));
//		}
//		return new ArrayList<>();
//	}

}

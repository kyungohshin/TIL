package security.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
/*
 * JPA는 인자없는 생성자를 가져야만하기때문에 지정.
 * 그리고 @@Builder는 @NoArgsConstructor와 같이 사용할수없으므로 @AllArgsConstructor를 붙여줌
 */
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String email;
	private String role;
	private String provider;
	private String providerId;

	@CreationTimestamp
	private Timestamp createDate;

}

package jwt.repository;

import org.springframework.data.repository.CrudRepository;

import jwt.model.User;

// CRUD 자동생성
// @Repository 어노테이션이 필요없음. JpaRepository를 상속했기때문에 알아서 빈으로 등록됨.
public interface UserRepository extends CrudRepository<User, Integer> {
	public User findByUsername(String username);
}

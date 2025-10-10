package tacos.repo;

import org.springframework.data.repository.CrudRepository;

import tacos.dto.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findByUsername(String username);
}
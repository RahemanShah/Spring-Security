package in.pr.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.pr.Modal.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByEmail(String email);
	
}

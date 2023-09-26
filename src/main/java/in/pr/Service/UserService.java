package in.pr.Service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import in.pr.Exceptions.UserException;
import in.pr.Modal.User;

public interface UserService {

	public User findUserById(Integer userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;

}

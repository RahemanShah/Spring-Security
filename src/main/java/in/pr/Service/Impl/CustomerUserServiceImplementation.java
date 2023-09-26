package in.pr.Service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.pr.Exceptions.UserException;
import in.pr.Modal.User;
import in.pr.Repo.UserRepository;

@Service
public class CustomerUserServiceImplementation implements UserDetailsService {
	
	
	@Autowired
	private UserRepository userRepo;
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepo.findByEmail(username);   // koi username ko email se login kr sakta hai
		
		//null meanse user not register and not nul meanse user already register
		if(user == null) {
		   throw new UserException("user not found with this email -->"+username);
			//throw new UsernameNotFoundException("user not found with this email -->"+username);
		}
		
		//create authorities set
		List<GrantedAuthority> authorities = new ArrayList<>(); 
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassWord(),authorities);
	}

}

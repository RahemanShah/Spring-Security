package in.pr.Service.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.pr.Config.JwtProvider;
import in.pr.Exceptions.UserException;
import in.pr.Modal.User;
import in.pr.Repo.UserRepository; //user is a user model user not security user
import in.pr.Request.LoginRequest;
import in.pr.Response.AuthResponse;
import in.pr.Service.UserService;
import in.pr.Service.Impl.CustomerUserServiceImplementation;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomerUserServiceImplementation customeUserServiceImpl;

	// signUp

	@PostMapping("/signups")
	public ResponseEntity<AuthResponse> createUserHandeler(@RequestBody User user) throws UserException {

		String email = user.getEmail();
		String passWord = user.getPassWord();
		String firstName = user.getFirstName();
		String lastName = user.getLastName();

		// if email already exists

		User emailExists = userRepo.findByEmail(email);

		if (emailExists != null) {
			throw new UserException("email already used with another account");
		}

		// if email not exists

		User createUser = new User();
		createUser.setEmail(email);
		createUser.setPassWord(passwordEncoder.encode(passWord)); // encode ur password here
		createUser.setFirstName(firstName);
		createUser.setLastName(lastName);

		// save user
		User saveUser = userRepo.save(createUser);

		// authentication with security core package its
		Authentication authentication = new UsernamePasswordAuthenticationToken(saveUser.getEmail(),
				saveUser.getPassWord());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// generate token to autowired JwtProvider downside the repo field then now see
		String generateToken = jwtProvider.generateToken(authentication);

		// create object for authResponse
		AuthResponse authResponse = new AuthResponse();

		authResponse.setJwt(generateToken);
		authResponse.setMessage("SighUp Sucess");

		// provide status
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED); // finally return
	}

	// login service

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {

		String username = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
	
		Authentication authentication = authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// generate token to autowired JwtProvider downside the repo field then now see
		String generateToken = jwtProvider.generateToken(authentication);

		// create object for authResponse
		AuthResponse authResponse = new AuthResponse();

		authResponse.setJwt(generateToken);
		authResponse.setMessage("SignIn Sucess");

		// provide status
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED); // finally return

	}

	private Authentication authenticate(String username, String password) {

		UserDetails userDetail = customeUserServiceImpl.loadUserByUsername(username);

		if (userDetail == null) {
			//throw new BadCredentialsException("Inavalid username..!");
			throw new UserException("Inavalid username..!");
		}

		if (!passwordEncoder.matches(password, userDetail.getPassword())) { // {!} are there in the method
			//throw new BadCredentialsException("Inavalid Password...!");
			throw new UserException("Inavalid password..!");
		}

		return new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
	}
}

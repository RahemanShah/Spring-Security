package in.pr.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHadelings {
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<String> handelingUserExp(UserException exp){
		return new ResponseEntity<>(exp.getMessage(), HttpStatus.NOT_FOUND);
	}

}

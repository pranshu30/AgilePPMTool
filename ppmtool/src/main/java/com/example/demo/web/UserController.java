package com.example.demo.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Project;
import com.example.demo.domain.User;
import com.example.demo.payload.JWTLoginSuccessResponse;
import com.example.demo.payload.LoginRequest;
import com.example.demo.security.JWTTokenProvider;
import com.example.demo.services.MapValidationServiceError;
import com.example.demo.services.UserService;
import com.example.demo.validator.UserValidator;
import static com.example.demo.security.SecurityConstant.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService; 
	
	@Autowired
    private MapValidationServiceError mapValidationServiceError;
	
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	private JWTTokenProvider tokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User newUser, BindingResult result){
		userValidator.validate(newUser, result);
    	
		
		ResponseEntity<?> error = mapValidationServiceError.MapValidationError(result);
    	if(error!=null) { return error;}
        
    	
    	
    	User user = userService.saveUser(newUser);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
		
}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
		
		ResponseEntity<?> error = mapValidationServiceError.MapValidationError(result);
    	if(error!=null) { return error;}
    	
    	Authentication authentication = authenticationManager.authenticate(
    			new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
    			);
    	
    	SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX +  tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
	}
	
}

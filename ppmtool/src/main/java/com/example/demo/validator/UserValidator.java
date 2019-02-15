package com.example.demo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.domain.User;

@Component
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		//Supporting User class in domain
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		User user = (User) object;
		if(user.getPassword().length()<6) {
			errors.rejectValue("password","Length", "Password should be atleast 6 characters");
		}
			
		if(!user.getConfirmPassword().equals(user.getPassword())) {
			errors.rejectValue("confirmPassword","Match","Confirm Password and Password donot match");
			
		}
	}
	

}

package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.User;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User newUser) {
		try {
		newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
		
		
		//Username has to unique
		//If user already exists in database
		
		newUser.setUsername(newUser.getUsername());
		newUser.setConfirmPassword("");
		//Password and confirm password match
		//we don't persist or show confirm password
		return userRepository.save(newUser);
		
	}
	catch(Exception e) {
		throw new UserAlreadyExistsException("Username " + newUser.getUsername() + " exists");
	}


	}
}

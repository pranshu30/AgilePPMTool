package com.example.demo.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.domain.User;

import org.springframework.stereotype.Repository;

import java.util.Optional;
public interface UserRepository extends CrudRepository<User, Long> {
	
	User findByUsername(String username);
	
	User getById(Long id);
	
	

}

package com.example.demo.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


@Service
public class MapValidationServiceError {
	public ResponseEntity<?> MapValidationError(BindingResult result) {
		 if(result.hasErrors()) {
	        	Map<String,String> errorMap = new HashMap<>();
	        	for(FieldError err:result.getFieldErrors()) {
	        		errorMap.put(err.getField(), err.getDefaultMessage());
	        	}
	        	
	        	return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
	        }
		 return null;
	        
	}
  
}

package com.example.demo.exceptions;

public class UserAlreadyExistsReponse {
	
	private String username;

	public UserAlreadyExistsReponse(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	

}

package com.example.demo.dtos;

public class AuthenticationResponse {
	private String jwt;

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	@Override
	public String toString() {
		return "AuthenticationResponse [jwt=" + jwt + "]";
	}
	
}

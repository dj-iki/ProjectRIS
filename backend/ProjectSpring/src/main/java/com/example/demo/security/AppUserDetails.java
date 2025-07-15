package com.example.demo.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import model.AppUser;

public class AppUserDetails implements UserDetails {

	private AppUser au;

	public AppUser getAu() {
		return au;
	}

	public void setAu(AppUser au) {
		this.au = au;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + au.getRole().getName()));
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return au.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return au.getUsername();
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

}

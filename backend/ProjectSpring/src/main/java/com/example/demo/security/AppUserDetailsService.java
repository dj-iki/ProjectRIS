package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repositories.AppUserRepository;

import model.AppUser;

@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	AppUserRepository aur;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		AppUserDetails aud = new AppUserDetails();
		AppUser au = aur.findAppUserByUsername(username);
		if (au == null) {
			System.out.println("User with username '" + username + "' not found");
			throw new UsernameNotFoundException("User with username '" + username + "' not found");
		}
		aud.setAu(au);
		return aud;
	}

}

package com.smartcontact.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import com.smartcontact.dao_repository.UserRepository;
import com.smartcontact.entity.User;


public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user=userRepository.getUserByName(username);
		
		if(user==null) {
			throw new UsernameNotFoundException("could not found user !!");
		}
		CustomUserDetails customUserDetails=new CustomUserDetails(user);
		
		
		return customUserDetails;
	}

}

package com.greenapex.ELibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.greenapex.ELibrary.model.User;
import com.greenapex.ELibrary.repository.UserRepository;

@Service
public class UseService {
	@Autowired
	public UserRepository userRepo;

	public User save(User user) {
		// TODO Auto-generated method stub
		return userRepo.save(user);
	}

	public User addUser(User user) {
		// TODO Auto-generated method stub
		User addUser = userRepo.save(user);
		return addUser;
	}

	public User getCustomerDetails(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email);
	}

}

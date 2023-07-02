package com.osi.user.service.services;

import java.util.List;

import com.osi.user.service.entities.User;

public interface IUserService {
	
	//create
	User saveUser(User user);
	
	//get All
	List<User> getAllUser();
	
	//get single user
	User getUser(String userId);

}

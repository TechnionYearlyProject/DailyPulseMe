package com.YP.user;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class userService {
          @Autowired
	private UserRepository userRepositry;
      
	public User getUser(String id) {
	return userRepositry.findOne(id);
	}
	public List<User> getAllUsers(){
		List<User> users =new ArrayList<>();
		userRepositry.findAll()
		.forEach(users::add);
		return users;
	}
	public void addUser(User user) {
		userRepositry.save(user);
		
	}
	public void updateUser(String id, User user) {
		userRepositry.save(user);	
	}
	public void deleteUser(String id) {
		userRepositry.delete(id);
		
	}
	
}

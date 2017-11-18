package com.YP.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class userController {
	@Autowired
private userService user_Service;
	
	@RequestMapping("/users")
	public List<User> getAllUsers() {
		return user_Service.getAllUsers();
	}
	@RequestMapping("/users/{id}")
	public User getUser(@PathVariable String id) {
		return user_Service.getUser(id);
	}
	@RequestMapping(method=RequestMethod.POST,value="/users")
	public void addUser(@RequestBody User user) {
		user_Service.addUser(user);
		
	}
	@RequestMapping(method=RequestMethod.PUT,value="/users/{id}")
	public void updateUser(@RequestBody User user,@PathVariable String id) {
		user_Service.updateUser(id,user);
		
	}
	@RequestMapping(method=RequestMethod.DELETE,value="/users/{id}")
	public void deleteUser(@PathVariable String id) {
		user_Service.deleteUser(id);
		
	}
	
}

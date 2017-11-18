package com.YP.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="app_user")
public class User {
	
	@Id
	private String id;
	private String firstName;
    private String lastName;
    private String password;
    private String email;
    
    public User(String id, String firstName, String lastName, String password, String email) {
		super();
	
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		
	}
	public User() {
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void updateAll(User user) {
		this.id=user.id;
	this.lastName=user.lastName;
	this.firstName=user.firstName;
	this.password=user.password;
	this.email=user.email;
	}
	
    
}

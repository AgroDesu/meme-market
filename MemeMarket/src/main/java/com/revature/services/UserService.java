package com.revature.services;

import com.revature.beans.Patron;
import com.revature.beans.User;

public interface UserService {
	public int addUser(User u, Patron p);
	public int addUser(User u);
	public User getUser(String username, String password);
	public User getUser(User u);
	public User getUserById(Integer id);
	public boolean deleteUser(User u);
	public boolean updateUser(User u);
}
package com.revature.data;

import java.util.List;

import com.revature.beans.User;

public interface UserDao {
	public int addUser(User u);
	
	public User getUser(String username, String password);
	
	public User getUser(String username);
	
	public User getUserByPatron(Integer id);
	
	public List<User> getAll();
	
	public User getUser(User u);
	

	public User getUserById(Integer id);
	

	public boolean deleteUser(User u);
	

	public boolean updateUser(User user);
}

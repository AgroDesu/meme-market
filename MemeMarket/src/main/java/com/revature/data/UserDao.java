package com.revature.data;

import com.revature.beans.User;

public interface UserDao {
	/**
	 * Returns the id of a user object inserted into the database.
	 * 
	 * @param user the user object to be inserted
	 * @return the id of the user object inserted
	 */
	public int addUser(User u);
	
	/**
	 * returns a login object from the database
	 * 
	 * @param username the username of the user
	 * @param password the password of the user
	 * @return the user from the database that matches the username and password
	 */
	public User getUser(String username, String password);
	
	/**
	 * returns a login object from the database
	 * 
	 * @param u previously created user object for updating with user information
	 * @return the user from the database that matches the username and password
	 */
	public User getUser(User u);
	
	/**
	 * returns a login object from the database
	 * 
	 * @param u previously created user object for updating with user information
	 * @return the user from the database that matches the username and password
	 */
	public User getUserById(Integer id);
	
	/**
	 * deletes a User from the database
	 * 
	 * @param user the User to be deleted
	 */
	public boolean deleteUser(User u);
	
	/**
	 * updates a User in the database
	 * 
	 * @param user the User to be updated
	 */
	public boolean updateUser(User user);
}

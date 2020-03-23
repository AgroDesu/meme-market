package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.beans.Patron;
import com.revature.beans.User;
import com.revature.data.PatronDao;
import com.revature.data.UserDao;

@Service
public class UserServiceHibernate implements UserService{
	@Autowired
	UserDao ud;
	@Autowired
	PatronDao pd;
	
	@Override
	public int addUser(User u, Patron p) {
		pd.addPatron(p);
		u.setPatron(p);
		ud.addUser(u);
		return u.getId();
	}
	@Override
	public int addUser(User u) {
		return ud.addUser(u);
	}
	@Override
	public User getUser(String username, String password) {
		return ud.getUser(username, password);
	}
	
	public List<User> getAllPatronUsers(){
		List<User> uList = ud.getAll();
		List<User> patronUserList = new ArrayList<>();
		for(int i=0;i<uList.size();i++) {
			if(uList.get(i).getPatron() != null) {
				patronUserList.add(uList.get(i));
			}
		}
		return patronUserList;
	}
	
	@Override
	public User getUser(String username) {
		return ud.getUser(username);
	}
	@Override
	public User getUserByPatron(Integer id) {
		return ud.getUserByPatron(id);
	}
	@Override
	public User getUser(User u) {
		return ud.getUser(u);
	}
	@Override
	public User getUserById(Integer id) {
		return ud.getUserById(id);
	}
	@Override
	public boolean deleteUser(User u) {
		return ud.deleteUser(u);
	}
	@Override
	public boolean updateUser(User u) {
		return ud.updateUser(u);
	}
	
}

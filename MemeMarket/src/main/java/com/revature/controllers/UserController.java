package com.revature.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Patron;
import com.revature.beans.User;
import com.revature.services.UserService;

@RestController
@CrossOrigin(origins= {"http://localhost:4200"})
public class UserController {
	@Autowired
	private UserService us;
	
	@GetMapping(path="/user/{id}")
	private ResponseEntity<User> getUser(@PathVariable Integer id) {
		User u = us.getUserById(id);
		return ResponseEntity.ok(u);
	}
	
	@GetMapping(path="/userpatron/{id}")
	private ResponseEntity<User> getUserByPatron(@PathVariable Integer id) {
		User u = us.getUserByPatron(id);
		return ResponseEntity.ok(u);
	}
	
	@PostMapping(path="/user")
	private ResponseEntity<Integer> addUser(@RequestBody User u) {
		Patron p = new Patron();
		p.setStonks(100);
		us.addUser(u, p);
		return ResponseEntity.ok(u.getId());
	}
	
	@PutMapping(path="/user")
	private ResponseEntity<User> updateUser(@RequestBody User u, HttpSession session) {
		// This method basically only updates stonks because of an issue with OwnedCards
		Integer stonks = u.getPatron().getStonks();
		User user = us.getUserById(u.getId());
		user.getPatron().setStonks(stonks);
		us.updateUser(user);
		session.setAttribute("loggedUser", user);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping(path="/patronUsers")
	private ResponseEntity<List<User>> getPatronUsers() {
		List<User> uList = us.getAllPatronUsers();
		return ResponseEntity.ok(uList);
	}
	
	@DeleteMapping(path="/user/{username}")
	private ResponseEntity<Object> deleteUser(@PathVariable String username) {
		User u = us.getUser(username);
		if(us.deleteUser(u)) {
			return ResponseEntity.status(204).build();
		}
		return ResponseEntity.status(500).build();
	}
}

package com.revature.controllers;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.beans.Trade;
import com.revature.beans.User;
import com.revature.services.TradeService;
import com.revature.services.UserService;

@RestController
@CrossOrigin(origins= {"http://localhost:4200"})
public class TradeController {
	@Autowired
	private TradeService ts;
	@Autowired
	private UserService us;
	
	@PostMapping(path="/trade")
	private ResponseEntity<Trade> addTrade(@RequestBody Trade t) {
		ts.addTrade(t);
		return ResponseEntity.ok(t);
	}
	
	@PostMapping(path="/trade/accept")
	private ResponseEntity<Trade> acceptTrade(@RequestBody Trade t, HttpSession session) {
		ts.acceptTrade(t);
		User u = (User) session.getAttribute("loggedUser");
		session.setAttribute("loggedUser", us.getUserById(u.getId()));
		return ResponseEntity.ok(t);
	}
	
	@PostMapping(path="/trade/reject")
	private ResponseEntity<Trade> rejectTrade(@RequestBody Trade t, HttpSession session) {
		if (ts.rejectTrade(t)) {
			return ResponseEntity.ok(t);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@GetMapping(path="/trade")
	private ResponseEntity<Set<Trade>> getTrades(HttpSession session) {
		User u = (User) session.getAttribute("loggedUser");
		if (u == null) return ResponseEntity.badRequest().build();
		return ResponseEntity.ok(ts.getTrades(u.getId()));
	}
}

package com.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.calculator.model.UserVO;
import com.calculator.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam("username") String username, 
						@RequestParam("password") String password) {
		return userService.login(username, password);
	}
	
	@ResponseBody
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String register(@RequestParam("username") String username, 
			@RequestParam("password") String password) {
		return userService.saveUser(new UserVO(username, password));
	}
	
	@RequestMapping("/logout")
	public String logout() {
		return userService.logout();
	}
	
}

package com.boney.security.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.boney.security.model.Account;
import com.boney.security.model.LoginRequestResponse;
import com.boney.security.service.AccountDetailsServiceImpl;

@Controller
public class LoginController {

	@Autowired
	AccountDetailsServiceImpl accountService;

	@GetMapping("/login")
	public String doLogin(Model model) {
		return "login";
	}

	@GetMapping("/logout")
	public String doLogOut(Model model, HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "loginOut";
	}

	@PostMapping("/login")
	public ResponseEntity<Boolean> doLogin(@RequestBody LoginRequestResponse login) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserDetails user = accountService.loadUserByUsername(auth.getName());
		System.out.println(user.getPassword());
		System.out.println(login.getEmailAddress() + "===>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println(login.getPassword() + "===================>>>>>>>>&&&&&&&&");

		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}

}

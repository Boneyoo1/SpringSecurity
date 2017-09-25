package com.boney.security.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.boney.security.dao.AccountDetailsDao;

public class CustomUsernameFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
	AccountDetailsDao accountDetailsDao;

	/*public CustomUsernameFilter() {
		super(new AntPathRequestMatcher("/login", "POST"));
		// TODO Auto-generated constructor stub
	}*/

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		String password = request.getParameter("form-password");
		if (accountDetailsDao != null) {
			System.out.println("Is not Null");
		} else {
			System.out.println("Is Null");

		}

		System.out.println(password+"==========");
		return super.obtainUsername(request);
	}

	/*
	 * @Override public Authentication attemptAuthentication(HttpServletRequest
	 * request, HttpServletResponse response) throws AuthenticationException,
	 * IOException, ServletException {
	 * 
	 * String username = request.getParameter("form-username"); String password =
	 * request.getParameter("form-password"); if (accountDetailsDao != null) {
	 * System.out.println("Is not Null"); } else { System.out.println("Is Null");
	 * 
	 * }
	 * 
	 * System.out.println(username); System.out.println(password);
	 * UsernamePasswordAuthenticationToken token = new
	 * UsernamePasswordAuthenticationToken(username, password); return
	 * this.getAuthenticationManager().authenticate(token); }
	 * 
	 */}

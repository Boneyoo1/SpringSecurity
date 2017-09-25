package com.boney.security.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.boney.security.dao.AccountDao;
import com.boney.security.dao.AccountDaoImpl;
import com.boney.security.dao.AccountDetailsDao;
import com.boney.security.handler.AccountSuccessHandler;
import com.boney.security.model.Account;
import com.boney.security.model.AccountUserDetails;
import com.boney.security.service.AccountDetailsServiceImpl;

public class AccountAuthenticationManager implements AuthenticationManager {
	@Autowired
	AccountSuccessHandler aservice;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		AccountDao accountDetailsDao = new AccountDaoImpl();
		Account details = null;
		List<GrantedAuthority> list = null;
		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
		String password = null;
		String username = authentication.getPrincipal().toString();
		if (token.getCredentials() != null) {
			password = authentication.getCredentials().toString(); // retrieve the password
			System.out.println(password + "======++++++++++++========= Password");
			// do something here
		}

		System.out.println(username + "======++++++++++++=========");
		list = new ArrayList<>();
		if (username != null) {
			details = accountDetailsDao.findByEmailAddress(username);
		}
		if (details != null) {
			if ((details.getPassword()).equals(password)) {
				list.add(new SimpleGrantedAuthority("ROLE_" + details.getType()));
			}
			System.out.println(username + "======++++++++++++========= Not Null");
		}
		System.out.println(password + "======++++++++++++=========");

		authentication.setAuthenticated(true);

		return authentication;
	}

}

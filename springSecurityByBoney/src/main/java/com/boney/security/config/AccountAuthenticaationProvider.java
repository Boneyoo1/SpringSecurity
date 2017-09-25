package com.boney.security.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.boney.security.dao.AccountDetailsDao;
import com.boney.security.model.Account;
import com.boney.security.model.AccountUser;

public class AccountAuthenticaationProvider implements AuthenticationProvider {

	@Autowired
	AccountDetailsDao dao;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName().trim();
		String password = authentication.getCredentials().toString().trim();
		Authentication auth = null;
		Account acount = new Account();

		Collection<GrantedAuthority> grantedAuths = new ArrayList<>();
		Optional<Account> accountop = dao.findByEmailAddress(userName);
		if (accountop != null) {
			grantedAuths.add(new SimpleGrantedAuthority("ROLE_" + accountop.get().getType()));
		} else {
			throw new UsernameNotFoundException("No account Found");
		}
		AccountUser appUser = new AccountUser(userName, password, true, true, true, true, grantedAuths, "TestEmail");
		auth = new UsernamePasswordAuthenticationToken(appUser, password, grantedAuths);
		return auth;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}

}

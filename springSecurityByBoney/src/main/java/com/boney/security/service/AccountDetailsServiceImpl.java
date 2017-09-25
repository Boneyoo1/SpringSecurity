package com.boney.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.boney.security.dao.AccountDao;
import com.boney.security.dao.AccountDetailsDao;
import com.boney.security.model.Account;
import com.boney.security.model.AccountUserDetails;

@Service
public class AccountDetailsServiceImpl implements UserDetailsService {

	@Autowired
	AccountDetailsDao accountDetailsDao;
	@Autowired
	AccountDao accountDao;

	@Override
	public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
		System.out.println(emailAddress + "======================<<<<<<<<>>>>>>>>>>>");
		Optional<Account> optionalUsers = accountDetailsDao.findByEmailAddress(emailAddress);
		optionalUsers.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
		return optionalUsers.map(AccountUserDetails::new).get();
	}

	public Account getAccount(String emailAddress) {

		return accountDao.findByEmailAddress(emailAddress);

	}
}

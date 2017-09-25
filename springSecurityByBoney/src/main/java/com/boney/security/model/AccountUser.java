package com.boney.security.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class AccountUser extends org.springframework.security.core.userdetails.User {
	private final String email;

	public AccountUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<GrantedAuthority> authorities,
			String email) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

}

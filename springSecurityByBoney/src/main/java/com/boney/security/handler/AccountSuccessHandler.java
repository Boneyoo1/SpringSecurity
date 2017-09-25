package com.boney.security.handler;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.boney.security.model.Account;

@Component
public class AccountSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		if (authentication.getCredentials() != null) {
			System.out.println(authentication.getCredentials().toString() + " @@@@@@@@@@@@@@@@@@");

		}
		System.out.println(authentication.getName());
		String targetUrl = determineTargetUrl(authentication);
		if (response.isCommitted()) {
			System.out.println("Can't redirect");
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(Authentication authentication) {
		boolean isSuperAdmin = false;
		boolean isStudent = false;
		boolean isSchoolAdmin = false;
		boolean isAgency = false;
		boolean isManager = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_STUDENT")) {
				isStudent = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROLE_SCHOOL_ADMIN")) {
				isSchoolAdmin = true;
				break;
			} else if (grantedAuthority.getAuthority().equals("ROLE_SUPER_ADMIN")) {
				isSuperAdmin = true;
				break;
			}
		}

		if (isStudent) {
			return "/dashboard";
		} else if (isSchoolAdmin) {
			return "/admindashboard";
		} else if (isSuperAdmin) {
			return "/collegeSetup";
		} else {
			throw new IllegalStateException();
		}
	}

	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
}

package com.boney.security.config;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import java.security.AuthProvider;
import java.util.Arrays;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

import com.boney.security.filter.CustomUsernameFilter;
import com.boney.security.handler.AccountSuccessHandler;
import com.boney.security.manager.AccountAuthenticationManager;
import com.boney.security.password.AccounCustomPasswordEncoder;
import com.boney.security.service.AccountDetailsServiceImpl;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
@EnableJpaRepositories(basePackages = { "com.boney.security" })
@EnableWebSecurity
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private AccountDetailsServiceImpl accountDetailsServiceImpl;

	@Autowired
	AccountSuccessHandler accountSuccessHandler;

	@Autowired
	AccountDetailsServiceImpl userDetailsService;

	@Bean
	public CustomUsernameFilter customUsernamePasswordAuthenticationFilter() throws Exception {
		CustomUsernameFilter customUsernamePasswordAuthenticationFilter = new CustomUsernameFilter();
		customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
		return customUsernamePasswordAuthenticationFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(new AccounCustomPasswordEncoder());

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		super.configure(web);
	}

	@Override

	protected void configure(HttpSecurity http) throws Exception {
		http
				.authenticationProvider(new AccountAuthenticaationProvider()).authorizeRequests()
				.antMatchers("/dashboard").hasAnyRole("STUDENT").antMatchers("/admindashboard").hasRole("SCHOOL_ADMIN")
				.antMatchers("/collegeSetup").hasAnyRole("SUPER_ADMIN").antMatchers("/").permitAll().anyRequest()
				.authenticated().and().addFilterBefore(new CustomUsernameFilter(), UsernamePasswordAuthenticationFilter.class).formLogin().loginPage("/login").usernameParameter("form-username")
				.passwordParameter("form-password").successHandler(accountSuccessHandler).permitAll().and().logout()
				.logoutUrl("/logout").permitAll();
	}

	private PasswordEncoder getPasswordEncoder() {
		return new PasswordEncoder() {
			@Override
			public String encode(CharSequence charSequence) {
				System.out.println("@@@@@@@@@###############==" + charSequence + "@@@@@@@@@############");
				StringBuffer buffer = new StringBuffer();
				String s = charSequence.toString();
				System.out.println(s.hashCode());
				Integer n = s.hashCode();
				String nnn = new String();
				nnn.valueOf(n);
				System.out.println(nnn);
				return nnn;
			}

			@Override
			public boolean matches(CharSequence charSequence, String s) {
				return true;
			}
		};
	}

}

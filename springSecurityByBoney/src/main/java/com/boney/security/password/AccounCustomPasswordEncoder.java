package com.boney.security.password;

import org.springframework.security.crypto.password.PasswordEncoder;

public class AccounCustomPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		System.out.println("Control in password" + rawPassword);
		return rawPassword.toString().trim();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		boolean isvalid = false;
		String s = rawPassword.toString().trim();
		System.out.println(s.hashCode() + "ppppppppppppppppppp");
		System.out.println(encodedPassword + "oooooooooooooo");
		System.out.println(encodedPassword.trim() + "oooooooooooooo");
		Integer n = s.hashCode();
		String nnn = s.trim();
		nnn = nnn.valueOf(n);
		System.out.println(nnn + "uuuuuuuuuuuuuuuuuuuu");
		if (nnn.equals(encodedPassword.trim())) {
			isvalid = true;
		}
		System.out.println(nnn + "kkkkkkkkkkk");
		return isvalid;
	}

}

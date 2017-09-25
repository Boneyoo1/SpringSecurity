package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class PasswordResetRequestResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2844944936215758702L;

	private String emailAddress;

	private String newPassword;

	private String confirmPassword;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}

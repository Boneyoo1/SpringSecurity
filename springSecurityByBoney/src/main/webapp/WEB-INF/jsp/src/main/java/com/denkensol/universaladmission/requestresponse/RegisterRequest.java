package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;

public class RegisterRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4651231553575746877L;
	private String emailAddress;
	private String password;
	private Long accountGuid;
	private String accountType;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getAccountGuid() {
		return accountGuid;
	}

	public void setAccountGuid(Long accountGuid) {
		this.accountGuid = accountGuid;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "RegisterRequest [emailAddress=" + emailAddress + ", password=" + password + ", accountGuid="
				+ accountGuid + ", accountType=" + accountType + "]";
	}

	
}

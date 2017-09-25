package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;

public class SchoolProspectsResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2034530699768339367L;

	private String firstName;
	private String lastName;
	private String emailAddress;
	private String phoneNumber;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}

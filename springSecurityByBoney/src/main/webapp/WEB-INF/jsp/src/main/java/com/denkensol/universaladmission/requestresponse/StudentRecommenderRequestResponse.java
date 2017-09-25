package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;

import com.denkensol.universaladmission.entity.Account;

public class StudentRecommenderRequestResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1405777137296489550L;

	private Long studentRecommenderGuid;

	private Account account;

	private String recommenderName;

	private String position;

	private String organization;

	private String emailAddress;

	public Long getStudentRecommenderGuid() {
		return studentRecommenderGuid;
	}

	public void setStudentRecommenderGuid(Long studentRecommenderGuid) {
		this.studentRecommenderGuid = studentRecommenderGuid;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getRecommenderName() {
		return recommenderName;
	}

	public void setRecommenderName(String recommenderName) {
		this.recommenderName = recommenderName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}

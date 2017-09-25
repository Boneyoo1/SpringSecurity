/*
 * 
 */
package com.boney.security.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class AccountEntity.
 */
@Entity
@Table(name = "ACCOUNT")
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1723872448230486608L;

	@Id
	@Column(name = "ACCOUNT_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long guid;

	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;

	/** The password. */
	@Column(name = "PASSWORD")
	private String password;

	/** The salt. */
	@Column(name = "SALT")
	private String salt;

	/** The type. */
	@Column(name = "TYPE")
	private String type;

	/** The created time stamp. */
	@Column(name = "CREATED_TIMESTAMP")
	private Timestamp createdTimeStamp;

	public Account() {
		// TODO Auto-generated constructor stub
	}

	public Account(Account account) {
		this.guid = account.getGuid();
		this.emailAddress = account.getEmailAddress();
		this.password = account.password;
		this.salt = account.getSalt();
		this.type = account.getType();
		this.createdTimeStamp = account.getCreatedTimeStamp();
		System.out.println(account);
	}

	public Long getGuid() {
		return guid;
	}

	public void setGuid(Long guid) {
		this.guid = guid;
	}

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

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(Timestamp createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	@Override
	public String toString() {
		return "Account [guid=" + guid + ", emailAddress=" + emailAddress + ", password=" + password + ", salt=" + salt
				+ ", type=" + type + ", createdTimeStamp=" + createdTimeStamp + "]";
	}

}

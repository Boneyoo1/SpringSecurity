package com.denkensol.universaladmission.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name = "PASSWORD_RESETS")
@NamedNativeQuery(name = "GET_PEASSWORD_RESET_BY_GUID", query = "select RESET_GUID as resetGUID,EMAIL_ADDRESS as emailAddress,TIMESTAMP as resetTimeStamp FROM PASSWORD_RESETS where RESET_GUID =:resetGUID")
public class PasswordReset implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 282987344251702600L;

	@Id
	@Column(name = "RESET_GUID", unique = true)
	private String resetGUID;

	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;

	@Column(name = "TIMESTAMP")
	private Timestamp resetTimeStamp;

	public String getResetGUID() {
		return resetGUID;
	}

	public void setResetGUID(String resetGUID) {
		this.resetGUID = resetGUID;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Timestamp getResetTimeStamp() {
		return resetTimeStamp;
	}

	public void setResetTimeStamp(Timestamp resetTimeStamp) {
		this.resetTimeStamp = resetTimeStamp;
	}

	@Override
	public String toString() {
		return "PasswordReset [resetGUID=" + resetGUID + ", emailAddress=" + emailAddress + ", resetTimeStamp="
				+ resetTimeStamp + "]";
	}

}

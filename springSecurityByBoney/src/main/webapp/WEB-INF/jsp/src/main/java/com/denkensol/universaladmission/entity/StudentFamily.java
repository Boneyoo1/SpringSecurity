package com.denkensol.universaladmission.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENT_FAMILY")
public class StudentFamily implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -471020849310908780L;

	@Id
	@Column(name = "STUDENT_FAMILY_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentFamilyGuid;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "ACCOUNT_GUID")
	private Account account;

	@Column(name = "RELATION_TYPE")
	private String relationType;

	@Column(name = "FULL_NAME")
	private String fullName;

	@Column(name = "OCCUPATION")
	private String occupation;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	public Long getStudentFamilyGuid() {
		return studentFamilyGuid;
	}

	public void setStudentFamilyGuid(Long studentFamilyGuid) {
		this.studentFamilyGuid = studentFamilyGuid;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getRelationType() {
		return relationType;
	}

	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}

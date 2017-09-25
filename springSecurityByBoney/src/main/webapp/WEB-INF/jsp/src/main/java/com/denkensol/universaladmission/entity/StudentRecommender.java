package com.denkensol.universaladmission.entity;

import java.io.Serializable;

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
@Table(name = "STUDENT_RECOMMENDER")
public class StudentRecommender implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7678305440306696421L;

	@Id
	@Column(name = "STUDENT_RECOMMENDER_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentRecommenderGuid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_GUID")
	private Account account;

	@Column(name = "RECOMMENDER_NAME")
	private String recommenderName;

	@Column(name = "POSITION")
	private String position;

	@Column(name = "ORGANIZATION")
	private String organization;

	@Column(name = "EMAIL_ADDRESS")
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

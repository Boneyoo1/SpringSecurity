package com.denkensol.universaladmission.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENT_EMPLOYEMENT")
@NamedNativeQueries({
		@NamedNativeQuery(name = "GET_ALL_STUDENT_EMPLOYEMENTS", query = "select STUDENT_EMPLOYEMENT_GUID as studentEmployementGuid,EMPLOYER_NAME as employer,START_DATE as startDate,END_DATE as endDate,CURRENTLY_WORKING as currentlyWorking,POSITION_TITLE as positionTitle,JOB_CATEGORY as jobCategory,INDUSTRY_CATEGORY as industryCategory,SUPERVISOR_NAME as supervisorName,SUPERVISOR_PHONE_NUMBER as supervisorPhoneNumber , SUPERVISOR_EMAIL as supervisorEmail,CITY as city,STATE as state,COUNTRY_GUID as employementCountryGuid from STUDENT_EMPLOYEMENT where account_guid=:accountGuid "), })

public class StudentEmployement {

	@Id
	@Column(name = "STUDENT_EMPLOYEMENT_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentEmployementGuid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_GUID")
	private Account account;

	@Column(name = "EMPLOYER_NAME")
	private String employer;

	@Column(name = "START_DATE")
	private String startDate;

	@Column(name = "END_DATE")
	private String endDate;

	@Column(name = "CURRENTLY_WORKING")
	private String currentlyWorking;
	
	@Column(name = "POSITION_TITLE")
	private String positionTitle;

	@Column(name = "JOB_CATEGORY")
	private String jobCategory;

	@Column(name = "INDUSTRY_CATEGORY")
	private String industryCategory;

	@Column(name = "SUPERVISOR_NAME")
	private String supervisorName;

	@Column(name = "SUPERVISOR_PHONE_NUMBER")
	private String supervisorPhoneNumber;

	@Column(name = "SUPERVISOR_EMAIL")
	private String supervisorEmail;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_GUID")
	private Country employementCountry;

	public Long getStudentEmployementGuid() {
		return studentEmployementGuid;
	}

	public void setStudentEmployementGuid(Long studentEmployementGuid) {
		this.studentEmployementGuid = studentEmployementGuid;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCurrentlyWorking() {
		return currentlyWorking;
	}

	public void setCurrentlyWorking(String currentlyWorking) {
		this.currentlyWorking = currentlyWorking;
	}

	public String getPositionTitle() {
		return positionTitle;
	}

	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}

	public String getJobCategory() {
		return jobCategory;
	}

	public void setJobCategory(String jobCategory) {
		this.jobCategory = jobCategory;
	}

	public String getIndustryCategory() {
		return industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	public String getSupervisorName() {
		return supervisorName;
	}

	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Country getEmployementCountry() {
		return employementCountry;
	}

	public void setEmployementCountry(Country employementCountry) {
		this.employementCountry = employementCountry;
	}

	public String getSupervisorPhoneNumber() {
		return supervisorPhoneNumber;
	}

	public void setSupervisorPhoneNumber(String supervisorPhoneNumber) {
		this.supervisorPhoneNumber = supervisorPhoneNumber;
	}

	public String getSupervisorEmail() {
		return supervisorEmail;
	}

	public void setSupervisorEmail(String supervisorEmail) {
		this.supervisorEmail = supervisorEmail;
	}
	


}

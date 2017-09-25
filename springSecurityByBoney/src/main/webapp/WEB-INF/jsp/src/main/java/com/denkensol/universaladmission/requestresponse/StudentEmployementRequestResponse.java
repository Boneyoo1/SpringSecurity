package com.denkensol.universaladmission.requestresponse;

import java.math.BigInteger;

public class StudentEmployementRequestResponse {

	private BigInteger studentEmployementGuid;

	private String employer;

	private String startDate;

	private String endDate;

	private String positionTitle;

	private String jobCategory;

	private String industryCategory;

	private String supervisorName;

	private String supervisorPhoneNumber;

	private String supervisorEmail;

	private String city;

	private String state;

	private String currentlyWorking;

	private BigInteger employementCountryGuid;

	public BigInteger getStudentEmployementGuid() {
		return studentEmployementGuid;
	}

	public void setStudentEmployementGuid(BigInteger studentEmployementGuid) {
		this.studentEmployementGuid = studentEmployementGuid;
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

	public String getCurrentlyWorking() {
		return currentlyWorking;
	}

	public void setCurrentlyWorking(String currentlyWorking) {
		this.currentlyWorking = currentlyWorking;
	}

	public BigInteger getEmployementCountryGuid() {
		return employementCountryGuid;
	}

	public void setEmployementCountryGuid(BigInteger employementCountryGuid) {
		this.employementCountryGuid = employementCountryGuid;
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

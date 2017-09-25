package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.math.BigInteger;

public class StudentEducationRequestResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigInteger studentEducationGuid;

	private String startDate;
	private String endDate;
	private String degreeReceivedOn;
	private String cgpaObtained;
	private BigInteger degreeSchoolGuid;
	private String degreeObtained;
	private String major;
	private String schoolCity;
	private String schoolName;
	private String schoolCountry;
	private String currentlyAttending;
	
	public BigInteger getStudentEducationGuid() {
		return studentEducationGuid;
	}

	public void setStudentEducationGuid(BigInteger studentEducationGuid) {
		this.studentEducationGuid = studentEducationGuid;
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

	public String getDegreeReceivedOn() {
		return degreeReceivedOn;
	}

	public void setDegreeReceivedOn(String degreeReceivedOn) {
		this.degreeReceivedOn = degreeReceivedOn;
	}

	public String getCgpaObtained() {
		return cgpaObtained;
	}

	public void setCgpaObtained(String cgpaObtained) {
		this.cgpaObtained = cgpaObtained;
	}

	public BigInteger getDegreeSchoolGuid() {
		return degreeSchoolGuid;
	}

	public void setDegreeSchoolGuid(BigInteger degreeSchoolGuid) {
		this.degreeSchoolGuid = degreeSchoolGuid;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getSchoolCity() {
		return schoolCity;
	}

	public void setSchoolCity(String schoolCity) {
		this.schoolCity = schoolCity;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolCountry() {
		return schoolCountry;
	}

	public void setSchoolCountry(String schoolCountry) {
		this.schoolCountry = schoolCountry;
	}

	public String getDegreeObtained() {
		return degreeObtained;
	}

	public void setDegreeObtained(String degreeObtained) {
		this.degreeObtained = degreeObtained;
	}

	public String getCurrentlyAttending() {
		return currentlyAttending;
	}

	public void setCurrentlyAttending(String currentlyAttending) {
		this.currentlyAttending = currentlyAttending;
	}

}

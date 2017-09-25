package com.denkensol.universaladmission.entity;

import java.io.Serializable;

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
@Table(name = "STUDENT_EDUCATION")
@NamedNativeQueries({
		@NamedNativeQuery(name = "GET_ALL_STUDENT_EDUCATIONS", query = "select STUDENT_EDUCATION_GUID as studentEducationGuid,START_DATE as startDate,END_DATE as endDate,DEGREE_RECEIVED_ON as degreeReceivedOn,CURRENTLY_ATTENDING as currentlyAttending,CGPA_OBTAINED as cgpaObtained,SCHOOL_GUID as degreeSchoolGuid,DEGREE_OBTAINED as degreeObtained,major as major,school_city as schoolCity,school_country as schoolCountry,school_name as schoolName from STUDENT_EDUCATION where account_guid=:accountGuid "), })

public class StudentEducation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "STUDENT_EDUCATION_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentEducationGuid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_GUID")
	private Account account;

	@Column(name = "START_DATE")
	private String startDate;

	@Column(name = "END_DATE")
	private String endDate;

	@Column(name = "DEGREE_RECEIVED_ON")
	private String degreeReceivedOn;

	@Column(name="CURRENTLY_ATTENDING")
	private String currentlyAttending;
	
	@Column(name = "CGPA_OBTAINED")
	private String cgpaObtained;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOL_GUID")
	private School degreeSchool;

	@Column(name = "DEGREE_OBTAINED")
	private String degreeObtained;

	@Column(name = "MAJOR")
	private String major;

	@Column(name = "SCHOOL_CITY")
	private String schoolCity;

	@Column(name = "SCHOOL_NAME")
	private String schoolName;

	@Column(name = "SCHOOL_COUNTRY")
	private String schoolCountry;

	@Column(name = "SCHOOL_DEGREE_NAME")
	private String schoolDegreeName;

	public Long getStudentEducationGuid() {
		return studentEducationGuid;
	}

	public void setStudentEducationGuid(Long studentEducationGuid) {
		this.studentEducationGuid = studentEducationGuid;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
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

	public String getCurrentlyAttending() {
		return currentlyAttending;
	}

	public void setCurrentlyAttending(String currentlyAttending) {
		this.currentlyAttending = currentlyAttending;
	}

	public String getCgpaObtained() {
		return cgpaObtained;
	}

	public void setCgpaObtained(String cgpaObtained) {
		this.cgpaObtained = cgpaObtained;
	}

	public School getDegreeSchool() {
		return degreeSchool;
	}

	public void setDegreeSchool(School degreeSchool) {
		this.degreeSchool = degreeSchool;
	}

	public String getDegreeObtained() {
		return degreeObtained;
	}

	public void setDegreeObtained(String degreeObtained) {
		this.degreeObtained = degreeObtained;
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

	public String getSchoolDegreeName() {
		return schoolDegreeName;
	}

	public void setSchoolDegreeName(String schoolDegreeName) {
		this.schoolDegreeName = schoolDegreeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}

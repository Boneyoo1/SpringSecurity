package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.math.BigInteger;

public class SchoolDegreeResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7159363801028522955L;

	private BigInteger schoolDegreeGuid;
	private String degreeOffered;
	private String degreeDepartment;
	private String contactPerson;
	private String emailAddress;
	private String phoneNumber;
	private Double departmentMinGREScore;
	private Double departmentMinTOEFLScore;
	private Double departmentMinGPA;
	private Long schoolGuid;
	private String addressLineOne;
	private String addressLineTwo;
	private String addressLine3;
	private String city;
	private String state;
	private String zipCode;
	private Double deptMinGREVerbalScore;
	private Double deptMinGREQuantScore;
	private Double deptMinGREWritingtScore;
	private Double deptInternationalAppFees;
	private String degreeWebSite;
	private Double deptMinGMATScore;
	private String requiresGREScore;
	private String requiresGMATScore;

	public BigInteger getSchoolDegreeGuid() {
		return schoolDegreeGuid;
	}

	public void setSchoolDegreeGuid(BigInteger schoolDegreeGuid) {
		this.schoolDegreeGuid = schoolDegreeGuid;
	}

	public String getDegreeOffered() {
		return degreeOffered;
	}

	public void setDegreeOffered(String degreeOffered) {
		this.degreeOffered = degreeOffered;
	}

	public String getDegreeDepartment() {
		return degreeDepartment;
	}

	public void setDegreeDepartment(String degreeDepartment) {
		this.degreeDepartment = degreeDepartment;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
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

	public Double getDepartmentMinGREScore() {
		return departmentMinGREScore;
	}

	public void setDepartmentMinGREScore(Double departmentMinGREScore) {
		this.departmentMinGREScore = departmentMinGREScore;
	}

	public Double getDepartmentMinTOEFLScore() {
		return departmentMinTOEFLScore;
	}

	public void setDepartmentMinTOEFLScore(Double departmentMinTOEFLScore) {
		this.departmentMinTOEFLScore = departmentMinTOEFLScore;
	}

	public Double getDepartmentMinGPA() {
		return departmentMinGPA;
	}

	public void setDepartmentMinGPA(Double departmentMinGPA) {
		this.departmentMinGPA = departmentMinGPA;
	}

	public Long getSchoolGuid() {
		return schoolGuid;
	}

	public void setSchoolGuid(Long schoolGuid) {
		this.schoolGuid = schoolGuid;
	}

	public String getAddressLineOne() {
		return addressLineOne;
	}

	public void setAddressLineOne(String addressLineOne) {
		this.addressLineOne = addressLineOne;
	}

	public String getAddressLineTwo() {
		return addressLineTwo;
	}

	public void setAddressLineTwo(String addressLineTwo) {
		this.addressLineTwo = addressLineTwo;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
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

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Double getDeptMinGREVerbalScore() {
		return deptMinGREVerbalScore;
	}

	public void setDeptMinGREVerbalScore(Double deptMinGREVerbalScore) {
		this.deptMinGREVerbalScore = deptMinGREVerbalScore;
	}

	public Double getDeptMinGREQuantScore() {
		return deptMinGREQuantScore;
	}

	public void setDeptMinGREQuantScore(Double deptMinGREQuantScore) {
		this.deptMinGREQuantScore = deptMinGREQuantScore;
	}

	public Double getDeptMinGREWritingtScore() {
		return deptMinGREWritingtScore;
	}

	public void setDeptMinGREWritingtScore(Double deptMinGREWritingtScore) {
		this.deptMinGREWritingtScore = deptMinGREWritingtScore;
	}

	public Double getDeptInternationalAppFees() {
		return deptInternationalAppFees;
	}

	public void setDeptInternationalAppFees(Double deptInternationalAppFees) {
		this.deptInternationalAppFees = deptInternationalAppFees;
	}

	public String getDegreeWebSite() {
		return degreeWebSite;
	}

	public void setDegreeWebSite(String degreeWebSite) {
		this.degreeWebSite = degreeWebSite;
	}

	public Double getDeptMinGMATScore() {
		return deptMinGMATScore;
	}

	public void setDeptMinGMATScore(Double deptMinGMATScore) {
		this.deptMinGMATScore = deptMinGMATScore;
	}

	public String getRequiresGREScore() {
		return requiresGREScore;
	}

	public void setRequiresGREScore(String requiresGREScore) {
		this.requiresGREScore = requiresGREScore;
	}

	public String getRequiresGMATScore() {
		return requiresGMATScore;
	}

	public void setRequiresGMATScore(String requiresGMATScore) {
		this.requiresGMATScore = requiresGMATScore;
	}

	@Override
	public String toString() {
		return "SchoolDegreeResponse [schoolDegreeGuid=" + schoolDegreeGuid + ", degreeOffered=" + degreeOffered
				+ ", degreeDepartment=" + degreeDepartment + ", contactPerson=" + contactPerson + ", emailAddress="
				+ emailAddress + ", phoneNumber=" + phoneNumber + ", departmentMinGREScore=" + departmentMinGREScore
				+ ", departmentMinTOEFLScore=" + departmentMinTOEFLScore + ", departmentMinGPA=" + departmentMinGPA
				+ ", schoolGuid=" + schoolGuid + ", addressLineOne=" + addressLineOne + ", addressLineTwo="
				+ addressLineTwo + ", addressLine3=" + addressLine3 + ", city=" + city + ", state=" + state
				+ ", zipCode=" + zipCode + ", deptMinGREVerbalScore=" + deptMinGREVerbalScore
				+ ", deptMinGREQuantScore=" + deptMinGREQuantScore + ", deptMinGREWritingtScore="
				+ deptMinGREWritingtScore + ", deptInternationalAppFees=" + deptInternationalAppFees
				+ ", degreeWebSite=" + degreeWebSite + ", deptMinGMATScore=" + deptMinGMATScore + ", requiresGREScore="
				+ requiresGREScore + ", requiresGMATScore=" + requiresGMATScore + "]";
	}

}

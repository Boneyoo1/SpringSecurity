package com.denkensol.universaladmission.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SCHOOL_DEGREE")
@NamedNativeQueries({
		@NamedNativeQuery(name = "GET_ALL_SCHOOLS_DEGREES", query = "select SCHOOL_DEGREE_GUID as schoolDegreeGuid,DEPT_MIN_GMAT_SCORE as deptMinGMATScore,REQUIRES_GRE_SCORE as requiresGREScore,REQUIRES_GMAT_SCORE as requiresGMATScore,DEGREE_OFFERED as degreeOffered from SCHOOL_DEGREE"),
		@NamedNativeQuery(name = "GET_SCHOOL_DEGREES_BY_SCHOOL", query = "select SCHOOL_DEGREE_GUID as schoolDegreeGuid,DEGREE_OFFERED as degreeOffered,DEPT_MIN_GRE_VERBAL_SCORE as deptMinGREVerbalScore,DEPT_MIN_GMAT_SCORE as deptMinGMATScore,REQUIRES_GRE_SCORE as requiresGREScore,REQUIRES_GMAT_SCORE as requiresGMATScore,DEPT_MIN_GRE_QUANT_SCORE as deptMinGREQuantScore,DEPT_MIN_GRE_WRITING_SCORE as deptMinGREWritingtScore,DEPT_INTERNATIONL_APP_FEES as deptInternationalAppFees,DEGREE_WEBSITE as degreeWebSite from SCHOOL_DEGREE where SCHOOL_GUID=:schoolGuid"), })

public class SchoolDegree implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SCHOOL_DEGREE_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long schoolDegreeGuid;

	@Column(name = "DEGREE_OFFERED")
	private String degreeOffered;

	@Column(name = "DEGREE_DEPARTMENT")
	private String degreeDepartment;

	@Column(name = "CONTACT_PERSON")
	private String contactPerson;

	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	@Column(name = "DEPARTMENT_MIN_GRE_SCORE")
	private Double departmentMinGREScore;

	@Column(name = "DEPARTMENT_MIN_TOEFL_SCORE")
	private Double departmentMinTOEFLScore;

	@Column(name = "DEPARTMENT_MIN_GPA")
	private Double departmentMinGPA;

	@Column(name = "DEPT_MIN_GRE_VERBAL_SCORE")
	private Double deptMinGREVerbalScore;

	@Column(name = "DEPT_MIN_GRE_QUANT_SCORE")
	private Double deptMinGREQuantScore;

	@Column(name = "DEPT_MIN_GRE_WRITING_SCORE")
	private Double deptMinGREWritingtScore;

	@Column(name = "DEPT_INTERNATIONL_APP_FEES")
	private Double deptInternationalAppFees;

	@Column(name = "DEGREE_WEBSITE")
	private String degreeWebSite;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOL_GUID")
	private School school;

	@Column(name = "ADDRESS_LINE_1")
	private String addressLineOne;

	@Column(name = "ADDRESS_LINE_2")
	private String addressLineTwo;

	@Column(name = "ADDRESS_LINE_3")
	private String addressLine3;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIP_CODE")
	private String zipCode;
	
	@Column(name="DEPT_MIN_GMAT_SCORE")
	private Double deptMinGMATScore;

	@Column(name="REQUIRES_GRE_SCORE")
	private String requiresGREScore;
	
	@Column(name="REQUIRES_GMAT_SCORE")
	private String requiresGMATScore;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolDegree")
	private List<StudentApplication> studentApplications = new ArrayList<StudentApplication>();

	public Long getSchoolDegreeGuid() {
		return schoolDegreeGuid;
	}

	public void setSchoolDegreeGuid(Long schoolDegreeGuid) {
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

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
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

	public List<StudentApplication> getStudentApplications() {
		return studentApplications;
	}

	public void setStudentApplications(List<StudentApplication> studentApplications) {
		this.studentApplications = studentApplications;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((degreeOffered == null) ? 0 : degreeOffered.hashCode());
		result = prime * result + ((school == null) ? 0 : school.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SchoolDegree other = (SchoolDegree) obj;
		if (degreeOffered == null) {
			if (other.degreeOffered != null)
				return false;
		} else if (!degreeOffered.equals(other.degreeOffered))
			return false;
		if (school == null) {
			if (other.school != null)
				return false;
		} else if (!school.equals(other.school))
			return false;
		return true;
	}

}

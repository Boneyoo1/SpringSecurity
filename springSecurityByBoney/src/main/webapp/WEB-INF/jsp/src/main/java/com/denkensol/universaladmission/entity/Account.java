/*
 * 
 */
package com.denkensol.universaladmission.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "account")
	private StudentProfile studentProfile;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	private List<StudentDocument> studentDocuments = new ArrayList<StudentDocument>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	private List<StudentEducation> studentEducation = new ArrayList<StudentEducation>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	private List<StudentFamily> studentFamily = new ArrayList<StudentFamily>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	private List<StudentTesting> studentTesting = new ArrayList<StudentTesting>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	private List<StudentWriting> studentWriting = new ArrayList<StudentWriting>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	private List<StudentEmployement> studentEmployement = new ArrayList<StudentEmployement>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinTable(name = "STUDENT_SCHOOL", joinColumns = { @JoinColumn(name = "ACCOUNT_GUID") }, inverseJoinColumns = {
			@JoinColumn(name = "SCHOOL_GUID") })
	private List<School> schools = new ArrayList<School>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	private List<StudentApplication> studentApplications = new ArrayList<StudentApplication>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	private List<SchoolQuestionAnswer> studentQuestionAnswers = new ArrayList<SchoolQuestionAnswer>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	private List<StudentSchoolDocument> studentSchoolDocuments = new ArrayList<StudentSchoolDocument>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
	private List<StudentRecommender> studentRecommenders = new ArrayList<StudentRecommender>();

	/**
	 * Instantiates a new account entity.
	 * 
	 * @param account
	 */
	public Account(Account account) {
		super();
		this.guid = account.getGuid();
		this.password = account.getPassword();
		this.salt = account.getSalt();
		this.type = account.getType();
		this.createdTimeStamp = account.getCreatedTimeStamp();
	}

	/**
	 * Instantiates a new account entity.
	 *
	 * @param guid
	 *            the guid
	 * @param emailAddress
	 *            the email address
	 * @param password
	 *            the password
	 * @param salt
	 *            the salt
	 * @param type
	 *            the type
	 * @param createdTimeStamp
	 *            the created time stamp
	 * @param updatedTimeStamp
	 *            the updated time stamp
	 * @param createdBy
	 *            the created by
	 * @param updatedBy
	 *            the updated by
	 */
	public Account(Long guid, String userName, String password, String salt, String type, Timestamp createdTimeStamp) {
		super();
		this.guid = guid;
		this.password = password;
		this.salt = salt;
		this.type = type;
		this.createdTimeStamp = createdTimeStamp;

	}

	public Account() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the guid.
	 *
	 * @return the guid
	 */
	public Long getGuid() {
		return guid;
	}

	/**
	 * Sets the guid.
	 *
	 * @param guid
	 *            the new guid
	 */
	public void setGuid(Long guid) {
		this.guid = guid;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the salt.
	 *
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * Sets the salt.
	 *
	 * @param salt
	 *            the new salt
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the created time stamp.
	 *
	 * @return the created time stamp
	 */
	public Timestamp getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public StudentProfile getStudentProfile() {
		return studentProfile;
	}

	public void setStudentProfile(StudentProfile studentProfile) {
		this.studentProfile = studentProfile;
	}

	public List<StudentDocument> getStudentDocuments() {
		return studentDocuments;
	}

	public void setStudentDocuments(List<StudentDocument> studentDocuments) {
		this.studentDocuments = studentDocuments;
	}

	public List<StudentEducation> getStudentEducation() {
		return studentEducation;
	}

	public void setStudentEducation(List<StudentEducation> studentEducation) {
		this.studentEducation = studentEducation;
	}

	public List<StudentFamily> getStudentFamily() {
		return studentFamily;
	}

	public void setStudentFamily(List<StudentFamily> studentFamily) {
		this.studentFamily = studentFamily;
	}

	public List<StudentTesting> getStudentTesting() {
		return studentTesting;
	}

	public void setStudentTesting(List<StudentTesting> studentTesting) {
		this.studentTesting = studentTesting;
	}

	public List<StudentWriting> getStudentWriting() {
		return studentWriting;
	}

	public void setStudentWriting(List<StudentWriting> studentWriting) {
		this.studentWriting = studentWriting;
	}

	public void setCreatedTimeStamp(Timestamp createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public List<StudentEmployement> getStudentEmployement() {
		return studentEmployement;
	}

	public void setStudentEmployement(List<StudentEmployement> studentEmployement) {
		this.studentEmployement = studentEmployement;
	}

	public List<School> getSchools() {
		return schools;
	}

	public void setSchools(List<School> schools) {
		this.schools = schools;
	}

	public List<StudentApplication> getStudentApplications() {
		return studentApplications;
	}

	public void setStudentApplications(List<StudentApplication> studentApplications) {
		this.studentApplications = studentApplications;
	}

	public List<SchoolQuestionAnswer> getStudentQuestionAnswers() {
		return studentQuestionAnswers;
	}

	public void setStudentQuestionAnswers(List<SchoolQuestionAnswer> studentQuestionAnswers) {
		this.studentQuestionAnswers = studentQuestionAnswers;
	}

	public List<StudentSchoolDocument> getStudentSchoolDocuments() {
		return studentSchoolDocuments;
	}

	public void setStudentSchoolDocuments(List<StudentSchoolDocument> studentSchoolDocuments) {
		this.studentSchoolDocuments = studentSchoolDocuments;
	}

	public List<StudentRecommender> getStudentRecommenders() {
		return studentRecommenders;
	}

	public void setStudentRecommenders(List<StudentRecommender> studentRecommenders) {
		this.studentRecommenders = studentRecommenders;
	}


}

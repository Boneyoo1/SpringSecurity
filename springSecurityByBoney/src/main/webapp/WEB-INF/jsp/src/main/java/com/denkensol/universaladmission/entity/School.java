package com.denkensol.universaladmission.entity;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SCHOOL")
@NamedNativeQueries({
		@NamedNativeQuery(name = "GET_ALL_SCHOOLS", query = "select SCHOOL_GUID as schoolGuid,SCHOOL_NAME as schoolName,state,city,SCHOOL_LOGO_URL as schoolLogoURL,website,TRANSCRIPTS_REQUIRES as transcriptsRequires,SOLV_CERT_REQUIRES as solvCERTRequires,REC_LETTERS_REQUIRES as recLettersRequires,MINIMUM_GRE_SCORE as minimumGREScore,MINIMUM_TOEFL_SCORE as minimumTOEFLScore,MINIMUM_GMAT_SCORE as minimumGMATcore,APPLICATION_CHECKLIST_LINK as applicationCheckListLink,TUITION_FEES_LINK as tuitionFeesLink,MAILING_FEE as mailingFee,GRAD_TUITION_FEE_PER_CREDIT as gradTuitionFeePerCredit,GRAD_CREDITS_REQUIRED as gradCreditsRequired,REQUIRES_PASSPORT as requiresPassport,MINIMUM_GPA as minimumGPA,EMAIL_DOMAIN_1 as emailDomainOne,EMAIL_DOMAIN_2 as emailDomainTwo,EMAIL_DOMAIN_3 as emailDomainThree,REQUIRES_EAD as requiresEAD,REQUIRES_I_20 as requiresI20,REQUIRES_F1_VISA as requiresF1Visa,REQUIRES_TOEFL_SCORE as requiresTOFELScore,SCHOOL_INFO as schoolInfo  from SCHOOL"), })

public class School implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SCHOOL_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long schoolGuid;

	@Column(name = "SCHOOL_ID")
	private String schoolId;

	@Column(name = "SCHOOL_NAME")
	private String schoolName;

	@Column(name = "SCHOOL_LOGO_URL")
	private String schoolLogoURL;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;

	@Column(name = "ADDRESS_LINE_1")
	private String addressLineOne;

	@Column(name = "ADDRESS_LINE_2")
	private String addressLineTwo;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ZIP_CODE")
	private String zipCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_GUID")
	private Country schoolCountry;

	@Column(name = "CONTACT_PERSON")
	private String contactPerson;

	@Column(name = "WEBSITE")
	private String website;

	@Column(name = "INTERNATIONAL_APP_FEES")
	private Double internationAppFees;

	@Column(name = "TRANSCRIPTS_REQUIRES")
	private String transcriptsRequires;

	@Column(name = "REC_LETTERS_REQUIRES")
	private String recLettersRequires;

	@Column(name = "SOP_REQUIRES")
	private String sopRequires;

	@Column(name = "SOLV_CERT_REQUIRES")
	private String solvCERTRequires;

	@Column(name = "RESUME_REQUIRES")
	private String resumeRequires;

	@Column(name = "MINIMUM_GRE_SCORE")
	private Double minimumGREScore;

	@Column(name = "MINIMUM_TOEFL_SCORE")
	private Double minimumTOEFLScore;

	@Column(name = "MINIMUM_GMAT_SCORE")
	private Double minimumGMATcore;

	@Column(name = "INTERNATIONAL_CREDENTIAL_EVAL_FEES")
	private Double internationCredentialEvalFees;

	@Column(name = "GRE_SCORE_SUBMISSION_SCHOOL_CODE")
	private Double greScoreSubmissionSchoolCode;

	@Column(name = "TOEFL_SCORE_SUBMISSION_SCHOOL_CODE")
	private Double toeflScoreSubmissionSchoolCode;

	@Column(name = "GMAT_SCORE_SUBMISSION_SCHOOL_CODE")
	private Double gmatScoreSubmissionSchoolCode;

	@Column(name = "APPLICATION_CHECKLIST_LINK")
	private String applicationCheckListLink;

	@Column(name = "TUITION_FEES_LINK")
	private String tuitionFeesLink;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "degreeSchool", cascade = CascadeType.ALL)
	private List<StudentEducation> schoolEducations = new ArrayList<StudentEducation>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "school", cascade = CascadeType.ALL)
	private List<SchoolDegree> schoolDegrees = new ArrayList<SchoolDegree>();

	@Column(name = "MAILING_FEE")
	private Double mailingFee;

	@Column(name = "GRAD_TUITION_FEE_PER_CREDIT")
	private Double gradTuitionFeePerCredit;

	@Column(name = "GRAD_CREDITS_REQUIRED")
	private Double gradCreditsRequired;

	@Column(name = "REQUIRES_PASSPORT")
	private String requiresPassport;

	@Column(name = "MINIMUM_GPA")
	private Double minimumGPA;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "school")
	private List<StudentApplication> studentApplications = new ArrayList<StudentApplication>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "school")
	private List<SchoolQuestionSection> schoolQuestions = new ArrayList<SchoolQuestionSection>();

	@Column(name = "SCHOOL_INFO")
	private String schoolInfo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "school", cascade = CascadeType.ALL)
	private List<SchoolDocument> schoolDocuments = new ArrayList<SchoolDocument>();

	@Column(name = "EMAIL_DOMAIN_1")
	private String emailDomainOne;

	@Column(name = "EMAIL_DOMAIN_2")
	private String emailDomainTwo;

	@Column(name = "EMAIL_DOMAIN_3")
	private String emailDomainThree;

	@Column(name = "REQUIRES_EAD")
	private String requiresEAD;

	@Column(name = "REQUIRES_I_20")
	private String requiresI20;

	@Column(name = "REQUIRES_F1_VISA")
	private String requiresF1Visa;

	@Column(name = "REQUIRES_TOEFL_SCORE")
	private String requiresTOFELScore;

	public Long getSchoolGuid() {
		return schoolGuid;
	}

	public void setSchoolGuid(Long schoolGuid) {
		this.schoolGuid = schoolGuid;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolLogoURL() {
		return schoolLogoURL;
	}

	public void setSchoolLogoURL(String schoolLogoURL) {
		this.schoolLogoURL = schoolLogoURL;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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

	public Country getSchoolCountry() {
		return schoolCountry;
	}

	public void setSchoolCountry(Country schoolCountry) {
		this.schoolCountry = schoolCountry;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public Double getInternationAppFees() {
		return internationAppFees;
	}

	public void setInternationAppFees(Double internationalAppFees) {
		this.internationAppFees = internationalAppFees;
	}

	public String getTranscriptsRequires() {
		return transcriptsRequires;
	}

	public void setTranscriptsRequires(String transcriptsRequires) {
		this.transcriptsRequires = transcriptsRequires;
	}

	public String getRecLettersRequires() {
		return recLettersRequires;
	}

	public void setRecLettersRequires(String recLettersRequires) {
		this.recLettersRequires = recLettersRequires;
	}

	public String getSopRequires() {
		return sopRequires;
	}

	public void setSopRequires(String sopRequires) {
		this.sopRequires = sopRequires;
	}

	public String getSolvCERTRequires() {
		return solvCERTRequires;
	}

	public void setSolvCERTRequires(String solvCERTRequires) {
		this.solvCERTRequires = solvCERTRequires;
	}

	public String getResumeRequires() {
		return resumeRequires;
	}

	public void setResumeRequires(String resumeRequires) {
		this.resumeRequires = resumeRequires;
	}

	public Double getMinimumGREScore() {
		return minimumGREScore;
	}

	public void setMinimumGREScore(Double minimumGREScore) {
		this.minimumGREScore = minimumGREScore;
	}

	public Double getMinimumTOEFLScore() {
		return minimumTOEFLScore;
	}

	public void setMinimumTOEFLScore(Double minimumTOEFLScore) {
		this.minimumTOEFLScore = minimumTOEFLScore;
	}

	public List<StudentEducation> getSchoolEducations() {
		return schoolEducations;
	}

	public void setSchoolEducations(List<StudentEducation> schoolEducations) {
		this.schoolEducations = schoolEducations;
	}

	public List<SchoolDegree> getSchoolDegrees() {
		return schoolDegrees;
	}

	public void setSchoolDegrees(List<SchoolDegree> schoolDegrees) {
		this.schoolDegrees = schoolDegrees;
	}

	public Double getInternationCredentialEvalFees() {
		return internationCredentialEvalFees;
	}

	public void setInternationCredentialEvalFees(Double internationCredentialEvalFees) {
		this.internationCredentialEvalFees = internationCredentialEvalFees;
	}

	public Double getGreScoreSubmissionSchoolCode() {
		return greScoreSubmissionSchoolCode;
	}

	public void setGreScoreSubmissionSchoolCode(Double greScoreSubmissionSchoolCode) {
		this.greScoreSubmissionSchoolCode = greScoreSubmissionSchoolCode;
	}

	public Double getToeflScoreSubmissionSchoolCode() {
		return toeflScoreSubmissionSchoolCode;
	}

	public void setToeflScoreSubmissionSchoolCode(Double toeflScoreSubmissionSchoolCode) {
		this.toeflScoreSubmissionSchoolCode = toeflScoreSubmissionSchoolCode;
	}

	public Double getGmatScoreSubmissionSchoolCode() {
		return gmatScoreSubmissionSchoolCode;
	}

	public void setGmatScoreSubmissionSchoolCode(Double gmatScoreSubmissionSchoolCode) {
		this.gmatScoreSubmissionSchoolCode = gmatScoreSubmissionSchoolCode;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public Double getMailingFee() {
		return mailingFee;
	}

	public void setMailingFee(Double mailingFee) {
		this.mailingFee = mailingFee;
	}

	public String getRequiresPassport() {
		return requiresPassport;
	}

	public void setRequiresPassport(String requiresPassport) {
		this.requiresPassport = requiresPassport;
	}

	public Double getMinimumGPA() {
		return minimumGPA;
	}

	public void setMinimumGPA(Double minimumGPA) {
		this.minimumGPA = minimumGPA;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Double getMinimumGMATcore() {
		return minimumGMATcore;
	}

	public void setMinimumGMATcore(Double minimumGMATcore) {
		this.minimumGMATcore = minimumGMATcore;
	}

	public List<StudentApplication> getStudentApplications() {
		return studentApplications;
	}

	public void setStudentApplications(List<StudentApplication> studentApplications) {
		this.studentApplications = studentApplications;
	}

	public List<SchoolQuestionSection> getSchoolQuestions() {
		return schoolQuestions;
	}

	public void setSchoolQuestions(List<SchoolQuestionSection> schoolQuestions) {
		this.schoolQuestions = schoolQuestions;
	}

	public String getSchoolInfo() {
		return schoolInfo;
	}

	public void setSchoolInfo(String schoolInfo) {
		this.schoolInfo = schoolInfo;
	}

	public List<SchoolDocument> getSchoolDocuments() {
		return schoolDocuments;
	}

	public void setSchoolDocuments(List<SchoolDocument> schoolDocuments) {
		this.schoolDocuments = schoolDocuments;
	}

	public String getApplicationCheckListLink() {
		return applicationCheckListLink;
	}

	public void setApplicationCheckListLink(String applicationCheckListLink) {
		this.applicationCheckListLink = applicationCheckListLink;
	}

	public String getTuitionFeesLink() {
		return tuitionFeesLink;
	}

	public void setTuitionFeesLink(String tuitionFeesLink) {
		this.tuitionFeesLink = tuitionFeesLink;
	}

	public Double getGradTuitionFeePerCredit() {
		return gradTuitionFeePerCredit;
	}

	public void setGradTuitionFeePerCredit(Double gradTuitionFeePerCredit) {
		this.gradTuitionFeePerCredit = gradTuitionFeePerCredit;
	}

	public Double getGradCreditsRequired() {
		return gradCreditsRequired;
	}

	public void setGradCreditsRequired(Double gradCreditsRequired) {
		this.gradCreditsRequired = gradCreditsRequired;
	}

	public String getEmailDomainOne() {
		return emailDomainOne;
	}

	public void setEmailDomainOne(String emailDomainOne) {
		this.emailDomainOne = emailDomainOne;
	}

	public String getEmailDomainTwo() {
		return emailDomainTwo;
	}

	public void setEmailDomainTwo(String emailDomainTwo) {
		this.emailDomainTwo = emailDomainTwo;
	}

	public String getEmailDomainThree() {
		return emailDomainThree;
	}

	public void setEmailDomainThree(String emailDomainThree) {
		this.emailDomainThree = emailDomainThree;
	}

	
	public String getRequiresEAD() {
		return requiresEAD;
	}

	public void setRequiresEAD(String requiresEAD) {
		this.requiresEAD = requiresEAD;
	}

	public String getRequiresI20() {
		return requiresI20;
	}

	public void setRequiresI20(String requiresI20) {
		this.requiresI20 = requiresI20;
	}

	public String getRequiresF1Visa() {
		return requiresF1Visa;
	}

	public void setRequiresF1Visa(String requiresF1Visa) {
		this.requiresF1Visa = requiresF1Visa;
	}

	public String getRequiresTOFELScore() {
		return requiresTOFELScore;
	}

	public void setRequiresTOFELScore(String requiresTOFELScore) {
		this.requiresTOFELScore = requiresTOFELScore;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((schoolId == null) ? 0 : schoolId.hashCode());
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
		School other = (School) obj;
		if (schoolId == null) {
			if (other.schoolId != null)
				return false;
		} else if (!schoolId.equals(other.schoolId))
			return false;
		return true;
	}

}

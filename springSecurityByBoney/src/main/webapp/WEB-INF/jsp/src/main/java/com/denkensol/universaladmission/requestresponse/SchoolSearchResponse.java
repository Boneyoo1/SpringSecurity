package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class SchoolSearchResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7513587505396174890L;

	private BigInteger schoolGuid;
	private String schoolId = "";
	private String schoolName = "";
	private String schoolLogoURL = "";
	private String phoneNumber = "";
	private String emailAddress = "";
	private String addressLineOne = "";
	private String addressLineTwo = "";
	private String city = "";
	private String state = "";
	private String zipCode = "";
	private Long schoolCountryGuid;
	private String contactPerson = "";
	private String website = "";
	private Double internationAppFees;
	private String transcriptsRequires = "";
	private String recLettersRequires = "";
	private String sopRequires = "";
	private String solvCERTRequires = "";
	private String resumeRequires = "";
	private Double minimumGREScore;
	private Double minimumTOEFLScore;
	private Double minimumGMATcore;
	private Double internationCredentialEvalFees;
	private Double greScoreSubmissionSchoolCode;
	private Double toeflScoreSubmissionSchoolCode;
	private Double gmatScoreSubmissionSchoolCode;
	private Double mailingFee;
	private String requiresPassport = "";
	private Double minimumGPA;
	private List<SchoolTermResponse> schoolTerms;
	private String applicationStatus;
	private List<SchoolDegreeResponse> schoolDegrees;
	private StudentApplicationResponse studentApplicationResponse;
	private String schoolInfo;
	private String schoolAppDocContent;
	private Double gradTuitionFeePerCredit;
	private Double gradCreditsRequired;
	private String applicationCheckListLink;
	private String tuitionFeesLink;
	private String emailDomainOne;
	private String emailDomainTwo;
	private String emailDomainThree;
	private String requiresEAD;
	private String requiresI20;
	private String requiresF1Visa;
	private String requiresTOFELScore;
	private List<SchoolDocumentRequestResponse> schoolDocuments;

	public String getSchoolAppDocContent() {
		return schoolAppDocContent;
	}

	public void setSchoolAppDocContent(String schoolAppDocContent) {
		this.schoolAppDocContent = schoolAppDocContent;
	}

	public BigInteger getSchoolGuid() {
		return schoolGuid;
	}

	public void setSchoolGuid(BigInteger schoolGuid) {
		this.schoolGuid = schoolGuid;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
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

	public Long getSchoolCountryGuid() {
		return schoolCountryGuid;
	}

	public void setSchoolCountryGuid(Long schoolCountryGuid) {
		this.schoolCountryGuid = schoolCountryGuid;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Double getInternationAppFees() {
		return internationAppFees;
	}

	public void setInternationAppFees(Double internationAppFees) {
		this.internationAppFees = internationAppFees;
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

	public Double getMinimumGMATcore() {
		return minimumGMATcore;
	}

	public void setMinimumGMATcore(Double minimumGMATcore) {
		this.minimumGMATcore = minimumGMATcore;
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

	public List<SchoolTermResponse> getSchoolTerms() {
		return schoolTerms;
	}

	public void setSchoolTerms(List<SchoolTermResponse> schoolTerms) {
		this.schoolTerms = schoolTerms;
	}

	public String getApplicationStatus() {
		if (applicationStatus == null || applicationStatus.isEmpty()) {
			applicationStatus = "Pending";
		}
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public List<SchoolDegreeResponse> getSchoolDegrees() {
		return schoolDegrees;
	}

	public void setSchoolDegrees(List<SchoolDegreeResponse> schoolDegrees) {
		this.schoolDegrees = schoolDegrees;
	}

	public StudentApplicationResponse getStudentApplicationResponse() {
		return studentApplicationResponse;
	}

	public void setStudentApplicationResponse(StudentApplicationResponse studentApplicationResponse) {
		this.studentApplicationResponse = studentApplicationResponse;
	}

	public String getSchoolInfo() {
		return schoolInfo;
	}

	public void setSchoolInfo(String schoolInfo) {
		this.schoolInfo = schoolInfo;
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

	public List<SchoolDocumentRequestResponse> getSchoolDocuments() {
		return schoolDocuments;
	}

	public void setSchoolDocuments(List<SchoolDocumentRequestResponse> schoolDocuments) {
		this.schoolDocuments = schoolDocuments;
	}

}

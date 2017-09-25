package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.math.BigInteger;

public class StudentProfileRequestResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5128528727158216699L;

	private BigInteger citizenShipCountryGuid;
	private String cityOfBirth;
	private BigInteger countryOfBirthGuid;
	private String dateOfBirth;
	private String emailAddress;
	private String emergencyContactName;
	private String emergencyContactNo;
	private String emergencyContactRelation;
	private String ethnicity;
	private String firstName;
	private String gender;
	private String homeContactNo;
	private String lastName;
	private String mailingAddressCity;
	private BigInteger mailingAddressCountryGuid;
	private String mailingAddressLine1;
	private String mailingAddressLine2;
	private String mailingAddressLine3;
	private String mailingAddressSameAsPermanent;
	private String mailingAddressState;
	private String mailingAddressZipCode;
	private String middleName;
	private String permanentAddressCity;
	private BigInteger permanentAddressCountryGuid;
	private String permanentAddressLine1;
	private String permanentAddressLine2;
	private String permanentAddressLine3;
	private String permanentAddressState;
	private String permanentAddressZipCode;
	private BigInteger firstLanguageGuid;
	private BigInteger religiousGuid;
	private String usCitizen;
	private BigInteger studentProfileGuid;
	private BigInteger visaTypeRequiredGuid;
	private String workContactNo;
	private String title;
	private String countryCodeHomePhone;
	private String countryCodeWorkPhone;
	private String countryCodeEmergencyPhone;

	private String suffix;
	private String haveOtherName;
	private String otherSuffix;
	private String otherFirstName;
	private String otherMiddleName;
	private String otherLastName;
	private String maritalStatus;

	private String emergencyAddressCity;
	private String emergencyAddressLine1;
	private String emergencyAddressLine2;
	private String emergencyAddressLine3;
	private String emergencyAddressSameAsPermanent;
	private String emergencyAddressState;
	private String emergencyAddressZipCode;

	private String ssn;
	private String itin;
	private String permanentUSResident;
	private BigInteger secondLanguageGuid;
	private BigInteger thirdLanguageGuid;
	private BigInteger emergencyAddressCountryGuid;
	private Boolean profileCompleted = false;
	private BigInteger accountGuid;
	private String pdfURL;

	public BigInteger getCitizenShipCountryGuid() {
		return citizenShipCountryGuid;
	}

	public void setCitizenShipCountryGuid(BigInteger citizenShipCountryGuid) {
		this.citizenShipCountryGuid = citizenShipCountryGuid;
	}

	public String getCityOfBirth() {
		return cityOfBirth;
	}

	public void setCityOfBirth(String cityOfBirth) {
		this.cityOfBirth = cityOfBirth;
	}

	public BigInteger getCountryOfBirthGuid() {
		return countryOfBirthGuid;
	}

	public void setCountryOfBirthGuid(BigInteger countryOfBirthGuid) {
		this.countryOfBirthGuid = countryOfBirthGuid;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmergencyContactName() {
		return emergencyContactName;
	}

	public void setEmergencyContactName(String emergencyContactName) {
		this.emergencyContactName = emergencyContactName;
	}

	public String getEmergencyContactNo() {
		return emergencyContactNo;
	}

	public void setEmergencyContactNo(String emergencyContactNo) {
		this.emergencyContactNo = emergencyContactNo;
	}

	public String getEmergencyContactRelation() {
		return emergencyContactRelation;
	}

	public void setEmergencyContactRelation(String emergencyContactRelation) {
		this.emergencyContactRelation = emergencyContactRelation;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHomeContactNo() {
		return homeContactNo;
	}

	public void setHomeContactNo(String homeContactNo) {
		this.homeContactNo = homeContactNo;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMailingAddressCity() {
		return mailingAddressCity;
	}

	public void setMailingAddressCity(String mailingAddressCity) {
		this.mailingAddressCity = mailingAddressCity;
	}

	public BigInteger getMailingAddressCountryGuid() {
		return mailingAddressCountryGuid;
	}

	public void setMailingAddressCountryGuid(BigInteger mailingAddressCountryGuid) {
		this.mailingAddressCountryGuid = mailingAddressCountryGuid;
	}

	public String getMailingAddressLine1() {
		return mailingAddressLine1;
	}

	public void setMailingAddressLine1(String mailingAddressLine1) {
		this.mailingAddressLine1 = mailingAddressLine1;
	}

	public String getMailingAddressLine2() {
		return mailingAddressLine2;
	}

	public void setMailingAddressLine2(String mailingAddressLine2) {
		this.mailingAddressLine2 = mailingAddressLine2;
	}

	public String getMailingAddressLine3() {
		return mailingAddressLine3;
	}

	public void setMailingAddressLine3(String mailingAddressLine3) {
		this.mailingAddressLine3 = mailingAddressLine3;
	}

	public String getMailingAddressSameAsPermanent() {
		return mailingAddressSameAsPermanent;
	}

	public void setMailingAddressSameAsPermanent(String mailingAddressSameAsPermanent) {
		this.mailingAddressSameAsPermanent = mailingAddressSameAsPermanent;
	}

	public String getMailingAddressState() {
		return mailingAddressState;
	}

	public void setMailingAddressState(String mailingAddressState) {
		this.mailingAddressState = mailingAddressState;
	}

	public String getMailingAddressZipCode() {
		return mailingAddressZipCode;
	}

	public void setMailingAddressZipCode(String mailingAddressZipCode) {
		this.mailingAddressZipCode = mailingAddressZipCode;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getPermanentAddressCity() {
		return permanentAddressCity;
	}

	public void setPermanentAddressCity(String permanentAddressCity) {
		this.permanentAddressCity = permanentAddressCity;
	}

	public BigInteger getPermanentAddressCountryGuid() {
		return permanentAddressCountryGuid;
	}

	public void setPermanentAddressCountryGuid(BigInteger permanentAddressCountryGuid) {
		this.permanentAddressCountryGuid = permanentAddressCountryGuid;
	}

	public String getPermanentAddressLine1() {
		return permanentAddressLine1;
	}

	public void setPermanentAddressLine1(String permanentAddressLine1) {
		this.permanentAddressLine1 = permanentAddressLine1;
	}

	public String getPermanentAddressLine2() {
		return permanentAddressLine2;
	}

	public void setPermanentAddressLine2(String permanentAddressLine2) {
		this.permanentAddressLine2 = permanentAddressLine2;
	}

	public String getPermanentAddressLine3() {
		return permanentAddressLine3;
	}

	public void setPermanentAddressLine3(String permanentAddressLine3) {
		this.permanentAddressLine3 = permanentAddressLine3;
	}

	public String getPermanentAddressState() {
		return permanentAddressState;
	}

	public void setPermanentAddressState(String permanentAddressState) {
		this.permanentAddressState = permanentAddressState;
	}

	public String getPermanentAddressZipCode() {
		return permanentAddressZipCode;
	}

	public void setPermanentAddressZipCode(String permanentAddressZipCode) {
		this.permanentAddressZipCode = permanentAddressZipCode;
	}

	public BigInteger getFirstLanguageGuid() {
		return firstLanguageGuid;
	}

	public void setFirstLanguageGuid(BigInteger firstLanguageGuid) {
		this.firstLanguageGuid = firstLanguageGuid;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getHaveOtherName() {
		return haveOtherName;
	}

	public void setHaveOtherName(String haveOtherName) {
		this.haveOtherName = haveOtherName;
	}

	public String getOtherSuffix() {
		return otherSuffix;
	}

	public void setOtherSuffix(String otherSuffix) {
		this.otherSuffix = otherSuffix;
	}

	public String getOtherFirstName() {
		return otherFirstName;
	}

	public void setOtherFirstName(String otherFirstName) {
		this.otherFirstName = otherFirstName;
	}

	public String getOtherMiddleName() {
		return otherMiddleName;
	}

	public void setOtherMiddleName(String otherMiddleName) {
		this.otherMiddleName = otherMiddleName;
	}

	public String getOtherLastName() {
		return otherLastName;
	}

	public void setOtherLastName(String otherLastName) {
		this.otherLastName = otherLastName;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getEmergencyAddressCity() {
		return emergencyAddressCity;
	}

	public void setEmergencyAddressCity(String emergencyAddressCity) {
		this.emergencyAddressCity = emergencyAddressCity;
	}

	public String getEmergencyAddressLine1() {
		return emergencyAddressLine1;
	}

	public void setEmergencyAddressLine1(String emergencyAddressLine1) {
		this.emergencyAddressLine1 = emergencyAddressLine1;
	}

	public String getEmergencyAddressLine2() {
		return emergencyAddressLine2;
	}

	public void setEmergencyAddressLine2(String emergencyAddressLine2) {
		this.emergencyAddressLine2 = emergencyAddressLine2;
	}

	public String getEmergencyAddressLine3() {
		return emergencyAddressLine3;
	}

	public void setEmergencyAddressLine3(String emergencyAddressLine3) {
		this.emergencyAddressLine3 = emergencyAddressLine3;
	}

	public String getEmergencyAddressSameAsPermanent() {
		return emergencyAddressSameAsPermanent;
	}

	public void setEmergencyAddressSameAsPermanent(String emergencyAddressSameAsPermanent) {
		this.emergencyAddressSameAsPermanent = emergencyAddressSameAsPermanent;
	}

	public String getEmergencyAddressState() {
		return emergencyAddressState;
	}

	public void setEmergencyAddressState(String emergencyAddressState) {
		this.emergencyAddressState = emergencyAddressState;
	}

	public String getEmergencyAddressZipCode() {
		return emergencyAddressZipCode;
	}

	public void setEmergencyAddressZipCode(String emergencyAddressZipCode) {
		this.emergencyAddressZipCode = emergencyAddressZipCode;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getItin() {
		return itin;
	}

	public void setItin(String itin) {
		this.itin = itin;
	}

	public String getPermanentUSResident() {
		return permanentUSResident;
	}

	public void setPermanentUSResident(String permanentUSResident) {
		this.permanentUSResident = permanentUSResident;
	}

	public BigInteger getSecondLanguageGuid() {
		return secondLanguageGuid;
	}

	public void setSecondLanguageGuid(BigInteger secondLanguageGuid) {
		this.secondLanguageGuid = secondLanguageGuid;
	}

	public BigInteger getThirdLanguageGuid() {
		return thirdLanguageGuid;
	}

	public void setThirdLanguageGuid(BigInteger thirdLanguageGuid) {
		this.thirdLanguageGuid = thirdLanguageGuid;
	}

	public BigInteger getEmergencyAddressCountryGuid() {
		return emergencyAddressCountryGuid;
	}

	public void setEmergencyAddressCountryGuid(BigInteger emergencyAddressCountryGuid) {
		this.emergencyAddressCountryGuid = emergencyAddressCountryGuid;
	}

	public BigInteger getReligiousGuid() {
		return religiousGuid;
	}

	public void setReligiousGuid(BigInteger religiousGuid) {
		this.religiousGuid = religiousGuid;
	}

	public String getUsCitizen() {
		return usCitizen;
	}

	public void setUsCitizen(String usCitizen) {
		this.usCitizen = usCitizen;
	}

	public BigInteger getStudentProfileGuid() {
		return studentProfileGuid;
	}

	public void setStudentProfileGuid(BigInteger studentProfileGuid) {
		this.studentProfileGuid = studentProfileGuid;
	}

	public BigInteger getVisaTypeRequiredGuid() {
		return visaTypeRequiredGuid;
	}

	public void setVisaTypeRequiredGuid(BigInteger visaTypeRequiredGuid) {
		this.visaTypeRequiredGuid = visaTypeRequiredGuid;
	}

	public String getWorkContactNo() {
		return workContactNo;
	}

	public void setWorkContactNo(String workContactNo) {
		this.workContactNo = workContactNo;
	}

	public Boolean getProfileCompleted() {
		return profileCompleted;
	}

	public void setProfileCompleted(Boolean profileCompleted) {
		this.profileCompleted = profileCompleted;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCountryCodeHomePhone() {
		return countryCodeHomePhone;
	}

	public void setCountryCodeHomePhone(String countryCodeHomePhone) {
		this.countryCodeHomePhone = countryCodeHomePhone;
	}

	public String getCountryCodeWorkPhone() {
		return countryCodeWorkPhone;
	}

	public void setCountryCodeWorkPhone(String countryCodeWorkPhone) {
		this.countryCodeWorkPhone = countryCodeWorkPhone;
	}

	public String getCountryCodeEmergencyPhone() {
		return countryCodeEmergencyPhone;
	}

	public void setCountryCodeEmergencyPhone(String countryCodeEmergencyPhone) {
		this.countryCodeEmergencyPhone = countryCodeEmergencyPhone;
	}

	public BigInteger getAccountGuid() {
		return accountGuid;
	}

	public void setAccountGuid(BigInteger accountGuid) {
		this.accountGuid = accountGuid;
	}

	public String getPdfURL() {
		return pdfURL;
	}

	public void setPdfURL(String pdfURL) {
		this.pdfURL = pdfURL;
	}


	

}

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
@Table(name = "STUDENT_PROFILE")
@NamedNativeQueries({
		@NamedNativeQuery(name = "GET_STUDENT_PROFILE", query = "select STUDENT_PROFILE_GUID as studentProfileGuid,  CITY_OF_BIRTH as cityOfBirth, DATE_OF_BIRTH as dateOfBirth, EMAIL_ADDRESS as emailAddress, EMERGENCY_CONTACT_NAME as emergencyContactName,COUNTRY_CODE_EMERGENCY_PHONE as countryCodeEmergencyPhone, EMERGENCY_CONTACT_NO as emergencyContactNo, EMERGENCY_CONTACT_RELATION as emergencyContactRelation, ETHNICITY as ethnicity, FIRST_NAME as firstName, GENDER as gender,COUNTRY_CODE_HOME_PHONE as countryCodeHomePhone, HOME_CONTACT_NO as homeContactNo, LAST_NAME as lastName, MAILING_ADDRESS_CITY as mailingAddressCity, MAILING_ADDRESS_LINE_1 as mailingAddressLine1, MAILING_ADDRESS_LINE_2 as mailingAddressLine2, MAILING_ADDRESS_LINE_3 as mailingAddressLine3, MAILING_ADDRESS_SAME_AS_PERMANENT as mailingAddressSameAsPermanent, MAILING_ADDRESS_STATE as mailingAddressState, MAILING_ADDRESS_ZIP_CODE as mailingAddressZipCode,TITLE as title, MIDDLE_NAME as middleName, PERMANENT_ADDRESS_CITY as permanentAddressCity, PERMANENT_ADDRESS_LINE_1 as permanentAddressLine1, PERMANENT_ADDRESS_LINE_2 as permanentAddressLine2, PERMANENT_ADDRESS_LINE_3 as permanentAddressLine3, PERMANENT_ADDRESS_STATE as permanentAddressState, PERMANENT_ADDRESS_ZIP_CODE as permanentAddressZipCode, IS_US_CITIZEN as usCitizen,COUNTRY_CODE_WORK_PHONE as countryCodeWorkPhone, WORK_CONTACT_NO as workContactNo, BIRTH_COUNTRY as countryOfBirthGuid, CITIZENSHIP_COUNTRY as citizenShipCountryGuid, MALING_ADDRESS_COUNTRY as mailingAddressCountryGuid, PERMANENT_ADDRESS_COUNTRY as permanentAddressCountryGuid, FIRST_LANGUAGE_GUID as firstLanguageGuid, RELIGIOUS_GUID as religiousGuid, VISA_TYPE_GUID as visaTypeRequiredGuid, SECOND_LANGUAGE_GUID as secondLanguageGuid, THIRD_LANGUAGE_GUID as thirdLanguageGuid,EMERGENCY_ADDRESS_COUNTRY as emergencyAddressCountryGuid,SUFFIX as suffix,HAVE_OTHER_NAME as haveOtherName,OTHER_SUFFIX as otherSuffix,OTHER_FIRST_NAME as otherFirstName,OTHER_MIDDLE_NAME as otherMiddleName,OTHER_LAST_NAME as otherLastName,MARITAL_STATUS as maritalStatus,EMERGENCY_ADDRESS_CITY as emergencyAddressCity,EMERGENCY_ADDRESS_LINE_1 as emergencyAddressLine1,EMERGENCY_ADDRESS_LINE_2 as emergencyAddressLine2,EMERGENCY_ADDRESS_LINE_3 as emergencyAddressLine3,EMERGENCY_ADDRESS_SAME_AS_PERMANENT as emergencyAddressSameAsPermanent,EMERGENCY_ADDRESS_STATE as emergencyAddressState,EMERGENCY_ADDRESS_ZIP_CODE as emergencyAddressZipCode,SSN as ssn,ITIN as itin,IS_US_PERMANENT_RESIDENT as permanentUSResident  from STUDENT_PROFILE where account_guid=:accountGuid "),
		@NamedNativeQuery(name = "GET_SCHOOL_PROSPECTS", query = "select first_name as firstName,LAST_NAME as lastName,sp.EMAIL_ADDRESS as emailAddress,WORK_CONTACT_NO as phoneNumber from STUDENT_PROFILE sp where sp.ACCOUNT_GUID not in (select sa.account_guid from STUDENT_APPLICATION sa inner join ACCOUNT a on a.ACCOUNT_GUID=sa.ACCOUNT_GUID inner join SCHOOL s on sa.SCHOOL_GUID=s.SCHOOL_GUID where sa.APPLICATION_STATUS='Completed' and  s.SCHOOL_GUID=:schoolGuid)"),
		@NamedNativeQuery(name = "GET_SCHOOL_APPLICANTS", query = "select first_name as firstName,LAST_NAME as lastName,DATE_OF_BIRTH as dateOfBirth,sd.DEGREE_OFFERED as degreeApplied,st.TERM_NAME as term,st.TERM_YEAR as year,sa.APPLICATION_STATUS as applicationStatus,sa.APPLICATION_SUBMISSION_TIMESTAMP as applicationSubmittedDate,sa.SCHOOL_APP_PACKET_PDF as pdfURL from STUDENT_PROFILE sp inner join ACCOUNT a on a.ACCOUNT_GUID=sp.ACCOUNT_GUID inner join STUDENT_APPLICATION sa on a.ACCOUNT_GUID=sa.ACCOUNT_GUID inner join SCHOOL s on sa.SCHOOL_GUID=s.SCHOOL_GUID left join SCHOOL_DEGREE sd on sa.SCHOOL_DEGREE_GUID=sd.SCHOOL_DEGREE_GUID left join SCHOOL_TERM st on sa.SCHOOL_TERM_GUID=st.SCHOOL_TERM_GUID where sa.APPLICATION_STATUS='Completed' and  s.SCHOOL_GUID=:schoolGuid"),
		@NamedNativeQuery(name = "GET_SCHOOL_APPLICANTS_PROFILE_DATA", query = "select sa.SCHOOL_APP_PACKET_PDF as pdfURL, a.account_guid as accountGuid,CITY_OF_BIRTH as cityOfBirth, DATE_OF_BIRTH as dateOfBirth, sp.EMAIL_ADDRESS as emailAddress, EMERGENCY_CONTACT_NAME as emergencyContactName,COUNTRY_CODE_EMERGENCY_PHONE as countryCodeEmergencyPhone, EMERGENCY_CONTACT_NO as emergencyContactNo, EMERGENCY_CONTACT_RELATION as emergencyContactRelation, ETHNICITY as ethnicity, FIRST_NAME as firstName, GENDER as gender,COUNTRY_CODE_HOME_PHONE as countryCodeHomePhone, HOME_CONTACT_NO as homeContactNo, LAST_NAME as lastName, MAILING_ADDRESS_CITY as mailingAddressCity, MAILING_ADDRESS_LINE_1 as mailingAddressLine1, MAILING_ADDRESS_LINE_2 as mailingAddressLine2, MAILING_ADDRESS_LINE_3 as mailingAddressLine3, MAILING_ADDRESS_SAME_AS_PERMANENT as mailingAddressSameAsPermanent, MAILING_ADDRESS_STATE as mailingAddressState, MAILING_ADDRESS_ZIP_CODE as mailingAddressZipCode,TITLE as title, MIDDLE_NAME as middleName, PERMANENT_ADDRESS_CITY as permanentAddressCity, PERMANENT_ADDRESS_LINE_1 as permanentAddressLine1, PERMANENT_ADDRESS_LINE_2 as permanentAddressLine2, PERMANENT_ADDRESS_LINE_3 as permanentAddressLine3, PERMANENT_ADDRESS_STATE as permanentAddressState, PERMANENT_ADDRESS_ZIP_CODE as permanentAddressZipCode, IS_US_CITIZEN as usCitizen,COUNTRY_CODE_WORK_PHONE as countryCodeWorkPhone, WORK_CONTACT_NO as workContactNo, BIRTH_COUNTRY as countryOfBirthGuid, CITIZENSHIP_COUNTRY as citizenShipCountryGuid, MALING_ADDRESS_COUNTRY as mailingAddressCountryGuid, PERMANENT_ADDRESS_COUNTRY as permanentAddressCountryGuid, FIRST_LANGUAGE_GUID as firstLanguageGuid, RELIGIOUS_GUID as religiousGuid, VISA_TYPE_GUID as visaTypeRequiredGuid, SECOND_LANGUAGE_GUID as secondLanguageGuid, THIRD_LANGUAGE_GUID as thirdLanguageGuid,EMERGENCY_ADDRESS_COUNTRY as emergencyAddressCountryGuid,SUFFIX as suffix,HAVE_OTHER_NAME as haveOtherName,OTHER_SUFFIX as otherSuffix,OTHER_FIRST_NAME as otherFirstName,OTHER_MIDDLE_NAME as otherMiddleName,OTHER_LAST_NAME as otherLastName,MARITAL_STATUS as maritalStatus,EMERGENCY_ADDRESS_CITY as emergencyAddressCity,EMERGENCY_ADDRESS_LINE_1 as emergencyAddressLine1,EMERGENCY_ADDRESS_LINE_2 as emergencyAddressLine2,EMERGENCY_ADDRESS_LINE_3 as emergencyAddressLine3,EMERGENCY_ADDRESS_SAME_AS_PERMANENT as emergencyAddressSameAsPermanent,EMERGENCY_ADDRESS_STATE as emergencyAddressState,EMERGENCY_ADDRESS_ZIP_CODE as emergencyAddressZipCode,SSN as ssn,ITIN as itin,IS_US_PERMANENT_RESIDENT as permanentUSResident  from STUDENT_PROFILE sp inner join ACCOUNT a on a.ACCOUNT_GUID=sp.ACCOUNT_GUID inner join STUDENT_APPLICATION sa on a.ACCOUNT_GUID=sa.ACCOUNT_GUID inner join SCHOOL s on sa.SCHOOL_GUID=s.SCHOOL_GUID left join SCHOOL_DEGREE sd on sa.SCHOOL_DEGREE_GUID=sd.SCHOOL_DEGREE_GUID left join SCHOOL_TERM st on sa.SCHOOL_TERM_GUID=st.SCHOOL_TERM_GUID where sa.APPLICATION_STATUS='Completed' and  s.SCHOOL_GUID=:schoolGuid") })

public class StudentProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 422292507258205738L;

	@Id
	@Column(name = "STUDENT_PROFILE_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentProfileGuid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_GUID")
	private Account account;

	@Column(name = "CITY_OF_BIRTH")
	private String cityOfBirth;
	
	@Column(name = "DATE_OF_BIRTH")
	private String dateOfBirth;
	
	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;
	
	@Column(name = "EMERGENCY_CONTACT_NAME")
	private String emergencyContactName;
	
	@Column(name = "COUNTRY_CODE_EMERGENCY_PHONE")
	private String countryCodeEmergencyPhone;
	
	@Column(name = "EMERGENCY_CONTACT_NO")
	private String emergencyContactNo;
	
	@Column(name = "EMERGENCY_CONTACT_RELATION")
	private String emergencyContactRelation;
	
	@Column(name = "ETHNICITY")
	private String ethnicity;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "GENDER")
	private String gender;
	
	@Column(name = "COUNTRY_CODE_HOME_PHONE")
	private String countryCodeHomePhone;
	
	@Column(name = "HOME_CONTACT_NO")
	private String homeContactNo;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "MAILING_ADDRESS_CITY")
	private String mailingAddressCity;
	
	@Column(name = "MAILING_ADDRESS_LINE_1")
	private String mailingAddressLine1;
	
	@Column(name = "MAILING_ADDRESS_LINE_2")
	private String mailingAddressLine2;
	
	@Column(name = "MAILING_ADDRESS_LINE_3")
	private String mailingAddressLine3;
	
	@Column(name = "MAILING_ADDRESS_SAME_AS_PERMANENT")
	private String mailingAddressSameAsPermanent;
	
	@Column(name = "MAILING_ADDRESS_STATE")
	private String mailingAddressState;
	
	@Column(name = "MAILING_ADDRESS_ZIP_CODE")
	private String mailingAddressZipCode;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "MIDDLE_NAME")
	private String middleName;
	
	@Column(name = "PERMANENT_ADDRESS_CITY")
	private String permanentAddressCity;
	
	@Column(name = "PERMANENT_ADDRESS_LINE_1")
	private String permanentAddressLine1;
	
	@Column(name = "PERMANENT_ADDRESS_LINE_2")
	private String permanentAddressLine2;
	
	@Column(name = "PERMANENT_ADDRESS_LINE_3")
	private String permanentAddressLine3;
	
	@Column(name = "PERMANENT_ADDRESS_STATE")
	private String permanentAddressState;
	
	@Column(name = "PERMANENT_ADDRESS_ZIP_CODE")
	private String permanentAddressZipCode;
	
	@Column(name = "IS_US_CITIZEN")
	private String usCitizen;
	
	@Column(name = "COUNTRY_CODE_WORK_PHONE")
	private String countryCodeWorkPhone;
	
	@Column(name = "WORK_CONTACT_NO")
	private String workContactNo;
	
	@Column(name = "SUFFIX")
	private String suffix;
	
	@Column(name = "HAVE_OTHER_NAME")
	private String haveOtherName;
	
	@Column(name = "OTHER_SUFFIX")
	private String otherSuffix;
	
	@Column(name = "OTHER_FIRST_NAME")
	private String otherFirstName;
	
	@Column(name = "OTHER_MIDDLE_NAME")
	private String otherMiddleName;
	
	@Column(name = "OTHER_LAST_NAME")
	private String otherLastName;
	
	@Column(name = "MARITAL_STATUS")
	private String maritalStatus;
	
	@Column(name = "EMERGENCY_ADDRESS_CITY")
	private String emergencyAddressCity;
	
	@Column(name = "EMERGENCY_ADDRESS_LINE_1")
	private String emergencyAddressLine1;
	
	@Column(name = "EMERGENCY_ADDRESS_LINE_2")
	private String emergencyAddressLine2;
	
	@Column(name = "EMERGENCY_ADDRESS_LINE_3")
	private String emergencyAddressLine3;
	
	@Column(name = "EMERGENCY_ADDRESS_SAME_AS_PERMANENT")
	private String emergencyAddressSameAsPermanent;
	
	@Column(name = "EMERGENCY_ADDRESS_STATE")
	private String emergencyAddressState;
	
	@Column(name = "EMERGENCY_ADDRESS_ZIP_CODE")
	private String emergencyAddressZipCode;

	@Column(name = "SSN")
	private String ssn;

	@Column(name = "ITIN")
	private String itin;

	@Column(name = "IS_US_PERMANENT_RESIDENT")
	private String permanentUSResident;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELIGIOUS_GUID")
	private Religious religious;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BIRTH_COUNTRY")
	private Country birthCountry;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CITIZENSHIP_COUNTRY")
	private Country citizenshipCountry;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VISA_TYPE_GUID")
	private VisaType visaTypeRequired;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FIRST_LANGUAGE_GUID")
	private Language firstLanguage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SECOND_LANGUAGE_GUID")
	private Language secondLanguage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "THIRD_LANGUAGE_GUID")
	private Language thirdLanguage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERMANENT_ADDRESS_COUNTRY")
	private Country permanentAddressCountry;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MALING_ADDRESS_COUNTRY")
	private Country mailingnAddressCountry;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMERGENCY_ADDRESS_COUNTRY")
	private Country emergencyAddressCountry;

	public Long getStudentProfileGuid() {
		return studentProfileGuid;
	}

	public void setStudentProfileGuid(Long studentProfileGuid) {
		this.studentProfileGuid = studentProfileGuid;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getCityOfBirth() {
		return cityOfBirth;
	}

	public void setCityOfBirth(String cityOfBirth) {
		this.cityOfBirth = cityOfBirth;
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

	public String getCountryCodeEmergencyPhone() {
		return countryCodeEmergencyPhone;
	}

	public void setCountryCodeEmergencyPhone(String countryCodeEmergencyPhone) {
		this.countryCodeEmergencyPhone = countryCodeEmergencyPhone;
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

	public String getCountryCodeHomePhone() {
		return countryCodeHomePhone;
	}

	public void setCountryCodeHomePhone(String countryCodeHomePhone) {
		this.countryCodeHomePhone = countryCodeHomePhone;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getUsCitizen() {
		return usCitizen;
	}

	public void setUsCitizen(String usCitizen) {
		this.usCitizen = usCitizen;
	}

	public String getCountryCodeWorkPhone() {
		return countryCodeWorkPhone;
	}

	public void setCountryCodeWorkPhone(String countryCodeWorkPhone) {
		this.countryCodeWorkPhone = countryCodeWorkPhone;
	}

	public String getWorkContactNo() {
		return workContactNo;
	}

	public void setWorkContactNo(String workContactNo) {
		this.workContactNo = workContactNo;
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

	public Religious getReligious() {
		return religious;
	}

	public void setReligious(Religious religious) {
		this.religious = religious;
	}

	public Country getBirthCountry() {
		return birthCountry;
	}

	public void setBirthCountry(Country birthCountry) {
		this.birthCountry = birthCountry;
	}

	public Country getCitizenshipCountry() {
		return citizenshipCountry;
	}

	public void setCitizenshipCountry(Country citizenshipCountry) {
		this.citizenshipCountry = citizenshipCountry;
	}

	public VisaType getVisaTypeRequired() {
		return visaTypeRequired;
	}

	public void setVisaTypeRequired(VisaType visaTypeRequired) {
		this.visaTypeRequired = visaTypeRequired;
	}

	public Language getFirstLanguage() {
		return firstLanguage;
	}

	public void setFirstLanguage(Language firstLanguage) {
		this.firstLanguage = firstLanguage;
	}

	public Language getSecondLanguage() {
		return secondLanguage;
	}

	public void setSecondLanguage(Language secondLanguage) {
		this.secondLanguage = secondLanguage;
	}

	public Language getThirdLanguage() {
		return thirdLanguage;
	}

	public void setThirdLanguage(Language thirdLanguage) {
		this.thirdLanguage = thirdLanguage;
	}

	public Country getPermanentAddressCountry() {
		return permanentAddressCountry;
	}

	public void setPermanentAddressCountry(Country permanentAddressCountry) {
		this.permanentAddressCountry = permanentAddressCountry;
	}

	public Country getMailingnAddressCountry() {
		return mailingnAddressCountry;
	}

	public void setMailingnAddressCountry(Country mailingnAddressCountry) {
		this.mailingnAddressCountry = mailingnAddressCountry;
	}

	public Country getEmergencyAddressCountry() {
		return emergencyAddressCountry;
	}

	public void setEmergencyAddressCountry(Country emergencyAddressCountry) {
		this.emergencyAddressCountry = emergencyAddressCountry;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;

public class StudentApplicationResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2749244580460910172L;
	private BigInteger schoolApplicationGuid;
	private String schoolQuestionsStatus = "No";
	private String schoolDocumentsStatus = "No";
	private String schoolAppFeesPaymentStatus;
	private String acceptTermsAndConditions;
	private String feePaReleaseAuthorization;
	private String doYouNeedTuitionAid;
	private String typeOfAidSought;
	private String regularEarlyAdmission;
	private String haveYouPreviouslyAttendedUNI;
	private Date startDateOfPreviousAttendance;
	private Date endDateOfPreviousAttendance;
	private String anyFamilyEverAttendedUNI;
	private String schoolAppPacketPDF;
	private Long schoolTermGuid;
	private Long schoolDegreeGuid;
	private String isTransferStudent;
	
	public BigInteger getSchoolApplicationGuid() {
		return schoolApplicationGuid;
	}

	public void setSchoolApplicationGuid(BigInteger schoolApplicationGuid) {
		this.schoolApplicationGuid = schoolApplicationGuid;
	}

	public String getSchoolQuestionsStatus() {
		return schoolQuestionsStatus;
	}

	public void setSchoolQuestionsStatus(String schoolQuestionsStatus) {
		this.schoolQuestionsStatus = schoolQuestionsStatus;
	}

	public String getSchoolDocumentsStatus() {
		return schoolDocumentsStatus;
	}

	public void setSchoolDocumentsStatus(String schoolDocumentsStatus) {
		this.schoolDocumentsStatus = schoolDocumentsStatus;
	}

	public String getSchoolAppFeesPaymentStatus() {
		return schoolAppFeesPaymentStatus;
	}

	public void setSchoolAppFeesPaymentStatus(String schoolAppFeesPaymentStatus) {
		this.schoolAppFeesPaymentStatus = schoolAppFeesPaymentStatus;
	}

	public String getAcceptTermsAndConditions() {
		return acceptTermsAndConditions;
	}

	public void setAcceptTermsAndConditions(String acceptTermsAndConditions) {
		this.acceptTermsAndConditions = acceptTermsAndConditions;
	}

	public String getFeePaReleaseAuthorization() {
		return feePaReleaseAuthorization;
	}

	public void setFeePaReleaseAuthorization(String feePaReleaseAuthorization) {
		this.feePaReleaseAuthorization = feePaReleaseAuthorization;
	}

	public String getDoYouNeedTuitionAid() {
		return doYouNeedTuitionAid;
	}

	public void setDoYouNeedTuitionAid(String doYouNeedTuitionAid) {
		this.doYouNeedTuitionAid = doYouNeedTuitionAid;
	}

	public String getTypeOfAidSought() {
		return typeOfAidSought;
	}

	public void setTypeOfAidSought(String typeOfAidSought) {
		this.typeOfAidSought = typeOfAidSought;
	}

	public String getRegularEarlyAdmission() {
		return regularEarlyAdmission;
	}

	public void setRegularEarlyAdmission(String regularEarlyAdmission) {
		this.regularEarlyAdmission = regularEarlyAdmission;
	}

	public String getHaveYouPreviouslyAttendedUNI() {
		return haveYouPreviouslyAttendedUNI;
	}

	public void setHaveYouPreviouslyAttendedUNI(String haveYouPreviouslyAttendedUNI) {
		this.haveYouPreviouslyAttendedUNI = haveYouPreviouslyAttendedUNI;
	}

	public Date getStartDateOfPreviousAttendance() {
		return startDateOfPreviousAttendance;
	}

	public void setStartDateOfPreviousAttendance(Date startDateOfPreviousAttendance) {
		this.startDateOfPreviousAttendance = startDateOfPreviousAttendance;
	}

	public Date getEndDateOfPreviousAttendance() {
		return endDateOfPreviousAttendance;
	}

	public void setEndDateOfPreviousAttendance(Date endDateOfPreviousAttendance) {
		this.endDateOfPreviousAttendance = endDateOfPreviousAttendance;
	}

	public String getAnyFamilyEverAttendedUNI() {
		return anyFamilyEverAttendedUNI;
	}

	public void setAnyFamilyEverAttendedUNI(String anyFamilyEverAttendedUNI) {
		this.anyFamilyEverAttendedUNI = anyFamilyEverAttendedUNI;
	}

	public String getSchoolAppPacketPDF() {
		return schoolAppPacketPDF;
	}

	public void setSchoolAppPacketPDF(String schoolAppPacketPDF) {
		this.schoolAppPacketPDF = schoolAppPacketPDF;
	}

	public Long getSchoolTermGuid() {
		return schoolTermGuid;
	}

	public void setSchoolTermGuid(Long schoolTermGuid) {
		this.schoolTermGuid = schoolTermGuid;
	}

	public Long getSchoolDegreeGuid() {
		return schoolDegreeGuid;
	}

	public void setSchoolDegreeGuid(Long schoolDegreeGuid) {
		this.schoolDegreeGuid = schoolDegreeGuid;
	}

	public String getIsTransferStudent() {
		return isTransferStudent;
	}

	public void setIsTransferStudent(String isTransferStudent) {
		this.isTransferStudent = isTransferStudent;
	}


}

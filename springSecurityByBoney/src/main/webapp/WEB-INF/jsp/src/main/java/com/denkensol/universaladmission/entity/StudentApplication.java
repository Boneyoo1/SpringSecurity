package com.denkensol.universaladmission.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENT_APPLICATION")
public class StudentApplication implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3256520232303363289L;

	@Id
	@Column(name = "SCHOOL_APPLICATION_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long schoolApplicationGuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_GUID")
	private Account account;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOL_GUID")
	private School school;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOL_DEGREE_GUID")
	private SchoolDegree schoolDegree;

	@Column(name = "DEGREE_DEPARTMENT")
	private String degreeDepartment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOL_TERM_GUID")
	private SchoolTerm schoolTerm;

	@Column(name = "SCHOOL_QUESTIONS_STATUS")
	private String schoolQuestionsStatus;

	@Column(name = "SCHOOL_DOCUMENTS_STATUS")
	private String schoolDocumentsStatus;

	@Column(name = "APPLICATION_STATUS")
	private String applicationStatus;

	@Column(name = "SCHOOL_APP_FEES_PAYMENT_STATUS")
	private String schoolAppFeesPaymentStatus;

	@Column(name = "ACCEPT_TERMS_AND_CONDITIONS")
	private String acceptTermsAndConditions;

	@Column(name = "FEE_PA_RELEASE_AUTROIZATION")
	private String feePaReleaseAuthorization;

	@Column(name = "DO_YOU_NEED_TUITION_AID")
	private String doYouNeedTuitionAid;

	@Column(name = "TYPE_OF_AID_SOUGHT")
	private String typeOfAidSought;

	@Column(name = "REGULAR_EARLY_ADMISSION")
	private String regularEarlyAdmission;

	@Column(name = "HAVE_YOU_PREVIOUSLY_ATTENDED_UNI")
	private String haveYouPreviouslyAttendedUNI;

	@Column(name = "START_DATE_OF_PREVIOUS_ATTENDANCE")
	private Date startDateOfPreviousAttendance;

	@Column(name = "END_DATE_OF_PREVIOUS_ATTENDANCE")
	private Date endDateOfPreviousAttendance;

	@Column(name = "ANY_FAMILY_EVER_ATTENDED_THE_UNI")
	private String anyFamilyEverAttendedUNI;

	@Column(name = "SCHOOL_APP_PACKET_PDF")
	private String schoolAppPacketPDF;

	@Column(name = "SEQ_NO")
	private Integer seqNo;

	@Column(name = "IS_TRANSFER_STUDENT")
	private String isTransferStudent;

	@Column(name = "APPLICATION_SUBMISSION_TIMESTAMP")
	private Timestamp applicationSubmissionTimeStamp;

	public Long getSchoolApplicationGuid() {
		return schoolApplicationGuid;
	}

	public void setSchoolApplicationGuid(Long schoolApplicationGuid) {
		this.schoolApplicationGuid = schoolApplicationGuid;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public SchoolDegree getSchoolDegree() {
		return schoolDegree;
	}

	public void setSchoolDegree(SchoolDegree schoolDegree) {
		this.schoolDegree = schoolDegree;
	}

	public String getDegreeDepartment() {
		return degreeDepartment;
	}

	public void setDegreeDepartment(String degreeDepartment) {
		this.degreeDepartment = degreeDepartment;
	}

	public SchoolTerm getSchoolTerm() {
		return schoolTerm;
	}

	public void setSchoolTerm(SchoolTerm schoolTerm) {
		this.schoolTerm = schoolTerm;
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

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
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

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String getIsTransferStudent() {
		return isTransferStudent;
	}

	public void setIsTransferStudent(String isTransferStudent) {
		this.isTransferStudent = isTransferStudent;
	}

	public Timestamp getApplicationSubmissionTimeStamp() {
		return applicationSubmissionTimeStamp;
	}

	public void setApplicationSubmissionTimeStamp(Timestamp applicationSubmissionTimeStamp) {
		this.applicationSubmissionTimeStamp = applicationSubmissionTimeStamp;
	}

}

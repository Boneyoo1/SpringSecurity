package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Date;

public class SchoolTermResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8969829657960662488L;

	private BigInteger schoolTermGuid;
	private Long schoolGuid;
	private String termYear;
	private String term;
	private Date applicationDeadLineDate;
	private Date earlyDecisionDeadLineDate;
	private Date applicationSubmissionStartDate;
	private Date documentsDeadLineDate;

	public BigInteger getSchoolTermGuid() {
		return schoolTermGuid;
	}

	public void setSchoolTermGuid(BigInteger schoolTermGuid) {
		this.schoolTermGuid = schoolTermGuid;
	}

	public String getTermYear() {
		return termYear;
	}

	public void setTermYear(String termYear) {
		this.termYear = termYear;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Date getApplicationDeadLineDate() {
		return applicationDeadLineDate;
	}

	public void setApplicationDeadLineDate(Date applicationDeadLineDate) {
		this.applicationDeadLineDate = applicationDeadLineDate;
	}

	public Date getEarlyDecisionDeadLineDate() {
		return earlyDecisionDeadLineDate;
	}

	public void setEarlyDecisionDeadLineDate(Date earlyDecisionDeadLineDate) {
		this.earlyDecisionDeadLineDate = earlyDecisionDeadLineDate;
	}

	public Date getApplicationSubmissionStartDate() {
		return applicationSubmissionStartDate;
	}

	public void setApplicationSubmissionStartDate(Date applicationSubmissionStartDate) {
		this.applicationSubmissionStartDate = applicationSubmissionStartDate;
	}

	public Date getDocumentsDeadLineDate() {
		return documentsDeadLineDate;
	}

	public void setDocumentsDeadLineDate(Date documentsDeadLineDate) {
		this.documentsDeadLineDate = documentsDeadLineDate;
	}

	public Long getSchoolGuid() {
		return schoolGuid;
	}

	public void setSchoolGuid(Long schoolGuid) {
		this.schoolGuid = schoolGuid;
	}

	@Override
	public String toString() {
		return "SchoolTermResponse [schoolTermGuid=" + schoolTermGuid + ", schoolGuid=" + schoolGuid + ", termYear="
				+ termYear + ", term=" + term + ", applicationDeadLineDate=" + applicationDeadLineDate
				+ ", earlyDecisionDeadLineDate=" + earlyDecisionDeadLineDate + ", applicationSubmissionStartDate="
				+ applicationSubmissionStartDate + ", documentsDeadLineDate=" + documentsDeadLineDate + "]";
	}

	
}

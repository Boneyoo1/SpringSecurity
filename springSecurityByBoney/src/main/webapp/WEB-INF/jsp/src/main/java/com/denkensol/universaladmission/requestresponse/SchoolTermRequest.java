package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.math.BigInteger;

public class SchoolTermRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3168101028740312005L;

	private BigInteger schoolTermGuid;
	private Long schoolGuid;
	private String termYear;
	private String term;
	private String applicationDeadLineDate;
	private String earlyDecisionDeadLineDate;
	private String applicationSubmissionStartDate;
	private String documentsDeadLineDate;

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

	public String getApplicationDeadLineDate() {
		return applicationDeadLineDate;
	}

	public void setApplicationDeadLineDate(String applicationDeadLineDate) {
		this.applicationDeadLineDate = applicationDeadLineDate;
	}

	public String getEarlyDecisionDeadLineDate() {
		return earlyDecisionDeadLineDate;
	}

	public void setEarlyDecisionDeadLineDate(String earlyDecisionDeadLineDate) {
		this.earlyDecisionDeadLineDate = earlyDecisionDeadLineDate;
	}

	public String getApplicationSubmissionStartDate() {
		return applicationSubmissionStartDate;
	}

	public void setApplicationSubmissionStartDate(String applicationSubmissionStartDate) {
		this.applicationSubmissionStartDate = applicationSubmissionStartDate;
	}

	public String getDocumentsDeadLineDate() {
		return documentsDeadLineDate;
	}

	public void setDocumentsDeadLineDate(String documentsDeadLineDate) {
		this.documentsDeadLineDate = documentsDeadLineDate;
	}

	public Long getSchoolGuid() {
		return schoolGuid;
	}

	public void setSchoolGuid(Long schoolGuid) {
		this.schoolGuid = schoolGuid;
	}

}

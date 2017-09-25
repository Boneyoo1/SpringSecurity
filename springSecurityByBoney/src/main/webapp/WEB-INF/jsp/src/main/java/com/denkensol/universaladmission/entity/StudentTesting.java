package com.denkensol.universaladmission.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENT_TESTING")
@NamedNativeQueries({
		@NamedNativeQuery(name = "GET_ALL_STUDENT_TESTS", query = "select STUDENT_TESTING_GUID as studentTestingGuid,TEST_TYPE as testType,TEST_TAKEN_ON as testTakenOn,VERBAL_SCORE as verbalScore,QUANT_SCORE as quantScore,ANALYTICAL_SCORE as analyticalScore,READING_SCORE as readingScore,WRITING_SCORE as writingScore,LISTENING_SCORE as listeningScore,SPEAKING_SCORE as speakingScore,INTEGRATED_REASONING_SCORE as integratedReasoningScore,TOTAL_BAND_SCORE as overAllBandScore from STUDENT_TESTING where account_guid=:accountGuid "), })

public class StudentTesting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "STUDENT_TESTING_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentTestingGuid;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_GUID")
	private Account account;

	@Column(name = "TEST_TYPE")
	private String testType;

	@Column(name = "TEST_TAKEN_ON")
	private String testTakenOn;

	@Column(name = "VERBAL_SCORE")
	private String verbalScore;

	@Column(name = "QUANT_SCORE")
	private String quantScore;

	@Column(name = "ANALYTICAL_SCORE")
	private String analyticalScore;

	@Column(name = "READING_SCORE")
	private String readingScore;

	@Column(name = "WRITING_SCORE")
	private String writingScore;

	@Column(name = "LISTENING_SCORE")
	private String listeningScore;

	@Column(name = "SPEAKING_SCORE")
	private String speakingScore;

	@Column(name = "TOTAL_BAND_SCORE")
	private String overAllBandScore;

	@Column(name = "INTEGRATED_REASONING_SCORE")
	private String integratedReasoningScore;

	public Long getStudentTestingGuid() {
		return studentTestingGuid;
	}

	public void setStudentTestingGuid(Long studentTestingGuid) {
		this.studentTestingGuid = studentTestingGuid;
	}

	public String getOverAllBandScore() {
		return overAllBandScore;
	}

	public void setOverAllBandScore(String overAllBandScore) {
		this.overAllBandScore = overAllBandScore;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getTestTakenOn() {
		return testTakenOn;
	}

	public void setTestTakenOn(String testTakenOn) {
		this.testTakenOn = testTakenOn;
	}

	public String getVerbalScore() {
		return verbalScore;
	}

	public void setVerbalScore(String verbalScore) {
		this.verbalScore = verbalScore;
	}

	public String getQuantScore() {
		return quantScore;
	}

	public void setQuantScore(String quantScore) {
		this.quantScore = quantScore;
	}

	public String getAnalyticalScore() {
		return analyticalScore;
	}

	public void setAnalyticalScore(String analyticalScore) {
		this.analyticalScore = analyticalScore;
	}

	public String getReadingScore() {
		return readingScore;
	}

	public void setReadingScore(String readingScore) {
		this.readingScore = readingScore;
	}

	public String getWritingScore() {
		return writingScore;
	}

	public void setWritingScore(String writingScore) {
		this.writingScore = writingScore;
	}

	public String getListeningScore() {
		return listeningScore;
	}

	public void setListeningScore(String listeningScore) {
		this.listeningScore = listeningScore;
	}

	public String getSpeakingScore() {
		return speakingScore;
	}

	public void setSpeakingScore(String speakingScore) {
		this.speakingScore = speakingScore;
	}

	public String getIntegratedReasoningScore() {
		return integratedReasoningScore;
	}

	public void setIntegratedReasoningScore(String integratedReasoningScore) {
		this.integratedReasoningScore = integratedReasoningScore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((studentTestingGuid == null) ? 0 : studentTestingGuid.hashCode());
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
		StudentTesting other = (StudentTesting) obj;
		if (studentTestingGuid == null) {
			if (other.studentTestingGuid != null)
				return false;
		} else if (!studentTestingGuid.equals(other.studentTestingGuid))
			return false;
		return true;
	}

}

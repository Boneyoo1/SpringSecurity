package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.math.BigInteger;

public class StudentTestingRequestResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7197255465985965259L;

	private BigInteger studentTestingGuid;

	private String testType;
	
	private String testTakenOn;

	private String verbalScore;

	private String quantScore;

	private String analyticalScore;

	private String readingScore;

	private String writingScore;

	private String listeningScore;

	private String speakingScore;

	private String integratedReasoningScore;
	
	private String overAllBandScore;

	public BigInteger getStudentTestingGuid() {
		return studentTestingGuid;
	}

	public void setStudentTestingGuid(BigInteger studentTestingGuid) {
		this.studentTestingGuid = studentTestingGuid;
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

	public String getOverAllBandScore() {
		return overAllBandScore;
	}

	public void setOverAllBandScore(String overAllBandScore) {
		this.overAllBandScore = overAllBandScore;
	}

	@Override
	public String toString() {
		return "StudentTestingRequestResponse [studentTestingGuid=" + studentTestingGuid + ", testType=" + testType
				+ ", testTakenOn=" + testTakenOn + ", verbalScore=" + verbalScore + ", quantScore=" + quantScore
				+ ", analyticalScore=" + analyticalScore + ", readingScore=" + readingScore + ", writingScore="
				+ writingScore + ", listeningScore=" + listeningScore + ", speakingScore=" + speakingScore
				+ ", integratedReasoningScore=" + integratedReasoningScore + ", overAllBandScore=" + overAllBandScore
				+ "]";
	}

}

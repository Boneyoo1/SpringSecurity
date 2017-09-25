package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;

public class SchoolSearchCriteria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5066021778163896848L;

	private String schoolName;
	private String stateName;
	private String cityName;
	private Double minGREScore;
	private Double minTOEFLScore;
	private Double minGMATScore;
	private Double minGPAScore;
	private String transcriptsRequired;
	private String solvCertRequired;
	private String recLettersRequired;
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Double getMinGREScore() {
		return minGREScore;
	}
	public void setMinGREScore(Double minGREScore) {
		this.minGREScore = minGREScore;
	}
	public Double getMinTOEFLScore() {
		return minTOEFLScore;
	}
	public void setMinTOEFLScore(Double minTOEFLScore) {
		this.minTOEFLScore = minTOEFLScore;
	}
	public Double getMinGMATScore() {
		return minGMATScore;
	}
	public void setMinGMATScore(Double minGMATScore) {
		this.minGMATScore = minGMATScore;
	}
	public Double getMinGPAScore() {
		return minGPAScore;
	}
	public void setMinGPAScore(Double minGPAScore) {
		this.minGPAScore = minGPAScore;
	}
	public String getTranscriptsRequired() {
		return transcriptsRequired;
	}
	public void setTranscriptsRequired(String transcriptsRequired) {
		this.transcriptsRequired = transcriptsRequired;
	}
	public String getSolvCertRequired() {
		return solvCertRequired;
	}
	public void setSolvCertRequired(String solvCertRequired) {
		this.solvCertRequired = solvCertRequired;
	}
	public String getRecLettersRequired() {
		return recLettersRequired;
	}
	public void setRecLettersRequired(String recLettersRequired) {
		this.recLettersRequired = recLettersRequired;
	}
	@Override
	public String toString() {
		return "SchoolSearchCriteria [schoolName=" + schoolName + ", stateName=" + stateName + ", cityName=" + cityName
				+ ", minGREScore=" + minGREScore + ", minTOEFLScore=" + minTOEFLScore + ", minGMATScore=" + minGMATScore
				+ ", minGPAScore=" + minGPAScore + ", transcriptsRequired=" + transcriptsRequired
				+ ", solvCertRequired=" + solvCertRequired + ", recLettersRequired=" + recLettersRequired + "]";
	}


}

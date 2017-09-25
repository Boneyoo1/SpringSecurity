package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;

public class StudentDashBoardProfileResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2860165084370308417L;
	private Boolean profileCompleted = false;
	private Boolean educationCompleted = false;
	private Boolean testingCompleted = false;
	private Boolean writingCompleted = false;
	private Boolean documentCompleted = false;
	private Boolean employementCompleted = false;
	private Boolean recommendersCompleted = false;

	public Boolean getProfileCompleted() {
		return profileCompleted;
	}

	public void setProfileCompleted(Boolean profileCompleted) {
		this.profileCompleted = profileCompleted;
	}

	public Boolean getEducationCompleted() {
		return educationCompleted;
	}

	public void setEducationCompleted(Boolean educationCompleted) {
		this.educationCompleted = educationCompleted;
	}

	public Boolean getTestingCompleted() {
		return testingCompleted;
	}

	public void setTestingCompleted(Boolean testingCompleted) {
		this.testingCompleted = testingCompleted;
	}

	public Boolean getWritingCompleted() {
		return writingCompleted;
	}

	public void setWritingCompleted(Boolean writingCompleted) {
		this.writingCompleted = writingCompleted;
	}

	public Boolean getDocumentCompleted() {
		return documentCompleted;
	}

	public void setDocumentCompleted(Boolean documentCompleted) {
		this.documentCompleted = documentCompleted;
	}

	public Boolean getEmployementCompleted() {
		return employementCompleted;
	}

	public void setEmployementCompleted(Boolean employementCompleted) {
		this.employementCompleted = employementCompleted;
	}

	public Boolean getRecommendersCompleted() {
		return recommendersCompleted;
	}

	public void setRecommendersCompleted(Boolean recommendersCompleted) {
		this.recommendersCompleted = recommendersCompleted;
	}

}

package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SchoolQuestionsSubmitRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7932414120243602228L;

	private Long termId;
	private Long degreeId;
	private Long schoolGuid;
	private List<SchoolQuestionRequest> questionAnswers = new ArrayList<SchoolQuestionRequest>();
	private String isTransferStudent;
	
	public Long getTermId() {
		return termId;
	}

	public void setTermId(Long termId) {
		this.termId = termId;
	}

	public Long getDegreeId() {
		return degreeId;
	}

	public void setDegreeId(Long degreeId) {
		this.degreeId = degreeId;
	}

	public Long getSchoolGuid() {
		return schoolGuid;
	}

	public void setSchoolGuid(Long schoolGuid) {
		this.schoolGuid = schoolGuid;
	}

	public List<SchoolQuestionRequest> getQuestionAnswers() {
		return questionAnswers;
	}

	public void setQuestionAnswers(List<SchoolQuestionRequest> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}

	public String getIsTransferStudent() {
		return isTransferStudent;
	}

	public void setIsTransferStudent(String isTransferStudent) {
		this.isTransferStudent = isTransferStudent;
	}

	@Override
	public String toString() {
		return "SchoolQuestionsSubmitRequest [termId=" + termId + ", degreeId=" + degreeId + ", schoolGuid="
				+ schoolGuid + ", questionAnswers=" + questionAnswers + ", isTransferStudent=" + isTransferStudent
				+ "]";
	}

}

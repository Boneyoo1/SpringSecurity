package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SchoolSectionQuestionsResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4358959136400888655L;

	private Long schoolQuestionSectionGuid;
	private String sectionName;
	private List<SchoolQuestionsResponse> questions = new ArrayList<SchoolQuestionsResponse>();
	private Long schoolGuid;
	private Double seqNo;
	private String isTransferStudent;

	public Long getSchoolQuestionSectionGuid() {
		return schoolQuestionSectionGuid;
	}

	public void setSchoolQuestionSectionGuid(Long schoolQuestionSectionGuid) {
		this.schoolQuestionSectionGuid = schoolQuestionSectionGuid;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public List<SchoolQuestionsResponse> getQuestions() {
		return questions;
	}

	public void setQuestions(List<SchoolQuestionsResponse> questions) {
		this.questions = questions;
	}

	public Long getSchoolGuid() {
		return schoolGuid;
	}

	public void setSchoolGuid(Long schoolGuid) {
		this.schoolGuid = schoolGuid;
	}

	public Double getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Double seqNo) {
		this.seqNo = seqNo;
	}

	public String getIsTransferStudent() {
		return isTransferStudent;
	}

	public void setIsTransferStudent(String isTransferStudent) {
		this.isTransferStudent = isTransferStudent;
	}

}

package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;

public class SchoolQuestionRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3811309078224514270L;

	private Long questionId;
	private Long schoolQuestionAnswerGuid;
	private String questionAnswer;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public Long getSchoolQuestionAnswerGuid() {
		return schoolQuestionAnswerGuid;
	}

	public void setSchoolQuestionAnswerGuid(Long schoolQuestionAnswerGuid) {
		this.schoolQuestionAnswerGuid = schoolQuestionAnswerGuid;
	}

}

package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;

public class SchoolQuestionAnswerOptionResponse implements Serializable {

	private static final long serialVersionUID = -174675500383502966L;
	private Long schoolQuestionAnswerOptionGuid;
	private String answer;
	private Double seqNo;

	public Long getSchoolQuestionAnswerOptionGuid() {
		return schoolQuestionAnswerOptionGuid;
	}

	public void setSchoolQuestionAnswerOptionGuid(Long schoolQuestionAnswerOptionGuid) {
		this.schoolQuestionAnswerOptionGuid = schoolQuestionAnswerOptionGuid;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Double getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Double double1) {
		this.seqNo = double1;
	}

}

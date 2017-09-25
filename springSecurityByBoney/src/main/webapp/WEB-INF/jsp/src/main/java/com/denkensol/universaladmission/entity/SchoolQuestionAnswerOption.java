package com.denkensol.universaladmission.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SCHOOL_QUESTION_ANSWER_OPTION")
public class SchoolQuestionAnswerOption implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 119758095064358841L;

	@Id
	@Column(name = "SCHOOL_QUESTION_ANSWER_OPTION_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long schoolQuestionAnswerOptionGuid;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "SCHOOL_QUESTION_GUID")
	private SchoolQuestion schoolQuestion;

	@Column(name = "ANSWER")
	private String answer;

	@Column(name = "SEQ_NO")
	private Double seqNo;

	public Long getSchoolQuestionAnswerOptionGuid() {
		return schoolQuestionAnswerOptionGuid;
	}

	public void setSchoolQuestionAnswerOptionGuid(Long schoolQuestionAnswerOptionGuid) {
		this.schoolQuestionAnswerOptionGuid = schoolQuestionAnswerOptionGuid;
	}

	public SchoolQuestion getSchoolQuestion() {
		return schoolQuestion;
	}

	public void setSchoolQuestion(SchoolQuestion schoolQuestion) {
		this.schoolQuestion = schoolQuestion;
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

	public void setSeqNo(Double answerOptionSeq) {
		this.seqNo = answerOptionSeq;
	}

}

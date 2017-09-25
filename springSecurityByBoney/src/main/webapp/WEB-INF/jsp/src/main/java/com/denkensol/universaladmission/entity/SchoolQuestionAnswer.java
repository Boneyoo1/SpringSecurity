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
@Table(name = "SCHOOL_QUESTION_ANSWER")
public class SchoolQuestionAnswer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6475829896120442325L;

	@Id
	@Column(name = "SCHOOL_QUESTION_ANSWER_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long schoolQuestionAnswerGuid;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "SCHOOL_QUESTION_GUID")
	private SchoolQuestion schoolQuestion;

	@Column(name = "ANSWER")
	private String answer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOL_TABLE_QUESTION_GUID")
	private SchoolQuestion schoolTableQuestion;

	@Column(name = "TABLE_QUESTION_ANSWER_ROW")
	private Integer tableQuestionAnswerRow;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOL_SUPER_TABLE_QUESTION_GUID")
	private SchoolQuestion schoolSuperTableQuestion;

	@Column(name = "SUPER_TABLE_QUESTION_ANSWER_ROW")
	private Integer superTableQuestionAnswerRow;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_GUID")
	private Account account;

	public Long getSchoolQuestionAnswerGuid() {
		return schoolQuestionAnswerGuid;
	}

	public void setSchoolQuestionAnswerGuid(Long schoolQuestionAnswerGuid) {
		this.schoolQuestionAnswerGuid = schoolQuestionAnswerGuid;
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

	public SchoolQuestion getSchoolTableQuestion() {
		return schoolTableQuestion;
	}

	public void setSchoolTableQuestion(SchoolQuestion schoolTableQuestion) {
		this.schoolTableQuestion = schoolTableQuestion;
	}

	public Integer getTableQuestionAnswerRow() {
		return tableQuestionAnswerRow;
	}

	public void setTableQuestionAnswerRow(Integer tableQuestionAnswerRow) {
		this.tableQuestionAnswerRow = tableQuestionAnswerRow;
	}

	public SchoolQuestion getSchoolSuperTableQuestion() {
		return schoolSuperTableQuestion;
	}

	public void setSchoolSuperTableQuestion(SchoolQuestion schoolSuperTableQuestion) {
		this.schoolSuperTableQuestion = schoolSuperTableQuestion;
	}

	public Integer getSuperTableQuestionAnswerRow() {
		return superTableQuestionAnswerRow;
	}

	public void setSuperTableQuestionAnswerRow(Integer superTableQuestionAnswerRow) {
		this.superTableQuestionAnswerRow = superTableQuestionAnswerRow;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	

}

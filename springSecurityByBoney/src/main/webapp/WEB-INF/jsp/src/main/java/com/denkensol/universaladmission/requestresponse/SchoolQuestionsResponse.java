package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class SchoolQuestionsResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8530228318276484547L;

	private Long schoolQuestionGuid;
	private Long schoolQuestionSecionGuid;
	private String questionTitle;
	private String questionType;
	private String questionLongText;
	private String questionMandatory;
	private String questionInactive;
	private Double seqNo;
	private String parentQuestionAnswerCondition;
	private BigInteger parentQuestionId;
	private String questionAnswer;
	private Long questionAnswerGuid;
	private BigInteger tableQuestionId;
	private List<SchoolQuestionsResponse> childQuestions = new ArrayList<SchoolQuestionsResponse>();
	private List<SchoolQuestionsResponse> tableQuestions = new ArrayList<SchoolQuestionsResponse>();
	List<SchoolQuestionAnswerOptionResponse> answerOptions = new ArrayList<SchoolQuestionAnswerOptionResponse>();
	private Double childQuestionseqNo;
	private String degreeList;

	public Double getChildQuestionseqNo() {
		return childQuestionseqNo;
	}

	public void setChildQuestionseqNo(Double childQuestionseqNo) {
		this.childQuestionseqNo = childQuestionseqNo;
	}

	public Long getSchoolQuestionGuid() {
		return schoolQuestionGuid;
	}

	public void setSchoolQuestionGuid(Long schoolQuestionGuid) {
		this.schoolQuestionGuid = schoolQuestionGuid;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getQuestionType() {
		return questionType;
	}

	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}

	public String getQuestionLongText() {
		return questionLongText;
	}

	public void setQuestionLongText(String questionLongText) {
		this.questionLongText = questionLongText;
	}

	public String getQuestionMandatory() {
		return questionMandatory;
	}

	public void setQuestionMandatory(String questionMandatory) {
		this.questionMandatory = questionMandatory;
	}

	public List<SchoolQuestionAnswerOptionResponse> getAnswerOptions() {
		return answerOptions;
	}

	public void setAnswerOptions(List<SchoolQuestionAnswerOptionResponse> answerOptions) {
		this.answerOptions = answerOptions;
	}

	public String getParentQuestionAnswerCondition() {
		return parentQuestionAnswerCondition;
	}

	public void setParentQuestionAnswerCondition(String parentQuestionAnswerCondition) {
		this.parentQuestionAnswerCondition = parentQuestionAnswerCondition;
	}

	public BigInteger getParentQuestionId() {
		return parentQuestionId;
	}

	public void setParentQuestionId(BigInteger parentQuestionId) {
		this.parentQuestionId = parentQuestionId;
	}

	public String getQuestionAnswer() {
		return questionAnswer;
	}

	public Long getQuestionAnswerGuid() {
		return questionAnswerGuid;
	}

	public void setQuestionAnswerGuid(Long questionAnswerGuid) {
		this.questionAnswerGuid = questionAnswerGuid;
	}

	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public BigInteger getTableQuestionId() {
		return tableQuestionId;
	}

	public void setTableQuestionId(BigInteger tableQuestionId) {
		this.tableQuestionId = tableQuestionId;
	}

	public List<SchoolQuestionsResponse> getChildQuestions() {
		return childQuestions;
	}

	public void setChildQuestions(List<SchoolQuestionsResponse> childQuestions) {
		this.childQuestions = childQuestions;
	}

	public List<SchoolQuestionsResponse> getTableQuestions() {
		return tableQuestions;
	}

	public void setTableQuestions(List<SchoolQuestionsResponse> tableQuestions) {
		this.tableQuestions = tableQuestions;
	}

	public Long getSchoolQuestionSecionGuid() {
		return schoolQuestionSecionGuid;
	}

	public void setSchoolQuestionSecionGuid(Long schoolQuestionSecionGuid) {
		this.schoolQuestionSecionGuid = schoolQuestionSecionGuid;
	}

	public String getQuestionInactive() {
		return questionInactive;
	}

	public void setQuestionInactive(String questionInactive) {
		this.questionInactive = questionInactive;
	}

	public Double getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Double seqNo) {
		this.seqNo = seqNo;
	}

	public String getDegreeList() {
		return degreeList;
	}

	public void setDegreeList(String degreeList) {
		this.degreeList = degreeList;
	}
	

}

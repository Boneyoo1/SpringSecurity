package com.denkensol.universaladmission.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SCHOOL_QUESTION")
public class SchoolQuestion implements Serializable {

	/**
	 * 
	 */

	@Id
	@Column(name = "SCHOOL_QUESTION_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long schoolQuestionGuid;

	private static final long serialVersionUID = 6278864040451795660L;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolQuestion", cascade = CascadeType.ALL)
	private List<SchoolQuestionAnswerOption> schoolQuestionAnswerOptions = new ArrayList<SchoolQuestionAnswerOption>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolQuestion", cascade = CascadeType.ALL)
	private List<SchoolQuestionAnswer> schoolQuestionAnswers = new ArrayList<SchoolQuestionAnswer>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolTableQuestion")
	private List<SchoolQuestionAnswer> schoolTableQuestionAnswers = new ArrayList<SchoolQuestionAnswer>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolSuperTableQuestion")
	private List<SchoolQuestionAnswer> schoolSuperTableQuestionAnswers = new ArrayList<SchoolQuestionAnswer>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOL_QUESTION_SECTION_GUID")
	private SchoolQuestionSection schoolQuestionSection;

	@Column(name = "QUESTION_TITLE")
	private String questionTitle;

	@Column(name = "QUESTION_TYPE")
	private String questionType;

	@Column(name = "QUESTION_LONG_TEXT")
	private String questionLongText;

	@Column(name = "QUESTION_MANDATORY")
	private String questionMandatory;

	@Column(name = "QUESTION_INACTIVE")
	private String questionInactive;

	@Column(name = "SEQ_NO")
	private Double seqNo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentQuestion")
	private List<SchoolQuestion> childQuestions = new ArrayList<SchoolQuestion>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_QUESTION_GUID")
	private SchoolQuestion parentQuestion;

	@Column(name = "PARENT_QUESTION_ANSWER_CONDITION")
	private String parentQuestionAnswerCondition;

	@Column(name = "CHILD_QUESTION_SEQ_NO")
	private Double childQuestionseqNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TABLE_QUESTION_GUID")
	private SchoolQuestion tableQuestion;

	@Column(name = "TABLE_QUESTION_SEQ_NO")
	private Double tableQuestionseqNo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tableQuestion")
	private List<SchoolQuestion> tableChildQuestions = new ArrayList<SchoolQuestion>();

	@Column(name = "DEGREE_LIST")
	private String degreeList;

	public Long getSchoolQuestionGuid() {
		return schoolQuestionGuid;
	}

	public void setSchoolQuestionGuid(Long schoolQuestionGuid) {
		this.schoolQuestionGuid = schoolQuestionGuid;
	}

	public List<SchoolQuestionAnswerOption> getSchoolQuestionAnswerOptions() {
		return schoolQuestionAnswerOptions;
	}

	public void setSchoolQuestionAnswerOptions(List<SchoolQuestionAnswerOption> schoolQuestionAnswerOptions) {
		this.schoolQuestionAnswerOptions = schoolQuestionAnswerOptions;
	}

	public List<SchoolQuestionAnswer> getSchoolQuestionAnswers() {
		return schoolQuestionAnswers;
	}

	public void setSchoolQuestionAnswers(List<SchoolQuestionAnswer> schoolQuestionAnswers) {
		this.schoolQuestionAnswers = schoolQuestionAnswers;
	}

	public List<SchoolQuestionAnswer> getSchoolTableQuestionAnswers() {
		return schoolTableQuestionAnswers;
	}

	public void setSchoolTableQuestionAnswers(List<SchoolQuestionAnswer> schoolTableQuestionAnswers) {
		this.schoolTableQuestionAnswers = schoolTableQuestionAnswers;
	}

	public List<SchoolQuestionAnswer> getSchoolSuperTableQuestionAnswers() {
		return schoolSuperTableQuestionAnswers;
	}

	public void setSchoolSuperTableQuestionAnswers(List<SchoolQuestionAnswer> schoolSuperTableQuestionAnswers) {
		this.schoolSuperTableQuestionAnswers = schoolSuperTableQuestionAnswers;
	}

	public SchoolQuestionSection getSchoolQuestionSection() {
		return schoolQuestionSection;
	}

	public void setSchoolQuestionSection(SchoolQuestionSection schoolQuestionSection) {
		this.schoolQuestionSection = schoolQuestionSection;
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

	public String getQuestionInactive() {
		return questionInactive;
	}

	public void setQuestionInactive(String questionInactive) {
		this.questionInactive = questionInactive;
	}

	public Double getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Double questionSequenceNo) {
		this.seqNo = questionSequenceNo;
	}

	public List<SchoolQuestion> getChildQuestions() {
		return childQuestions;
	}

	public void setChildQuestions(List<SchoolQuestion> childQuestions) {
		this.childQuestions = childQuestions;
	}

	public SchoolQuestion getParentQuestion() {
		return parentQuestion;
	}

	public void setParentQuestion(SchoolQuestion parentQuestion) {
		this.parentQuestion = parentQuestion;
	}

	public String getParentQuestionAnswerCondition() {
		return parentQuestionAnswerCondition;
	}

	public void setParentQuestionAnswerCondition(String parentQuestionAnswerCondition) {
		this.parentQuestionAnswerCondition = parentQuestionAnswerCondition;
	}

	public Double getChildQuestionseqNo() {
		return childQuestionseqNo;
	}

	public void setChildQuestionseqNo(Double parentQuestionSeqNo) {
		this.childQuestionseqNo = parentQuestionSeqNo;
	}

	public Double getTableQuestionseqNo() {
		return tableQuestionseqNo;
	}

	public void setTableQuestionseqNo(Double tableQuestionSequence) {
		this.tableQuestionseqNo = tableQuestionSequence;
	}

	public SchoolQuestion getTableQuestion() {
		return tableQuestion;
	}

	public void setTableQuestion(SchoolQuestion tableQuestion) {
		this.tableQuestion = tableQuestion;
	}

	public List<SchoolQuestion> getTableChildQuestions() {
		return tableChildQuestions;
	}

	public void setTableChildQuestions(List<SchoolQuestion> tableChildQuestions) {
		this.tableChildQuestions = tableChildQuestions;
	}

	public String getDegreeList() {
		return degreeList;
	}

	public void setDegreeList(String degreeList) {
		this.degreeList = degreeList;
	}

}

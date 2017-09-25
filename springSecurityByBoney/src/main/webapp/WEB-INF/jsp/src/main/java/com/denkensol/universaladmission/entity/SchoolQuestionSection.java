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
@Table(name = "SCHOOL_QUESTION_SECTION")
public class SchoolQuestionSection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8636498118476561603L;

	@Id
	@Column(name = "SCHOOL_QUESTION_SECTION_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long schoolQuestionSectionGuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOL_GUID")
	private School school;

	@Column(name = "SECTION_NAME")
	private String sectionName;

	@Column(name = "SEQ_NO")
	private Double seqNo;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolQuestionSection", cascade = CascadeType.ALL)
	private List<SchoolQuestion> schoolQuestions = new ArrayList<SchoolQuestion>();

	public Long getSchoolQuestionSectionGuid() {
		return schoolQuestionSectionGuid;
	}

	public void setSchoolQuestionSectionGuid(Long schoolQuestionSectionGuid) {
		this.schoolQuestionSectionGuid = schoolQuestionSectionGuid;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public Double getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Double sectionSequenceNo) {
		this.seqNo = sectionSequenceNo;
	}

	public List<SchoolQuestion> getSchoolQuestions() {
		return schoolQuestions;
	}

	public void setSchoolQuestions(List<SchoolQuestion> schoolQuestions) {
		this.schoolQuestions = schoolQuestions;
	}

}

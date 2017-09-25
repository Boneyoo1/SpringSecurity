package com.denkensol.universaladmission.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SCHOOL_TERM")
@NamedNativeQueries({
		@NamedNativeQuery(name = "GET_ALL_SCHOOLS_TERMS", query = "select SCHOOL_TERM_GUID as schoolTermGuid,TERM_YEAR as termYear,TERM_NAME as term,APP_DEADLINE_DATE as applicationDeadLineDate,EARLY_DECISION_DEADLINE_DATE as earlyDecisionDeadLineDate from SCHOOL_TERM"),
		@NamedNativeQuery(name = "GET_SCHOOL_TERMS_BY_SCHOOL", query = "select SCHOOL_TERM_GUID as schoolTermGuid,TERM_YEAR as termYear,TERM_NAME as term,APP_DEADLINE_DATE as applicationDeadLineDate,EARLY_DECISION_DEADLINE_DATE as earlyDecisionDeadLineDate,APP_SUBMISSION_START_DATE as applicationSubmissionStartDate,DOCUMENTS_DEADLINE_DATE as documentsDeadLineDate  from SCHOOL_TERM where SCHOOL_GUID=:schoolGuid"), })

public class SchoolTerm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3798917736069104468L;

	@Id
	@Column(name = "SCHOOL_TERM_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long schoolTermGuid;

	@Column(name = "TERM_YEAR")
	private String termYear;

	@Column(name = "TERM_NAME")
	private String term;

	@Column(name = "APP_DEADLINE_DATE")
	private Date applicationDeadLineDate;

	@Column(name = "EARLY_DECISION_DEADLINE_DATE")
	private Date earlyDecisionDeadLineDate;

	@Column(name = "APP_SUBMISSION_START_DATE")
	private Date applicationSubmissionStartDate;

	@Column(name = "DOCUMENTS_DEADLINE_DATE")
	private Date documentsDeadLineDate;

	@Column(name = "DEGREE_GUID")
	private Long degree;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOL_GUID")
	private School school;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolTerm")
	private List<StudentApplication> studentApplications = new ArrayList<StudentApplication>();

	public Long getSchoolTermGuid() {
		return schoolTermGuid;
	}

	public void setSchoolTermGuid(Long schoolTermGuid) {
		this.schoolTermGuid = schoolTermGuid;
	}

	public String getTermYear() {
		return termYear;
	}

	public void setTermYear(String termYear) {
		this.termYear = termYear;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Date getApplicationDeadLineDate() {
		return applicationDeadLineDate;
	}

	public void setApplicationDeadLineDate(Date applicationDeadLineDate) {
		this.applicationDeadLineDate = applicationDeadLineDate;
	}

	public Date getEarlyDecisionDeadLineDate() {
		return earlyDecisionDeadLineDate;
	}

	public void setEarlyDecisionDeadLineDate(Date earlyDecisionDeadLineDate) {
		this.earlyDecisionDeadLineDate = earlyDecisionDeadLineDate;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public Date getApplicationSubmissionStartDate() {
		return applicationSubmissionStartDate;
	}

	public void setApplicationSubmissionStartDate(Date applicationSubmissionStartDate) {
		this.applicationSubmissionStartDate = applicationSubmissionStartDate;
	}

	public Date getDocumentsDeadLineDate() {
		return documentsDeadLineDate;
	}

	public void setDocumentsDeadLineDate(Date documentsDeadLineDate) {
		this.documentsDeadLineDate = documentsDeadLineDate;
	}

	public Long getDegree() {
		return degree;
	}

	public void setDegree(Long degree) {
		this.degree = degree;
	}

	public List<StudentApplication> getStudentApplications() {
		return studentApplications;
	}

	public void setStudentApplications(List<StudentApplication> studentApplications) {
		this.studentApplications = studentApplications;
	}

}

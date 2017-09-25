package com.denkensol.universaladmission.entity;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SCHOOL_DOCUMENT")
public class SchoolDocument implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5732669903403132610L;

	@Id
	@Column(name = "SCHOOL_DOCUMENT_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long schoolDocumentGuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOL_GUID")
	private School school;

	@Column(name = "DOCUMENT_NAME")
	private String documentName;

	@Column(name = "DOCUMENT_URL")
	private String documentURL;

	@Column(name = "DOCUMENT_PATH")
	private String documentPath;

	@Column(name = "IS_MANDATORY")
	private String isMandatory;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolDocument")
	private List<StudentSchoolDocument> studentSchoolDocuments = new ArrayList<StudentSchoolDocument>();

	@Column(name = "DEGREE_LIST")
	private String degreeList;

	public Long getSchoolDocumentGuid() {
		return schoolDocumentGuid;
	}

	public void setSchoolDocumentGuid(Long schoolDocumentGuid) {
		this.schoolDocumentGuid = schoolDocumentGuid;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getIsMandatory() {
		return isMandatory;
	}

	public void setIsMandatory(String isMandatory) {
		this.isMandatory = isMandatory;
	}

	public List<StudentSchoolDocument> getStudentSchoolDocuments() {
		return studentSchoolDocuments;
	}

	public void setStudentSchoolDocuments(List<StudentSchoolDocument> studentSchoolDocuments) {
		this.studentSchoolDocuments = studentSchoolDocuments;
	}

	public String getDocumentURL() {
		return documentURL;
	}

	public void setDocumentURL(String documentURL) {
		this.documentURL = documentURL;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public String getDegreeList() {
		return degreeList;
	}

	public void setDegreeList(String degreeList) {
		this.degreeList = degreeList;
	}

}

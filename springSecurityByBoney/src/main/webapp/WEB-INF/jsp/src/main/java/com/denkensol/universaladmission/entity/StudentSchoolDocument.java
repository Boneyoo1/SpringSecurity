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
@Table(name = "STUDENET_SCHOOL_DOCUMENT")
public class StudentSchoolDocument implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3075735881097111530L;

	@Id
	@Column(name = "STUDENET_SCHOOL_DOCUMENT_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentSchoolDocumentGuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_GUID")
	private Account account;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOL_GUID")
	private School school;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "SCHOOL_DOCUMENT_GUID")
	private SchoolDocument schoolDocument;

	@Column(name = "DOCUMENT_NAME")
	private String documentName;

	@Column(name = "DOCUMENT_PATH")
	private String documentPath;

	public Long getStudentSchoolDocumentGuid() {
		return studentSchoolDocumentGuid;
	}

	public void setStudentSchoolDocumentGuid(Long studentSchoolDocumentGuid) {
		this.studentSchoolDocumentGuid = studentSchoolDocumentGuid;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public SchoolDocument getSchoolDocument() {
		return schoolDocument;
	}

	public void setSchoolDocument(SchoolDocument schoolDocument) {
		this.schoolDocument = schoolDocument;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

}

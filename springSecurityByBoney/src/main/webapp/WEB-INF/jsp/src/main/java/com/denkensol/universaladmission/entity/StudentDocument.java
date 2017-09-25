package com.denkensol.universaladmission.entity;

import java.io.Serializable;

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
import javax.persistence.Table;

@Entity
@Table(name = "STUDENT_DOCUMENT")

@NamedNativeQueries({
		@NamedNativeQuery(name = "GET_ALL_STUDENT_DOCUMENTS", query = "select STUDENT_DOCUMENT_GUID as studentDocumentGuid,DOCUMENT_TYPE as documentType,DOCUMENT_NAME as documentName,DOCUMENT_PATH_GUID as documentPathGuid from STUDENT_DOCUMENT where account_guid=:accountGuid "), })
public class StudentDocument implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2008892028668195302L;

	@Id
	@Column(name = "STUDENT_DOCUMENT_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentDocumentGuid;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_GUID")
	private Account account;

	@Column(name = "DOCUMENT_TYPE")
	private String documentType;

	@Column(name = "DOCUMENT_NAME")
	private String documentName;

	@Column(name = "DOCUMENT_PATH_GUID")
	private String documentPathGuid;

	public Long getStudentDocumentGuid() {
		return studentDocumentGuid;
	}

	public void setStudentDocumentGuid(Long studentDocumentGuid) {
		this.studentDocumentGuid = studentDocumentGuid;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentPathGuid() {
		return documentPathGuid;
	}

	public void setDocumentPathGuid(String documentPathGuid) {
		this.documentPathGuid = documentPathGuid;
	}

}

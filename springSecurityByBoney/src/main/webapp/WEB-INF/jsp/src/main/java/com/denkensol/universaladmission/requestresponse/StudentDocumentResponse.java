package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.math.BigInteger;

public class StudentDocumentResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5728332150051358466L;

	private BigInteger studentDocumentGuid;
	private String documentType;
	private String documentName;
	private String documentPathGuid;
	private String documentPath;
	private String degreeList;
	private String viewDocumentUrl;



	public BigInteger getStudentDocumentGuid() {
		return studentDocumentGuid;
	}

	public void setStudentDocumentGuid(BigInteger studentDocumentGuid) {
		this.studentDocumentGuid = studentDocumentGuid;
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
	
	public String getViewDocumentUrl() {
		return viewDocumentUrl;
	}

	public void setViewDocumentUrl(String viewDocumentUrl) {
		this.viewDocumentUrl = viewDocumentUrl;
	}

}

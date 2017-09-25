package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;

public class SchoolDocumentRequestResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9089532868852369292L;

	private Long schoolDocumentGuid;
	private Long schoolGuid;
	private String documentName;
	private String isMandatory;
	private Long studentSchoolDocumentGuid;
	private String documentPath;
	private String documentURL;
	private String uploadedDocumentName;
	private String degreeList;
	private String studentSchoolDocumentName;
	private String viewDocumentUrl;
	


	public String getUploadedDocumentName() {
		return uploadedDocumentName;
	}

	public void setUploadedDocumentName(String uploadedDocumentName) {
		this.uploadedDocumentName = uploadedDocumentName;
	}

	public Long getSchoolDocumentGuid() {
		return schoolDocumentGuid;
	}

	public void setSchoolDocumentGuid(Long schoolDocumentGuid) {
		this.schoolDocumentGuid = schoolDocumentGuid;
	}

	public Long getSchoolGuid() {
		return schoolGuid;
	}

	public void setSchoolGuid(Long schoolGuid) {
		this.schoolGuid = schoolGuid;
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

	public Long getStudentSchoolDocumentGuid() {
		return studentSchoolDocumentGuid;
	}

	public void setStudentSchoolDocumentGuid(Long studentSchoolDocumentGuid) {
		this.studentSchoolDocumentGuid = studentSchoolDocumentGuid;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public String getDocumentURL() {
		return documentURL;
	}

	public void setDocumentURL(String documentURL) {
		this.documentURL = documentURL;
	}

	public String getDegreeList() {
		return degreeList;
	}

	public void setDegreeList(String degreeList) {
		this.degreeList = degreeList;
	}

	
	public String getStudentSchoolDocumentName() {
		return studentSchoolDocumentName;
	}

	public void setStudentSchoolDocumentName(String studentSchoolDocumentName) {
		this.studentSchoolDocumentName = studentSchoolDocumentName;
	}
	
	public String getViewDocumentUrl() {
		return viewDocumentUrl;
	}

	public void setViewDocumentUrl(String viewDocumentUrl) {
		this.viewDocumentUrl = viewDocumentUrl;
	}

}

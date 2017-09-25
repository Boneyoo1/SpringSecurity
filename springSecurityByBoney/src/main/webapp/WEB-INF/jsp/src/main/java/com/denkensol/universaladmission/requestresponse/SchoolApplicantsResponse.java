package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import com.denkensol.universaladmission.constant.CredentialsConstants;

public class SchoolApplicantsResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5812602562844203484L;
	private String firstName;
	private String lastName;
	private String dateOfBirth;
	private String term;
	private String year;
	private String degreeApplied;
	private String applicationStatus;
	private String applicationSubmittedDate;
	private String pdfURL;
	private String viewPdfURL;
	private String downloadpdfURL;
	
	


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDegreeApplied() {
		return degreeApplied;
	}

	public void setDegreeApplied(String degreeApplied) {
		this.degreeApplied = degreeApplied;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public String getApplicationSubmittedDate() {
		return applicationSubmittedDate;
	}

	public void setApplicationSubmittedDate(Timestamp applicationSubmittedDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss");
		String appSubmittedDate = "";
		if (applicationSubmittedDate != null) {
			appSubmittedDate = simpleDateFormat.format(applicationSubmittedDate);
		}

		
		this.applicationSubmittedDate = appSubmittedDate;
	}

	public String getPdfURL() {
		return pdfURL;
	}

	public void setPdfURL(String pdfURL) {
		if (pdfURL != null && !pdfURL.isEmpty()) {
			pdfURL = "https://s3-us-west-2.amazonaws.com/" + CredentialsConstants.AWS_S3_BUCKET_NAME
					+ "/student-documents/" + pdfURL;
		}
		this.pdfURL = pdfURL;
	}

	public String getViewPdfURL() {
		return viewPdfURL;
	}

	public void setViewPdfURL(String viewPdfURL) {
		this.viewPdfURL = viewPdfURL;
	}
	


	public String getDownloadpdfURL() {
		return downloadpdfURL;
	}

	public void setDownloadpdfURL(String downloadpdfURL) {
		this.downloadpdfURL = downloadpdfURL;
	}

}

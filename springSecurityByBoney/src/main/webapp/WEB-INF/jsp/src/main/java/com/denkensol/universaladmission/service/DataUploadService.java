package com.denkensol.universaladmission.service;

import java.text.ParseException;

public interface DataUploadService {

	void uploadReligiouns();

	void uploadCountries();

	void uploadLanguages();

	void uploadVisaTypes();

	void uploadSchoolMasters();

	void uploadSchoolDegrees();

	void uploadSchoolTerms() throws ParseException;
	
	void uploadSchoolQuestions();

	void uploadSchoolDocuments();
	
	void uploadDegrees();

}

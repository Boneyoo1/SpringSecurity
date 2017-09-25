package com.denkensol.universaladmission.endpoint;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.denkensol.universaladmission.service.DataUploadService;

@Controller
public class DataUploadController {

	@Autowired
	DataUploadService dataUploadService;

	@PostMapping(value = "/uploadReligiouns")
	public ResponseEntity<Boolean> uploadReligiouns() {
		dataUploadService.uploadReligiouns();
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/uploadCountries")
	public ResponseEntity<Boolean> uploadCountries() {
		dataUploadService.uploadCountries();
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/uploadLanguages")
	public ResponseEntity<Boolean> uploadLanguages() {
		dataUploadService.uploadLanguages();
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/uploadVisaTypes")
	public ResponseEntity<Boolean> uploadVisaTypes() {
		dataUploadService.uploadVisaTypes();
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/uploadSchoolMasters")
	public ResponseEntity<Boolean> uploadSchoolMasters() {
		dataUploadService.uploadSchoolMasters();
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/uploadSchoolDegrees")
	public ResponseEntity<Boolean> uploadSchoolDegrees() {
		dataUploadService.uploadSchoolDegrees();
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/uploadSchoolTerms")
	public ResponseEntity<Boolean> uploadSchoolTerms() throws ParseException {
		dataUploadService.uploadSchoolTerms();
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}
	@PostMapping(value = "/uploadSchoolQuestions")
	public ResponseEntity<Boolean> uploadSchoolQuestions() {
		dataUploadService.uploadSchoolQuestions();
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}
	@PostMapping(value = "/uploadSchoolDocuments")
	public ResponseEntity<Boolean> uploadSchoolDocuments() {
		dataUploadService.uploadSchoolDocuments();
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}
	
	@PostMapping(value = "/uploadDegrees")
	public ResponseEntity<Boolean> uploadDegrees() {
		dataUploadService.uploadDegrees();
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}
}

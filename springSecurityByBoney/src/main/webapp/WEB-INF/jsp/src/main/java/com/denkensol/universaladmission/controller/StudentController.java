package com.denkensol.universaladmission.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.denkensol.universaladmission.entity.Account;
import com.denkensol.universaladmission.requestresponse.SchoolDocumentRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentApplicationResponse;
import com.denkensol.universaladmission.requestresponse.StudentDashBoardProfileResponse;
import com.denkensol.universaladmission.requestresponse.StudentDocumentResponse;
import com.denkensol.universaladmission.requestresponse.StudentEducationRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentEmployementRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentProfileRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentRecommenderRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentTestingRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentWritingRequestResponse;
import com.denkensol.universaladmission.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Autowired
	StudentService studentService;

	@PostMapping(value = "/saveUpdateStudentProfile")
	public ResponseEntity<Long> saveUpdateStudentProfile(
			@RequestBody StudentProfileRequestResponse studentProfileRequestResponse, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		Long studentProfileGuid = studentService.saveUpdateStudentProfile(studentProfileRequestResponse, account);
		return new ResponseEntity<Long>(studentProfileGuid, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/getStudentProfile")
	public ResponseEntity<StudentProfileRequestResponse> getStudentProfile(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		StudentProfileRequestResponse studentProfileRequestResponse = studentService
				.getStudentProfile(account.getGuid());
		return new ResponseEntity<StudentProfileRequestResponse>(studentProfileRequestResponse, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getStudentDocuments")
	public ResponseEntity<List<StudentDocumentResponse>> getStudentDocuments(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		List<StudentDocumentResponse> studentDocuments = studentService.getStudentDocuments(account.getGuid());
		return new ResponseEntity<List<StudentDocumentResponse>>(studentDocuments, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/uploadStudentDocument")
	public ResponseEntity<Boolean> uploadStudentDocument(MultipartHttpServletRequest request,
			HttpServletRequest servletRequest) {
		Boolean isUploaded = true;
		studentService.uploadStudentDocument(request, servletRequest);
		return new ResponseEntity<Boolean>(isUploaded, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/deleteStudentDocument")
	public ResponseEntity<Boolean> deleteStudentDocument(@RequestBody Long studentDocumentGuid) {
		Boolean isUploaded = true;
		studentService.deleteStudentDocument(studentDocumentGuid);
		return new ResponseEntity<Boolean>(isUploaded, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/downloadStudentDocument")
	public ResponseEntity<String> downloadStudentDocument(@RequestBody Long studentDocumentGuid,
			HttpServletResponse response) {
		return new ResponseEntity<String>(studentService.downloadStudentDocument(studentDocumentGuid), HttpStatus.OK);
	}


	@GetMapping(value = "/getStudentWritings")
	public ResponseEntity<List<StudentWritingRequestResponse>> getStudentWritings(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		List<StudentWritingRequestResponse> studentWritings = studentService.getStudentWritings(account.getGuid());
		return new ResponseEntity<List<StudentWritingRequestResponse>>(studentWritings, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/saveStudentWriting")
	public ResponseEntity<Boolean> saveStudentWriting(
			@RequestBody StudentWritingRequestResponse studentWritingRequestResponse,
			HttpServletRequest servletRequest) {
		Boolean isSaved = true;
		studentService.saveStudentWriting(studentWritingRequestResponse, servletRequest);
		return new ResponseEntity<Boolean>(isSaved, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/deleteStudentWriting")
	public ResponseEntity<Boolean> deleteStudentWriting(@RequestBody Long studentWritingGuid) {
		Boolean isUploaded = true;
		studentService.deleteStudentWriting(studentWritingGuid);
		return new ResponseEntity<Boolean>(isUploaded, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getStudentApplication")
	public ResponseEntity<Boolean> getStudentApplication(HttpServletRequest servletRequest) {
		HttpSession session = servletRequest.getSession(true);
		Account account = (Account) session.getAttribute("account");
		return new ResponseEntity<Boolean>(studentService.getStudentApplication(account.getGuid()),
				HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getStudentTestings", produces = "application/json")
	public ResponseEntity<List<StudentTestingRequestResponse>> getStudentTestings(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		List<StudentTestingRequestResponse> studentTestings = studentService.getStudentTestings(account.getGuid());
		return new ResponseEntity<List<StudentTestingRequestResponse>>(studentTestings, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/saveStudentTesting")
	public ResponseEntity<Boolean> saveStudentTesting(
			@RequestBody StudentTestingRequestResponse studentTestingRequestResponse,
			HttpServletRequest servletRequest) {
		Boolean isSaved = true;
		studentService.saveStudentTesting(studentTestingRequestResponse, servletRequest);
		return new ResponseEntity<Boolean>(isSaved, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/deleteStudentTesting")
	public ResponseEntity<Boolean> deleteStudentTesting(@RequestBody Long studentTestingGuid) {
		Boolean isUploaded = true;
		studentService.deleteStudentTesting(studentTestingGuid);
		return new ResponseEntity<Boolean>(isUploaded, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getStudentEducations", produces = "application/json")
	public ResponseEntity<List<StudentEducationRequestResponse>> getStudentEducations(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		List<StudentEducationRequestResponse> studentEducations = studentService
				.getStudentEducations(account.getGuid());
		return new ResponseEntity<List<StudentEducationRequestResponse>>(studentEducations, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/saveStudentEducation")
	public ResponseEntity<Boolean> saveStudentEducation(
			@RequestBody StudentEducationRequestResponse studentEducationRequestResponse,
			HttpServletRequest servletRequest) {
		Boolean isSaved = true;
		studentService.saveStudentEducation(studentEducationRequestResponse, servletRequest);
		return new ResponseEntity<Boolean>(isSaved, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/deleteStudentEducation")
	public ResponseEntity<Boolean> deleteStudentEducation(@RequestBody Long studentEducationGuid) {
		Boolean isUploaded = true;
		studentService.deleteStudentEducation(studentEducationGuid);
		return new ResponseEntity<Boolean>(isUploaded, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getStudentEmployement", produces = "application/json")
	public ResponseEntity<List<StudentEmployementRequestResponse>> getStudentEmployement(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		List<StudentEmployementRequestResponse> studentEmployements = studentService
				.getStudentEmployement(account.getGuid());
		return new ResponseEntity<List<StudentEmployementRequestResponse>>(studentEmployements, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/saveStudentEmployement")
	public ResponseEntity<Boolean> saveStudentEmployement(
			@RequestBody StudentEmployementRequestResponse studentEmployementRequestResponse,
			HttpServletRequest servletRequest) {
		Boolean isSaved = true;
		studentService.saveStudentEmployement(studentEmployementRequestResponse, servletRequest);
		return new ResponseEntity<Boolean>(isSaved, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/deleteStudentEmployement")
	public ResponseEntity<Boolean> deleteStudentEmployement(@RequestBody Long studentWritingGuid) {
		Boolean isUploaded = true;
		studentService.deleteStudentEmployement(studentWritingGuid);
		return new ResponseEntity<Boolean>(isUploaded, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getStudentDashBoardProfileDetails", produces = "application/json")
	public ResponseEntity<StudentDashBoardProfileResponse> getStudentDashBoardProfileDetails(
			HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		StudentDashBoardProfileResponse studentDashBoardProfileResponse = studentService
				.getStudentDashBoardProfileDetails(account.getGuid());
		return new ResponseEntity<StudentDashBoardProfileResponse>(studentDashBoardProfileResponse,
				HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/uploadStudentSchoolDocument")
	public ResponseEntity<Boolean> uploadStudentSchoolDocument(MultipartHttpServletRequest request,
			HttpServletRequest servletRequest) {
		Boolean isUploaded = true;
		studentService.uploadStudentSchoolDocument(request, servletRequest);
		return new ResponseEntity<Boolean>(isUploaded, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getSchoolDocuments/{schoolGuid}")
	public ResponseEntity<List<SchoolDocumentRequestResponse>> getSchoolDocuments(
			@PathVariable("schoolGuid") Long schoolGuid, HttpServletRequest servletRequest) {
		List<SchoolDocumentRequestResponse> schoolDocuments = studentService.getSchoolDocuments(schoolGuid,
				servletRequest);
		return new ResponseEntity<List<SchoolDocumentRequestResponse>>(schoolDocuments, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getStudentDocument/{studentDocumentGuid}")
	public ResponseEntity<StudentDocumentResponse> getStudentDocument(
			@PathVariable("studentDocumentGuid") Long studentDocumentGuid, HttpServletRequest request) {
		StudentDocumentResponse studentDocumentResponse = studentService.getStudentDocument(studentDocumentGuid);
		return new ResponseEntity<StudentDocumentResponse>(studentDocumentResponse, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getStudentRecommenders")
	public ResponseEntity<List<StudentRecommenderRequestResponse>> getStudentRecommenders(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		List<StudentRecommenderRequestResponse> studentRecommenders = studentService
				.getStudentRecommenders(account.getGuid());
		return new ResponseEntity<List<StudentRecommenderRequestResponse>>(studentRecommenders, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/saveStudentRecommender")
	public ResponseEntity<Boolean> saveStudentRecommender(
			@RequestBody StudentRecommenderRequestResponse studentRecommenderRequestResponse,
			HttpServletRequest servletRequest) {
		Boolean isSaved = true;
		studentService.saveStudentRecommender(studentRecommenderRequestResponse, servletRequest);
		return new ResponseEntity<Boolean>(isSaved, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/deleteStudentRecommender")
	public ResponseEntity<Boolean> deleteStudentRecommender(@RequestBody Long studentRecommenderGuid) {
		Boolean isDeleted = true;
		studentService.deleteStudentRecommender(studentRecommenderGuid);
		return new ResponseEntity<Boolean>(isDeleted, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/submitSchoolApplication")
	public ResponseEntity<Boolean> submitSchoolApplication(@RequestBody Long schoolApplicationGuid,
			HttpServletRequest request) {
		Boolean isSubmited = true;
		studentService.submitSchoolApplication(schoolApplicationGuid);
		return new ResponseEntity<Boolean>(isSubmited, HttpStatus.ACCEPTED);
	}

}
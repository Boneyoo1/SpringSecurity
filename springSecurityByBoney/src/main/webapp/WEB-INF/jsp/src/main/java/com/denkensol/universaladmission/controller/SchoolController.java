package com.denkensol.universaladmission.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.denkensol.universaladmission.entity.School;
import com.denkensol.universaladmission.requestresponse.SchoolDegreeResponse;
import com.denkensol.universaladmission.requestresponse.SchoolDocumentRequestResponse;
import com.denkensol.universaladmission.requestresponse.SchoolQuestionsResponse;
import com.denkensol.universaladmission.requestresponse.SchoolQuestionsSubmitRequest;
import com.denkensol.universaladmission.requestresponse.SchoolSearchCriteria;
import com.denkensol.universaladmission.requestresponse.SchoolSearchResponse;
import com.denkensol.universaladmission.requestresponse.SchoolSectionQuestionsResponse;
import com.denkensol.universaladmission.requestresponse.SchoolTermRequest;
import com.denkensol.universaladmission.requestresponse.SchoolTermResponse;
import com.denkensol.universaladmission.service.SchoolPacketPDFService;
import com.denkensol.universaladmission.service.SchoolService;

@Controller
@RequestMapping("/school")
public class SchoolController {

	@Autowired
	SchoolService schoolService;

	@Autowired
	SchoolPacketPDFService schoolPacketPDFService;

	@PostMapping(value = "/getSchools")
	public ResponseEntity<List<SchoolSearchResponse>> getSchools(@RequestBody SchoolSearchCriteria schoolSearchCriteria,
			HttpServletRequest req) {
		List<SchoolSearchResponse> schools = schoolService.getSchools(schoolSearchCriteria, req);
		return new ResponseEntity<List<SchoolSearchResponse>>(schools, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getSchoolDegrees/{schoolId}")
	public ResponseEntity<List<SchoolDegreeResponse>> getSchoolDegrees(@PathVariable("schoolId") String schoolId) {
		Long schoolIdLong = null;
		if (schoolId != null && !schoolId.equalsIgnoreCase("null")) {
			schoolIdLong = Long.parseLong(schoolId);
		}
		List<SchoolDegreeResponse> schoolDegrees = schoolService.getSchoolDegrees(schoolIdLong);
		return new ResponseEntity<List<SchoolDegreeResponse>>(schoolDegrees, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/addColleges")
	public ResponseEntity<List<String>> addColleges(@RequestBody List<School> addColleges, HttpServletRequest request) {
		List<String> collegesAdded = schoolService.addColleges(addColleges, request);
		return new ResponseEntity<List<String>>(collegesAdded, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getStudentSchools")
	public ResponseEntity<List<SchoolSearchResponse>> getStudentSchools(HttpServletRequest request) {
		List<SchoolSearchResponse> schools = schoolService.getStudentSchools(request);
		return new ResponseEntity<List<SchoolSearchResponse>>(schools, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getSchoolInfo/{schoolGuid}")
	public ResponseEntity<SchoolSearchResponse> getSchoolInfo(@PathVariable("schoolGuid") Long schoolGuid,
			HttpServletRequest request) {
		SchoolSearchResponse schoolData = schoolService.getSchoolInfo(schoolGuid, request);
		return new ResponseEntity<SchoolSearchResponse>(schoolData, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/deleteStudentSchool")
	public ResponseEntity<Boolean> deleteStudentSchool(@RequestBody Long schoolGuid, HttpServletRequest request) {
		Boolean isUploaded = true;
		schoolService.deleteStudentSchool(schoolGuid, request);
		return new ResponseEntity<Boolean>(isUploaded, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getSchoolSectionQuestions/{schoolGuid}")
	public ResponseEntity<List<SchoolSectionQuestionsResponse>> getSchoolSectionQuestions(
			@PathVariable("schoolGuid") Long schoolGuid, HttpServletRequest request) {
		List<SchoolSectionQuestionsResponse> schoolSectionQuestions = schoolService
				.getSchoolSectionQuestions(schoolGuid, request);
		return new ResponseEntity<List<SchoolSectionQuestionsResponse>>(schoolSectionQuestions, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/submitSchoolQuestions")
	public ResponseEntity<Boolean> submitSchoolQuestions(
			@RequestBody SchoolQuestionsSubmitRequest schoolQuestionsSubmitRequest, HttpServletRequest request) {
		Boolean isUploaded = false;
		isUploaded = schoolService.submitSchoolQuestions(schoolQuestionsSubmitRequest, request);
		return new ResponseEntity<Boolean>(isUploaded, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/submitAttachmentSchoolQuestions")
	public ResponseEntity<Boolean> submitAttachmentSchoolQuestions(MultipartHttpServletRequest request,
			HttpServletRequest servletRequest) {
		Boolean isUploaded = true;
		schoolService.submitAttachmentSchoolQuestions(request, servletRequest);
		return new ResponseEntity<Boolean>(isUploaded, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/generatePdfPacket/{schoolApplicationGuid}")
	public ResponseEntity<String> generatePdfPacket(@PathVariable("schoolApplicationGuid") Long schoolApplicationGuid,
			HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<String>(schoolPacketPDFService.generatePdfPacket(schoolApplicationGuid, request),
				HttpStatus.OK);

	}

	@GetMapping(value = "/getApplicationPDF/{schoolApplicationGuid}")
	public ResponseEntity<String> getApplicationPDF(@PathVariable("schoolApplicationGuid") Long schoolApplicationGuid,
			HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<String>(schoolPacketPDFService.getApplicationPDF(schoolApplicationGuid, request),
				HttpStatus.OK);

	}

	@PostMapping(value = "/saveSchoolInfo")
	public ResponseEntity<Long> saveSchoolInfo(@RequestBody SchoolSearchResponse schoolSearchResponse) {
		Long schoolGuid = schoolService.saveSchoolInfo(schoolSearchResponse);
		return new ResponseEntity<Long>(schoolGuid, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/saveSchoolInfoContent")
	public ResponseEntity<Boolean> saveSchoolInfoContent(@RequestBody SchoolSearchResponse schoolSearchResponse) {
		schoolService.saveSchoolInfoContent(schoolSearchResponse);
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getSchoolQuestionSections/{schoolGuid}")
	public ResponseEntity<List<SchoolSectionQuestionsResponse>> getSchoolQuestionSections(
			@PathVariable("schoolGuid") Long schoolGuid, HttpServletRequest request) {
		List<SchoolSectionQuestionsResponse> schoolSectionQuestions = schoolService
				.getSchoolQuestionSections(schoolGuid);
		return new ResponseEntity<List<SchoolSectionQuestionsResponse>>(schoolSectionQuestions, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/saveQuestionSection")
	public ResponseEntity<Boolean> saveQuestionSection(
			@RequestBody SchoolSectionQuestionsResponse schoolSectionQuestionsResponse) {
		schoolService.saveQuestionSection(schoolSectionQuestionsResponse);
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/deleteSchoolDegree")
	public ResponseEntity<Boolean> deleteSchoolDegree(@RequestBody Long schoolDegreeGuid) {
		Boolean isDeleted = true;
		schoolService.deleteSchoolDegree(schoolDegreeGuid);
		return new ResponseEntity<Boolean>(isDeleted, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/saveSchoolDegree")
	public ResponseEntity<Boolean> saveSchoolDegree(@RequestBody SchoolDegreeResponse schoolDegreeResponse) {
		schoolService.saveSchoolDegree(schoolDegreeResponse);
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getSchoolDegree/{schoolDegreeGuid}")
	public ResponseEntity<SchoolDegreeResponse> getSchoolDegree(@PathVariable("schoolDegreeGuid") Long schoolDegreeGuid,
			HttpServletRequest request) {
		SchoolDegreeResponse schoolDegreeData = schoolService.getSchoolDegree(schoolDegreeGuid);
		return new ResponseEntity<SchoolDegreeResponse>(schoolDegreeData, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getSchoolTerms/{schoolId}")
	public ResponseEntity<List<SchoolTermResponse>> getSchoolTerms(@PathVariable("schoolId") Long schoolId) {
		List<SchoolTermResponse> schoolTerms = schoolService.getSchoolTerms(schoolId);
		return new ResponseEntity<List<SchoolTermResponse>>(schoolTerms, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/saveSchoolTerm")
	public ResponseEntity<Boolean> saveSchoolTerm(@RequestBody SchoolTermRequest schoolTermRequest) {
		try {
			schoolService.saveSchoolTerm(schoolTermRequest);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/deleteSchoolTerm")
	public ResponseEntity<Boolean> deleteSchoolTerm(@RequestBody Long schoolTermGuid) {
		Boolean isDeleted = true;
		schoolService.deleteSchoolTerm(schoolTermGuid);
		return new ResponseEntity<Boolean>(isDeleted, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getSchoolTerm/{schoolTermGuid}")
	public ResponseEntity<SchoolTermRequest> getSchoolTerm(@PathVariable("schoolTermGuid") Long schoolTermGuid,
			HttpServletRequest request) {
		SchoolTermRequest schoolTermData = schoolService.getSchoolTerm(schoolTermGuid);
		return new ResponseEntity<SchoolTermRequest>(schoolTermData, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/saveQuestion")
	public ResponseEntity<Boolean> saveQuestion(@RequestBody SchoolQuestionsResponse schoolQuestionsResponse) {
		schoolService.saveQuestion(schoolQuestionsResponse);
		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getSchoolSectionQuestionsBySection/{schoolQuestionSecionGuid}")
	public ResponseEntity<List<SchoolQuestionsResponse>> getSchoolSectionQuestionsBySection(
			@PathVariable("schoolQuestionSecionGuid") Long schoolQuestionSecionGuid) {
		List<SchoolQuestionsResponse> sectionQuestions = schoolService
				.getSchoolSectionQuestionsBySection(schoolQuestionSecionGuid);
		return new ResponseEntity<List<SchoolQuestionsResponse>>(sectionQuestions, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getSchoolQuestion/{schoolQuestionGuid}")
	public ResponseEntity<SchoolQuestionsResponse> getSchoolQuestion(
			@PathVariable("schoolQuestionGuid") Long schoolQuestionGuid, HttpServletRequest request) {
		SchoolQuestionsResponse schoolQuestionData = schoolService.getSchoolQuestion(schoolQuestionGuid);
		return new ResponseEntity<SchoolQuestionsResponse>(schoolQuestionData, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/deleteSchoolQuestionSection")
	public ResponseEntity<Boolean> deleteSchoolQuestionSection(@RequestBody Long schoolQuestionSectionGuid) {
		Boolean isDeleted = true;
		schoolService.deleteSchoolQuestionSection(schoolQuestionSectionGuid);
		return new ResponseEntity<Boolean>(isDeleted, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/deleteSchoolQuestion")
	public ResponseEntity<Boolean> deleteSchoolQuestion(@RequestBody Long schoolQuestionGuid) {
		Boolean isDeleted = true;
		schoolService.deleteSchoolQuestion(schoolQuestionGuid);
		return new ResponseEntity<Boolean>(isDeleted, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getSchoolQuestionSection/{schoolQuestionSectionGuid}")
	public ResponseEntity<SchoolSectionQuestionsResponse> getSchoolQuestionSection(
			@PathVariable("schoolQuestionSectionGuid") Long schoolQuestionSectionGuid, HttpServletRequest request) {
		SchoolSectionQuestionsResponse schoolSectionQuestionsResponse = schoolService
				.getSchoolQuestionSection(schoolQuestionSectionGuid);
		return new ResponseEntity<SchoolSectionQuestionsResponse>(schoolSectionQuestionsResponse, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getSchoolDocuments/{schoolGuid}")
	public ResponseEntity<List<SchoolDocumentRequestResponse>> getSchoolDocuments(
			@PathVariable("schoolGuid") Long schoolGuid) {
		List<SchoolDocumentRequestResponse> schoolDocuments = schoolService.getSchoolDocuments(schoolGuid);
		return new ResponseEntity<List<SchoolDocumentRequestResponse>>(schoolDocuments, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/saveSchoolDocument")
	public ResponseEntity<Boolean> saveSchoolDocument(MultipartHttpServletRequest request,
			HttpServletRequest servletRequest) {
		Boolean isUploaded = true;
		schoolService.saveSchoolDocument(request, servletRequest);
		return new ResponseEntity<Boolean>(isUploaded, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getSchoolDocument/{schoolDocumentGuid}")
	public ResponseEntity<SchoolDocumentRequestResponse> getSchoolDocument(
			@PathVariable("schoolDocumentGuid") Long schoolDocumentGuid, HttpServletRequest request) {
		SchoolDocumentRequestResponse schoolDocumentRequestResponse = schoolService
				.getSchoolDocument(schoolDocumentGuid);
		return new ResponseEntity<SchoolDocumentRequestResponse>(schoolDocumentRequestResponse, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/deleteSchoolDocument")
	public ResponseEntity<Boolean> deleteSchoolDocument(@RequestBody Long schoolDocumentGuid) {
		Boolean isDeleted = true;
		schoolService.deleteSchoolDocument(schoolDocumentGuid);
		return new ResponseEntity<Boolean>(isDeleted, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/getSchoolsByCriteria")
	public ResponseEntity<List<SchoolSearchResponse>> getSchoolsByCriteria(
			@RequestBody SchoolSearchCriteria schoolSearchCriteria) {
		List<SchoolSearchResponse> schools = schoolService.getSchoolsByCriteria(schoolSearchCriteria);
		return new ResponseEntity<List<SchoolSearchResponse>>(schools, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/addCollege")
	public ResponseEntity<String> addCollege(@RequestBody School school, HttpServletRequest request) {
		schoolService.addCollege(school, request);
		return new ResponseEntity<String>("College Added Successfully", HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getCollegeInfo/{schoolGuid}")
	public ResponseEntity<SchoolSearchResponse> getCollegeInfo(@PathVariable("schoolGuid") Long schoolGuid,
			HttpServletRequest request) {
		SchoolSearchResponse schoolData = schoolService.getCollegeInfo(schoolGuid);
		return new ResponseEntity<SchoolSearchResponse>(schoolData, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/generateSchoolAppPDF")
	public ResponseEntity<String> generateSchoolAppPDF(@RequestBody SchoolSearchResponse schoolSearchResponse,
			HttpServletRequest request) {
		schoolService.generateSchoolAppPDF(schoolSearchResponse, request);
		return new ResponseEntity<String>("College Added Successfully", HttpStatus.ACCEPTED);
	}

}

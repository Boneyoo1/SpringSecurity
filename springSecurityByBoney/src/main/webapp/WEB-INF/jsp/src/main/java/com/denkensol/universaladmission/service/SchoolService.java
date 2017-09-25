package com.denkensol.universaladmission.service;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.denkensol.universaladmission.entity.School;
import com.denkensol.universaladmission.requestresponse.SchoolApplicantsResponse;
import com.denkensol.universaladmission.requestresponse.SchoolDegreeResponse;
import com.denkensol.universaladmission.requestresponse.SchoolDocumentRequestResponse;
import com.denkensol.universaladmission.requestresponse.SchoolProspectsResponse;
import com.denkensol.universaladmission.requestresponse.SchoolQuestionsResponse;
import com.denkensol.universaladmission.requestresponse.SchoolQuestionsSubmitRequest;
import com.denkensol.universaladmission.requestresponse.SchoolSearchCriteria;
import com.denkensol.universaladmission.requestresponse.SchoolSearchResponse;
import com.denkensol.universaladmission.requestresponse.SchoolSectionQuestionsResponse;
import com.denkensol.universaladmission.requestresponse.SchoolTermRequest;
import com.denkensol.universaladmission.requestresponse.SchoolTermResponse;

public interface SchoolService {

	List<SchoolSearchResponse> getSchools(SchoolSearchCriteria schoolSearchCriteria, HttpServletRequest req);

	List<SchoolDegreeResponse> getSchoolDegrees(Long schoolId);

	List<String> addColleges(List<School> addColleges, HttpServletRequest servletRequest);

	List<SchoolSearchResponse> getStudentSchools(HttpServletRequest request);

	SchoolSearchResponse getSchoolInfo(Long schoolGuid, HttpServletRequest request);

	void deleteStudentSchool(Long schoolGuid, HttpServletRequest request);

	List<SchoolSectionQuestionsResponse> getSchoolSectionQuestions(Long schoolGuid, HttpServletRequest request);

	Boolean submitSchoolQuestions(SchoolQuestionsSubmitRequest schoolQuestionsSubmitRequest,
			HttpServletRequest request);

	void submitAttachmentSchoolQuestions(MultipartHttpServletRequest request, HttpServletRequest servletRequest);

	Long saveSchoolInfo(SchoolSearchResponse schoolSearchResponse);

	void saveSchoolInfoContent(SchoolSearchResponse schoolSearchResponse);

	List<SchoolSectionQuestionsResponse> getSchoolQuestionSections(Long schoolGuid);

	void saveQuestionSection(SchoolSectionQuestionsResponse schoolSectionQuestionsResponse);

	void deleteSchoolDegree(Long schoolDegreeGuid);

	void saveSchoolDegree(SchoolDegreeResponse schoolDegreeResponse);

	SchoolDegreeResponse getSchoolDegree(Long schoolDegreeGuid);

	List<SchoolTermResponse> getSchoolTerms(Long schoolId);

	void saveSchoolTerm(SchoolTermRequest schoolTermRequest) throws ParseException;

	void deleteSchoolTerm(Long schoolTermGuid);

	SchoolTermRequest getSchoolTerm(Long schoolTermGuid);

	void saveQuestion(SchoolQuestionsResponse schoolQuestionsResponse);

	List<SchoolQuestionsResponse> getSchoolSectionQuestionsBySection(Long schoolQuestionSecionGuid);

	SchoolQuestionsResponse getSchoolQuestion(Long schoolQuestionGuid);

	void deleteSchoolQuestionSection(Long schoolQuestionSectionGuid);

	void deleteSchoolQuestion(Long schoolQuestionGuid);

	SchoolSectionQuestionsResponse getSchoolQuestionSection(Long schoolQuestionSectionGuid);

	List<SchoolDocumentRequestResponse> getSchoolDocuments(Long schoolGuid);

	void saveSchoolDocument(MultipartHttpServletRequest request,HttpServletRequest servletRequest);

	void deleteSchoolDocument(Long schoolDocumentGuid);

	SchoolDocumentRequestResponse getSchoolDocument(Long schoolDocumentGuid);

	List<SchoolSearchResponse> getSchoolsByCriteria(SchoolSearchCriteria schoolSearchCriteria);

	void addCollege(School school, HttpServletRequest request);

	SchoolSearchResponse getCollegeInfo(Long schoolGuid);

	void generateSchoolAppPDF(SchoolSearchResponse schoolSearchResponse, HttpServletRequest request);

	List<SchoolApplicantsResponse> getSchoolApplicants(String domainName);

	List<SchoolProspectsResponse> getSchoolProspects(String domainName);

	String downloadApplicationData(String domainName);

	void downloadStudentApplicationData(String pdfURL);

}

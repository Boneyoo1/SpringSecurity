package com.denkensol.universaladmission.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.denkensol.universaladmission.entity.Account;
import com.denkensol.universaladmission.requestresponse.SchoolDocumentRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentDashBoardProfileResponse;
import com.denkensol.universaladmission.requestresponse.StudentDocumentResponse;
import com.denkensol.universaladmission.requestresponse.StudentEducationRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentEmployementRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentProfileRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentRecommenderRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentTestingRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentWritingRequestResponse;

public interface StudentService {

	Long saveUpdateStudentProfile(StudentProfileRequestResponse studentProfileRequestResponse, Account account);

	StudentProfileRequestResponse getStudentProfile(Long studentProfileRequestResponse);

	List<StudentDocumentResponse> getStudentDocuments(Long guid);

	void uploadStudentDocument(MultipartHttpServletRequest request, HttpServletRequest servletRequest);

	void deleteStudentDocument(Long studentDocumentGuid);

	String downloadStudentDocument(Long studentDocumentGuid);

	List<StudentWritingRequestResponse> getStudentWritings(Long guid);

	void saveStudentWriting(StudentWritingRequestResponse studentWritingRequestResponse,
			HttpServletRequest servletRequest);

	void deleteStudentWriting(Long studentWritingGuid);

	void saveStudentTesting(StudentTestingRequestResponse studentTestingRequestResponse,
			HttpServletRequest servletRequest);

	List<StudentTestingRequestResponse> getStudentTestings(Long guid);

	void deleteStudentTesting(Long studentTestingGuid);

	List<StudentEducationRequestResponse> getStudentEducations(Long guid);

	void saveStudentEducation(StudentEducationRequestResponse studentEducationRequestResponse,
			HttpServletRequest servletRequest);

	void deleteStudentEducation(Long studentEducationGuid);

	List<StudentEmployementRequestResponse> getStudentEmployement(Long guid);

	void saveStudentEmployement(StudentEmployementRequestResponse studentEmployementRequestResponse,
			HttpServletRequest servletRequest);

	void deleteStudentEmployement(Long studentWritingGuid);

	StudentDashBoardProfileResponse getStudentDashBoardProfileDetails(Long guid);

	void uploadStudentSchoolDocument(MultipartHttpServletRequest request, HttpServletRequest servletRequest);

	List<SchoolDocumentRequestResponse> getSchoolDocuments(Long schoolGuid, HttpServletRequest servletRequest);

	Boolean getStudentApplication(Long accountGuid);

	StudentDocumentResponse getStudentDocument(Long studentDocumentGuid);

	List<StudentRecommenderRequestResponse> getStudentRecommenders(Long guid);

	void saveStudentRecommender(StudentRecommenderRequestResponse studentRecommenderRequestResponse,
			HttpServletRequest servletRequest);

	void deleteStudentRecommender(Long studentRecommenderGuid);

	void submitSchoolApplication(Long schoolApplicationGuid);

}

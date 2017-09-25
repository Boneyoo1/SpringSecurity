package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.StudentDocument;
import com.denkensol.universaladmission.requestresponse.StudentDocumentResponse;

public interface StudentDocumentsDao {

	void saveUpdateStudentDocuments(List<StudentDocument> studentDocuments);

	StudentDocument getStudentDocumentByGuid(Long studentDocumentGuid);

	List<StudentDocumentResponse> getAllStudentDocuments(Long accountGuid);

	void uploadStudentDocument(StudentDocument studentDocument);

	void deleteStudentDocument(StudentDocument studentDocument);
}

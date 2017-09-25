package com.denkensol.universaladmission.daoimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.StudentDocumentsDao;
import com.denkensol.universaladmission.entity.StudentDocument;
import com.denkensol.universaladmission.requestresponse.StudentDocumentResponse;

@Repository
public class StudentDocumentsDaoImpl extends BaseDaoImpl<Long, StudentDocument> implements StudentDocumentsDao {

	@Override
	public void saveUpdateStudentDocuments(List<StudentDocument> studentDocuments) {
		saveOrUpdateBatch(studentDocuments);

	}

	@Override
	public StudentDocument getStudentDocumentByGuid(Long studentDocumentGuid) {
		return getById(studentDocumentGuid);
	}

	@Override
	public List<StudentDocumentResponse> getAllStudentDocuments(Long accountGuid) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("accountGuid", accountGuid);
		return (List<StudentDocumentResponse>) executeNativeNamedQuery("GET_ALL_STUDENT_DOCUMENTS", parameters,
				StudentDocumentResponse.class);
	}

	@Override
	public void uploadStudentDocument(StudentDocument studentDocument) {
		saveOrUpdate(studentDocument);
	}

	@Override
	public void deleteStudentDocument(StudentDocument studentDocument) {
		delete(studentDocument);
	}

}

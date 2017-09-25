package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.StudentSchoolDocument;

public interface StudentSchoolDocumentDao {

	StudentSchoolDocument getStudentSchoolDocumentByGuId(Long studentSchoolDocumentGuid);

	void uploadStudentSchoolDocument(StudentSchoolDocument studentSchoolDocument);

	StudentSchoolDocument getDocumentByStudentAndDocumentId(Long schoolDocumentGuid, Long guid);
	
	List<StudentSchoolDocument> getDocumentByStudentAndSchoolId(Long accountGuid, Long schoolGuid);

	StudentSchoolDocument getDocumentByStudentAndDocumentIdAndSchoolId(Long schoolDocumentGuid,Long schoolGuid, Long guid);

}

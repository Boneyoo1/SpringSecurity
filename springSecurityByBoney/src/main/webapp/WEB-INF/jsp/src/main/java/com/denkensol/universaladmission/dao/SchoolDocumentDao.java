package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.SchoolDocument;

public interface SchoolDocumentDao {

	List<SchoolDocument> getSchoolDocuments(Long schoolGuid);

	SchoolDocument getSchoolDocumentByGuid(Long schoolDocumentGuid);

	void saveSchoolDocument(SchoolDocument schoolDocument);

	void deleteSchoolDocument(SchoolDocument schoolDocument);
	
	public void saveAllSchoolDocuments(List<SchoolDocument> newSchooDocumentlList);


}

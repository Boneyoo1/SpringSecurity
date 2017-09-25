package com.denkensol.universaladmission.daoimpl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.SchoolDocumentDao;
import com.denkensol.universaladmission.entity.SchoolDocument;

@Repository
public class SchoolDocumentDaoImpl extends BaseDaoImpl<Long, SchoolDocument> implements SchoolDocumentDao {

	@Override
	public List<SchoolDocument> getSchoolDocuments(Long schoolGuid) {
		Criterion criterionObj = Restrictions.eq("school.schoolGuid", schoolGuid);
		return getAllByCondition(criterionObj, null);
	}

	@Override
	public SchoolDocument getSchoolDocumentByGuid(Long schoolDocumentGuid) {
		return getById(schoolDocumentGuid);
	}

	@Override
	public void saveSchoolDocument(SchoolDocument schoolDocument) {
		saveOrUpdate(schoolDocument);
	}

	@Override
	public void deleteSchoolDocument(SchoolDocument schoolDocument) {
		delete(schoolDocument);
	}
	
	@Override
	public void saveAllSchoolDocuments(List newSchooDocumentlList) {
				saveBatch(newSchooDocumentlList);

	}

}

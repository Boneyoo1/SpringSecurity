package com.denkensol.universaladmission.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.StudentSchoolDocumentDao;
import com.denkensol.universaladmission.entity.StudentSchoolDocument;

@Repository
public class StudentSchoolDocumentDaoImpl extends BaseDaoImpl<Long, StudentSchoolDocument>
		implements StudentSchoolDocumentDao {

	@Override
	public StudentSchoolDocument getStudentSchoolDocumentByGuId(Long studentSchoolDocumentGuid) {
		return getById(studentSchoolDocumentGuid);
	}

	@Override
	public void uploadStudentSchoolDocument(StudentSchoolDocument studentSchoolDocument) {
		saveOrUpdate(studentSchoolDocument);
	}

	@Override
	public StudentSchoolDocument getDocumentByStudentAndDocumentId(Long schoolDocumentGuid, Long guid) {
		Criterion criterionObj = Restrictions.eq("schoolDocument.schoolDocumentGuid", schoolDocumentGuid);
		Criterion criterionObj1 = Restrictions.eq("account.guid", guid);
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(criterionObj);
		criterions.add(criterionObj1);
		return getByConditions(criterions);
	}

	@Override
	public StudentSchoolDocument getDocumentByStudentAndDocumentIdAndSchoolId(Long schoolDocumentGuid, Long schoolGuid,
			Long guid) {
		Criterion criterionObj = Restrictions.eq("schoolDocument.schoolDocumentGuid", schoolDocumentGuid);
		Criterion criterionObj1 = Restrictions.eq("account.guid", guid);
		Criterion criterionObj2 = Restrictions.eq("school.schoolGuid", schoolGuid);
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(criterionObj);
		criterions.add(criterionObj1);
		criterions.add(criterionObj2);
		return getByConditions(criterions);
	}

	@Override
	public List<StudentSchoolDocument> getDocumentByStudentAndSchoolId(Long accountGuid, Long schoolGuid) {

		Criterion criterionObj = Restrictions.eq("school.schoolGuid", schoolGuid);
		Criterion criterionObj1 = Restrictions.eq("account.guid", accountGuid);
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(criterionObj);
		criterions.add(criterionObj1);
		return getAllByConditions(criterions, null);
	}

}

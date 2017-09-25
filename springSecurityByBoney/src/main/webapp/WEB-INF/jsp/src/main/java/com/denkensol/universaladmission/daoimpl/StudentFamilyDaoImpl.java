package com.denkensol.universaladmission.daoimpl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.StudentFamilyDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.StudentFamily;

@Repository
public class StudentFamilyDaoImpl extends BaseDaoImpl<Long, StudentFamily> implements StudentFamilyDao {

	@Override
	public void saveUpdateStudentFamilyMembers(List<StudentFamily> studentFamilyData) {
		saveOrUpdateBatch(studentFamilyData);
	}

	@Override
	public StudentFamily getStudentFamilyDetailsByGuid(Long studentFamilyGuid) {
		return getById(studentFamilyGuid);
	}

	@Override
	public List<StudentFamily> getAllStudentFamilyMembers(Long accountGuid) {
		Criterion criterionObj = Restrictions.eq("account.guid", accountGuid);
		SortCriteriaData sortCriteria = new SortCriteriaData("fullName", true);
		return getAllByCondition(criterionObj, sortCriteria);
	}

}

package com.denkensol.universaladmission.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.StudentApplicationDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.StudentApplication;

@Repository
public class StudentApplicationDaoImpl extends BaseDaoImpl<Long, StudentApplication> implements StudentApplicationDao {

	@Override
	public void saveStudentApplications(List<StudentApplication> studentApplications) {
		mergeBatch(studentApplications);
	}

	@Override
	public StudentApplication getStudentApplicationsByGuid(Long guid) {
		Criterion criterionObj = Restrictions.eq("account.guid", guid);
		Criterion criterionObjOne = Restrictions.eq("isTransferStudent", "X");
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(criterionObj);
		criterions.add(criterionObjOne);
		return getByConditions(criterions);

	}

	@Override
	public StudentApplication getStudentApplicationBySchool(Long schoolGuid, Long guid) {
		Criterion criterionObj = Restrictions.eq("account.guid", guid);
		Criterion criterionObjOne = Restrictions.eq("school.schoolGuid", schoolGuid);
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(criterionObj);
		criterions.add(criterionObjOne);
		return getByConditions(criterions);

	}

	@Override
	public void saveStudentApplication(StudentApplication studentApplication) {
		saveOrUpdate(studentApplication);
	}

	@Override
	public StudentApplication getStudentApplicationByGuid(Long schoolApplicationGuid) {
		return getById(schoolApplicationGuid);
	}

}

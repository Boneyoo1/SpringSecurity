package com.denkensol.universaladmission.daoimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.StudentEmploymentDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.StudentEmployement;
import com.denkensol.universaladmission.requestresponse.StudentEmployementRequestResponse;

@Repository
public class StudentEmploymentDaoImpl extends BaseDaoImpl<Long, StudentEmployement> implements StudentEmploymentDao {

	@Override
	public void saveUpdateStudentEmployement(List<StudentEmployement> studentEducations) {
		saveOrUpdateBatch(studentEducations);
	}

	@Override
	public StudentEmployement getStudentEmployementByGuid(Long studentEmployementGuid) {
		return getById(studentEmployementGuid);
	}

	@Override
	public List<StudentEmployement> getAllStudentEmployements(Long accountGuid) {
		Criterion criterionObj = Restrictions.eq("account.guid", accountGuid);
		SortCriteriaData sortCriteria = new SortCriteriaData("startDate", true);
		return getAllByCondition(criterionObj, sortCriteria);
	}

	@Override
	public void deleteStudentEmployement(StudentEmployement studentEmployement) {
		delete(studentEmployement);

	}

	@Override
	public void saveUpdateStudentEmployement(StudentEmployement studentEmployement) {
		saveOrUpdate(studentEmployement);

	}

	@Override
	public List<StudentEmployementRequestResponse> getStudentEmployements(Long accountGuid) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("accountGuid", accountGuid);
		return (List<StudentEmployementRequestResponse>) executeNativeNamedQuery("GET_ALL_STUDENT_EMPLOYEMENTS",
				parameters, StudentEmployementRequestResponse.class);
	}

}

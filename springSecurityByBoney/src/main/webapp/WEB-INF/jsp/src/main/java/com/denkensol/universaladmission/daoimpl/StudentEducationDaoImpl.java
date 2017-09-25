package com.denkensol.universaladmission.daoimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.StudentEducationDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.StudentEducation;
import com.denkensol.universaladmission.requestresponse.StudentEducationRequestResponse;

@Repository
public class StudentEducationDaoImpl extends BaseDaoImpl<Long, StudentEducation> implements StudentEducationDao {

	@Override
	public void saveUpdateStudentEducations(List<StudentEducation> studentEducations) {
		saveOrUpdateBatch(studentEducations);
	}

	@Override
	public StudentEducation getStudentEducationByGuid(Long studentEducationGuid) {
		return getById(studentEducationGuid);
	}

	@Override
	public List<StudentEducation> getAllStudentEducations(Long accountGuid) {
		Criterion criterionObj = Restrictions.eq("account.guid", accountGuid);
		SortCriteriaData sortCriteria = new SortCriteriaData("startDate", true);
		return getAllByCondition(criterionObj, sortCriteria);
	}

	@Override
	public void deleteStudentEducation(StudentEducation studentEducation) {
		delete(studentEducation);
	}

	@Override
	public void saveUpdateStudentEducation(StudentEducation studentEducation) {
		saveOrUpdate(studentEducation);
	}

	@Override
	public List<StudentEducationRequestResponse> getStudentEducations(Long accountGuid) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("accountGuid", accountGuid);
		return (List<StudentEducationRequestResponse>) executeNativeNamedQuery("GET_ALL_STUDENT_EDUCATIONS", parameters,
				StudentEducationRequestResponse.class);

	}

}

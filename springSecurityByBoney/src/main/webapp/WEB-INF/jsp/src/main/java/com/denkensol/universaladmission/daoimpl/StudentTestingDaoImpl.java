package com.denkensol.universaladmission.daoimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.StudentTestingDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.StudentTesting;
import com.denkensol.universaladmission.requestresponse.StudentTestingRequestResponse;

@Repository
public class StudentTestingDaoImpl extends BaseDaoImpl<Long, StudentTesting> implements StudentTestingDao {

	@Override
	public void saveUpdateStudentTestsData(List<StudentTesting> studentTestsData) {
		saveOrUpdateBatch(studentTestsData);
	}

	@Override
	public StudentTesting getStudentTestByGuid(Long studentTestGuid) {
		return getById(studentTestGuid);
	}

	@Override
	public List<StudentTesting> getAllStudentTests(Long accountGuid) {
		Criterion criterionObj = Restrictions.eq("account.guid", accountGuid);
		SortCriteriaData sortCriteria = new SortCriteriaData("testType", true);
		return getAllByCondition(criterionObj, sortCriteria);
	}

	@Override
	public void saveUpdateStudentTestData(StudentTesting studentTesting) {
		saveOrUpdate(studentTesting);
	}

	@Override
	public List<StudentTestingRequestResponse> getStudentTestings(Long accountGuid) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("accountGuid", accountGuid);
		return (List<StudentTestingRequestResponse>) executeNativeNamedQuery("GET_ALL_STUDENT_TESTS", parameters,
				StudentTestingRequestResponse.class);
	}

	@Override
	public void deleteStudentTesting(StudentTesting studentTesting) {
		delete(studentTesting);
	}

}

package com.denkensol.universaladmission.daoimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.StudentWritingDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.StudentWriting;
import com.denkensol.universaladmission.requestresponse.StudentWritingRequestResponse;

@Repository
public class StudentWritingDaoImpl extends BaseDaoImpl<Long, StudentWriting> implements StudentWritingDao {

	@Override
	public void saveUpdateStudentWritings(List<StudentWriting> studentWritings) {
		saveOrUpdateBatch(studentWritings);
	}

	@Override
	public StudentWriting getStudentWritingByGuid(Long studentWritingGuid) {
		return getById(studentWritingGuid);
	
	}

	@Override
	public List<StudentWriting> getAllStudentWritings(Long accountGuid) {
		Criterion criterionObj = Restrictions.eq("account.guid", accountGuid);
		SortCriteriaData sortCriteria = new SortCriteriaData("writingType", true);
		return getAllByCondition(criterionObj, sortCriteria);
	}

	@Override
	public void deleteStudentWriting(StudentWriting studentWriting) {
		delete(studentWriting);
	}

	@Override
	public void saveUpdateStudentWriting(StudentWriting studentWriting) {
		saveOrUpdate(studentWriting);
	}

	@Override
	public List<StudentWritingRequestResponse> getStudentWritings(Long guid) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("accountGuid", guid);
		return (List<StudentWritingRequestResponse>) executeNativeNamedQuery("GET_ALL_STUDENT_WRITINGS", parameters, StudentWritingRequestResponse.class);
	
	}

}

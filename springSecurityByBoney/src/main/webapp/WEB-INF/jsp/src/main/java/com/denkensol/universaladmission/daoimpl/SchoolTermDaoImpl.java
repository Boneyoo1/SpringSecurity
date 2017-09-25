package com.denkensol.universaladmission.daoimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.SchoolTermDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.SchoolTerm;
import com.denkensol.universaladmission.requestresponse.SchoolTermResponse;

@Repository
public class SchoolTermDaoImpl extends BaseDaoImpl<Long, SchoolTerm> implements SchoolTermDao {

	@Override
	public void saveUpdateSchoolTerm(SchoolTerm schoolTerm) {
		saveOrUpdate(schoolTerm);

	}

	@Override
	public SchoolTerm getSchoolTermByGuid(Long schoolTermGuid) {
		return getById(schoolTermGuid);
	}

	@Override
	public List<SchoolTerm> getAllSchoolTerms() {
		SortCriteriaData sortCriteriaData = new SortCriteriaData("termYear", true);
		return getAll(sortCriteriaData);
	}

	@Override
	public void saveAllSchoolTerms(List<SchoolTerm> newSchoolTermList) {
		saveBatch(newSchoolTermList);

	}

	@Override
	public void updateAllSchoolTerms(List<SchoolTerm> updatedSchoolTermList) {
		updateBatch(updatedSchoolTermList);

	}

	@Override
	public void deleteSchoolTerms(List<SchoolTerm> existingSchoolTermList) {
		deleteBatch(existingSchoolTermList);

	}

	@Override
	public List<SchoolTermResponse> getSchoolTermsBySchool(Long schoolId) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("schoolGuid", schoolId);

		return (List<SchoolTermResponse>) executeNativeNamedQuery("GET_SCHOOL_TERMS_BY_SCHOOL", parameters,
				SchoolTermResponse.class);
	}

	@Override
	public List<SchoolTermResponse> getAllSchoolsTerms() {
		return (List<SchoolTermResponse>) executeNativeNamedQuery("GET_ALL_SCHOOLS_TERMS", null,
				SchoolTermResponse.class);
	}

	@Override
	public void saveSchoolTerm(SchoolTerm schoolTerm) {
		saveOrUpdate(schoolTerm);
	}

	@Override
	public void deleteSchoolTerm(SchoolTerm schoolTerm) {
		delete(schoolTerm);
	}
}

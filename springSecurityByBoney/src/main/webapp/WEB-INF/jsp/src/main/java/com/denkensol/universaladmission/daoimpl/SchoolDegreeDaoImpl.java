package com.denkensol.universaladmission.daoimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.SchoolDegreeDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.SchoolDegree;
import com.denkensol.universaladmission.requestresponse.SchoolDegreeResponse;

@Repository
public class SchoolDegreeDaoImpl extends BaseDaoImpl<Long, SchoolDegree> implements SchoolDegreeDao {

	@Override
	public void saveUpdateSchoolDegree(SchoolDegree schoolDegree) {
		saveOrUpdate(schoolDegree);
	}

	@Override
	public SchoolDegree getSchoolDegreeByGuid(Long schoolDegreeGuid) {
		return getById(schoolDegreeGuid);
	}

	@Override
	public List<SchoolDegree> getAllSchoolDegrees() {
		SortCriteriaData sortCriteriaData = new SortCriteriaData("degreeOffered", true);
		return getAll(sortCriteriaData);
	}

	@Override
	public void saveAllSchoolDegrees(List<SchoolDegree> newSchoolDegreeList) {
		saveBatch(newSchoolDegreeList);

	}

	@Override
	public void updateAllSchoolDegrees(List<SchoolDegree> updatedSchoolDegreeList) {
		updateBatch(updatedSchoolDegreeList);

	}

	@Override
	public void deleteSchoolDegrees(List<SchoolDegree> existingSchoolDegreeList) {
		deleteBatch(existingSchoolDegreeList);

	}

	@Override
	public List<SchoolDegreeResponse> getSchoolDegreesBySchool(Long schoolGuid) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("schoolGuid", schoolGuid);
		return (List<SchoolDegreeResponse>) executeNativeNamedQuery("GET_SCHOOL_DEGREES_BY_SCHOOL", parameters,
				SchoolDegreeResponse.class);
		
	}

	@Override
	public List<SchoolDegreeResponse> getAllSchoolsDegrees() {
		return (List<SchoolDegreeResponse>) executeNativeNamedQuery("GET_ALL_SCHOOLS_DEGREES", null,
				SchoolDegreeResponse.class);
	}

	@Override
	public void deleteSchoolDegree(SchoolDegree schoolDegree) {
		delete(schoolDegree);
	}

	@Override
	public void saveSchoolDegree(SchoolDegree schoolDegree) {
		saveOrUpdate(schoolDegree);
	}

}

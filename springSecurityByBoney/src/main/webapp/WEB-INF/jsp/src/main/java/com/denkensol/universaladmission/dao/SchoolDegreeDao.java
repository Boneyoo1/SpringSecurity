package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.SchoolDegree;
import com.denkensol.universaladmission.requestresponse.SchoolDegreeResponse;

public interface SchoolDegreeDao {

	public void saveUpdateSchoolDegree(SchoolDegree schoolDegree);

	public SchoolDegree getSchoolDegreeByGuid(Long schoolDegreeGuid);

	public List<SchoolDegree> getAllSchoolDegrees();

	public void saveAllSchoolDegrees(List<SchoolDegree> newSchoolDegreeList);

	public void updateAllSchoolDegrees(List<SchoolDegree> updatedSchoolDegreeList);

	public void deleteSchoolDegrees(List<SchoolDegree> existingSchoolDegreeList);

	public List<SchoolDegreeResponse> getSchoolDegreesBySchool(Long schoolId);

	public List<SchoolDegreeResponse> getAllSchoolsDegrees();

	public void deleteSchoolDegree(SchoolDegree schoolDegree);

	public void saveSchoolDegree(SchoolDegree schoolDegree);
}

package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.School;
import com.denkensol.universaladmission.requestresponse.SchoolSearchResponse;

public interface SchoolDao {

	void saveUpdateSchool(School school);

	School getSchoolByGuid(Long schoolGuid);

	List<School> getAllSchools();

	void saveAllSchools(List<School> newSchoolList);

	void deleteSchools(List<School> existingSchoolList);

	void updateAllSchools(List<School> updatedReligiosList);

	List<SchoolSearchResponse> getSchools();

	School getSchoolBySchoolDomain(String emailDomain);

}

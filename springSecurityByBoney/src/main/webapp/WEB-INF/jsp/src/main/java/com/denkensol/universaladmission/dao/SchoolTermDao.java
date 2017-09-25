package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.SchoolTerm;
import com.denkensol.universaladmission.requestresponse.SchoolTermResponse;

public interface SchoolTermDao {

	public void saveUpdateSchoolTerm(SchoolTerm schoolTerm);

	public SchoolTerm getSchoolTermByGuid(Long schoolTermGuid);

	public List<SchoolTerm> getAllSchoolTerms();

	public void saveAllSchoolTerms(List<SchoolTerm> newSchoolTermList);

	public void updateAllSchoolTerms(List<SchoolTerm> updatedSchoolTermList);

	public void deleteSchoolTerms(List<SchoolTerm> existingSchoolTermList);

	public List<SchoolTermResponse> getSchoolTermsBySchool(Long schoolId);

	public List<SchoolTermResponse> getAllSchoolsTerms();

	public void saveSchoolTerm(SchoolTerm schoolTerm);

	public void deleteSchoolTerm(SchoolTerm schoolTerm);
}

package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.StudentFamily;

public interface StudentFamilyDao {

	void saveUpdateStudentFamilyMembers(List<StudentFamily> studentFamilyData);

	StudentFamily getStudentFamilyDetailsByGuid(Long studentFamilyGuid);

	List<StudentFamily> getAllStudentFamilyMembers(Long accountGuid);

}

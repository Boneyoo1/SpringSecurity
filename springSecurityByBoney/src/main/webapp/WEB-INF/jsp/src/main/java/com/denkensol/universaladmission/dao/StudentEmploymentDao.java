package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.StudentEmployement;
import com.denkensol.universaladmission.requestresponse.StudentEmployementRequestResponse;

public interface StudentEmploymentDao {

	void saveUpdateStudentEmployement(List<StudentEmployement> studentEducations);

	StudentEmployement getStudentEmployementByGuid(Long studentEmployementGuid);

	List<StudentEmployement> getAllStudentEmployements(Long accountGuid);

	void deleteStudentEmployement(StudentEmployement studentEmployement);

	void saveUpdateStudentEmployement(StudentEmployement studentEmployement);

	List<StudentEmployementRequestResponse> getStudentEmployements(Long accountGuid);

	
	
}

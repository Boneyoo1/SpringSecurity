package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.StudentApplication;
import com.denkensol.universaladmission.requestresponse.StudentApplicationResponse;

public interface StudentApplicationDao {

	void saveStudentApplications(List<StudentApplication> studentApplications);
	
	StudentApplication getStudentApplicationsByGuid(Long guid);

	StudentApplication getStudentApplicationByGuid(Long schoolApplicationGuid);
	
	StudentApplication getStudentApplicationBySchool(Long schoolGuid, Long guid);
	

	void saveStudentApplication(StudentApplication studentApplication);


}

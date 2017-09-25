package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.StudentEducation;
import com.denkensol.universaladmission.requestresponse.StudentEducationRequestResponse;

public interface StudentEducationDao {

	void saveUpdateStudentEducations(List<StudentEducation> studentEducations);

	StudentEducation getStudentEducationByGuid(Long studentEducationGuid);

	List<StudentEducation> getAllStudentEducations(Long accountGuid);

	void deleteStudentEducation(StudentEducation studentEducation);

	void saveUpdateStudentEducation(StudentEducation studentEducation);

	List<StudentEducationRequestResponse> getStudentEducations(Long accountGuid);
}

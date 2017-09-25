package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.StudentTesting;
import com.denkensol.universaladmission.requestresponse.StudentTestingRequestResponse;

public interface StudentTestingDao {

	void saveUpdateStudentTestsData(List<StudentTesting> studentTestsData);

	StudentTesting getStudentTestByGuid(Long studentTestGuid);

	List<StudentTesting> getAllStudentTests(Long accountGuid);

	void saveUpdateStudentTestData(StudentTesting studentTesting);

	List<StudentTestingRequestResponse> getStudentTestings(Long accountGuid);

	void deleteStudentTesting(StudentTesting studentTesting);
}

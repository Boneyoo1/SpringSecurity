package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.StudentWriting;
import com.denkensol.universaladmission.requestresponse.StudentWritingRequestResponse;

public interface StudentWritingDao {

	void saveUpdateStudentWritings(List<StudentWriting> studentWritings);

	StudentWriting getStudentWritingByGuid(Long studentWritingGuid);

	List<StudentWriting> getAllStudentWritings(Long accountGuid);

	void deleteStudentWriting(StudentWriting studentWriting);

	void saveUpdateStudentWriting(StudentWriting studentWriting);

	List<StudentWritingRequestResponse> getStudentWritings(Long guid);
}

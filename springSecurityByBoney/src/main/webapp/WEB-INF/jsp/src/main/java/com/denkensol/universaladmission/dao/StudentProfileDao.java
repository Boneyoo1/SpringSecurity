package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.StudentProfile;
import com.denkensol.universaladmission.requestresponse.SchoolApplicantsResponse;
import com.denkensol.universaladmission.requestresponse.SchoolProspectsResponse;
import com.denkensol.universaladmission.requestresponse.StudentProfileRequestResponse;

public interface StudentProfileDao {

	Long saveUpdateStudentProfile(StudentProfile studentProfile);

	StudentProfile getStudentProfileByGuId(Long studentProfileGuid);

	StudentProfile getStudentProfileByAccountGuId(Long accountGuid);

	StudentProfileRequestResponse getStudentProfileByAccountId(Long accountGuid);

	List<SchoolProspectsResponse> getSchoolProspects(Long schoolGuid);

	List<SchoolApplicantsResponse> getSchoolApplicants(Long schoolGuid);

	List<StudentProfileRequestResponse> getAppliedStudentsData(Long schoolGuid);

}

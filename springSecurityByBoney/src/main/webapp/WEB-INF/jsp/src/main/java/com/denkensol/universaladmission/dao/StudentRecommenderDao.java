package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.StudentRecommender;

public interface StudentRecommenderDao {

	void saveSaveStudentRecommender(StudentRecommender studentRecommender);

	void deleteSaveStudentRecommender(StudentRecommender studentRecommender);

	StudentRecommender getStudentRecommenderByGuid(Long studentRecommenderGuid);
	
	List<StudentRecommender> getAllStudentRecommenders(Long accountGuid);
}

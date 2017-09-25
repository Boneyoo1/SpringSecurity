package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.SchoolQuestion;

public interface SchoolQuestionDao {

	SchoolQuestion getSchoolQuestionById(Long questionId);

	void saveQuestion(SchoolQuestion schoolQuestion);

	void deleteSchoolQuestion(SchoolQuestion schoolQuestion);
	
List<SchoolQuestion> getAllSchoolQuestions();
	
	public void saveAllSchoolQuestions(List<SchoolQuestion> newSchoolQuestionList);
	

}

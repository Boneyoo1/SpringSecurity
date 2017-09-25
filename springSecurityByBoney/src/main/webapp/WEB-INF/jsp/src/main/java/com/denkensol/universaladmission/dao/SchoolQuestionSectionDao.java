package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.SchoolQuestionSection;

public interface SchoolQuestionSectionDao {

	List<SchoolQuestionSection> getSchoolSectionQuestions(Long schoolGuid);

	SchoolQuestionSection getSchoolSectionById(Long schoolQuestionSectionGuid);

	void saveSchoolQuestionSection(SchoolQuestionSection schoolQuestionSection);

	void deleteQuestionSection(SchoolQuestionSection schoolQuestionSection);
	
	List<SchoolQuestionSection> getAllSchoolQuestionSections();
}

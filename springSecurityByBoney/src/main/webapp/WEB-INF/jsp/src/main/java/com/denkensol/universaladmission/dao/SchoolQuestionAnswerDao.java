package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.SchoolQuestionAnswer;

public interface SchoolQuestionAnswerDao {

	SchoolQuestionAnswer getSchoolQuestionAnswerById(Long schoolQuestionAnswerGuid);

	void saveAnswersList(List<SchoolQuestionAnswer> answersList);

	SchoolQuestionAnswer getSchoolQuestionAnswerByQuestionIdAndStudentId(Long schoolQuestionGuid, Long guid);

}

package com.denkensol.universaladmission.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.SchoolQuestionDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.SchoolQuestion;

@Repository
public class SchoolQuestionDaoImpl extends BaseDaoImpl<Long, SchoolQuestion> implements SchoolQuestionDao {

	@Override
	public SchoolQuestion getSchoolQuestionById(Long questionId) {
		return getById(questionId);
	}

	@Override
	public void saveQuestion(SchoolQuestion schoolQuestion) {
		saveOrUpdate(schoolQuestion);

	}

	@Override
	public void deleteSchoolQuestion(SchoolQuestion schoolQuestion) {
		delete(schoolQuestion);
	}
	
	@Override
	public List<SchoolQuestion> getAllSchoolQuestions() {

		SortCriteriaData sortCriteriaData = new SortCriteriaData("questionTitle", true);
		return getAll(sortCriteriaData);
	}
	
	@Override
	public void saveAllSchoolQuestions(List newSchoolQuestionList) {
				saveBatch(newSchoolQuestionList);

	}

}

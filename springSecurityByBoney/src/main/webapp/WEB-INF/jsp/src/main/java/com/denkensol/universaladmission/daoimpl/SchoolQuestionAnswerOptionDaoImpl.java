package com.denkensol.universaladmission.daoimpl;

import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.SchoolQuestionAnswerOptionDao;
import com.denkensol.universaladmission.entity.SchoolQuestionAnswerOption;

@Repository
public class SchoolQuestionAnswerOptionDaoImpl extends BaseDaoImpl<Long, SchoolQuestionAnswerOption>
		implements SchoolQuestionAnswerOptionDao {

	@Override
	public SchoolQuestionAnswerOption getSchoolQuestionAnswerOptionByGuid(Long schoolQuestionAnswerOptionGuid) {
		return getById(schoolQuestionAnswerOptionGuid);
	}

}

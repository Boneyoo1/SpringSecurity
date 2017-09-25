package com.denkensol.universaladmission.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.SchoolQuestionAnswerDao;
import com.denkensol.universaladmission.entity.SchoolQuestionAnswer;

@Repository
public class SchoolQuestionAnswerDaoImpl extends BaseDaoImpl<Long, SchoolQuestionAnswer>
		implements SchoolQuestionAnswerDao {

	@Override
	public SchoolQuestionAnswer getSchoolQuestionAnswerById(Long schoolQuestionAnswerGuid) {
		return getById(schoolQuestionAnswerGuid);
	}

	@Override
	public void saveAnswersList(List<SchoolQuestionAnswer> answersList) {
		mergeBatch(answersList);
	}

	@Override
	public SchoolQuestionAnswer getSchoolQuestionAnswerByQuestionIdAndStudentId(Long schoolQuestionGuid, Long guid) {

		Criterion criterionObj = Restrictions.eq("schoolQuestion.schoolQuestionGuid", schoolQuestionGuid);
		Criterion criterionObjOne = Restrictions.eq("account.guid", guid);
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(criterionObj);
		criterions.add(criterionObjOne);
		return getByConditions(criterions);

	}

}

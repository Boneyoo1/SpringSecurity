package com.denkensol.universaladmission.daoimpl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.StudentRecommenderDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.StudentRecommender;

@Repository
public class StudentRecommenderDaoImpl extends BaseDaoImpl<Long, StudentRecommender> implements StudentRecommenderDao {

	@Override
	public void saveSaveStudentRecommender(StudentRecommender studentRecommender) {
		saveOrUpdate(studentRecommender);
	}

	@Override
	public void deleteSaveStudentRecommender(StudentRecommender studentRecommender) {
		delete(studentRecommender);
	}

	@Override
	public StudentRecommender getStudentRecommenderByGuid(Long studentRecommenderGuid) {
		return getById(studentRecommenderGuid);
	}

	@Override
	public List<StudentRecommender> getAllStudentRecommenders(Long accountGuid) {
		Criterion criterionObj = Restrictions.eq("account.guid", accountGuid);
		SortCriteriaData sortCriteria = new SortCriteriaData("recommenderName", true);
		return getAllByCondition(criterionObj, sortCriteria);
	}

}

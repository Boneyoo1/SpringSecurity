package com.denkensol.universaladmission.daoimpl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.SchoolQuestionSectionDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.SchoolQuestionSection;

@Repository
public class SchoolQuestionSectionDaoImpl extends BaseDaoImpl<Long, SchoolQuestionSection>
		implements SchoolQuestionSectionDao {

	@Override
	public List<SchoolQuestionSection> getSchoolSectionQuestions(Long schoolGuid) {

		Criterion criterionObj = Restrictions.eq("school.schoolGuid", schoolGuid);
		SortCriteriaData sortCriteriaData = new SortCriteriaData("seqNo", true);
		return getAllByCondition(criterionObj, sortCriteriaData);

	}

	@Override
	public SchoolQuestionSection getSchoolSectionById(Long schoolQuestionSectionGuid) {
		return getById(schoolQuestionSectionGuid);
	}

	@Override
	public void saveSchoolQuestionSection(SchoolQuestionSection schoolQuestionSection) {
		saveOrUpdate(schoolQuestionSection);
	}

	@Override
	public void deleteQuestionSection(SchoolQuestionSection schoolQuestionSection) {
		delete(schoolQuestionSection);
	}

	@Override
	public List<SchoolQuestionSection> getAllSchoolQuestionSections() {
		SortCriteriaData sortCriteriaData = new SortCriteriaData("sectionName", true);
		return getAll(sortCriteriaData);
	}
}

package com.denkensol.universaladmission.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.SchoolDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.School;
import com.denkensol.universaladmission.requestresponse.SchoolSearchResponse;

@Repository
public class SchoolDaoImpl extends BaseDaoImpl<Long, School> implements SchoolDao {

	@Override
	public void saveUpdateSchool(School school) {
		saveOrUpdate(school);
	}

	@Override
	public School getSchoolByGuid(Long schoolGuid) {
		return getById(schoolGuid);
	}

	@Override
	public List<School> getAllSchools() {
		SortCriteriaData sortCriteriaData = new SortCriteriaData("schoolName", true);
		return getAll(sortCriteriaData);
	}

	@Override
	public void saveAllSchools(List<School> newSchoolList) {
		saveBatch(newSchoolList);

	}

	@Override
	public void deleteSchools(List<School> existingSchoolList) {
		deleteBatch(existingSchoolList);

	}

	@Override
	public void updateAllSchools(List<School> updatedReligiosList) {
		updateBatch(updatedReligiosList);

	}

	@Override
	public List<SchoolSearchResponse> getSchools() {
		return (List<SchoolSearchResponse>) executeNativeNamedQuery("GET_ALL_SCHOOLS", null,
				SchoolSearchResponse.class);

	}

	@Override
	public School getSchoolBySchoolDomain(String schoolDomain) {
		Criterion criterionObjOne = Restrictions.eq("emailDomainOne", schoolDomain);
		Criterion criterionObjTwo = Restrictions.or(criterionObjOne,Restrictions.eq("emailDomainTwo", schoolDomain));
		Criterion criterionObjThree = Restrictions.or(criterionObjTwo,Restrictions.eq("emailDomainThree", schoolDomain));
		return getByCondition(criterionObjThree);
	}
}

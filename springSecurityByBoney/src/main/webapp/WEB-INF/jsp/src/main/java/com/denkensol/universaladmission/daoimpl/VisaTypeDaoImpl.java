package com.denkensol.universaladmission.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.VisaTypeDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.VisaType;
import com.denkensol.universaladmission.requestresponse.VisaTypeRequestResponse;

@Repository
public class VisaTypeDaoImpl extends BaseDaoImpl<Long, VisaType> implements VisaTypeDao {

	@Override
	public void saveAllVisaTypes(List<VisaType> visaTypes) {
		saveBatch(visaTypes);
	}

	@Override
	public void updateAllVisaTypes(List<VisaType> visaTypes) {
		updateBatch(visaTypes);
	}

	@Override
	public void deleteVisaTypes(List<VisaType> visaTypes) {
		deleteBatch(visaTypes);
	}

	@Override
	public List<VisaType> getAllVisaTypes() {
		SortCriteriaData sortCriteria = new SortCriteriaData("visaTypeName", true);
		return getAll(sortCriteria);
	}

	@Override
	public VisaType getVisaTypeById(Long visaTypeId) {
		return getById(visaTypeId);
	}

	@Override
	public List<VisaTypeRequestResponse> processAllVisaTypesList() {
		return (List<VisaTypeRequestResponse>) executeNativeNamedQuery("GET_ALL_VISA_TYPES", null,
				VisaTypeRequestResponse.class);
	}

}

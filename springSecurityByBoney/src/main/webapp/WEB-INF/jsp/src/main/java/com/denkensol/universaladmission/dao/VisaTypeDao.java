package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.VisaType;
import com.denkensol.universaladmission.requestresponse.VisaTypeRequestResponse;

public interface VisaTypeDao {

	void saveAllVisaTypes(List<VisaType> visaTypes);

	void updateAllVisaTypes(List<VisaType> visaTypes);

	void deleteVisaTypes(List<VisaType> visaTypes);

	List<VisaType> getAllVisaTypes();

	VisaType getVisaTypeById(Long visaTypeId);

	List<VisaTypeRequestResponse> processAllVisaTypesList();
}

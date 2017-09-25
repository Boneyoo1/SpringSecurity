package com.denkensol.universaladmission.service;

import java.util.List;

import com.denkensol.universaladmission.entity.VisaType;
import com.denkensol.universaladmission.requestresponse.VisaTypeRequestResponse;

public interface VisaTypeService {

	void saveAllVisaTypes(List<VisaType> visaTypes);

	void updateAllVisaTypes(List<VisaType> visaTypes);

	void deleteVisaTypes(List<VisaType> visaTypes);

	List<VisaType> getAllVisaTypes();

	VisaType getCountryById(Long visaTypeId);

	List<VisaTypeRequestResponse> processAllVisaTypesList();
}

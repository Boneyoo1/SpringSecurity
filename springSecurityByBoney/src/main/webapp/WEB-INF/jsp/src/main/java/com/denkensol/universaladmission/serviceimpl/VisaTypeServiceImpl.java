package com.denkensol.universaladmission.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.denkensol.universaladmission.dao.VisaTypeDao;
import com.denkensol.universaladmission.entity.VisaType;
import com.denkensol.universaladmission.requestresponse.VisaTypeRequestResponse;
import com.denkensol.universaladmission.service.VisaTypeService;

@Service
public class VisaTypeServiceImpl implements VisaTypeService {

	@Autowired
	VisaTypeDao visaTypeDao;

	@Override
	public void saveAllVisaTypes(List<VisaType> visaTypes) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAllVisaTypes(List<VisaType> visaTypes) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteVisaTypes(List<VisaType> visaTypes) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional(readOnly = true)
	public List<VisaType> getAllVisaTypes() {
		return visaTypeDao.getAllVisaTypes();
	}

	@Override
	public VisaType getCountryById(Long visaTypeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<VisaTypeRequestResponse> processAllVisaTypesList() {
		return visaTypeDao.processAllVisaTypesList();
	}

}

package com.denkensol.universaladmission.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.denkensol.universaladmission.dao.DegreesDao;
import com.denkensol.universaladmission.requestresponse.DegreesRequestResponse;
import com.denkensol.universaladmission.service.DegreeService;


@Service
public class DegreeServiceImpl implements DegreeService {
	@Autowired
	DegreesDao degreesDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<DegreesRequestResponse> getAllDegreesList() {
		return degreesDao.getAllDegreesList();
	}

}

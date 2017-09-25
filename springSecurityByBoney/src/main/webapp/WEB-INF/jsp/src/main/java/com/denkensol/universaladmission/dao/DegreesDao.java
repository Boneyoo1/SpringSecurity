package com.denkensol.universaladmission.dao;

import java.util.List;


import com.denkensol.universaladmission.entity.Degrees;
import com.denkensol.universaladmission.requestresponse.DegreesRequestResponse;

public interface DegreesDao {

	List<Degrees> getAllDegrees();
	
	void saveAllDegrees(List<Degrees> degrees);
	
	void updateAllDegrees(List<Degrees> degrees);
	
	void deleteDegrees(List<Degrees> degrees);
	
	List<DegreesRequestResponse> getAllDegreesList();
}

package com.denkensol.universaladmission.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.DegreesDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.Degrees;
import com.denkensol.universaladmission.requestresponse.DegreesRequestResponse;
@Repository
public class DegreesDaoImpl extends BaseDaoImpl<Long, Degrees> implements DegreesDao {
	@Override
	public List<Degrees> getAllDegrees() {
		SortCriteriaData sortCriteria = new SortCriteriaData("degreeName", true);
		return getAll(sortCriteria);
	}
	
	@Override
	public void saveAllDegrees(List<Degrees> degrees) {
		saveBatch(degrees);
	}
	
	@Override
	public void updateAllDegrees(List<Degrees> degrees) {
		updateBatch(degrees);
	}
	
	@Override
	public void deleteDegrees(List<Degrees> degrees) {
		deleteBatch(degrees);
	}

	@Override
	public List<DegreesRequestResponse> getAllDegreesList() {
		return (List<DegreesRequestResponse>) executeNativeNamedQuery("GET_ALL_DEGREES", null,
				DegreesRequestResponse.class);

	}

}

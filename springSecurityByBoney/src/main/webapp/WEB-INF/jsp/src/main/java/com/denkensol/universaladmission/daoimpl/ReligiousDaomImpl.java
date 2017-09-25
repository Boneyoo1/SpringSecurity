package com.denkensol.universaladmission.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.ReligiousDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.Religious;
import com.denkensol.universaladmission.requestresponse.ReligoesRequestResponse;

@Repository
public class ReligiousDaomImpl extends BaseDaoImpl<Long, Religious> implements ReligiousDao {

	@Override
	public void saveAllRelegious(List<Religious> religious) {
		saveBatch(religious);

	}

	@Override
	public void updateAllRelegious(List<Religious> religious) {
		updateBatch(religious);
	}

	@Override
	public void deleteRelegious(List<Religious> religious) {
		deleteBatch(religious);
	}

	@Override
	public List<Religious> getAllRelegious() {
		SortCriteriaData sortCriteria = new SortCriteriaData("religiousName", true);
		return getAll(sortCriteria);
	}

	@Override
	public Religious getRelegiousById(Long relegiousId) {
		return getById(relegiousId);
	}

	@Override
	public List<ReligoesRequestResponse> getAllReliegionsList() {
		return (List<ReligoesRequestResponse>) executeNativeNamedQuery("GET_ALL_RELEGIOUS", null,
				ReligoesRequestResponse.class);
	}

}

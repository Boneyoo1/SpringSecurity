package com.denkensol.universaladmission.dao;

import java.util.List;

import com.denkensol.universaladmission.entity.Religious;
import com.denkensol.universaladmission.requestresponse.ReligoesRequestResponse;

public interface ReligiousDao {

	void saveAllRelegious(List<Religious> religious);

	void updateAllRelegious(List<Religious> religious);

	void deleteRelegious(List<Religious> religious);

	List<Religious> getAllRelegious();

	Religious getRelegiousById(Long relegiousId);

	List<ReligoesRequestResponse> getAllReliegionsList();

}

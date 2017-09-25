package com.denkensol.universaladmission.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.denkensol.universaladmission.dao.ReligiousDao;
import com.denkensol.universaladmission.entity.Religious;
import com.denkensol.universaladmission.requestresponse.ReligoesRequestResponse;
import com.denkensol.universaladmission.service.ReligiousService;

@Service
public class ReligiousServiceImpl implements ReligiousService {

	@Autowired
	ReligiousDao religiousDao;

	@Override
	public void saveAllRelegious(List<Religious> religious) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAllRelegious(List<Religious> religious) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteRelegious(List<Religious> religious) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional(readOnly = true)
	public List<Religious> getAllRelegious() {
		return religiousDao.getAllRelegious();
	}

	@Override
	public Religious getRelegiousById(Long relegiousId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<ReligoesRequestResponse> getAllReliegionsList() {
		return religiousDao.getAllReliegionsList();
	}

}

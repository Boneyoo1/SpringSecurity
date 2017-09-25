package com.denkensol.universaladmission.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.denkensol.universaladmission.dao.LanguageDao;
import com.denkensol.universaladmission.entity.Language;
import com.denkensol.universaladmission.requestresponse.LanguageRequestResponse;
import com.denkensol.universaladmission.service.LanguageService;

@Service
public class LanguageServiceImpl implements LanguageService {

	@Autowired
	LanguageDao languageDao;

	@Override
	public void saveAllLanguages(List<Language> languages) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAllLanguages(List<Language> languages) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteLanguages(List<Language> languages) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional(readOnly = true)
	public List<Language> getAllLanguages() {
		return languageDao.getAllLanguages();
	}

	@Override
	public Language getLanguageById(Long languageId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<LanguageRequestResponse> getAllLanguagesList() {
		return languageDao.getAllLanguagesList();
	}

}

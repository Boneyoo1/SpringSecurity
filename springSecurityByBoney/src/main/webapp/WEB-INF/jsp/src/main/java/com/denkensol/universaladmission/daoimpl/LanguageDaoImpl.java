package com.denkensol.universaladmission.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.LanguageDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.Language;
import com.denkensol.universaladmission.requestresponse.LanguageRequestResponse;

@Repository
public class LanguageDaoImpl extends BaseDaoImpl<Long, Language> implements LanguageDao {

	@Override
	public void saveAllLanguages(List<Language> languages) {
		saveBatch(languages);
	}

	@Override
	public void updateAllLanguages(List<Language> languages) {
		updateBatch(languages);
	}

	@Override
	public void deleteLanguages(List<Language> languages) {
		deleteBatch(languages);
	}

	@Override
	public List<Language> getAllLanguages() {
		SortCriteriaData sortCriteria = new SortCriteriaData("languageName", true);
		return getAll(sortCriteria);
	}

	@Override
	public Language getLanguageById(Long languageId) {
		return getById(languageId);
	}

	@Override
	public List<LanguageRequestResponse> getAllLanguagesList() {
		return (List<LanguageRequestResponse>) executeNativeNamedQuery("GET_ALL_LANGUAGES", null,
				LanguageRequestResponse.class);
	}

}

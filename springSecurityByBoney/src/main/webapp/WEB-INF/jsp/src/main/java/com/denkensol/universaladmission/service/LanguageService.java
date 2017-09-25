package com.denkensol.universaladmission.service;

import java.util.List;

import com.denkensol.universaladmission.entity.Language;
import com.denkensol.universaladmission.requestresponse.LanguageRequestResponse;

public interface LanguageService {

	void saveAllLanguages(List<Language> languages);

	void updateAllLanguages(List<Language> languages);

	void deleteLanguages(List<Language> languages);

	List<Language> getAllLanguages();

	Language getLanguageById(Long languageId);

	List<LanguageRequestResponse> getAllLanguagesList();
}

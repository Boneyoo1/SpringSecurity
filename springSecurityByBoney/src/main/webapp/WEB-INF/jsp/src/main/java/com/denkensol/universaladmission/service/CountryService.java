package com.denkensol.universaladmission.service;

import java.util.List;

import com.denkensol.universaladmission.entity.Country;
import com.denkensol.universaladmission.requestresponse.CountryRequestResponse;

public interface CountryService {

	void saveAllCountries(List<Country> countries);

	void updateAllCountries(List<Country> countries);

	void deleteCountries(List<Country> countries);

	List<Country> getAllCountries();

	Country getCountryById(Long countryId);

	List<CountryRequestResponse> getAllCountriesList();
}

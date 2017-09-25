package com.denkensol.universaladmission.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.denkensol.universaladmission.dao.CountryDao;
import com.denkensol.universaladmission.entity.Country;
import com.denkensol.universaladmission.requestresponse.CountryRequestResponse;
import com.denkensol.universaladmission.service.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

	@Autowired
	CountryDao countryDao;

	@Override
	public void saveAllCountries(List<Country> countries) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAllCountries(List<Country> countries) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteCountries(List<Country> countries) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional(readOnly = true)
	public List<Country> getAllCountries() {
		return countryDao.getAllCountries();
	}

	@Override
	public Country getCountryById(Long countryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CountryRequestResponse> getAllCountriesList() {
		return countryDao.getAllCountriesList();
	}

}

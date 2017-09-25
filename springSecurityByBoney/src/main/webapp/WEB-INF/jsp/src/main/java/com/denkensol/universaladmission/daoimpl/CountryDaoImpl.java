package com.denkensol.universaladmission.daoimpl;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.CountryDao;
import com.denkensol.universaladmission.dto.SortCriteriaData;
import com.denkensol.universaladmission.entity.Country;
import com.denkensol.universaladmission.requestresponse.CountryRequestResponse;

@Repository
public class CountryDaoImpl extends BaseDaoImpl<Long, Country> implements CountryDao {

	@Override
	public void saveAllCountries(List<Country> countries) {
		saveBatch(countries);
	}

	@Override
	public void updateAllCountries(List<Country> countries) {
		updateBatch(countries);
	}

	@Override
	public void deleteCountries(List<Country> countries) {
		deleteBatch(countries);
	}

	@Override
	public List<Country> getAllCountries() {
		SortCriteriaData sortCriteria = new SortCriteriaData("countryName", true);
		return getAll(sortCriteria);
	}

	@Override
	public Country getCountryById(Long countryId) {
		return getById(countryId);
	}

	@Override
	public List<CountryRequestResponse> getAllCountriesList() {
		return (List<CountryRequestResponse>) executeNativeNamedQuery("GET_ALL_COUNTRIES", null,
				CountryRequestResponse.class);

	}

	@Override
	public Country getCountryByName(String countryName) {
		Criterion criterionObj = Restrictions.eq("countryName", countryName);
		return getByCondition(criterionObj);
	}

}

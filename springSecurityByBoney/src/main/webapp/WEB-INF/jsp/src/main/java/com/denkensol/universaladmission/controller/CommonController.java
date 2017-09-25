package com.denkensol.universaladmission.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.denkensol.universaladmission.requestresponse.CountryRequestResponse;
import com.denkensol.universaladmission.requestresponse.DegreesRequestResponse;
import com.denkensol.universaladmission.requestresponse.LanguageRequestResponse;
import com.denkensol.universaladmission.requestresponse.ReligoesRequestResponse;
import com.denkensol.universaladmission.requestresponse.VisaTypeRequestResponse;
import com.denkensol.universaladmission.service.CountryService;
import com.denkensol.universaladmission.service.DegreeService;
import com.denkensol.universaladmission.service.LanguageService;
import com.denkensol.universaladmission.service.ReligiousService;
import com.denkensol.universaladmission.service.VisaTypeService;

@Controller
@RequestMapping("/common")
public class CommonController {

	@Autowired
	LanguageService languageService;

	@Autowired
	CountryService countryService;

	@Autowired
	DegreeService degreeServie;
	
	@Autowired
	ReligiousService religiousService;

	@Autowired
	VisaTypeService visaTypeService;

	@GetMapping(value = "/getAllCountries")
	public ResponseEntity<List<CountryRequestResponse>> getAllCountries() {
		List<CountryRequestResponse> countriesList = countryService.getAllCountriesList();
		return new ResponseEntity<List<CountryRequestResponse>>(countriesList, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getAllLanguages")
	public ResponseEntity<List<LanguageRequestResponse>> getAllLanguages() {
		List<LanguageRequestResponse> languagesList = languageService.getAllLanguagesList();
		return new ResponseEntity<List<LanguageRequestResponse>>(languagesList, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getAllReliegions")
	public ResponseEntity<List<ReligoesRequestResponse>> getAllReliegions() {
		List<ReligoesRequestResponse> religiouesList = religiousService.getAllReliegionsList();
		return new ResponseEntity<List<ReligoesRequestResponse>>(religiouesList, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getAllVisaTypes")
	public ResponseEntity<List<VisaTypeRequestResponse>> processAllVisaTypes() {
		List<VisaTypeRequestResponse> visaTypesList = visaTypeService.processAllVisaTypesList();
		return new ResponseEntity<List<VisaTypeRequestResponse>>(visaTypesList, HttpStatus.ACCEPTED);
	}
	
	@GetMapping(value = "/getAllDegrees")
	public ResponseEntity<List<DegreesRequestResponse>> getAllDegrees() {
		List<DegreesRequestResponse> degreesList = degreeServie.getAllDegreesList();
		return new ResponseEntity<List<DegreesRequestResponse>>(degreesList, HttpStatus.ACCEPTED);
	}
}

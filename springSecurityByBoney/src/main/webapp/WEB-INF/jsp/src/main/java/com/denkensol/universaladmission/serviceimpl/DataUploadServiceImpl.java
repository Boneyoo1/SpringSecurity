package com.denkensol.universaladmission.serviceimpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amazonaws.util.IOUtils;
import com.denkensol.universaladmission.dao.CountryDao;
import com.denkensol.universaladmission.dao.DegreesDao;
import com.denkensol.universaladmission.dao.LanguageDao;
import com.denkensol.universaladmission.dao.ReligiousDao;
import com.denkensol.universaladmission.dao.SchoolDao;
import com.denkensol.universaladmission.dao.SchoolDegreeDao;
import com.denkensol.universaladmission.dao.SchoolDocumentDao;
import com.denkensol.universaladmission.dao.SchoolQuestionDao;
import com.denkensol.universaladmission.dao.SchoolQuestionSectionDao;
import com.denkensol.universaladmission.dao.SchoolTermDao;
import com.denkensol.universaladmission.dao.VisaTypeDao;
import com.denkensol.universaladmission.entity.Country;
import com.denkensol.universaladmission.entity.Degrees;
import com.denkensol.universaladmission.entity.Language;
import com.denkensol.universaladmission.entity.Religious;
import com.denkensol.universaladmission.entity.School;
import com.denkensol.universaladmission.entity.SchoolDegree;
import com.denkensol.universaladmission.entity.SchoolDocument;
import com.denkensol.universaladmission.entity.SchoolQuestion;
import com.denkensol.universaladmission.entity.SchoolQuestionAnswerOption;
import com.denkensol.universaladmission.entity.SchoolQuestionSection;
import com.denkensol.universaladmission.entity.SchoolTerm;
import com.denkensol.universaladmission.entity.VisaType;
import com.denkensol.universaladmission.service.DataUploadService;
import com.denkensol.universaladmission.template.AmazonS3Template;
import com.denkensol.universaladmission.util.CommonUtil;
import com.mysql.jdbc.Constants;

@Service
public class DataUploadServiceImpl implements DataUploadService {

	@Autowired
	ReligiousDao religiousDao;

	@Autowired
	CountryDao countryDao;

	@Autowired
	LanguageDao languageDao;

	@Autowired
	VisaTypeDao visaTypeDao;

	@Autowired
	SchoolDao schoolDao;

	@Autowired
	SchoolDegreeDao schoolDegreeDao;

	@Autowired
	SchoolTermDao schoolTermDao;

	@Autowired
	private Environment env;

	@Autowired
	SchoolQuestionSectionDao schoolQuestionSectionDao;

	@Autowired
	SchoolQuestionDao schoolQuestionDao;

	@Autowired
	SchoolDocumentDao schoolDocumentDao;

	@Autowired
	AmazonS3Template amazonS3Template;
	
	@Autowired
	DegreesDao degreesDao;

	@Override
	@Transactional
	public void uploadReligiouns() {
		List<Religious> existingReligiousList = religiousDao.getAllRelegious();
		List<Religious> excelReligiousList = new ArrayList<Religious>();
		List<Religious> newReligiousList = new ArrayList<Religious>();
		String filePath = env.getProperty("data.download-path") + env.getProperty("religious.data-file-name");
		Workbook myWorkBook = CommonUtil.getWorkBookFromFile(filePath);
		Sheet sheet = myWorkBook.getSheetAt(0);
		int i = 0;
		for (Row eachRow : sheet) {
			if (i < 1) {
				i++;
				continue;
			}

			String religiousName = eachRow.getCell(0).getStringCellValue().trim();
			if (religiousName != null) {
				Religious religiousObj = new Religious();
				religiousObj.setReligiousName(religiousName);

				if (!newReligiousList.contains(religiousObj) && !existingReligiousList.contains(religiousObj)) {
					newReligiousList.add(religiousObj);
				}
				if (!excelReligiousList.contains(religiousObj)) {
					excelReligiousList.add(religiousObj);
				}
			}
		}
		if (newReligiousList != null && !newReligiousList.isEmpty()) {
			religiousDao.saveAllRelegious(newReligiousList);
		}

		List<Religious> updatedReligiosList = findUpdatedReligiousList(existingReligiousList, excelReligiousList);
		if (updatedReligiosList != null && !updatedReligiosList.isEmpty()) {
			religiousDao.updateAllRelegious(updatedReligiosList);
		}
		if (existingReligiousList != null && !existingReligiousList.isEmpty()) {
			existingReligiousList.removeAll(excelReligiousList);
			religiousDao.deleteRelegious(existingReligiousList);
		}
	}

	private List<Religious> findUpdatedReligiousList(List<Religious> existingReligiousList,
			List<Religious> excelReligiousList) {

		List<Religious> updatedReligiosList = new ArrayList<Religious>();
		Map<String, List<Religious>> existingReligiosMap = existingReligiousList.stream()
				.collect(Collectors.groupingBy(religiousObj -> religiousObj.getReligiousName()));
		if (excelReligiousList != null && !excelReligiousList.isEmpty() && existingReligiousList != null
				&& !existingReligiousList.isEmpty()) {
			for (Religious eachReligious : excelReligiousList) {
				if (existingReligiousList.contains(eachReligious) && !updatedReligiosList.contains(eachReligious)) {
					eachReligious.setGuid(existingReligiosMap.get(eachReligious.getReligiousName()).get(0).getGuid());
					updatedReligiosList.add(eachReligious);
				}
			}
		}
		return updatedReligiosList;
	}

	@Override
	@Transactional
	public void uploadCountries() {

		List<Country> existingCountryList = countryDao.getAllCountries();
		List<Country> excelCountryList = new ArrayList<Country>();
		List<Country> newCountryList = new ArrayList<Country>();
		String filePath = env.getProperty("data.download-path") + env.getProperty("countries.data-file-name");
		Workbook myWorkBook = CommonUtil.getWorkBookFromFile(filePath);
		Sheet sheet = myWorkBook.getSheetAt(0);
		int i = 0;
		for (Row eachRow : sheet) {
			if (i < 1) {
				i++;
				continue;
			}

			String countryName = eachRow.getCell(0).getStringCellValue().trim();
			if (countryName != null) {
				Country countryObj = new Country();
				countryObj.setCountryName(countryName);

				if (!newCountryList.contains(countryObj) && !existingCountryList.contains(countryObj)) {
					newCountryList.add(countryObj);
				}
				if (!excelCountryList.contains(countryObj)) {
					excelCountryList.add(countryObj);
				}
			}
		}
		if (newCountryList != null && !newCountryList.isEmpty()) {
			countryDao.saveAllCountries(newCountryList);
		}

		List<Country> updatedReligiosList = findUpdatedCountryList(existingCountryList, excelCountryList);
		if (updatedReligiosList != null && !updatedReligiosList.isEmpty()) {
			countryDao.updateAllCountries(updatedReligiosList);
		}
		if (existingCountryList != null && !existingCountryList.isEmpty()) {
			existingCountryList.removeAll(excelCountryList);
			countryDao.deleteCountries(existingCountryList);
		}

	}

	private List<Country> findUpdatedCountryList(List<Country> existingCountryList, List<Country> excelCountryList) {

		List<Country> updatedCountryList = new ArrayList<Country>();
		Map<String, List<Country>> existingCountryMap = existingCountryList.stream()
				.collect(Collectors.groupingBy(countryObj -> countryObj.getCountryName()));
		if (excelCountryList != null && !excelCountryList.isEmpty() && existingCountryList != null
				&& !existingCountryList.isEmpty()) {
			for (Country eachCountry : excelCountryList) {
				if (existingCountryList.contains(eachCountry) && !updatedCountryList.contains(eachCountry)) {
					eachCountry.setGuid(existingCountryMap.get(eachCountry.getCountryName()).get(0).getGuid());
					updatedCountryList.add(eachCountry);
				}
			}
		}
		return updatedCountryList;
	}

	@Override
	@Transactional
	public void uploadLanguages() {

		List<Language> existingLanguageList = languageDao.getAllLanguages();
		List<Language> excelLanguageList = new ArrayList<Language>();
		List<Language> newLanguageList = new ArrayList<Language>();
		String filePath = env.getProperty("data.download-path") + env.getProperty("languages.data-file-name");
		Workbook myWorkBook = CommonUtil.getWorkBookFromFile(filePath);
		Sheet sheet = myWorkBook.getSheetAt(0);
		int i = 0;
		for (Row eachRow : sheet) {
			if (i < 1) {
				i++;
				continue;
			}

			String languageName = eachRow.getCell(0).getStringCellValue().trim();
			if (languageName != null) {
				Language languageObj = new Language();
				languageObj.setLanguageName(languageName);

				if (!newLanguageList.contains(languageObj) && !existingLanguageList.contains(languageObj)) {
					newLanguageList.add(languageObj);
				}
				if (!excelLanguageList.contains(languageObj)) {
					excelLanguageList.add(languageObj);
				}
			}
		}
		if (newLanguageList != null && !newLanguageList.isEmpty()) {
			languageDao.saveAllLanguages(newLanguageList);
		}

		List<Language> updatedReligiosList = findUpdatedLanguageList(existingLanguageList, excelLanguageList);
		if (updatedReligiosList != null && !updatedReligiosList.isEmpty()) {
			languageDao.updateAllLanguages(updatedReligiosList);
		}
		if (existingLanguageList != null && !existingLanguageList.isEmpty()) {
			existingLanguageList.removeAll(excelLanguageList);
			languageDao.deleteLanguages(existingLanguageList);
		}
	}

	private List<Language> findUpdatedLanguageList(List<Language> existingLanguageList,
			List<Language> excelLanguageList) {
		List<Language> updatedLanguageList = new ArrayList<Language>();
		Map<String, List<Language>> existingLanguageMap = existingLanguageList.stream()
				.collect(Collectors.groupingBy(languageObj -> languageObj.getLanguageName()));
		if (excelLanguageList != null && !excelLanguageList.isEmpty() && existingLanguageList != null
				&& !existingLanguageList.isEmpty()) {
			for (Language eachLanguage : excelLanguageList) {
				if (existingLanguageList.contains(eachLanguage) && !updatedLanguageList.contains(eachLanguage)) {
					eachLanguage.setGuid(existingLanguageMap.get(eachLanguage.getLanguageName()).get(0).getGuid());
					updatedLanguageList.add(eachLanguage);
				}
			}
		}
		return updatedLanguageList;
	}

	@Override
	@Transactional
	public void uploadVisaTypes() {

		List<VisaType> existingVisaTypeList = visaTypeDao.getAllVisaTypes();
		List<VisaType> excelVisaTypeList = new ArrayList<VisaType>();
		List<VisaType> newVisaTypeList = new ArrayList<VisaType>();
		String filePath = env.getProperty("data.download-path") + env.getProperty("visaTypes.data-file-name");
		Workbook myWorkBook = CommonUtil.getWorkBookFromFile(filePath);
		Sheet sheet = myWorkBook.getSheetAt(0);
		int i = 0;
		for (Row eachRow : sheet) {
			if (i < 1) {
				i++;
				continue;
			}

			String visaTypeName = eachRow.getCell(0).getStringCellValue().trim();
			if (visaTypeName != null) {
				VisaType visaTypeObj = new VisaType();
				visaTypeObj.setVisaTypeName(visaTypeName);

				if (!newVisaTypeList.contains(visaTypeObj) && !existingVisaTypeList.contains(visaTypeObj)) {
					newVisaTypeList.add(visaTypeObj);
				}
				if (!excelVisaTypeList.contains(visaTypeObj)) {
					excelVisaTypeList.add(visaTypeObj);
				}
			}
		}
		if (newVisaTypeList != null && !newVisaTypeList.isEmpty()) {
			visaTypeDao.saveAllVisaTypes(newVisaTypeList);
		}

		List<VisaType> updatedReligiosList = findUpdatedVisaTypeList(existingVisaTypeList, excelVisaTypeList);
		if (updatedReligiosList != null && !updatedReligiosList.isEmpty()) {
			visaTypeDao.updateAllVisaTypes(updatedReligiosList);
		}
		if (existingVisaTypeList != null && !existingVisaTypeList.isEmpty()) {
			existingVisaTypeList.removeAll(excelVisaTypeList);
			visaTypeDao.deleteVisaTypes(existingVisaTypeList);
		}
	}

	private List<VisaType> findUpdatedVisaTypeList(List<VisaType> existingVisaTypeList,
			List<VisaType> excelVisaTypeList) {

		List<VisaType> updatedVisaTypeList = new ArrayList<VisaType>();
		Map<String, List<VisaType>> existingVisaTypeMap = existingVisaTypeList.stream()
				.collect(Collectors.groupingBy(languageObj -> languageObj.getVisaTypeName()));
		if (excelVisaTypeList != null && !excelVisaTypeList.isEmpty() && existingVisaTypeList != null
				&& !existingVisaTypeList.isEmpty()) {
			for (VisaType eachVisaType : excelVisaTypeList) {
				if (existingVisaTypeList.contains(eachVisaType) && !updatedVisaTypeList.contains(eachVisaType)) {
					eachVisaType.setGuid(existingVisaTypeMap.get(eachVisaType.getVisaTypeName()).get(0).getGuid());
					updatedVisaTypeList.add(eachVisaType);
				}
			}
		}
		return updatedVisaTypeList;
	}

	private List<School> findUpdatedSchoolList(List<School> existingSchoolList, List<School> excelSchoolList) {
		List<School> updatedSchoolList = new ArrayList<School>();
		Map<String, List<School>> existingSchoolMap = existingSchoolList.stream()
				.collect(Collectors.groupingBy(schoolObj -> schoolObj.getSchoolName()));
		if (excelSchoolList != null && !excelSchoolList.isEmpty() && existingSchoolList != null
				&& !existingSchoolList.isEmpty()) {
			for (School eachSchool : excelSchoolList) {
				if (existingSchoolList.contains(eachSchool) && !updatedSchoolList.contains(eachSchool)) {
					eachSchool.setSchoolGuid(existingSchoolMap.get(eachSchool.getSchoolName()).get(0).getSchoolGuid());
					updatedSchoolList.add(eachSchool);
				}
			}
		}
		return updatedSchoolList;
	}

	@Override
	@Transactional
	public void uploadSchoolMasters() {

		List<School> existingSchoolList = schoolDao.getAllSchools();
		List<School> excelSchoolList = new ArrayList<School>();
		List<School> newSchoolList = new ArrayList<School>();
		String filePath = env.getProperty("data.download-path") + env.getProperty("school.data-file-name");
		Workbook myWorkBook = CommonUtil.getWorkBookFromFile(filePath);
		Sheet sheet = myWorkBook.getSheetAt(0);
		int i = 0;
		for (Row eachRow : sheet) {
			if (i < 1) {
				i++;
				continue;
			}
			String schoolId = (String) getCellValue(eachRow.getCell(0));
			String schoolName = (String) getCellValue(eachRow.getCell(1));
			String schoolLogo = (String) getCellValue(eachRow.getCell(2));
			String phoneNumber = (String) getCellValue(eachRow.getCell(3));
			String emailAddress = (String) getCellValue(eachRow.getCell(4));
			String addressLineOne = (String) getCellValue(eachRow.getCell(5));
			String addressLineTwo = (String) getCellValue(eachRow.getCell(6));
			String city = (String) getCellValue(eachRow.getCell(7));
			String state = (String) getCellValue(eachRow.getCell(8));
			Double zipCode = (Double) getCellValue(eachRow.getCell(9));
			String contactPerson = (String) getCellValue(eachRow.getCell(10));
			String website = (String) getCellValue(eachRow.getCell(11));
			Double internationalAppFees = (Double) getCellValue(eachRow.getCell(12));
			Double internationalCredEvalFees = (Double) getCellValue(eachRow.getCell(13));
			Double mailingFees = (Double) getCellValue(eachRow.getCell(14));

			Double gradTuitionFeePerCredit = (Double) getCellValue(eachRow.getCell(15));
			Double gradCreditsRequired = (Double) getCellValue(eachRow.getCell(16));

			String requiresTranscripts = (String) getCellValue(eachRow.getCell(17));
			String requiresRecLetters = (String) getCellValue(eachRow.getCell(18));
			String requiresSOP = (String) getCellValue(eachRow.getCell(19));
			String requiresSolvCert = (String) getCellValue(eachRow.getCell(20));
			String requiresResume = (String) getCellValue(eachRow.getCell(21));
			String requiresPassport = (String) getCellValue(eachRow.getCell(22));

			String requiresEAD = (String) getCellValue(eachRow.getCell(23));
			String requiresI20 = (String) getCellValue(eachRow.getCell(24));
			String requiresF1Visa = (String) getCellValue(eachRow.getCell(25));
			String requiresToeflScore = (String) getCellValue(eachRow.getCell(26));

			Double minimumGreScore = (Double) getCellValue(eachRow.getCell(27));
			Double minimumGmatScore = (Double) getCellValue(eachRow.getCell(28));
			Double minimumToeflScore = (Double) getCellValue(eachRow.getCell(29));
			Double minimumGPA = (Double) getCellValue(eachRow.getCell(30));

			Double greScoreSubmissionSchoolCode = null;
			if (eachRow.getCell(31) != null && eachRow.getCell(31).getCellType() == Cell.CELL_TYPE_NUMERIC) {
				greScoreSubmissionSchoolCode = eachRow.getCell(31).getNumericCellValue();
			} else if (eachRow.getCell(31) != null && eachRow.getCell(31).getCellType() == Cell.CELL_TYPE_STRING) {
				String cellValue = (String) getCellValue(eachRow.getCell(31));
				if (cellValue != null && !cellValue.isEmpty()) {
					greScoreSubmissionSchoolCode = Double.valueOf(cellValue);
				}
			}
			Double toeflScoreSubmissionSchoolCode = null;
			if (eachRow.getCell(32) != null && eachRow.getCell(32).getCellType() == Cell.CELL_TYPE_NUMERIC) {
				toeflScoreSubmissionSchoolCode = eachRow.getCell(26).getNumericCellValue();
			} else if (eachRow.getCell(32) != null && eachRow.getCell(32).getCellType() == Cell.CELL_TYPE_STRING) {
				String cellValue = (String) getCellValue(eachRow.getCell(32));
				if (cellValue != null && !cellValue.isEmpty()) {
					toeflScoreSubmissionSchoolCode = Double.valueOf(cellValue);
				}
			}
			Double gmatScoreSubmissionSchoolCode = null;
			// String gmatScoreSubmissionSchoolCode = null;

			if (eachRow.getCell(33) != null && eachRow.getCell(33).getCellType() == Cell.CELL_TYPE_NUMERIC) {
				gmatScoreSubmissionSchoolCode = (Double) getCellValue(eachRow.getCell(33));
			}

			String applicationCheckListLink = (String) getCellValue(eachRow.getCell(34));

			String tuitionFeesLink = (String) getCellValue(eachRow.getCell(35));

			String emailDomainOne = (String) getCellValue(eachRow.getCell(36));
			String emailDomainTwo = (String) getCellValue(eachRow.getCell(37));
			String emailDomainThree = (String) getCellValue(eachRow.getCell(38));

			if (schoolName != null) {

				School schoolObj = new School();
				schoolObj.setSchoolId(schoolId);
				schoolObj.setSchoolName(schoolName);
				schoolObj.setSchoolLogoURL(schoolLogo);
				schoolObj.setPhoneNumber(phoneNumber);
				schoolObj.setEmailAddress(emailAddress);
				schoolObj.setAddressLineOne(addressLineOne);
				schoolObj.setAddressLineTwo(addressLineTwo);
				schoolObj.setCity(city);
				schoolObj.setState(state);
				String zipCodeStr = "";
				if (zipCode != null) {
					zipCodeStr = zipCode.toString();
					zipCodeStr = zipCodeStr.replaceAll("(?<=^\\d+)\\.0*$", "");
				}
				schoolObj.setZipCode(zipCodeStr);
				schoolObj.setContactPerson(contactPerson);
				schoolObj.setWebsite(website);
				schoolObj.setInternationAppFees(internationalAppFees);
				schoolObj.setInternationCredentialEvalFees(internationalCredEvalFees);
				schoolObj.setMailingFee(mailingFees);
				schoolObj.setMinimumGREScore(minimumGreScore);
				schoolObj.setMinimumTOEFLScore(minimumToeflScore);
				schoolObj.setMinimumGMATcore(minimumGmatScore);
				schoolObj.setMinimumGPA(minimumGPA);
				schoolObj.setGreScoreSubmissionSchoolCode(greScoreSubmissionSchoolCode);
				schoolObj.setToeflScoreSubmissionSchoolCode(toeflScoreSubmissionSchoolCode);
				schoolObj.setGmatScoreSubmissionSchoolCode(gmatScoreSubmissionSchoolCode);

				if (requiresEAD != null && !requiresEAD.isEmpty() && requiresEAD.equalsIgnoreCase("YES FOR TRANSFER")) {
					requiresEAD = "YES FOR TRANSFER";
				} else if (requiresEAD != null && !requiresEAD.isEmpty() && requiresEAD.equalsIgnoreCase("YES")) {
					requiresEAD = "YES";
				} else {
					requiresEAD = "NO";
				}
				if (requiresI20 != null && !requiresI20.isEmpty() && requiresI20.equalsIgnoreCase("YES FOR TRANSFER")) {
					requiresI20 = "YES FOR TRANSFER";
				} else if (requiresI20 != null && !requiresI20.isEmpty() && requiresI20.equalsIgnoreCase("YES")) {
					requiresI20 = "YES";
				} else {
					requiresI20 = "NO";
				}
				if (requiresF1Visa != null && !requiresF1Visa.isEmpty()
						&& requiresF1Visa.equalsIgnoreCase("YES FOR TRANSFER")) {
					requiresF1Visa = "YES FOR TRANSFER";
				} else if (requiresF1Visa != null && !requiresF1Visa.isEmpty()
						&& requiresF1Visa.equalsIgnoreCase("YES")) {
					requiresF1Visa = "YES";
				} else {
					requiresF1Visa = "NO";
				}
				if (requiresToeflScore != null && !requiresToeflScore.isEmpty()
						&& requiresToeflScore.equalsIgnoreCase("YES FOR TRANSFER")) {
					requiresToeflScore = "YES FOR TRANSFER";
				} else if (requiresToeflScore != null && !requiresToeflScore.isEmpty()
						&& requiresToeflScore.equalsIgnoreCase("YES")) {
					requiresToeflScore = "YES";
				} else {
					requiresToeflScore = "NO";
				}
				schoolObj.setRequiresEAD(requiresEAD);
				schoolObj.setRequiresI20(requiresI20);
				schoolObj.setRequiresF1Visa(requiresF1Visa);
				schoolObj.setRequiresTOFELScore(requiresToeflScore);

				String countryName = "United States";

				Country schoolCountry = countryDao.getCountryByName(countryName);
				schoolObj.setSchoolCountry(schoolCountry);
				if (requiresTranscripts != null && !requiresTranscripts.isEmpty()
						&& requiresTranscripts.equalsIgnoreCase("YES FOR TRANSFER")) {
					requiresTranscripts = "YES FOR TRANSFER";
				} else if (requiresTranscripts != null && !requiresTranscripts.isEmpty()
						&& requiresTranscripts.equalsIgnoreCase("YES")) {
					requiresTranscripts = "YES";
				} else {
					requiresTranscripts = "NO";
				}
				if (requiresRecLetters != null && !requiresRecLetters.isEmpty()
						&& requiresRecLetters.equalsIgnoreCase("YES FOR TRANSFER")) {
					requiresRecLetters = "YES FOR TRANSFER";
				} else if (requiresRecLetters != null && !requiresRecLetters.isEmpty()
						&& requiresRecLetters.equalsIgnoreCase("YES")) {
					requiresRecLetters = "YES";
				} else {
					requiresRecLetters = "NO";
				}

				if (requiresSOP != null && !requiresSOP.isEmpty() && requiresSOP.equalsIgnoreCase("YES FOR TRANSFER")) {
					requiresSOP = "YES FOR TRANSFER";
				} else if (requiresSOP != null && !requiresSOP.isEmpty() && requiresSOP.equalsIgnoreCase("YES")) {
					requiresSOP = "YES";
				} else {
					requiresSOP = "NO";
				}
				if (requiresSolvCert != null && !requiresSolvCert.isEmpty()
						&& requiresSolvCert.equalsIgnoreCase("YES FOR TRANSFER")) {
					requiresSolvCert = "YES FOR TRANSFER";
				} else if (requiresSolvCert != null && !requiresSolvCert.isEmpty()
						&& requiresSolvCert.equalsIgnoreCase("YES")) {
					requiresSolvCert = "YES";
				} else {
					requiresSolvCert = "NO";
				}
				if (requiresResume != null && !requiresResume.isEmpty()
						&& requiresResume.equalsIgnoreCase("YES FOR TRANSFER")) {
					requiresResume = "YES FOR TRANSFER";
				} else if (requiresResume != null && !requiresResume.isEmpty()
						&& requiresResume.equalsIgnoreCase("YES")) {
					requiresResume = "YES";
				} else {
					requiresResume = "NO";
				}
				if (requiresPassport != null && !requiresPassport.isEmpty()
						&& requiresPassport.equalsIgnoreCase("YES FOR TRANSFER")) {
					requiresPassport = "YES FOR TRANSFER";
				} else if (requiresPassport != null && !requiresPassport.isEmpty()
						&& requiresPassport.equalsIgnoreCase("YES")) {
					requiresPassport = "YES";
				} else {
					requiresPassport = "NO";
				}

				schoolObj.setTranscriptsRequires(requiresTranscripts);
				schoolObj.setRecLettersRequires(requiresRecLetters);
				schoolObj.setSopRequires(requiresSOP);
				schoolObj.setSolvCERTRequires(requiresSolvCert);
				schoolObj.setResumeRequires(requiresResume);
				schoolObj.setRequiresPassport(requiresPassport);
				schoolObj.setGradTuitionFeePerCredit(gradTuitionFeePerCredit);
				schoolObj.setGradCreditsRequired(gradCreditsRequired);
				schoolObj.setApplicationCheckListLink(applicationCheckListLink);
				schoolObj.setTuitionFeesLink(tuitionFeesLink);
				schoolObj.setEmailDomainOne(emailDomainOne);
				schoolObj.setEmailDomainTwo(emailDomainTwo);
				schoolObj.setEmailDomainThree(emailDomainThree);
				if (!newSchoolList.contains(schoolObj) && !existingSchoolList.contains(schoolObj)) {
					newSchoolList.add(schoolObj);
				}
				if (!excelSchoolList.contains(schoolObj)) {
					excelSchoolList.add(schoolObj);
				}
			}
		}
		if (newSchoolList != null && !newSchoolList.isEmpty()) {
			schoolDao.saveAllSchools(newSchoolList);
		}

		List<School> updatedReligiosList = findUpdatedSchoolList(existingSchoolList, excelSchoolList);
		if (updatedReligiosList != null && !updatedReligiosList.isEmpty()) {
			schoolDao.updateAllSchools(updatedReligiosList);
		}
		if (existingSchoolList != null && !existingSchoolList.isEmpty()) {
			existingSchoolList.removeAll(excelSchoolList);
			schoolDao.deleteSchools(existingSchoolList);
		}

	}

	@Override
	@Transactional
	public void uploadSchoolDegrees() {

		List<School> existingSchoolList = schoolDao.getAllSchools();

		Map<Double, List<School>> existingSchoolMap = existingSchoolList.stream()
				.collect(Collectors.groupingBy(schoolObj -> Double.valueOf(schoolObj.getSchoolId())));

		List<SchoolDegree> existingSchoolDegreeList = schoolDegreeDao.getAllSchoolDegrees();
		List<SchoolDegree> excelSchoolDegreeList = new ArrayList<SchoolDegree>();
		List<SchoolDegree> newSchoolDegreeList = new ArrayList<SchoolDegree>();
		String filePath = env.getProperty("data.download-path") + env.getProperty("school-degree.data-file-name");
		Workbook myWorkBook = CommonUtil.getWorkBookFromFile(filePath);
		Sheet sheet = myWorkBook.getSheetAt(0);
		int i = 0;
		for (Row eachRow : sheet) {
			if (i < 1) {
				i++;
				continue;
			}

			Double schoolId = (Double) getCellValue(eachRow.getCell(0));

			String schoolName = (String) getCellValue(eachRow.getCell(1));
			String degreeOffered = (String) getCellValue(eachRow.getCell(2));
			String degreeDepartment = (String) getCellValue(eachRow.getCell(3));
			String contactPerson = (String) getCellValue(eachRow.getCell(4));
			String emailAddress = (String) getCellValue(eachRow.getCell(5));
			String phoneNumber = (String) getCellValue(eachRow.getCell(6));

			String addressLine1 = (String) getCellValue(eachRow.getCell(7));
			String addressLine2 = (String) getCellValue(eachRow.getCell(8));
			String addressLine3 = (String) getCellValue(eachRow.getCell(9));
			String city = (String) getCellValue(eachRow.getCell(10));
			String state = (String) getCellValue(eachRow.getCell(11));
			String zipCode = String.valueOf(getCellValue(eachRow.getCell(12)));
			Double departmentMinGREScore = (Double) getCellValue(eachRow.getCell(13));

			Double departmentMinGREVerbalScore = (Double) getCellValue(eachRow.getCell(14));
			Double departmentMinGREQuantScore = (Double) getCellValue(eachRow.getCell(15));
			Double departmentMinGREWritingScore = (Double) getCellValue(eachRow.getCell(16));

			Double departmentMinTOEFLScore = (Double) getCellValue(eachRow.getCell(17));
			Double departmentMinGPA = (Double) getCellValue(eachRow.getCell(18));

			Double departmentIntrnationalAppFees = (Double) getCellValue(eachRow.getCell(19));
			String degreeWebsite = (String) getCellValue(eachRow.getCell(20));
			Double departmentMinGMATScore = (Double) getCellValue(eachRow.getCell(21));
			String requiresGREScore = (String) getCellValue(eachRow.getCell(22));
			String requiresGMATScore = (String) getCellValue(eachRow.getCell(23));

			School school = null;
			if (existingSchoolMap.get(schoolId) != null) {
				school = existingSchoolMap.get(schoolId).get(0);
			}

			String zipCodeStr = "";
			if (zipCode != null) {
				zipCodeStr = zipCode.toString();
				zipCodeStr = zipCodeStr.replaceAll("(?<=^\\d+)\\.0*$", "");
			}

			SchoolDegree schoolDegree = new SchoolDegree();
			schoolDegree.setContactPerson(contactPerson);
			schoolDegree.setDegreeDepartment(degreeDepartment);
			schoolDegree.setDegreeOffered(degreeOffered.trim());
			schoolDegree.setDepartmentMinGREScore(departmentMinGREScore);
			schoolDegree.setDepartmentMinTOEFLScore(departmentMinTOEFLScore);
			schoolDegree.setDepartmentMinGPA(departmentMinGPA);
			schoolDegree.setEmailAddress(emailAddress);
			schoolDegree.setPhoneNumber(phoneNumber);
			schoolDegree.setAddressLineOne(addressLine1);
			schoolDegree.setAddressLineTwo(addressLine2);
			schoolDegree.setAddressLine3(addressLine3);
			schoolDegree.setCity(city);
			schoolDegree.setState(state);
			schoolDegree.setZipCode(zipCodeStr);
			schoolDegree.setSchool(school);

			schoolDegree.setDeptMinGREVerbalScore(departmentMinGREVerbalScore);
			schoolDegree.setDeptMinGREQuantScore(departmentMinGREQuantScore);
			schoolDegree.setDeptMinGREWritingtScore(departmentMinGREWritingScore);
			schoolDegree.setDeptInternationalAppFees(departmentIntrnationalAppFees);
			schoolDegree.setDegreeWebSite(degreeWebsite);
			schoolDegree.setDeptMinGMATScore(departmentMinGMATScore);

			if (requiresGREScore != null && !requiresGREScore.isEmpty()
					&& requiresGREScore.equalsIgnoreCase("YES FOR TRANSFER")) {
				requiresGREScore = "YES FOR TRANSFER";
			} else if (requiresGREScore != null && !requiresGREScore.isEmpty()
					&& requiresGREScore.equalsIgnoreCase("YES")) {
				requiresGREScore = "YES";
			} else {
				requiresGREScore = "NO";
			}

			if (requiresGMATScore != null && !requiresGMATScore.isEmpty()
					&& requiresGMATScore.equalsIgnoreCase("YES FOR TRANSFER")) {
				requiresGREScore = "YES FOR TRANSFER";
			} else if (requiresGMATScore != null && !requiresGMATScore.isEmpty()
					&& requiresGMATScore.equalsIgnoreCase("YES")) {
				requiresGMATScore = "YES";
			} else {
				requiresGMATScore = "NO";
			}

			schoolDegree.setRequiresGREScore(requiresGREScore);
			schoolDegree.setRequiresGMATScore(requiresGMATScore);

			if (!newSchoolDegreeList.contains(schoolDegree) && !existingSchoolDegreeList.contains(schoolDegree)) {
				newSchoolDegreeList.add(schoolDegree);
			}
			if (!existingSchoolDegreeList.contains(schoolDegree)) {
				excelSchoolDegreeList.add(schoolDegree);
			}

		}

		if (newSchoolDegreeList != null && !newSchoolDegreeList.isEmpty()) {
			schoolDegreeDao.saveAllSchoolDegrees(newSchoolDegreeList);
		}

		List<SchoolDegree> updatedReligiosList = findUpdatedSchoolDegreesList(existingSchoolDegreeList,
				excelSchoolDegreeList);

		if (updatedReligiosList != null && !updatedReligiosList.isEmpty()) {
			schoolDegreeDao.updateAllSchoolDegrees(updatedReligiosList);
		}

		if (existingSchoolDegreeList != null && !existingSchoolDegreeList.isEmpty()) {
			existingSchoolDegreeList.removeAll(excelSchoolDegreeList);
			schoolDegreeDao.deleteSchoolDegrees(existingSchoolDegreeList);
		}

	}


	private List<SchoolDegree> findUpdatedSchoolDegreesList(List<SchoolDegree> existingSchoolDegreeList,
			List<SchoolDegree> excelSchoolDegreeList) {
		List<SchoolDegree> updatedSchoolDegreesList = new ArrayList<SchoolDegree>();
		Map<String, List<SchoolDegree>> existingSchoolMap = existingSchoolDegreeList.stream()
				.collect(Collectors.groupingBy(schoolDegreeObj -> schoolDegreeObj.getSchool().getSchoolId() + "|"
						+ schoolDegreeObj.getDegreeOffered()));
		if (excelSchoolDegreeList != null && !excelSchoolDegreeList.isEmpty() && existingSchoolDegreeList != null
				&& !existingSchoolDegreeList.isEmpty()) {
			for (SchoolDegree eachSchoolDegree : excelSchoolDegreeList) {
				if (existingSchoolDegreeList.contains(eachSchoolDegree)
						&& !updatedSchoolDegreesList.contains(eachSchoolDegree)) {
					eachSchoolDegree.setSchoolDegreeGuid(existingSchoolMap
							.get(eachSchoolDegree.getSchool().getSchoolId() + "|" + eachSchoolDegree.getDegreeOffered())
							.get(0).getSchoolDegreeGuid());
					updatedSchoolDegreesList.add(eachSchoolDegree);
				}
			}
		}
		return updatedSchoolDegreesList;
	}

	@Override
	@Transactional
	public void uploadSchoolTerms() throws ParseException {

		List<School> existingSchoolList = schoolDao.getAllSchools();

		Map<Double, List<School>> existingSchoolMap = existingSchoolList.stream()
				.collect(Collectors.groupingBy(schoolObj -> Double.valueOf(schoolObj.getSchoolId())));

		List<SchoolTerm> existingSchoolTermList = schoolTermDao.getAllSchoolTerms();
		List<SchoolTerm> excelSchoolTermList = new ArrayList<SchoolTerm>();
		List<SchoolTerm> newSchoolTermList = new ArrayList<SchoolTerm>();
		String filePath = env.getProperty("data.download-path") + env.getProperty("school-term.data-file-name");
		Workbook myWorkBook = CommonUtil.getWorkBookFromFile(filePath);
		Sheet sheet = myWorkBook.getSheetAt(0);
		int i = 0;
		for (Row eachRow : sheet) {
			if (i < 1) {
				i++;
				continue;
			}

			Double schoolId = null;

			if (eachRow.getCell(0) != null && eachRow.getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
				schoolId = eachRow.getCell(0).getNumericCellValue();
			} else if (eachRow.getCell(0) != null && eachRow.getCell(0).getCellType() == Cell.CELL_TYPE_STRING) {
				String cellValue = (String) getCellValue(eachRow.getCell(0));
				if (cellValue != null && !cellValue.isEmpty()) {
					schoolId = Double.valueOf(cellValue);
				}
			}

			Double termYear = (Double) getCellValue(eachRow.getCell(1));
			String term = (String) getCellValue(eachRow.getCell(2));
			String degree = (String) getCellValue(eachRow.getCell(3));
			Double applicationDeadLineDate = (Double) getCellValue(eachRow.getCell(4));
			Double earlyDecisionDeadLineDate = (Double) getCellValue(eachRow.getCell(5));
			Double applicationSubmissionStartDate = (Double) getCellValue(eachRow.getCell(6));
			Double documentsDeadLineDate = (Double) getCellValue(eachRow.getCell(7));

			Date applicationDeadLineDate1 = null;
			java.sql.Date applicationDeadLineDateSQL = null;
			if (applicationDeadLineDate != null) {
				applicationDeadLineDate1 = DateUtil.getJavaDate(applicationDeadLineDate);
				String applicationDeadLineDateStr = new SimpleDateFormat("yyyy-MM-dd").format(applicationDeadLineDate1);
				applicationDeadLineDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(applicationDeadLineDateStr);
				applicationDeadLineDateSQL = new java.sql.Date(applicationDeadLineDate1.getTime());
			}

			Date earlyDecisionDeadLineDate1 = null;
			java.sql.Date earlyDecisionDeadLineDateSQL = null;
			if (earlyDecisionDeadLineDate != null) {
				earlyDecisionDeadLineDate1 = DateUtil.getJavaDate(earlyDecisionDeadLineDate);
				String earlyDecisionDeadLineDateStr = new SimpleDateFormat("yyyy-MM-dd")
						.format(earlyDecisionDeadLineDate1);
				earlyDecisionDeadLineDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(earlyDecisionDeadLineDateStr);
				earlyDecisionDeadLineDateSQL = new java.sql.Date(earlyDecisionDeadLineDate1.getTime());
			}

			Date applicationSubmissionStartDate1 = null;
			java.sql.Date applicationSubmissionStartDateSQL = null;
			if (applicationSubmissionStartDate != null) {
				applicationSubmissionStartDate1 = DateUtil.getJavaDate(applicationSubmissionStartDate);
				String applicationSubmissionStartDateStr = new SimpleDateFormat("yyyy-MM-dd")
						.format(applicationSubmissionStartDate1);
				applicationSubmissionStartDate1 = new SimpleDateFormat("yyyy-MM-dd")
						.parse(applicationSubmissionStartDateStr);
				applicationSubmissionStartDateSQL = new java.sql.Date(applicationSubmissionStartDate1.getTime());
			}

			Date documentsDeadLineDate1 = null;
			java.sql.Date documentsDeadLineDateSQL = null;
			if (applicationSubmissionStartDate != null) {
				documentsDeadLineDate1 = DateUtil.getJavaDate(documentsDeadLineDate);
				String applicationSubmissionStartDateStr = new SimpleDateFormat("yyyy-MM-dd")
						.format(documentsDeadLineDate1);
				documentsDeadLineDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(applicationSubmissionStartDateStr);
				documentsDeadLineDateSQL = new java.sql.Date(documentsDeadLineDate1.getTime());
			}

			School school = null;
			if (existingSchoolMap.get(schoolId) != null) {
				school = existingSchoolMap.get(schoolId).get(0);
			}

			String termYearStr = "";
			if (termYear != null) {
				DecimalFormat df = new DecimalFormat("0.#");
				termYearStr = df.format(termYear);
			}

			SchoolTerm schoolTerm = new SchoolTerm();
			schoolTerm.setSchool(school);
			schoolTerm.setTerm(term);
			schoolTerm.setTermYear(termYearStr);
			schoolTerm.setApplicationDeadLineDate(applicationDeadLineDateSQL);
			schoolTerm.setEarlyDecisionDeadLineDate(earlyDecisionDeadLineDateSQL);
			schoolTerm.setApplicationSubmissionStartDate(applicationSubmissionStartDateSQL);
			schoolTerm.setDocumentsDeadLineDate(documentsDeadLineDateSQL);

			if (!newSchoolTermList.contains(schoolTerm) && !existingSchoolTermList.contains(schoolTerm)) {
				newSchoolTermList.add(schoolTerm);
			}
			if (!existingSchoolTermList.contains(schoolTerm)) {
				excelSchoolTermList.add(schoolTerm);
			}

		}

		if (newSchoolTermList != null && !newSchoolTermList.isEmpty()) {
			schoolTermDao.saveAllSchoolTerms(newSchoolTermList);
		}

		List<SchoolTerm> updatedReligiosList = findUpdatedSchoolTermsList(existingSchoolTermList, excelSchoolTermList);
		if (updatedReligiosList != null && !updatedReligiosList.isEmpty()) {
			schoolTermDao.updateAllSchoolTerms(updatedReligiosList);
		}
		if (existingSchoolTermList != null && !existingSchoolTermList.isEmpty()) {
			existingSchoolTermList.removeAll(excelSchoolTermList);
			schoolTermDao.deleteSchoolTerms(existingSchoolTermList);
		}

	}

	private List<SchoolTerm> findUpdatedSchoolTermsList(List<SchoolTerm> existingSchoolTermList,
			List<SchoolTerm> excelSchoolTermList) {

		List<SchoolTerm> updatedSchoolTermsList = new ArrayList<SchoolTerm>();
		Map<String, List<SchoolTerm>> existingSchoolMap = existingSchoolTermList.stream()
				.collect(Collectors.groupingBy(schoolDegreeObj -> schoolDegreeObj.getSchoolTermGuid().toString() + "|"
						+ schoolDegreeObj.getTermYear()));
		if (excelSchoolTermList != null && !excelSchoolTermList.isEmpty() && existingSchoolTermList != null
				&& !existingSchoolTermList.isEmpty()) {
			for (SchoolTerm eachSchoolTerm : excelSchoolTermList) {
				if (existingSchoolTermList.contains(eachSchoolTerm)
						&& !updatedSchoolTermsList.contains(eachSchoolTerm)) {
					eachSchoolTerm.setSchoolTermGuid(existingSchoolMap
							.get(eachSchoolTerm.getSchoolTermGuid().toString() + "|" + eachSchoolTerm.getTermYear())
							.get(0).getSchoolTermGuid());
					updatedSchoolTermsList.add(eachSchoolTerm);
				}
			}
		}
		return updatedSchoolTermsList;

	}

	public Object getCellValue(Cell cell) {
		Object cellValue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				cellValue = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				cellValue = cell.getNumericCellValue();
				break;
			}
		}
		return cellValue;
	}

	@Override
	@Transactional
	public void uploadSchoolQuestions() {

		List newSchoolQuestionList = new ArrayList();
		String filePath = env.getProperty("data.download-path") + env.getProperty("school-question.data-file-name");
		Workbook myWorkBook = CommonUtil.getWorkBookFromFile(filePath);
		Sheet sheet = myWorkBook.getSheetAt(0);
		int i = 0;
		for (Row eachRow : sheet) {
			if (i < 1) {
				i++;
				continue;
			}

			Double schoolId = (Double) getCellValue(eachRow.getCell(0));
			String schoolName = (String) getCellValue(eachRow.getCell(1));
			Double sectionSequenceNo = (Double) getCellValue(eachRow.getCell(2));
			String sectionName = (String) getCellValue(eachRow.getCell(3));
			Double questionSequenceNo = (Double) getCellValue(eachRow.getCell(4));
			String questionTitle = (String) getCellValue(eachRow.getCell(5));
			String questionDescription = (String) getCellValue(eachRow.getCell(6));
			String questionType = (String) getCellValue(eachRow.getCell(7));
			String mandatory = (String) getCellValue(eachRow.getCell(8));
			String queInactive = (String) getCellValue(eachRow.getCell(9));
			Double answerOptionSeq = (Double) getCellValue(eachRow.getCell(10));
			String answerOption = (String) getCellValue(eachRow.getCell(11));
			String parentQuestion = (String) getCellValue(eachRow.getCell(12));
			String parentQuestionAnsrCond = (String) getCellValue(eachRow.getCell(13));
			Double parentQuestionSeqNo = (Double) getCellValue(eachRow.getCell(14));
			Double tableQuestionId = (Double) getCellValue(eachRow.getCell(15));
			Double tableQuestionSequence = (Double) getCellValue(eachRow.getCell(16));
			String degreeList = (String) getCellValue(eachRow.getCell(17));
			if (schoolId != null && schoolName != null && sectionName != null && questionTitle != null) {
				SchoolQuestionSection schoolQuestionSection = new SchoolQuestionSection();
				SchoolQuestion schoolQuestion = new SchoolQuestion();
				SchoolQuestionAnswerOption schoolQuestionAnswerOption = new SchoolQuestionAnswerOption();

				List<School> existingSchoolList = schoolDao.getAllSchools();
				Map<Double, List<School>> existingSchoolMap = existingSchoolList.stream()
						.collect(Collectors.groupingBy(schoolObj -> Double.valueOf(schoolObj.getSchoolId())));
				School school = null;

				if (existingSchoolMap.get(schoolId) != null) {
					school = existingSchoolMap.get(schoolId).get(0);
				}

				List<SchoolQuestionSection> existingSecList = schoolQuestionSectionDao.getAllSchoolQuestionSections();
				int exisistingSectioncount = 0;
				for (int exisistingSections = 0; exisistingSections < existingSecList.size(); exisistingSections++) {

					if (existingSecList.get(exisistingSections).getSectionName() != null
							&& existingSecList.get(exisistingSections).getSeqNo() != null
							&& (existingSecList.get(exisistingSections).getSectionName().equalsIgnoreCase(sectionName)
									&& existingSecList.get(exisistingSections).getSeqNo().equals(sectionSequenceNo)
									&& existingSecList.get(exisistingSections).getSchool().equals(school))) {

						exisistingSectioncount++;
					}

				}

				if (exisistingSectioncount < 1) {
					schoolQuestionSection.setSectionName(sectionName);
					schoolQuestionSection.setSeqNo(sectionSequenceNo);
					schoolQuestionSection.setSchool(school);
					newSchoolQuestionList.add(schoolQuestionSection);
				}
				if (newSchoolQuestionList != null && !newSchoolQuestionList.isEmpty()) {
					schoolQuestionDao.saveAllSchoolQuestions(newSchoolQuestionList);
				}

				List<SchoolQuestion> existingQuesList = schoolQuestionDao.getAllSchoolQuestions();
				int exisistingQuestioncount = 0;
				for (int exisistingQuestions = 0; exisistingQuestions < existingQuesList
						.size(); exisistingQuestions++) {

					if (existingQuesList.get(exisistingQuestions).getQuestionTitle() != null
							&& existingQuesList.get(exisistingQuestions).getSeqNo() != null
							&& (existingQuesList.get(exisistingQuestions).getQuestionTitle()
									.equalsIgnoreCase(questionTitle)
									&& existingQuesList.get(exisistingQuestions).getSeqNo().equals(questionSequenceNo)
									&& existingQuesList.get(exisistingQuestions).getQuestionType()
											.equalsIgnoreCase(questionType))) {

						exisistingQuestioncount++;
					}

				}

				if (exisistingQuestioncount < 1) {

					schoolQuestion.setChildQuestionseqNo(parentQuestionSeqNo);

					List<SchoolQuestion> existingQuestionsList = schoolQuestionDao.getAllSchoolQuestions();
					if (parentQuestionAnsrCond != null) {
						for (int parentQuestions = 0; parentQuestions < existingQuestionsList
								.size(); parentQuestions++) {
							if (existingQuestionsList.get(parentQuestions).getQuestionTitle() != null
									&& existingQuestionsList.get(parentQuestions).getQuestionTitle()
											.equalsIgnoreCase(parentQuestion)) {

								schoolQuestion.setParentQuestion(existingQuestionsList.get(parentQuestions));
							}
						}

					}

					schoolQuestion.setParentQuestionAnswerCondition(parentQuestionAnsrCond);
					schoolQuestion.setQuestionInactive(queInactive);
					schoolQuestion.setQuestionLongText(questionTitle);
					schoolQuestion.setQuestionMandatory(mandatory);
					schoolQuestion.setQuestionTitle(questionTitle);
					schoolQuestion.setQuestionType(questionType);
					schoolQuestion.setDegreeList(degreeList);

					List<SchoolQuestionSection> existingSectionList = schoolQuestionSectionDao
							.getAllSchoolQuestionSections();

					for (int existingSections = 0; existingSections < existingSectionList.size(); existingSections++) {
						if (existingSectionList.get(existingSections).getSectionName() != null && existingSectionList
								.get(existingSections).getSectionName().equalsIgnoreCase(sectionName)) {

							schoolQuestion.setSchoolQuestionSection(existingSectionList.get(existingSections));
						}
					}
					if (parentQuestion != null && parentQuestionAnsrCond != null) {
						questionSequenceNo = null;
					}
					if (questionSequenceNo != null) {
						schoolQuestion.setSeqNo(questionSequenceNo);
					}

					Object tableQuestion = null;
					if (schoolQuestion.getTableQuestion() != null) {
						tableQuestion = schoolQuestion.getTableQuestion().getSchoolQuestionGuid();
					}

					schoolQuestion.setTableQuestion((SchoolQuestion) tableQuestion);
					schoolQuestion.setTableQuestionseqNo(tableQuestionSequence);
					newSchoolQuestionList.add(schoolQuestion);
				}
				if (newSchoolQuestionList != null && !newSchoolQuestionList.isEmpty()) {
					schoolQuestionDao.saveAllSchoolQuestions(newSchoolQuestionList);
				}
				schoolQuestionAnswerOption.setAnswer(answerOption);

				List<SchoolQuestion> existingQuestionList = schoolQuestionDao.getAllSchoolQuestions();
				for (int existingQuestions = 0; existingQuestions < existingQuestionList.size(); existingQuestions++) {
					if (existingQuestionList.get(existingQuestions).getQuestionTitle() != null && existingQuestionList
							.get(existingQuestions).getQuestionTitle().equalsIgnoreCase(questionTitle)) {

						schoolQuestionAnswerOption.setSchoolQuestion(existingQuestionList.get(existingQuestions));
					}
				}

				schoolQuestionAnswerOption.setSeqNo(answerOptionSeq);// sequence

				if (answerOption != null || answerOptionSeq != null) {
					newSchoolQuestionList.add(schoolQuestionAnswerOption);
				}

				if (newSchoolQuestionList != null && !newSchoolQuestionList.isEmpty()) {
					schoolQuestionDao.saveAllSchoolQuestions(newSchoolQuestionList);
				}
			} else {
				break;
			}

		}
	}

	@Override
	@Transactional
	public void uploadSchoolDocuments() {

		List<SchoolDocument> newSchooDocumentlList = new ArrayList<SchoolDocument>();
		String filePath = env.getProperty("data.download-path") + env.getProperty("school-documents.data-file-name");

		Workbook myWorkBook = CommonUtil.getWorkBookFromFile(filePath);
		Sheet sheet = myWorkBook.getSheetAt(0);
		int i = 0;
		for (Row eachRow : sheet) {
			if (i < 1) {
				i++;
				continue;
			}
			Double schoolId = (Double) getCellValue(eachRow.getCell(0));
			String schoolName = (String) getCellValue(eachRow.getCell(1));
			String documentName = (String) getCellValue(eachRow.getCell(2));
			String documentPath = (String) getCellValue(eachRow.getCell(3));
			String documentURL = (String) getCellValue(eachRow.getCell(4));
			String isMandatoy = (String) getCellValue(eachRow.getCell(5));
			String degreeList = (String) getCellValue(eachRow.getCell(6));

			List<School> existingSchoolList = schoolDao.getAllSchools();
			Map<Double, List<School>> existingSchoolMap = existingSchoolList.stream()
					.collect(Collectors.groupingBy(schoolObj -> Double.valueOf(schoolObj.getSchoolId())));
			School school = null;

			if (existingSchoolMap.get(schoolId) != null) {
				school = existingSchoolMap.get(schoolId).get(0);
			}

			SchoolDocument schoolDocument = new SchoolDocument();

			schoolDocument.setSchool(school);
			schoolDocument.setDocumentName(documentName);
			schoolDocument.setDocumentURL(documentURL);
			schoolDocument.setIsMandatory(isMandatoy);
			schoolDocument.setDegreeList(degreeList);
			if (documentURL != null) {
				File dest = null;
				try {

					URL url = new URL(documentURL);
					HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();

					httpcon.setRequestProperty("User-Agent", "NING/1.0");
					
					String documentId = CommonUtil.generateSalt(10);
					documentId = documentId + ".pdf";

					String fileExtension = CommonUtil.getExtension(documentName);
					String path = env.getProperty("java.io.tmpdir");
					if (fileExtension != null && !fileExtension.isEmpty()) {
						fileExtension = "." + fileExtension;
					}
					dest = new File(path + documentId + fileExtension);
					IOUtils.copy(httpcon.getInputStream(),  new FileOutputStream(dest));
					amazonS3Template.saveSchoolDocuments(documentId, dest);
					String docPath = amazonS3Template.getSchoolDocumentS3ObjectUrl(documentId);
					schoolDocument.setDocumentPath(docPath);

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {

					if (dest != null) {
						dest.delete();
					}
				}
				newSchooDocumentlList.add(schoolDocument);
			}
			if (newSchooDocumentlList != null && !newSchooDocumentlList.isEmpty()) {
				schoolDocumentDao.saveAllSchoolDocuments(newSchooDocumentlList);
			}

			// schoolDocument.set
		}
	}

	@Override
	@Transactional
	public void uploadDegrees() {
		List<Degrees> existingDegreesList = degreesDao.getAllDegrees();
		List<Degrees> excelDegreesList = new ArrayList<Degrees>();
		List<Degrees> newDegreesList = new ArrayList<Degrees>();
		String filePath = env.getProperty("data.download-path") + env.getProperty("degrees.data-file-name");
		
		Workbook myWorkBook = CommonUtil.getWorkBookFromFile(filePath);
		Sheet sheet = myWorkBook.getSheetAt(0);
		int i = 0;
		for (Row eachRow : sheet) {
			if (i < 1) {
				i++;
				continue;
			}

			String degreeName = eachRow.getCell(0).getStringCellValue();
			if (degreeName != null) {
				Degrees degreeObj = new Degrees();
				degreeObj.setDegreeName(degreeName);

				if (!newDegreesList.contains(degreeObj) && !existingDegreesList.contains(degreeObj)) {
					newDegreesList.add(degreeObj);
				}
				if (!excelDegreesList.contains(degreeObj)) {
					excelDegreesList.add(degreeObj);
				}
			}
		}
		if (newDegreesList != null && !newDegreesList.isEmpty()) {
			degreesDao.saveAllDegrees(newDegreesList);
		}

		List<Degrees> updatedDegreesList = findUpdatedDegreeList(existingDegreesList, excelDegreesList);
		if (updatedDegreesList != null && !updatedDegreesList.isEmpty()) {
			degreesDao.updateAllDegrees(updatedDegreesList);
		}
		if (existingDegreesList != null && !existingDegreesList.isEmpty()) {
			existingDegreesList.removeAll(excelDegreesList);
			degreesDao.deleteDegrees(existingDegreesList);
		}

	}

	private List<Degrees> findUpdatedDegreeList(List<Degrees> existingDegreesList, List<Degrees> excelDegreesList) {

		List<Degrees> updatedDegreesList = new ArrayList<Degrees>();
		Map<String, List<Degrees>> existingDegreesMap = existingDegreesList.stream()
				.collect(Collectors.groupingBy(degreeObj -> degreeObj.getDegreeName()));
		if (excelDegreesList != null && !excelDegreesList.isEmpty() && existingDegreesList != null
				&& !existingDegreesList.isEmpty()) {
			for (Degrees eachDegree : excelDegreesList) {
				if (existingDegreesList.contains(eachDegree) && !updatedDegreesList.contains(eachDegree)) {
					eachDegree.setGuid(existingDegreesMap.get(eachDegree.getDegreeName()).get(0).getGuid());
					updatedDegreesList.add(eachDegree);
				}
			}
		}
		return updatedDegreesList;
	}

}

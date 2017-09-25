
package com.denkensol.universaladmission.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.denkensol.universaladmission.constant.ApplicationConstants;
import com.denkensol.universaladmission.dao.AccountDao;
import com.denkensol.universaladmission.dao.CountryDao;
import com.denkensol.universaladmission.dao.SchoolDao;
import com.denkensol.universaladmission.dao.SchoolDegreeDao;
import com.denkensol.universaladmission.dao.SchoolDocumentDao;
import com.denkensol.universaladmission.dao.SchoolQuestionAnswerDao;
import com.denkensol.universaladmission.dao.SchoolQuestionAnswerOptionDao;
import com.denkensol.universaladmission.dao.SchoolQuestionDao;
import com.denkensol.universaladmission.dao.SchoolQuestionSectionDao;
import com.denkensol.universaladmission.dao.SchoolTermDao;
import com.denkensol.universaladmission.dao.StudentApplicationDao;
import com.denkensol.universaladmission.dao.StudentDocumentsDao;
import com.denkensol.universaladmission.dao.StudentProfileDao;
import com.denkensol.universaladmission.dao.StudentSchoolDocumentDao;
import com.denkensol.universaladmission.entity.Account;
import com.denkensol.universaladmission.entity.School;
import com.denkensol.universaladmission.entity.SchoolDegree;
import com.denkensol.universaladmission.entity.SchoolDocument;
import com.denkensol.universaladmission.entity.SchoolQuestion;
import com.denkensol.universaladmission.entity.SchoolQuestionAnswer;
import com.denkensol.universaladmission.entity.SchoolQuestionAnswerOption;
import com.denkensol.universaladmission.entity.SchoolQuestionSection;
import com.denkensol.universaladmission.entity.SchoolTerm;
import com.denkensol.universaladmission.entity.StudentApplication;
import com.denkensol.universaladmission.entity.StudentDocument;
import com.denkensol.universaladmission.entity.StudentSchoolDocument;
import com.denkensol.universaladmission.requestresponse.SchoolApplicantsResponse;
import com.denkensol.universaladmission.requestresponse.SchoolDegreeResponse;
import com.denkensol.universaladmission.requestresponse.SchoolDocumentRequestResponse;
import com.denkensol.universaladmission.requestresponse.SchoolProspectsResponse;
import com.denkensol.universaladmission.requestresponse.SchoolQuestionAnswerOptionResponse;
import com.denkensol.universaladmission.requestresponse.SchoolQuestionRequest;
import com.denkensol.universaladmission.requestresponse.SchoolQuestionsResponse;
import com.denkensol.universaladmission.requestresponse.SchoolQuestionsSubmitRequest;
import com.denkensol.universaladmission.requestresponse.SchoolSearchCriteria;
import com.denkensol.universaladmission.requestresponse.SchoolSearchResponse;
import com.denkensol.universaladmission.requestresponse.SchoolSectionQuestionsResponse;
import com.denkensol.universaladmission.requestresponse.SchoolTermRequest;
import com.denkensol.universaladmission.requestresponse.SchoolTermResponse;
import com.denkensol.universaladmission.requestresponse.StudentApplicationResponse;
import com.denkensol.universaladmission.requestresponse.StudentDocumentResponse;
import com.denkensol.universaladmission.requestresponse.StudentProfileRequestResponse;
import com.denkensol.universaladmission.service.SchoolService;
import com.denkensol.universaladmission.template.AmazonS3Template;
import com.denkensol.universaladmission.util.CommonUtil;
import com.google.gson.Gson;

import ch.qos.logback.core.net.SyslogOutputStream;

@Service
public class SchoolServiceImpl implements SchoolService {

	@Autowired
	private Environment env;

	@Autowired
	SchoolDao schoolDao;

	@Autowired
	SchoolDegreeDao schoolDegreeDao;

	@Autowired
	SchoolTermDao schoolTermDao;

	@Autowired
	AccountDao accountDao;

	@Autowired
	SchoolQuestionSectionDao schoolQuestionSectionDao;

	@Autowired
	SchoolQuestionDao schoolQuestionDao;

	@Autowired
	StudentApplicationDao studentApplicationDao;

	@Autowired
	SchoolQuestionAnswerDao schoolQuestionAnswerDao;

	@Autowired
	SchoolQuestionAnswerOptionDao schoolQuestionAnswerOptionDao;

	@Autowired
	CountryDao countryDao;

	@Autowired
	SchoolDocumentDao schoolDocumentDao;

	@Autowired
	AmazonS3Template amazonS3Template;

	@Autowired
	StudentSchoolDocumentDao studentSchoolDocumentDao;

	@Autowired
	StudentProfileDao studentProfileDao;

	@Autowired
	StudentDocumentsDao studentDocumentsDao;

	// List<String> filesListInDir = new ArrayList<String>();

	// List<String> filesListInDir;

	@Override
	@Transactional
	public List<SchoolSearchResponse> getSchools(SchoolSearchCriteria schoolSearchCriteria, HttpServletRequest req) {
		List<SchoolSearchResponse> schools = new ArrayList<SchoolSearchResponse>();
		List<SchoolSearchResponse> adminSchools = new ArrayList<SchoolSearchResponse>();
		schools = schoolDao.getSchools();
		// newly added

		HttpSession session = req.getSession(true);
		Account accountDetails = (Account) session.getAttribute("account");
		String domain = null;
		Long accountGuid = null;
		Account accountData = null;
		String accountType = null;
		if (accountDetails != null) {
			accountData = (Account) accountDetails;
			String UserEmail = accountData.getEmailAddress();
			String[] loggedInAdminDomain = UserEmail.split("@");
			domain = loggedInAdminDomain[1];
			accountType = accountData.getType();
		}
		if (accountType != null && !"".equalsIgnoreCase(accountType) && "SCHOOL_ADMIN".equalsIgnoreCase(accountType)) {
			adminSchools = getAdminSchools(schools, req);
			return adminSchools;
		} else {
			return schools;
		}
		// end

		// return schools;
	}

	public List<SchoolSearchResponse> getAdminSchools(List<SchoolSearchResponse> schools, HttpServletRequest req) {
		List<SchoolSearchResponse> adminSchools = new ArrayList<SchoolSearchResponse>();

		HttpSession session = req.getSession(true);
		Account accountDetails = (Account) session.getAttribute("account");
		String domain = null;
		Long accountGuid = null;
		Account accountData = null;
		String accountType = null;
		if (accountDetails != null) {
			accountData = (Account) accountDetails;
			String UserEmail = accountData.getEmailAddress();
			String[] loggedInAdminDomain = UserEmail.split("@");
			domain = loggedInAdminDomain[1];
			accountType = accountData.getType();
		}
		for (int i = 0; i < schools.size(); i++) {
			if ((schools.get(i).getEmailDomainOne() != null && !"".equalsIgnoreCase(schools.get(i).getEmailDomainOne())
					&& schools.get(i).getEmailDomainOne().contains(domain))
					|| (schools.get(i).getEmailDomainTwo() != null
							&& !"".equalsIgnoreCase(schools.get(i).getEmailDomainTwo())
							&& schools.get(i).getEmailDomainTwo().contains(domain))
					|| (schools.get(i).getEmailDomainThree() != null
							&& !"".equalsIgnoreCase(schools.get(i).getEmailDomainThree())
							&& schools.get(i).getEmailDomainThree().contains(domain))) {
				adminSchools.add(schools.get(i));
			}
		}

		return adminSchools;
	}

	@Override
	@Transactional
	public List<SchoolDegreeResponse> getSchoolDegrees(Long schoolId) {
		List<SchoolDegreeResponse> schoolDerees = new ArrayList<SchoolDegreeResponse>();
		if (schoolId != null) {
			schoolDerees = schoolDegreeDao.getSchoolDegreesBySchool(schoolId);
		} else {
			schoolDerees = schoolDegreeDao.getAllSchoolsDegrees();
		}
		return schoolDerees;
	}

	@Override
	@Transactional
	public List<String> addColleges(List<School> addColleges, HttpServletRequest servletRequest) {
		List<String> schoolNames = new ArrayList<String>();
		HttpSession session = servletRequest.getSession(true);
		Account account = (Account) session.getAttribute("account");
		List<School> addedSchools = new ArrayList<School>();
		List<StudentApplication> studentApplications = new ArrayList<StudentApplication>();
		if (CommonUtil.isListNotNullAndNotEmpty(addColleges)) {
			for (School eachSchoolGuid : addColleges) {
				School eachSchool = schoolDao.getSchoolByGuid(eachSchoolGuid.getSchoolGuid());
				addedSchools.add(eachSchool);
				schoolNames.add(eachSchool.getSchoolName());
				StudentApplication studentApplication = new StudentApplication();
				studentApplication.setSchool(eachSchool);
				studentApplication.setAccount(account);
				studentApplication.setSeqNo(1);
				studentApplications.add(studentApplication);
			}
		}
		account.setSchools(addedSchools);
		accountDao.saveAccount(account);
		studentApplicationDao.saveStudentApplications(studentApplications);
		return schoolNames;
	}

	@Override
	@Transactional
	public List<SchoolSearchResponse> getStudentSchools(HttpServletRequest request) {

		List<SchoolSearchResponse> studentSchoolsResponse = new ArrayList<SchoolSearchResponse>();
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		List<School> studentSchools = account.getSchools();
		if (CommonUtil.isListNotNullAndNotEmpty(studentSchools)) {
			for (School eachSchool : studentSchools) {
				SchoolSearchResponse schoolData = new SchoolSearchResponse();
				schoolData.setSchoolGuid(BigInteger.valueOf(eachSchool.getSchoolGuid()));
				schoolData.setSchoolLogoURL(eachSchool.getSchoolLogoURL());
				schoolData.setSchoolName(eachSchool.getSchoolName());
				StudentApplication studentApplication = studentApplicationDao
						.getStudentApplicationBySchool(eachSchool.getSchoolGuid(), account.getGuid());
				if (studentApplication != null) {
					schoolData.setApplicationStatus(studentApplication.getApplicationStatus());
				}
				studentSchoolsResponse.add(schoolData);
			}
		}
		return studentSchoolsResponse;
	}

	@Override
	@Transactional
	public SchoolSearchResponse getSchoolInfo(Long schoolGuid, HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");

		SchoolSearchResponse schoolData = new SchoolSearchResponse();
		School eachSchool = schoolDao.getSchoolByGuid(schoolGuid);
		schoolData.setSchoolInfo(eachSchool.getSchoolInfo());
		schoolData.setAddressLineOne(eachSchool.getAddressLineOne());
		schoolData.setAddressLineTwo(eachSchool.getAddressLineTwo());
		schoolData.setCity(eachSchool.getCity());
		schoolData.setContactPerson(eachSchool.getContactPerson());
		schoolData.setEmailAddress(eachSchool.getEmailAddress());
		schoolData.setGmatScoreSubmissionSchoolCode(eachSchool.getGmatScoreSubmissionSchoolCode());
		schoolData.setGreScoreSubmissionSchoolCode(eachSchool.getGreScoreSubmissionSchoolCode());
		schoolData.setInternationAppFees(eachSchool.getInternationAppFees());
		schoolData.setInternationCredentialEvalFees(eachSchool.getInternationCredentialEvalFees());
		schoolData.setMailingFee(eachSchool.getMailingFee());
		schoolData.setMinimumGMATcore(eachSchool.getMinimumGMATcore());
		schoolData.setMinimumGPA(eachSchool.getMinimumGPA());
		schoolData.setMinimumGREScore(eachSchool.getMinimumGREScore());
		schoolData.setMinimumTOEFLScore(eachSchool.getMinimumTOEFLScore());
		schoolData.setPhoneNumber(eachSchool.getPhoneNumber());
		schoolData.setRecLettersRequires(eachSchool.getRecLettersRequires());
		schoolData.setRequiresPassport(eachSchool.getRequiresPassport());
		schoolData.setResumeRequires(eachSchool.getResumeRequires());
		Long countryGuid = null;
		if (eachSchool.getSchoolCountry() != null) {
			countryGuid = eachSchool.getSchoolCountry().getGuid();
		}
		schoolData.setSchoolCountryGuid(countryGuid);
		schoolData.setSchoolGuid(BigInteger.valueOf(eachSchool.getSchoolGuid()));
		schoolData.setSchoolId(eachSchool.getSchoolId());
		schoolData.setSchoolLogoURL(eachSchool.getSchoolLogoURL());
		schoolData.setSchoolName(eachSchool.getSchoolName());
		schoolData.setSolvCERTRequires(eachSchool.getSolvCERTRequires());
		schoolData.setSopRequires(eachSchool.getSopRequires());
		schoolData.setState(eachSchool.getState());
		schoolData.setToeflScoreSubmissionSchoolCode(eachSchool.getToeflScoreSubmissionSchoolCode());
		schoolData.setTranscriptsRequires(eachSchool.getTranscriptsRequires());
		schoolData.setRequiresEAD(eachSchool.getRequiresEAD());
		schoolData.setRequiresI20(eachSchool.getRequiresI20());
		schoolData.setRequiresF1Visa(eachSchool.getRequiresF1Visa());
		schoolData.setRequiresTOFELScore(eachSchool.getRequiresTOFELScore());
		schoolData.setWebsite(eachSchool.getWebsite());
		schoolData.setZipCode(eachSchool.getZipCode());
		schoolData.setTuitionFeesLink(eachSchool.getTuitionFeesLink());
		schoolData.setApplicationCheckListLink(eachSchool.getApplicationCheckListLink());
		schoolData.setEmailDomainOne(eachSchool.getEmailDomainOne());
		schoolData.setEmailDomainTwo(eachSchool.getEmailDomainTwo());
		schoolData.setEmailDomainThree(eachSchool.getEmailDomainThree());
		List<SchoolTermResponse> schoolTerms = schoolTermDao.getSchoolTermsBySchool(schoolGuid);
		schoolData.setSchoolTerms(schoolTerms);

		List<SchoolDegreeResponse> schoolDegrees = schoolDegreeDao.getSchoolDegreesBySchool(schoolGuid);
		schoolData.setSchoolDegrees(schoolDegrees);
		List<SchoolDocument> schoolDocuments = eachSchool.getSchoolDocuments();
		List<SchoolDocumentRequestResponse> schoolDocumentRequestResponses = null;
		if (CommonUtil.isListNotNullAndNotEmpty(schoolDocuments)) {
			schoolDocumentRequestResponses = new ArrayList<SchoolDocumentRequestResponse>();
			for (SchoolDocument eachDocumets : schoolDocuments) {
				SchoolDocumentRequestResponse schoolDocumentRequestResponse = new SchoolDocumentRequestResponse();
				schoolDocumentRequestResponse.setSchoolDocumentGuid(eachDocumets.getSchoolDocumentGuid());
				schoolDocumentRequestResponse.setSchoolGuid(eachDocumets.getSchool().getSchoolGuid());
				schoolDocumentRequestResponse.setDegreeList(eachDocumets.getDegreeList());
				schoolDocumentRequestResponse.setDocumentName(eachDocumets.getDocumentName());
				schoolDocumentRequestResponse.setDocumentPath(eachDocumets.getDocumentPath());
				schoolDocumentRequestResponse.setDocumentURL(eachDocumets.getDocumentURL());
				schoolDocumentRequestResponse.setIsMandatory(eachDocumets.getIsMandatory());
				schoolDocumentRequestResponses.add(schoolDocumentRequestResponse);

			}

		}
		schoolData.setSchoolDocuments(schoolDocumentRequestResponses);
		StudentApplicationResponse studentApplicationResponse = new StudentApplicationResponse();
		StudentApplication studentApplication = studentApplicationDao.getStudentApplicationBySchool(schoolGuid,
				account.getGuid());
		if (studentApplication != null) {

			studentApplicationResponse
					.setSchoolApplicationGuid(BigInteger.valueOf(studentApplication.getSchoolApplicationGuid()));
			studentApplicationResponse.setSchoolQuestionsStatus(studentApplication.getSchoolQuestionsStatus());
			studentApplicationResponse.setSchoolDocumentsStatus(studentApplication.getSchoolDocumentsStatus());
			studentApplicationResponse.setIsTransferStudent(studentApplication.getIsTransferStudent());
			Long schoolTermGuid = null;
			if (studentApplication.getSchoolTerm() != null) {
				schoolTermGuid = studentApplication.getSchoolTerm().getSchoolTermGuid();
			}
			studentApplicationResponse.setSchoolTermGuid(schoolTermGuid);
			studentApplicationResponse.setAcceptTermsAndConditions(studentApplication.getAcceptTermsAndConditions());
			schoolData.setApplicationStatus(studentApplication.getApplicationStatus());
			Long schoolDegreeGuid = null;
			if (studentApplication.getSchoolDegree() != null) {
				schoolDegreeGuid = studentApplication.getSchoolDegree().getSchoolDegreeGuid();
			}
			studentApplicationResponse.setSchoolDegreeGuid(schoolDegreeGuid);
		}
		schoolData.setStudentApplicationResponse(studentApplicationResponse);
		return schoolData;
	}

	@Override
	@Transactional
	public void deleteStudentSchool(Long schoolGuid, HttpServletRequest request) {

		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		List<School> studentSchools = account.getSchools();
		School eachSchool = schoolDao.getSchoolByGuid(schoolGuid);
		studentSchools.remove(eachSchool);
		account.setSchools(studentSchools);
		accountDao.saveAccount(account);
	}

	@Override
	@Transactional
	public List<SchoolSectionQuestionsResponse> getSchoolSectionQuestions(Long schoolGuid, HttpServletRequest request) {
		List<SchoolSectionQuestionsResponse> schoolSectionQuestions = new ArrayList<SchoolSectionQuestionsResponse>();
		List<SchoolQuestionSection> schoolQuestionSections = schoolQuestionSectionDao
				.getSchoolSectionQuestions(schoolGuid);

		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		StudentApplication studentApplication = null;
		if (CommonUtil.isListNotNullAndNotEmpty(schoolQuestionSections)) {
			for (SchoolQuestionSection schoolQuestionSection : schoolQuestionSections) {
				SchoolSectionQuestionsResponse schoolSectionQuestionsResponse = new SchoolSectionQuestionsResponse();
				studentApplication = studentApplicationDao.getStudentApplicationBySchool(schoolGuid, account.getGuid());
				if (studentApplication != null) {
					schoolSectionQuestionsResponse.setIsTransferStudent(studentApplication.getIsTransferStudent());
				}
				schoolSectionQuestionsResponse.setSectionName(schoolQuestionSection.getSectionName());
				schoolSectionQuestionsResponse
						.setSchoolQuestionSectionGuid(schoolQuestionSection.getSchoolQuestionSectionGuid());
				List<SchoolQuestion> sectionQuestions = schoolQuestionSection.getSchoolQuestions();
				List<SchoolQuestionsResponse> questions = getQuestionsData(sectionQuestions, account.getGuid());
				schoolSectionQuestionsResponse.setQuestions(questions);
				schoolSectionQuestions.add(schoolSectionQuestionsResponse);
			}
		} else {
			studentApplication = studentApplicationDao.getStudentApplicationBySchool(schoolGuid, account.getGuid());
			if (studentApplication != null) {
				SchoolSectionQuestionsResponse schoolSectionQuestionsResponse = new SchoolSectionQuestionsResponse();
				schoolSectionQuestionsResponse.setIsTransferStudent(studentApplication.getIsTransferStudent());
				schoolSectionQuestions.add(schoolSectionQuestionsResponse);

			}

		}
		return schoolSectionQuestions;
	}

	@Override
	@Transactional
	public Boolean submitSchoolQuestions(SchoolQuestionsSubmitRequest schoolQuestionsSubmitRequest,
			HttpServletRequest request) {
		SchoolTerm schoolTerm = schoolTermDao.getSchoolTermByGuid(schoolQuestionsSubmitRequest.getTermId());
		SchoolDegree schoolDegree = schoolDegreeDao.getSchoolDegreeByGuid(schoolQuestionsSubmitRequest.getDegreeId());
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		StudentApplication studentApplication = studentApplicationDao
				.getStudentApplicationBySchool(schoolQuestionsSubmitRequest.getSchoolGuid(), account.getGuid());
		studentApplication.setSchoolDegree(schoolDegree);
		studentApplication.setSchoolTerm(schoolTerm);
		studentApplication.setSchoolQuestionsStatus("Completed");
		studentApplication.setIsTransferStudent(schoolQuestionsSubmitRequest.getIsTransferStudent());
		studentApplicationDao.saveStudentApplication(studentApplication);

		List<SchoolQuestionAnswer> answersList = new ArrayList<SchoolQuestionAnswer>();
		if (CommonUtil.isListNotNullAndNotEmpty(schoolQuestionsSubmitRequest.getQuestionAnswers())) {
			for (SchoolQuestionRequest schoolQuestionRequest : schoolQuestionsSubmitRequest.getQuestionAnswers()) {

				SchoolQuestionAnswer schoolQuestionAnswer = new SchoolQuestionAnswer();
				if (schoolQuestionRequest.getSchoolQuestionAnswerGuid() != null) {
					schoolQuestionAnswer = schoolQuestionAnswerDao
							.getSchoolQuestionAnswerById(schoolQuestionRequest.getSchoolQuestionAnswerGuid());
				}
				schoolQuestionAnswer.setAnswer(schoolQuestionRequest.getQuestionAnswer());
				SchoolQuestion schoolQuestion = schoolQuestionDao
						.getSchoolQuestionById(schoolQuestionRequest.getQuestionId());
				schoolQuestionAnswer.setSchoolQuestion(schoolQuestion);
				schoolQuestionAnswer.setAccount(account);
				answersList.add(schoolQuestionAnswer);
			}
		}

		schoolQuestionAnswerDao.saveAnswersList(answersList);
		return true;
	}

	@Override
	@Transactional
	public void submitAttachmentSchoolQuestions(MultipartHttpServletRequest request,
			HttpServletRequest servletRequest) {
		// TODO Auto-generated method stub

		Iterator<String> iterator = request.getFileNames();
		String uploadsDir = env.getProperty("student.question.documents-upload-path");
		if (!new File(uploadsDir).exists()) {
			new File(uploadsDir).mkdir();
		}

		File resourceFileName = new File(uploadsDir);
		uploadsDir = resourceFileName.getAbsolutePath() + "/";

		List<SchoolQuestionAnswer> answersList = new ArrayList<SchoolQuestionAnswer>();
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String questionAnswerGuid = key.split("_")[2];
			String questionGuid = key.split("_")[1];

			MultipartFile multipartFile = request.getFile(key);
			String fileName = multipartFile.getOriginalFilename();

			String fileId = CommonUtil.generateSalt(10) + fileName;
			String filePath = uploadsDir + fileId;
			File dest = new File(filePath);

			SchoolQuestionAnswer schoolQuestionAnswer = new SchoolQuestionAnswer();
			if (!questionAnswerGuid.equalsIgnoreCase("null") && questionAnswerGuid != null
					&& !questionAnswerGuid.isEmpty()) {
				schoolQuestionAnswer = schoolQuestionAnswerDao
						.getSchoolQuestionAnswerById(Long.valueOf(questionAnswerGuid));
			}
			schoolQuestionAnswer.setAnswer(fileId);
			SchoolQuestion schoolQuestion = schoolQuestionDao.getSchoolQuestionById(Long.valueOf(questionGuid));
			schoolQuestionAnswer.setSchoolQuestion(schoolQuestion);
			schoolQuestionAnswer.setAccount(account);
			answersList.add(schoolQuestionAnswer);

			try {
				multipartFile.transferTo(dest);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		schoolQuestionAnswerDao.saveAnswersList(answersList);

	}

	private List<SchoolQuestionsResponse> getQuestionsData(List<SchoolQuestion> sectionQuestions, Long accountGuid) {
		List<SchoolQuestionsResponse> questions = new ArrayList<SchoolQuestionsResponse>();
		if (CommonUtil.isListNotNullAndNotEmpty(sectionQuestions)) {
			for (SchoolQuestion schoolQuestion : sectionQuestions) {
				SchoolQuestionsResponse schoolQuestionsResponse = new SchoolQuestionsResponse();
				schoolQuestionsResponse.setSchoolQuestionGuid(schoolQuestion.getSchoolQuestionGuid());

				SchoolQuestionAnswer schoolQuestionAnswer = schoolQuestionAnswerDao
						.getSchoolQuestionAnswerByQuestionIdAndStudentId(schoolQuestion.getSchoolQuestionGuid(),
								accountGuid);
				String schoolQuestionAnswerStr = "";
				Long schoolQuestionAnswerGuid = null;
				if (schoolQuestionAnswer != null) {
					schoolQuestionAnswerStr = schoolQuestionAnswer.getAnswer();
					schoolQuestionAnswerGuid = schoolQuestionAnswer.getSchoolQuestionAnswerGuid();
				}
				schoolQuestionsResponse.setDegreeList(schoolQuestion.getDegreeList());
				schoolQuestionsResponse.setQuestionTitle(schoolQuestion.getQuestionTitle());
				schoolQuestionsResponse.setQuestionLongText(schoolQuestion.getQuestionLongText());
				schoolQuestionsResponse.setQuestionType(schoolQuestion.getQuestionType());
				schoolQuestionsResponse.setQuestionMandatory(schoolQuestion.getQuestionMandatory());

				List<SchoolQuestion> childQuestions = schoolQuestion.getChildQuestions();
				List<SchoolQuestion> tableQuestions = schoolQuestion.getTableChildQuestions();

				schoolQuestionsResponse.setChildQuestions(getChildQuestionsData(childQuestions, accountGuid));
				schoolQuestionsResponse.setTableQuestions(getChildQuestionsData(tableQuestions, accountGuid));

				if (schoolQuestion.getQuestionType().equalsIgnoreCase(ApplicationConstants.QUESTION_TYPE_ATTACHMENT)) {

					String uploadsDir = env.getProperty("student.question.documents-upload-path");

					File resourceFileName = new File(uploadsDir);
					uploadsDir = resourceFileName.getAbsolutePath() + "/";

					if (schoolQuestionAnswerStr != null && !schoolQuestionAnswerStr.isEmpty()) {
						schoolQuestionAnswerStr = uploadsDir + schoolQuestionAnswerStr;
					}

				}

				schoolQuestionsResponse.setQuestionAnswer(schoolQuestionAnswerStr);
				schoolQuestionsResponse.setQuestionAnswerGuid(schoolQuestionAnswerGuid);

				BigInteger parentQuestionId = null;
				if (schoolQuestion.getParentQuestion() != null) {
					parentQuestionId = BigInteger.valueOf(schoolQuestion.getParentQuestion().getSchoolQuestionGuid());
				}

				BigInteger tableQuestionId = null;
				if (schoolQuestion.getTableQuestion() != null) {
					tableQuestionId = BigInteger.valueOf(schoolQuestion.getTableQuestion().getSchoolQuestionGuid());
				}
				schoolQuestionsResponse.setTableQuestionId(tableQuestionId);
				schoolQuestionsResponse.setParentQuestionId(parentQuestionId);
				schoolQuestionsResponse
						.setParentQuestionAnswerCondition(schoolQuestion.getParentQuestionAnswerCondition());
				List<SchoolQuestionAnswerOptionResponse> answerOptions = new ArrayList<SchoolQuestionAnswerOptionResponse>();
				List<SchoolQuestionAnswerOption> questionAnswersList = schoolQuestion.getSchoolQuestionAnswerOptions();
				if (CommonUtil.isListNotNullAndNotEmpty(questionAnswersList)) {
					for (SchoolQuestionAnswerOption schoolQuestionAnswerOption : questionAnswersList) {
						SchoolQuestionAnswerOptionResponse schoolQuestionAnswerOptionResponse = new SchoolQuestionAnswerOptionResponse();
						schoolQuestionAnswerOptionResponse.setAnswer(schoolQuestionAnswerOption.getAnswer());
						schoolQuestionAnswerOptionResponse.setSchoolQuestionAnswerOptionGuid(
								schoolQuestionAnswerOption.getSchoolQuestionAnswerOptionGuid());
						schoolQuestionAnswerOptionResponse.setSeqNo(schoolQuestionAnswerOption.getSeqNo());
						answerOptions.add(schoolQuestionAnswerOptionResponse);
					}
				}
				schoolQuestionsResponse.setAnswerOptions(answerOptions);
				if (schoolQuestionsResponse.getParentQuestionId() == null
						&& schoolQuestionsResponse.getTableQuestionId() == null) {
					questions.add(schoolQuestionsResponse);
				}
			}
		}
		return questions;
	}

	private List<SchoolQuestionsResponse> getChildQuestionsData(List<SchoolQuestion> sectionQuestions,
			Long accountGuid) {
		List<SchoolQuestionsResponse> questions = new ArrayList<SchoolQuestionsResponse>();
		if (CommonUtil.isListNotNullAndNotEmpty(sectionQuestions)) {
			for (SchoolQuestion schoolQuestion : sectionQuestions) {
				SchoolQuestionsResponse schoolQuestionsResponse = new SchoolQuestionsResponse();
				schoolQuestionsResponse.setSchoolQuestionGuid(schoolQuestion.getSchoolQuestionGuid());

				SchoolQuestionAnswer schoolQuestionAnswer = schoolQuestionAnswerDao
						.getSchoolQuestionAnswerByQuestionIdAndStudentId(schoolQuestion.getSchoolQuestionGuid(),
								accountGuid);
				String schoolQuestionAnswerStr = "";
				Long schoolQuestionAnswerGuid = null;
				if (schoolQuestionAnswer != null) {
					schoolQuestionAnswerStr = schoolQuestionAnswer.getAnswer();
					schoolQuestionAnswerGuid = schoolQuestionAnswer.getSchoolQuestionAnswerGuid();
				}

				schoolQuestionsResponse.setQuestionTitle(schoolQuestion.getQuestionTitle());
				schoolQuestionsResponse.setQuestionLongText(schoolQuestion.getQuestionLongText());
				schoolQuestionsResponse.setQuestionType(schoolQuestion.getQuestionType());
				schoolQuestionsResponse.setQuestionMandatory(schoolQuestion.getQuestionMandatory());

				List<SchoolQuestion> childQuestions = schoolQuestion.getChildQuestions();
				List<SchoolQuestion> tableQuestions = schoolQuestion.getTableChildQuestions();

				schoolQuestionsResponse.setChildQuestions(getQuestionsData(childQuestions, accountGuid));
				schoolQuestionsResponse.setTableQuestions(getQuestionsData(tableQuestions, accountGuid));

				if (schoolQuestion.getQuestionType().equalsIgnoreCase(ApplicationConstants.QUESTION_TYPE_ATTACHMENT)) {
					String uploadsDir = env.getProperty("student.question.documents-upload-path");

					File resourceFileName = new File(uploadsDir);
					uploadsDir = resourceFileName.getAbsolutePath() + "/";
					if (schoolQuestionAnswerStr != null && !schoolQuestionAnswerStr.isEmpty()) {
						schoolQuestionAnswerStr = uploadsDir + schoolQuestionAnswerStr;
					}
				}

				schoolQuestionsResponse.setQuestionAnswer(schoolQuestionAnswerStr);
				schoolQuestionsResponse.setQuestionAnswerGuid(schoolQuestionAnswerGuid);

				BigInteger parentQuestionId = null;
				if (schoolQuestion.getParentQuestion() != null) {
					parentQuestionId = BigInteger.valueOf(schoolQuestion.getParentQuestion().getSchoolQuestionGuid());
				}

				BigInteger tableQuestionId = null;
				if (schoolQuestion.getTableQuestion() != null) {
					tableQuestionId = BigInteger.valueOf(schoolQuestion.getTableQuestion().getSchoolQuestionGuid());
				}
				schoolQuestionsResponse.setTableQuestionId(tableQuestionId);
				schoolQuestionsResponse.setParentQuestionId(parentQuestionId);
				schoolQuestionsResponse
						.setParentQuestionAnswerCondition(schoolQuestion.getParentQuestionAnswerCondition());
				List<SchoolQuestionAnswerOptionResponse> answerOptions = new ArrayList<SchoolQuestionAnswerOptionResponse>();
				List<SchoolQuestionAnswerOption> questionAnswersList = schoolQuestion.getSchoolQuestionAnswerOptions();
				if (CommonUtil.isListNotNullAndNotEmpty(questionAnswersList)) {
					for (SchoolQuestionAnswerOption schoolQuestionAnswerOption : questionAnswersList) {
						SchoolQuestionAnswerOptionResponse schoolQuestionAnswerOptionResponse = new SchoolQuestionAnswerOptionResponse();
						schoolQuestionAnswerOptionResponse.setAnswer(schoolQuestionAnswerOption.getAnswer());
						schoolQuestionAnswerOptionResponse.setSchoolQuestionAnswerOptionGuid(
								schoolQuestionAnswerOption.getSchoolQuestionAnswerOptionGuid());
						schoolQuestionAnswerOptionResponse.setSeqNo(schoolQuestionAnswerOption.getSeqNo());
						answerOptions.add(schoolQuestionAnswerOptionResponse);
					}
				}
				schoolQuestionsResponse.setAnswerOptions(answerOptions);
				questions.add(schoolQuestionsResponse);
			}
		}
		return questions;
	}

	@Override
	@Transactional
	public Long saveSchoolInfo(SchoolSearchResponse schoolSearchResponse) {
		Long schoolGuid = schoolSearchResponse.getSchoolGuid().longValue();
		School school = new School();
		if (schoolGuid != null) {
			school = schoolDao.getSchoolByGuid(schoolGuid);
			schoolSearchResponse.setSchoolId(school.getSchoolId());
			schoolSearchResponse.setSchoolName(school.getSchoolName());
			schoolSearchResponse.setSchoolLogoURL(school.getSchoolLogoURL());
			schoolSearchResponse.setSchoolInfo(school.getSchoolInfo());
		}
		String schoolJsonStr = new Gson().toJson(schoolSearchResponse);
		school = new Gson().fromJson(schoolJsonStr, School.class);
		if (schoolSearchResponse.getSchoolCountryGuid() != null) {
			school.setSchoolCountry(countryDao.getCountryById(schoolSearchResponse.getSchoolCountryGuid()));
		}
		schoolDao.saveUpdateSchool(school);
		return schoolGuid;
	}

	@Override
	@Transactional
	public void saveSchoolInfoContent(SchoolSearchResponse schoolSearchResponse) {
		Long schoolGuid = schoolSearchResponse.getSchoolGuid().longValue();
		School school = new School();
		if (schoolGuid != null) {
			school = schoolDao.getSchoolByGuid(schoolGuid);
		}
		school.setSchoolInfo(schoolSearchResponse.getSchoolInfo());
		schoolDao.saveUpdateSchool(school);
	}

	@Override
	@Transactional
	public List<SchoolSectionQuestionsResponse> getSchoolQuestionSections(Long schoolGuid) {
		List<SchoolSectionQuestionsResponse> schoolSectionQuestions = new ArrayList<SchoolSectionQuestionsResponse>();
		List<SchoolQuestionSection> schoolQuestionSections = schoolQuestionSectionDao
				.getSchoolSectionQuestions(schoolGuid);

		if (CommonUtil.isListNotNullAndNotEmpty(schoolQuestionSections)) {
			for (SchoolQuestionSection schoolQuestionSection : schoolQuestionSections) {
				SchoolSectionQuestionsResponse schoolSectionQuestionsResponse = new SchoolSectionQuestionsResponse();
				schoolSectionQuestionsResponse.setSectionName(schoolQuestionSection.getSectionName());
				schoolSectionQuestionsResponse
						.setSchoolQuestionSectionGuid(schoolQuestionSection.getSchoolQuestionSectionGuid());
				schoolSectionQuestions.add(schoolSectionQuestionsResponse);
			}
		}
		return schoolSectionQuestions;
	}

	@Override
	@Transactional
	public void saveQuestionSection(SchoolSectionQuestionsResponse schoolSectionQuestionsResponse) {
		SchoolQuestionSection schoolQuestionSection = new SchoolQuestionSection();
		if (schoolSectionQuestionsResponse.getSchoolQuestionSectionGuid() != null) {
			schoolQuestionSection = schoolQuestionSectionDao
					.getSchoolSectionById(schoolSectionQuestionsResponse.getSchoolQuestionSectionGuid());
		}
		schoolQuestionSection.setSectionName(schoolSectionQuestionsResponse.getSectionName());
		School school = schoolDao.getSchoolByGuid(schoolSectionQuestionsResponse.getSchoolGuid());
		schoolQuestionSection.setSchool(school);
		schoolQuestionSection.setSeqNo(schoolSectionQuestionsResponse.getSeqNo());
		schoolQuestionSectionDao.saveSchoolQuestionSection(schoolQuestionSection);
	}

	@Override
	@Transactional
	public void deleteSchoolDegree(Long schoolDegreeGuid) {

		SchoolDegree schoolDegree = schoolDegreeDao.getSchoolDegreeByGuid(schoolDegreeGuid);
		schoolDegreeDao.deleteSchoolDegree(schoolDegree);

	}

	@Override
	@Transactional
	public void saveSchoolDegree(SchoolDegreeResponse schoolDegreeResponse) {
		SchoolDegree schoolDegree = new SchoolDegree();
		if (schoolDegreeResponse.getSchoolDegreeGuid() != null) {
			schoolDegree = schoolDegreeDao
					.getSchoolDegreeByGuid(schoolDegreeResponse.getSchoolDegreeGuid().longValue());
		}
		String schoolDegreeJsonStr = new Gson().toJson(schoolDegreeResponse);
		schoolDegree = new Gson().fromJson(schoolDegreeJsonStr, SchoolDegree.class);
		School school = schoolDao.getSchoolByGuid(schoolDegreeResponse.getSchoolGuid());
		schoolDegree.setSchool(school);
		schoolDegreeDao.saveSchoolDegree(schoolDegree);

	}

	@Override
	@Transactional
	public SchoolDegreeResponse getSchoolDegree(Long schoolDegreeGuid) {
		SchoolDegree schoolDegree = schoolDegreeDao.getSchoolDegreeByGuid(schoolDegreeGuid);
		SchoolDegreeResponse schoolDegreeResponse = new SchoolDegreeResponse();
		schoolDegreeResponse.setAddressLine3(schoolDegree.getAddressLine3());
		schoolDegreeResponse.setAddressLineOne(schoolDegree.getAddressLineOne());
		schoolDegreeResponse.setAddressLineTwo(schoolDegree.getAddressLineTwo());
		schoolDegreeResponse.setCity(schoolDegree.getCity());
		schoolDegreeResponse.setContactPerson(schoolDegree.getContactPerson());
		schoolDegreeResponse.setDegreeDepartment(schoolDegree.getDegreeDepartment());
		schoolDegreeResponse.setDegreeOffered(schoolDegree.getDegreeOffered());
		schoolDegreeResponse.setDepartmentMinGPA(schoolDegree.getDepartmentMinGPA());
		schoolDegreeResponse.setDepartmentMinGREScore(schoolDegree.getDepartmentMinGREScore());
		schoolDegreeResponse.setDepartmentMinTOEFLScore(schoolDegree.getDepartmentMinTOEFLScore());
		schoolDegreeResponse.setEmailAddress(schoolDegree.getEmailAddress());
		schoolDegreeResponse.setPhoneNumber(schoolDegree.getPhoneNumber());
		schoolDegreeResponse.setSchoolDegreeGuid(BigInteger.valueOf(schoolDegree.getSchoolDegreeGuid()));
		schoolDegreeResponse.setSchoolGuid(schoolDegree.getSchool().getSchoolGuid());
		schoolDegreeResponse.setState(schoolDegree.getState());
		schoolDegreeResponse.setZipCode(schoolDegree.getZipCode());
		schoolDegreeResponse.setDeptMinGMATScore(schoolDegree.getDeptMinGMATScore());
		schoolDegreeResponse.setRequiresGMATScore(schoolDegree.getRequiresGMATScore());
		schoolDegreeResponse.setRequiresGREScore(schoolDegree.getRequiresGREScore());
		return schoolDegreeResponse;

	}

	@Override
	@Transactional
	public List<SchoolTermResponse> getSchoolTerms(Long schoolId) {
		List<SchoolTermResponse> termsList = new ArrayList<SchoolTermResponse>();
		termsList = schoolTermDao.getSchoolTermsBySchool(schoolId);
		return termsList;
	}

	@Override
	@Transactional
	public void saveSchoolTerm(SchoolTermRequest schoolTermRequest) throws ParseException {
		SchoolTerm schoolTerm = new SchoolTerm();
		if (schoolTermRequest.getSchoolTermGuid() != null) {
			schoolTerm = schoolTermDao.getSchoolTermByGuid(schoolTermRequest.getSchoolTermGuid().longValue());
			schoolTerm.setDegree(schoolTerm.getDegree());
		}

		String applicationDeadLineDate = schoolTermRequest.getApplicationDeadLineDate();
		String earlyDecisionDeadLineDate = schoolTermRequest.getEarlyDecisionDeadLineDate();
		String applicationSubmissionStartDate = schoolTermRequest.getApplicationSubmissionStartDate();
		String documentsDeadLineDate = schoolTermRequest.getDocumentsDeadLineDate();

		Date applicationDeadLineDate1 = null;
		java.sql.Date applicationDeadLineDateSQL = null;
		if (applicationDeadLineDate != null && !applicationDeadLineDate.isEmpty()) {
			applicationDeadLineDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(applicationDeadLineDate);
			applicationDeadLineDateSQL = new java.sql.Date(applicationDeadLineDate1.getTime());
		}

		Date earlyDecisionDeadLineDate1 = null;
		java.sql.Date earlyDecisionDeadLineDateSQL = null;
		if (earlyDecisionDeadLineDate != null && !earlyDecisionDeadLineDate.isEmpty()) {
			earlyDecisionDeadLineDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(earlyDecisionDeadLineDate);
			earlyDecisionDeadLineDateSQL = new java.sql.Date(earlyDecisionDeadLineDate1.getTime());
		}

		Date applicationSubmissionStartDate1 = null;
		java.sql.Date applicationSubmissionStartDateSQL = null;
		if (applicationSubmissionStartDate != null && !applicationSubmissionStartDate.isEmpty()) {
			applicationSubmissionStartDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(applicationSubmissionStartDate);
			applicationSubmissionStartDateSQL = new java.sql.Date(applicationSubmissionStartDate1.getTime());
		}

		Date documentsDeadLineDate1 = null;
		java.sql.Date documentsDeadLineDateSQL = null;
		if (documentsDeadLineDate != null && !documentsDeadLineDate.isEmpty()) {
			documentsDeadLineDate1 = new SimpleDateFormat("dd/MM/yyyy").parse(documentsDeadLineDate);
			documentsDeadLineDateSQL = new java.sql.Date(documentsDeadLineDate1.getTime());
		}

		schoolTerm.setApplicationDeadLineDate(applicationDeadLineDateSQL);
		schoolTerm.setApplicationSubmissionStartDate(applicationSubmissionStartDateSQL);
		schoolTerm.setDocumentsDeadLineDate(documentsDeadLineDateSQL);
		schoolTerm.setEarlyDecisionDeadLineDate(earlyDecisionDeadLineDateSQL);
		School school = schoolDao.getSchoolByGuid(schoolTermRequest.getSchoolGuid());
		schoolTerm.setTerm(schoolTermRequest.getTerm());
		schoolTerm.setTermYear(schoolTermRequest.getTermYear());
		schoolTerm.setSchool(school);
		schoolTermDao.saveSchoolTerm(schoolTerm);
	}

	@Override
	@Transactional
	public void deleteSchoolTerm(Long schoolTermGuid) {
		SchoolTerm schoolTerm = schoolTermDao.getSchoolTermByGuid(schoolTermGuid);
		schoolTermDao.deleteSchoolTerm(schoolTerm);

	}

	@Override
	@Transactional
	public SchoolTermRequest getSchoolTerm(Long schoolTermGuid) {
		SchoolTermRequest schoolTermRequest = new SchoolTermRequest();
		SchoolTerm schoolTerm = schoolTermDao.getSchoolTermByGuid(schoolTermGuid);
		schoolTermRequest.setSchoolTermGuid(BigInteger.valueOf(schoolTerm.getSchoolTermGuid()));
		schoolTermRequest.setTerm(schoolTerm.getTerm());
		schoolTermRequest.setTermYear(schoolTerm.getTermYear());

		java.sql.Date applicationDeadLineDateSQL = schoolTerm.getApplicationDeadLineDate();
		java.sql.Date earlyDecisionDeadLineDateSQL = schoolTerm.getEarlyDecisionDeadLineDate();
		java.sql.Date applicationSubmissionStartDateSQL = schoolTerm.getApplicationSubmissionStartDate();
		java.sql.Date documentsDeadLineDateSQL = schoolTerm.getDocumentsDeadLineDate();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String applicationDeadLineDateStr = applicationDeadLineDateSQL != null ? sdf.format(applicationDeadLineDateSQL)
				: "";
		String earlyDecisionDeadLineDateStr = earlyDecisionDeadLineDateSQL != null
				? sdf.format(earlyDecisionDeadLineDateSQL)
				: "";
		String applicationSubmissionStartDateStr = applicationSubmissionStartDateSQL != null
				? sdf.format(applicationSubmissionStartDateSQL)
				: "";
		String documentsDeadLineDateStr = documentsDeadLineDateSQL != null ? sdf.format(documentsDeadLineDateSQL) : "";

		schoolTermRequest.setApplicationDeadLineDate(applicationDeadLineDateStr);
		schoolTermRequest.setApplicationSubmissionStartDate(applicationSubmissionStartDateStr);
		schoolTermRequest.setDocumentsDeadLineDate(documentsDeadLineDateStr);
		schoolTermRequest.setEarlyDecisionDeadLineDate(earlyDecisionDeadLineDateStr);

		return schoolTermRequest;
	}

	@Override
	@Transactional
	public void saveQuestion(SchoolQuestionsResponse schoolQuestionsResponse) {

		SchoolQuestion schoolQuestion = new SchoolQuestion();
		if (schoolQuestionsResponse.getSchoolQuestionGuid() != null) {
			schoolQuestion = schoolQuestionDao.getSchoolQuestionById(schoolQuestionsResponse.getSchoolQuestionGuid());
		}

		if (schoolQuestionsResponse.getSchoolQuestionSecionGuid() != null) {
			SchoolQuestionSection schoolQuestionSection = schoolQuestionSectionDao
					.getSchoolSectionById(schoolQuestionsResponse.getSchoolQuestionSecionGuid());
			schoolQuestion.setSchoolQuestionSection(schoolQuestionSection);
		}
		String selectedSchoolQuestionDegrees = null;
		selectedSchoolQuestionDegrees = schoolQuestionsResponse.getDegreeList();
		selectedSchoolQuestionDegrees = selectedSchoolQuestionDegrees.substring(0,
				selectedSchoolQuestionDegrees.length() - 1);

		schoolQuestion.setDegreeList(selectedSchoolQuestionDegrees);
		schoolQuestion.setQuestionTitle(schoolQuestionsResponse.getQuestionTitle());
		schoolQuestion.setQuestionType(schoolQuestionsResponse.getQuestionType());
		schoolQuestion.setQuestionLongText(schoolQuestionsResponse.getQuestionLongText());
		schoolQuestion.setQuestionMandatory(schoolQuestionsResponse.getQuestionMandatory());
		schoolQuestion.setQuestionInactive(schoolQuestionsResponse.getQuestionInactive());
		schoolQuestion.setSeqNo(schoolQuestionsResponse.getSeqNo());
		schoolQuestion.setChildQuestionseqNo(schoolQuestionsResponse.getChildQuestionseqNo());
		schoolQuestion.setParentQuestionAnswerCondition(schoolQuestionsResponse.getParentQuestionAnswerCondition());
		if (schoolQuestionsResponse.getParentQuestionId() != null) {
			SchoolQuestion parentQuestion = schoolQuestionDao
					.getSchoolQuestionById(schoolQuestionsResponse.getParentQuestionId().longValue());
			schoolQuestion.setParentQuestion(parentQuestion);
		}
		if (CommonUtil.isListNotNullAndNotEmpty(schoolQuestionsResponse.getAnswerOptions())) {
			List<SchoolQuestionAnswerOption> schoolQuestionAnswerOptions = new ArrayList<SchoolQuestionAnswerOption>();
			for (SchoolQuestionAnswerOptionResponse schoolQuestionAnswerOptionResponse : schoolQuestionsResponse
					.getAnswerOptions()) {

				SchoolQuestionAnswerOption schoolQuestionAnswerOption = new SchoolQuestionAnswerOption();
				if (schoolQuestionAnswerOptionResponse.getSchoolQuestionAnswerOptionGuid() != null) {
					schoolQuestionAnswerOption = schoolQuestionAnswerOptionDao.getSchoolQuestionAnswerOptionByGuid(
							schoolQuestionAnswerOptionResponse.getSchoolQuestionAnswerOptionGuid());
				}
				schoolQuestionAnswerOption.setAnswer(schoolQuestionAnswerOptionResponse.getAnswer());
				schoolQuestionAnswerOption.setSeqNo(schoolQuestionAnswerOptionResponse.getSeqNo());
				schoolQuestionAnswerOption.setSchoolQuestion(schoolQuestion);
				schoolQuestionAnswerOptions.add(schoolQuestionAnswerOption);
			}
			schoolQuestion.setSchoolQuestionAnswerOptions(schoolQuestionAnswerOptions);
		}
		schoolQuestionDao.saveQuestion(schoolQuestion);
	}

	@Override
	@Transactional
	public List<SchoolQuestionsResponse> getSchoolSectionQuestionsBySection(Long schoolQuestionSecionGuid) {
		SchoolQuestionSection schoolQuestionSection = schoolQuestionSectionDao
				.getSchoolSectionById(schoolQuestionSecionGuid);
		List<SchoolQuestionsResponse> sectionQuestions = getSectionQuestionsData(
				schoolQuestionSection.getSchoolQuestions());
		return sectionQuestions;
	}

	private List<SchoolQuestionsResponse> getSectionQuestionsData(List<SchoolQuestion> sectionQuestions) {
		List<SchoolQuestionsResponse> questions = new ArrayList<SchoolQuestionsResponse>();
		if (CommonUtil.isListNotNullAndNotEmpty(sectionQuestions)) {
			for (SchoolQuestion schoolQuestion : sectionQuestions) {
				SchoolQuestionsResponse schoolQuestionsResponse = new SchoolQuestionsResponse();
				schoolQuestionsResponse.setSchoolQuestionGuid(schoolQuestion.getSchoolQuestionGuid());
				schoolQuestionsResponse.setQuestionTitle(schoolQuestion.getQuestionTitle());
				schoolQuestionsResponse.setQuestionLongText(schoolQuestion.getQuestionLongText());
				schoolQuestionsResponse.setQuestionType(schoolQuestion.getQuestionType());
				schoolQuestionsResponse.setQuestionMandatory(schoolQuestion.getQuestionMandatory());
				schoolQuestionsResponse.setQuestionInactive(schoolQuestion.getQuestionInactive());
				schoolQuestionsResponse.setSeqNo(schoolQuestion.getSeqNo());
				questions.add(schoolQuestionsResponse);
			}
		}
		return questions;
	}

	@Override
	@Transactional
	public SchoolQuestionsResponse getSchoolQuestion(Long schoolQuestionGuid) {
		SchoolQuestionsResponse schoolQuestionsResponse = new SchoolQuestionsResponse();
		SchoolQuestion schoolQuestion = schoolQuestionDao.getSchoolQuestionById(schoolQuestionGuid);
		schoolQuestionsResponse.setSchoolQuestionGuid(schoolQuestion.getSchoolQuestionGuid());
		schoolQuestionsResponse.setQuestionTitle(schoolQuestion.getQuestionTitle());
		schoolQuestionsResponse.setQuestionLongText(schoolQuestion.getQuestionLongText());
		schoolQuestionsResponse.setQuestionType(schoolQuestion.getQuestionType());
		schoolQuestionsResponse.setQuestionMandatory(schoolQuestion.getQuestionMandatory());
		schoolQuestionsResponse.setQuestionInactive(schoolQuestion.getQuestionInactive());
		schoolQuestionsResponse.setSeqNo(schoolQuestion.getSeqNo());
		schoolQuestionsResponse.setDegreeList(schoolQuestion.getDegreeList());
		schoolQuestionsResponse
				.setSchoolQuestionSecionGuid(schoolQuestion.getSchoolQuestionSection().getSchoolQuestionSectionGuid());

		List<SchoolQuestionAnswerOptionResponse> answerOptions = new ArrayList<SchoolQuestionAnswerOptionResponse>();
		List<SchoolQuestionAnswerOption> questionAnswersList = schoolQuestion.getSchoolQuestionAnswerOptions();
		if (CommonUtil.isListNotNullAndNotEmpty(questionAnswersList)) {
			for (SchoolQuestionAnswerOption schoolQuestionAnswerOption : questionAnswersList) {
				SchoolQuestionAnswerOptionResponse schoolQuestionAnswerOptionResponse = new SchoolQuestionAnswerOptionResponse();
				schoolQuestionAnswerOptionResponse.setAnswer(schoolQuestionAnswerOption.getAnswer());
				schoolQuestionAnswerOptionResponse.setSchoolQuestionAnswerOptionGuid(
						schoolQuestionAnswerOption.getSchoolQuestionAnswerOptionGuid());
				schoolQuestionAnswerOptionResponse.setSeqNo(schoolQuestionAnswerOption.getSeqNo());
				answerOptions.add(schoolQuestionAnswerOptionResponse);
			}
		}
		schoolQuestionsResponse.setAnswerOptions(answerOptions);

		return schoolQuestionsResponse;
	}

	@Override
	@Transactional
	public void deleteSchoolQuestionSection(Long schoolQuestionSectionGuid) {

		SchoolQuestionSection schoolQuestionSection = schoolQuestionSectionDao
				.getSchoolSectionById(schoolQuestionSectionGuid);
		schoolQuestionSectionDao.deleteQuestionSection(schoolQuestionSection);

	}

	@Override
	@Transactional
	public void deleteSchoolQuestion(Long schoolQuestionGuid) {
		SchoolQuestion SchoolQuestion = schoolQuestionDao.getSchoolQuestionById(schoolQuestionGuid);
		schoolQuestionDao.deleteSchoolQuestion(SchoolQuestion);
	}

	@Override
	@Transactional
	public SchoolSectionQuestionsResponse getSchoolQuestionSection(Long schoolQuestionSectionGuid) {
		SchoolQuestionSection schoolQuestionSection = schoolQuestionSectionDao
				.getSchoolSectionById(schoolQuestionSectionGuid);
		SchoolSectionQuestionsResponse schoolSectionQuestionsResponse = new SchoolSectionQuestionsResponse();
		schoolSectionQuestionsResponse.setSectionName(schoolQuestionSection.getSectionName());
		schoolSectionQuestionsResponse
				.setSchoolQuestionSectionGuid(schoolQuestionSection.getSchoolQuestionSectionGuid());
		schoolSectionQuestionsResponse.setSeqNo(schoolQuestionSection.getSeqNo());
		return schoolSectionQuestionsResponse;
	}

	@Override
	@Transactional
	public List<SchoolDocumentRequestResponse> getSchoolDocuments(Long schoolGuid) {
		List<SchoolDocumentRequestResponse> schoolDocuments = new ArrayList<SchoolDocumentRequestResponse>();
		List<SchoolDocument> schollDocs = schoolDocumentDao.getSchoolDocuments(schoolGuid);
		String SchoolDocumentPresignedUrl;
		if (CommonUtil.isListNotNullAndNotEmpty(schollDocs)) {
			for (SchoolDocument schoolDocument : schollDocs) {
				SchoolDocumentRequestResponse schoolDocumentRequestResponse = new SchoolDocumentRequestResponse();
				schoolDocumentRequestResponse.setDocumentName(schoolDocument.getDocumentName());
				schoolDocumentRequestResponse.setIsMandatory(schoolDocument.getIsMandatory());
				SchoolDocumentPresignedUrl = amazonS3Template.getSchoolDocumentViewPresignedUrl(
						CommonUtil.getFileNameFromURL(schoolDocument.getDocumentPath()),
						schoolDocument.getDocumentName() + ".pdf");
				// schoolDocumentRequestResponse.setDocumentPath(schoolDocument.getDocumentPath());
				schoolDocumentRequestResponse.setDocumentPath(SchoolDocumentPresignedUrl);
				schoolDocumentRequestResponse.setDocumentURL(schoolDocument.getDocumentURL());
				schoolDocumentRequestResponse.setSchoolDocumentGuid(schoolDocument.getSchoolDocumentGuid());
				schoolDocumentRequestResponse.setSchoolGuid(schoolGuid);
				schoolDocumentRequestResponse.setDegreeList(schoolDocument.getDegreeList());
				schoolDocuments.add(schoolDocumentRequestResponse);
			}
		}
		return schoolDocuments;
	}

	@Override
	@Transactional
	public void saveSchoolDocument(MultipartHttpServletRequest request, HttpServletRequest servletRequest) {
		MultipartFile multipartFile = request.getFile("form-document-file");
		String originalDocumentName = multipartFile.getOriginalFilename();
		String documentName = request.getParameter("form-document-name");
		String isMandatory = request.getParameter("form-document-mandatory");
		String documentURL = request.getParameter("form-document-url");
		String selectedDocumentsDegrees = request.getParameter("form-document-degree-list");
		if (selectedDocumentsDegrees == null || selectedDocumentsDegrees == "") {
			selectedDocumentsDegrees = "";
		} else {
			selectedDocumentsDegrees = selectedDocumentsDegrees.substring(0, selectedDocumentsDegrees.length() - 1);
		}

		if ("".equals(isMandatory) || isMandatory == null || isMandatory.isEmpty()) {
			isMandatory = "NO";
		}
		Long schoolDocumentGuidLong = null;
		String schoolDocumentGuid = request.getParameter("form_hidden_document_guid");
		if (schoolDocumentGuid != null && !schoolDocumentGuid.isEmpty()
				&& !schoolDocumentGuid.equalsIgnoreCase("null")) {
			schoolDocumentGuidLong = Long.parseLong(schoolDocumentGuid);
		}
		Long schoolGuidLong = null;
		String schoolGuid = request.getParameter("form_hidden_schoolGuid");
		if (schoolGuid != null && !schoolGuid.isEmpty() && !schoolGuid.equalsIgnoreCase("null")) {
			schoolGuidLong = Long.parseLong(schoolGuid);
		}
		String fileExtension = CommonUtil.getExtension(documentName);
		if (fileExtension != null && !fileExtension.isEmpty()) {
			fileExtension = "." + fileExtension;
		}
		String documentId = CommonUtil.generateSalt(10);
		File dest = null;
		try {
			String path = env.getProperty("java.io.tmpdir");
			dest = new File(path + documentId + fileExtension);
			multipartFile.transferTo(dest);
			documentId = documentId + ".pdf";
			amazonS3Template.saveSchoolDocuments(documentId, dest);
			// amazonS3Template.saveSchoolDocumentsWithdocumentName(documentId, dest,
			// originalDocumentName);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		} finally {

			if (dest != null) {
				try {
					FileUtils.forceDeleteOnExit(dest);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		SchoolDocument schoolDocument = new SchoolDocument();
		if (schoolDocumentGuidLong != null) {
			schoolDocument = schoolDocumentDao.getSchoolDocumentByGuid(schoolDocumentGuidLong);
		}
		School school = schoolDao.getSchoolByGuid(schoolGuidLong);
		schoolDocument.setSchool(school);
		schoolDocument.setDocumentName(originalDocumentName);
		schoolDocument.setDocumentURL(documentURL);
		schoolDocument.setIsMandatory(isMandatory);
		schoolDocument.setDocumentPath(amazonS3Template.getSchoolDocumentS3ObjectUrl(documentId));
		schoolDocument.setDegreeList(selectedDocumentsDegrees);
		schoolDocumentDao.saveSchoolDocument(schoolDocument);
	}

	@Override
	@Transactional
	public void deleteSchoolDocument(Long schoolDocumentGuid) {
		SchoolDocument schoolDocument = schoolDocumentDao.getSchoolDocumentByGuid(schoolDocumentGuid);
		schoolDocumentDao.deleteSchoolDocument(schoolDocument);
	}

	@Override
	@Transactional
	public SchoolDocumentRequestResponse getSchoolDocument(Long schoolDocumentGuid) {
		SchoolDocument schoolDocument = schoolDocumentDao.getSchoolDocumentByGuid(schoolDocumentGuid);
		SchoolDocumentRequestResponse schoolDocumentRequestResponse = new SchoolDocumentRequestResponse();
		schoolDocumentRequestResponse.setDocumentName(schoolDocument.getDocumentName());
		schoolDocumentRequestResponse.setIsMandatory(schoolDocument.getIsMandatory());
		schoolDocumentRequestResponse.setDocumentPath(schoolDocument.getDocumentPath());
		schoolDocumentRequestResponse.setDocumentName(schoolDocument.getDocumentName());
		schoolDocumentRequestResponse.setDocumentURL(schoolDocument.getDocumentURL());
		schoolDocumentRequestResponse.setDegreeList(schoolDocument.getDegreeList());
		schoolDocumentRequestResponse.setSchoolDocumentGuid(schoolDocument.getSchoolDocumentGuid());
		return schoolDocumentRequestResponse;
	}

	@Override
	@Transactional
	public List<SchoolSearchResponse> getSchoolsByCriteria(SchoolSearchCriteria schoolSearchCriteria) {
		List<SchoolSearchResponse> schools = schoolDao.getSchools();

		Stream<SchoolSearchResponse> schoolsStream = schools.stream();
		if (schoolSearchCriteria.getSchoolName() != null && !schoolSearchCriteria.getSchoolName().isEmpty()) {
			schoolsStream = schoolsStream.filter(schoolNameSearch(schoolSearchCriteria.getSchoolName()));
		}
		if (schoolSearchCriteria.getStateName() != null && !schoolSearchCriteria.getStateName().isEmpty()) {
			schoolsStream = schoolsStream.filter(schoolStateSearch(schoolSearchCriteria.getStateName()));
		}
		if (schoolSearchCriteria.getCityName() != null && !schoolSearchCriteria.getCityName().isEmpty()) {
			schoolsStream = schoolsStream.filter(schoolCitySearch(schoolSearchCriteria.getCityName()));
		}
		if (schoolSearchCriteria.getMinGMATScore() != null) {
			schoolsStream = schoolsStream.filter(schoolMinGMATScoreSearch(schoolSearchCriteria.getMinGMATScore()));
		}
		if (schoolSearchCriteria.getMinGREScore() != null) {
			schoolsStream = schoolsStream.filter(schoolMinGREScoreSearch(schoolSearchCriteria.getMinGREScore()));

		}
		if (schoolSearchCriteria.getMinTOEFLScore() != null) {
			schoolsStream = schoolsStream.filter(schoolMinTOEFLScoreSearch(schoolSearchCriteria.getMinTOEFLScore()));

		}
		if (schoolSearchCriteria.getMinGPAScore() != null) {
			schoolsStream = schoolsStream.filter(schoolMinGPAScoreSearch(schoolSearchCriteria.getMinGPAScore()));

		}
		if (schoolSearchCriteria.getTranscriptsRequired() != null
				&& !schoolSearchCriteria.getTranscriptsRequired().isEmpty()) {
			schoolsStream = schoolsStream
					.filter(schoolTranscriptsRequiredSearch(schoolSearchCriteria.getTranscriptsRequired()));
		}
		if (schoolSearchCriteria.getSolvCertRequired() != null
				&& !schoolSearchCriteria.getSolvCertRequired().isEmpty()) {
			schoolsStream = schoolsStream
					.filter(schoolSolvCertificatedRequiredSearch(schoolSearchCriteria.getSolvCertRequired()));
		}
		if (schoolSearchCriteria.getRecLettersRequired() != null
				&& !schoolSearchCriteria.getRecLettersRequired().isEmpty()) {
			schoolsStream = schoolsStream
					.filter(schoolRecLettersRequiredSearch(schoolSearchCriteria.getRecLettersRequired()));
		}
		schools = schoolsStream.collect(Collectors.toList());
		return schools;
	}

	private Predicate<? super SchoolSearchResponse> schoolRecLettersRequiredSearch(String recLettersRequired) {
		return u -> (u.getRecLettersRequires() != null
				|| u.getRecLettersRequires().equalsIgnoreCase(recLettersRequired));
	}

	private Predicate<? super SchoolSearchResponse> schoolSolvCertificatedRequiredSearch(String solvCertRequired) {
		return u -> (u.getSolvCERTRequires() != null || u.getSolvCERTRequires().equalsIgnoreCase(solvCertRequired));
	}

	private Predicate<? super SchoolSearchResponse> schoolTranscriptsRequiredSearch(String transcriptsRequired) {
		return u -> (u.getTranscriptsRequires() != null
				|| u.getTranscriptsRequires().equalsIgnoreCase(transcriptsRequired));
	}

	private Predicate<? super SchoolSearchResponse> schoolMinGMATScoreSearch(Double minGMATScore) {
		return u -> (u.getMinimumGMATcore() == null || u.getMinimumGMATcore() <= minGMATScore);
	}

	private Predicate<? super SchoolSearchResponse> schoolMinGREScoreSearch(Double minGREScore) {
		return u -> (u.getMinimumGREScore() == null || u.getMinimumGREScore() <= minGREScore);
	}

	private Predicate<? super SchoolSearchResponse> schoolMinTOEFLScoreSearch(Double minTOEFLScore) {
		return u -> (u.getMinimumTOEFLScore() == null || u.getMinimumTOEFLScore() <= minTOEFLScore);
	}

	private Predicate<? super SchoolSearchResponse> schoolMinGPAScoreSearch(Double minGPAScore) {
		return u -> (u.getMinimumGPA() == null || u.getMinimumGPA() <= minGPAScore);
	}

	private Predicate<? super SchoolSearchResponse> schoolNameSearch(String schoolName) {
		return u -> (u.getSchoolName() != null && CommonUtil.containsIgnoreCase(u.getSchoolName(), schoolName));
	}

	private Predicate<? super SchoolSearchResponse> schoolStateSearch(String stateName) {
		return u -> (u.getState() != null && CommonUtil.containsIgnoreCase(u.getState(), stateName));
	}

	private Predicate<? super SchoolSearchResponse> schoolCitySearch(String cityName) {
		return u -> (u.getCity() != null && CommonUtil.containsIgnoreCase(u.getCity(), cityName));
	}

	@Override
	@Transactional
	public void addCollege(School school, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		StudentApplication studentApplication = new StudentApplication();
		School eachSchool = schoolDao.getSchoolByGuid(school.getSchoolGuid());
		studentApplication.setSchool(eachSchool);
		studentApplication.setAccount(account);
		studentApplication.setSeqNo(1);
		List<School> addedSchools = account.getSchools();
		addedSchools.add(eachSchool);
		account.setSchools(addedSchools);
		accountDao.saveAccount(account);
		studentApplicationDao.saveStudentApplication(studentApplication);
	}

	@Override
	@Transactional
	public SchoolSearchResponse getCollegeInfo(Long schoolGuid) {

		SchoolSearchResponse schoolData = new SchoolSearchResponse();
		School eachSchool = schoolDao.getSchoolByGuid(schoolGuid);

		schoolData.setSchoolInfo(eachSchool.getSchoolInfo());
		schoolData.setAddressLineOne(eachSchool.getAddressLineOne());
		schoolData.setAddressLineTwo(eachSchool.getAddressLineTwo());
		schoolData.setCity(eachSchool.getCity());
		schoolData.setContactPerson(eachSchool.getContactPerson());
		schoolData.setEmailAddress(eachSchool.getEmailAddress());
		schoolData.setGmatScoreSubmissionSchoolCode(eachSchool.getGmatScoreSubmissionSchoolCode());
		schoolData.setGreScoreSubmissionSchoolCode(eachSchool.getGreScoreSubmissionSchoolCode());
		schoolData.setInternationAppFees(eachSchool.getInternationAppFees());
		schoolData.setInternationCredentialEvalFees(eachSchool.getInternationCredentialEvalFees());
		schoolData.setMailingFee(eachSchool.getMailingFee());
		schoolData.setMinimumGMATcore(eachSchool.getMinimumGMATcore());
		schoolData.setMinimumGPA(eachSchool.getMinimumGPA());
		schoolData.setMinimumGREScore(eachSchool.getMinimumGREScore());
		schoolData.setMinimumTOEFLScore(eachSchool.getMinimumTOEFLScore());
		schoolData.setPhoneNumber(eachSchool.getPhoneNumber());
		schoolData.setRecLettersRequires(eachSchool.getRecLettersRequires());
		schoolData.setRequiresPassport(eachSchool.getRequiresPassport());
		schoolData.setResumeRequires(eachSchool.getResumeRequires());
		schoolData.setGradTuitionFeePerCredit(eachSchool.getGradTuitionFeePerCredit());
		schoolData.setGradCreditsRequired(eachSchool.getGradCreditsRequired());
		schoolData.setApplicationCheckListLink(eachSchool.getApplicationCheckListLink());
		schoolData.setTuitionFeesLink(eachSchool.getTuitionFeesLink());
		schoolData.setRequiresI20(eachSchool.getRequiresI20());
		schoolData.setRequiresEAD(eachSchool.getRequiresEAD());
		schoolData.setRequiresF1Visa(eachSchool.getRequiresF1Visa());
		schoolData.setRequiresTOFELScore(eachSchool.getRequiresTOFELScore());
		Long countryGuid = null;
		if (eachSchool.getSchoolCountry() != null) {
			countryGuid = eachSchool.getSchoolCountry().getGuid();
		}
		schoolData.setSchoolCountryGuid(countryGuid);
		schoolData.setSchoolGuid(BigInteger.valueOf(eachSchool.getSchoolGuid()));
		schoolData.setSchoolId(eachSchool.getSchoolId());
		schoolData.setSchoolLogoURL(eachSchool.getSchoolLogoURL());
		schoolData.setSchoolName(eachSchool.getSchoolName());
		schoolData.setSolvCERTRequires(eachSchool.getSolvCERTRequires());
		schoolData.setSopRequires(eachSchool.getSopRequires());
		schoolData.setState(eachSchool.getState());
		schoolData.setToeflScoreSubmissionSchoolCode(eachSchool.getToeflScoreSubmissionSchoolCode());
		schoolData.setTranscriptsRequires(eachSchool.getTranscriptsRequires());
		schoolData.setWebsite(eachSchool.getWebsite());
		schoolData.setZipCode(eachSchool.getZipCode());
		List<SchoolTermResponse> schoolTerms = schoolTermDao.getSchoolTermsBySchool(schoolGuid);
		schoolData.setSchoolTerms(schoolTerms);
		List<SchoolDegreeResponse> schoolDegrees = schoolDegreeDao.getSchoolDegreesBySchool(schoolGuid);
		schoolData.setSchoolDegrees(schoolDegrees);
		return schoolData;
	}

	@Override
	public void generateSchoolAppPDF(SchoolSearchResponse schoolSearchResponse, HttpServletRequest request) {

		try {
			OutputStream file = new FileOutputStream(new File("C:\\Users\\kanduk\\Documents\\MyWork\\Test1.pdf"));

			ITextRenderer renderer = new ITextRenderer();
			File htmlFile = new File(
					"C:\\Users\\kanduk\\Documents\\My Work\\Documents\\My Web Sites\\Spring Suite Workspace\\Universal Admission\\src\\main\\webapp\\WEB-INF\\jsp\\schoolAppPDFPacket.jsp");
			renderer.setDocument(htmlFile);
			renderer.layout();

			renderer.createPDF(file);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Override
	// @Transactional
	// public List<SchoolApplicantsResponse> getSchoolApplicants(String domainName)
	// {
	// School school = schoolDao.getSchoolBySchoolDomain(domainName);
	// List<SchoolApplicantsResponse> schoolApplicants =
	// studentProfileDao.getSchoolApplicants(school.getSchoolGuid());
	// return schoolApplicants;
	// }
	@Override
	@Transactional
	public List<SchoolApplicantsResponse> getSchoolApplicants(String domainName) {
		School school = schoolDao.getSchoolBySchoolDomain(domainName);
		List<SchoolApplicantsResponse> schoolApplicants = studentProfileDao.getSchoolApplicants(school.getSchoolGuid());

		for (int i = 0; i < schoolApplicants.size(); i++) {
			String StudentDocumentPresignedUrl;
			String StudentDocumentViewPresignedUrl;

			StudentDocumentPresignedUrl = amazonS3Template.getStudentPDFDocumentDownloadPresignedUrl(
					CommonUtil.getFileNameFromURL(schoolApplicants.get(i).getPdfURL()),
					schoolApplicants.get(i).getFirstName() + schoolApplicants.get(i).getLastName() + ".pdf");

			StudentDocumentViewPresignedUrl = amazonS3Template.getStudentPDFDocumentViewPresignedUrl(
					CommonUtil.getFileNameFromURL(schoolApplicants.get(i).getPdfURL()),
					schoolApplicants.get(i).getFirstName() + schoolApplicants.get(i).getLastName() + ".pdf");

			schoolApplicants.get(i).setDownloadpdfURL(StudentDocumentPresignedUrl);

			schoolApplicants.get(i).setViewPdfURL(StudentDocumentViewPresignedUrl);

		}
		return schoolApplicants;
	}

	@Override
	@Transactional
	public List<SchoolProspectsResponse> getSchoolProspects(String domainName) {
		School school = schoolDao.getSchoolBySchoolDomain(domainName);
		List<SchoolProspectsResponse> schoolApplicants = studentProfileDao.getSchoolProspects(school.getSchoolGuid());
		return schoolApplicants;
	}

	@Override
	@Transactional
	public String downloadApplicationData(String domainName) {

		String zipdownloadurl = "";

		File desktop = new File(System.getProperty("user.home"), "Desktop");
		String path = env.getProperty("java.io.tmpdir");
		String mainFolderName = path + "/ApplicationData";
		File f = new File(mainFolderName);
		if (!f.exists()) {
			f.mkdirs();
		} else {
			try {
				FileUtils.forceDelete(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (!f.exists()) {
				f.mkdirs();
			}
		}

		String pdfApplicationsFileName = mainFolderName + "/PDF APPLICATIONS";
		File pdfApplicationFileName = new File(pdfApplicationsFileName);
		if (!pdfApplicationFileName.exists()) {
			pdfApplicationFileName.mkdirs();
		}
		String applicationListFileName = "APPLICATIONLIST.XLS";
		Workbook exportWorkBook = CommonUtil.createWorkBook(applicationListFileName);
		if (exportWorkBook.getNumberOfSheets() == 1 && exportWorkBook.getSheetAt(0) != null) {
			exportWorkBook.removeSheetAt(0);
		}
		Sheet exportWorkBookSheet = exportWorkBook.createSheet("APPLICATIONLIST");
		Map<Integer, Object[]> sheetData = new HashMap<Integer, Object[]>();
		addHeaderRow(exportWorkBookSheet);

		School school = schoolDao.getSchoolBySchoolDomain(domainName);
		List<StudentProfileRequestResponse> studentsList = studentProfileDao
				.getAppliedStudentsData(school.getSchoolGuid());
		if (CommonUtil.isListNotNullAndNotEmpty(studentsList)) {
			int excelRowCount = 0;
			for (StudentProfileRequestResponse studentProfileRequestResponse : studentsList) {
				excelRowCount++;
				prepareExcelDataToSheet(excelRowCount, studentProfileRequestResponse, sheetData);
				String eachStudentFolderName = "";
				if (studentProfileRequestResponse.getFirstName() != null) {
					eachStudentFolderName += studentProfileRequestResponse.getFirstName();
				}
				if (studentProfileRequestResponse.getLastName() != null) {
					eachStudentFolderName += studentProfileRequestResponse.getLastName();
				}
				if (studentProfileRequestResponse.getStudentProfileGuid() != null) {
					eachStudentFolderName += "_" + studentProfileRequestResponse.getStudentProfileGuid();
				}

				String eachStudentFolderFileName = mainFolderName + "/" + eachStudentFolderName;
				File eachStudentFolder = new File(eachStudentFolderFileName);
				if (!eachStudentFolder.exists()) {
					eachStudentFolder.mkdirs();
				}
				List<StudentSchoolDocument> studentDocuments = studentSchoolDocumentDao.getDocumentByStudentAndSchoolId(
						studentProfileRequestResponse.getAccountGuid().longValue(), school.getSchoolGuid());

				if (CommonUtil.isListNotNullAndNotEmpty(studentDocuments)) {
					for (StudentSchoolDocument studentSchoolDocument : studentDocuments) {
						if (studentSchoolDocument.getDocumentPath() != null
								&& !studentSchoolDocument.getDocumentPath().isEmpty()) {
							URL url = null;
							try {
								String StudentSchoolDocumenPresignedUrl;;
								StudentSchoolDocumenPresignedUrl=amazonS3Template.getStudentSchoolDocumentDownloadPresignedUrl(CommonUtil.getFileNameFromURL(studentSchoolDocument.getDocumentPath()),
						studentSchoolDocument.getDocumentName());
								
//								url = new URL(studentSchoolDocument.getDocumentPath());
								url = new URL(StudentSchoolDocumenPresignedUrl);
								InputStream in = url.openStream();

								// Files.copy(in,
								// Paths.get(eachStudentFolderFileName + "/"
								// + CommonUtil
								// .getFileNameFromURL(studentSchoolDocument.getDocumentPath())),
								// StandardCopyOption.REPLACE_EXISTING);
								Files.copy(in,
										Paths.get(eachStudentFolderFileName + "/"
												+ studentSchoolDocument.getDocumentName()),
										StandardCopyOption.REPLACE_EXISTING);
								in.close();
							} catch (MalformedURLException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

					}
				}

				List<StudentDocumentResponse> studentDocs = studentDocumentsDao
						.getAllStudentDocuments(studentProfileRequestResponse.getAccountGuid().longValue());

				System.out.println("account guid##:"+studentProfileRequestResponse.getAccountGuid().longValue());
				
				if (CommonUtil.isListNotNullAndNotEmpty(studentDocs)) {
					for (StudentDocumentResponse studentDocumentResponse : studentDocs) {
						if (studentDocumentResponse.getDocumentPathGuid() != null
								&& !studentDocumentResponse.getDocumentPathGuid().isEmpty()) {

							URL url = null;
							try {
								String StudentDocumentPresignedUrl;;
								StudentDocumentPresignedUrl=amazonS3Template.getStudentDocumentDownloadPresignedUrl(studentDocumentResponse.getDocumentPathGuid(),studentDocumentResponse.getDocumentName());
								url = new URL(StudentDocumentPresignedUrl);
								
//								url = new URL("https://s3-us-west-2.amazonaws.com/s3-us-west-2-dev/student-documents/"
//										+ studentDocumentResponse.getDocumentPathGuid());

								url.getFile();
								InputStream in = url.openStream();
								Files.copy(in,
										Paths.get(eachStudentFolderFileName + "/"
												+ studentDocumentResponse.getDocumentName()),
										StandardCopyOption.REPLACE_EXISTING);
								in.close();
							} catch (MalformedURLException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

					}
				}

				if (studentProfileRequestResponse.getPdfURL() != null
						&& !studentProfileRequestResponse.getPdfURL().isEmpty()) {

					URL url = null;
					try {
						
						String StudentPDFDocumentPresignedUrl;;
						String documentKey;
						documentKey=CommonUtil.getFileNameFromURL("https://s3-us-west-2.amazonaws.com/s3-us-west-2-dev/student-documents/"
								+ studentProfileRequestResponse.getPdfURL());
						
						StudentPDFDocumentPresignedUrl=amazonS3Template.getStudentPDFDocumentDownloadPresignedUrl(documentKey,studentProfileRequestResponse.getFirstName()
								+ studentProfileRequestResponse.getLastName() + ".pdf");
						
						url = new URL(StudentPDFDocumentPresignedUrl);
//						url = new URL("https://s3-us-west-2.amazonaws.com/s3-us-west-2-dev/student-documents/"
//								+ studentProfileRequestResponse.getPdfURL());
						InputStream in = url.openStream();
						Files.copy(in,
								Paths.get(pdfApplicationsFileName + "/"
										+ (studentProfileRequestResponse.getFirstName()
												+ studentProfileRequestResponse.getLastName() + ".pdf")));
						// Files.copy(in,
						// Paths.get(pdfApplicationsFileName + "/"
						// + CommonUtil.getFileNameFromURL(studentProfileRequestResponse.getPdfURL())),
						// StandardCopyOption.REPLACE_EXISTING);
						in.close();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}

		writeDataIntoSheet(sheetData, exportWorkBookSheet);
		writeExcelDataIntoFile(mainFolderName + "/" + applicationListFileName, exportWorkBook);

		File directoryToZip = new File(mainFolderName);

		List<File> fileList = new ArrayList<File>();

		File[] files = directoryToZip.listFiles();
		for (File file : files) {
			fileList.add(file);
			try {
				if (file.isDirectory()) {

					System.out.println("directory:" + file.getCanonicalPath());

				} else {
					System.out.println("     file:" + file.getCanonicalPath());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		zipdownloadurl = writeZipFile(directoryToZip, fileList, path);

		return zipdownloadurl;

	}

	public String writeZipFile(File directoryToZip, List<File> fileList, String path) {

		String zipdownloadurl = "";
		String absPath = "";
		File zip = null;

		try {
			FileOutputStream fos = new FileOutputStream(path + "/" + directoryToZip.getName() + ".zip");

			absPath = path + "/" + directoryToZip.getName() + ".zip";

			ZipOutputStream zos = new ZipOutputStream(fos);

			for (File file : fileList) {
				if (!file.isDirectory()) { // we only zip files, not directories
					addToZip(directoryToZip, file, zos);
				} else {
					String relativePath = getRelativePath(absPath, file);
					if (relativePath.trim().length() > 0) {
						ZipEntry ze = new ZipEntry(relativePath + "/");
						zos.putNextEntry(ze);
						zos.closeEntry();
					}
					zipDirectory(file, absPath, zos);
				}

			}

			zos.close();
			fos.close();

			zip = new File(absPath);

			String documentId = CommonUtil.generateSalt(10);

			documentId = "ApplicationData" + documentId;

			amazonS3Template.saveSchoolDocuments(documentId, zip);
			String DownloadPresignedUrl;
			zipdownloadurl=amazonS3Template.getSchoolDocumentDownloadPresignedUrl(documentId,documentId+".zip");
//			zipdownloadurl = amazonS3Template.getSchoolDocumentS3ObjectUrl(documentId);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (zip != null) {
				zip.delete();
			}
		}
		return zipdownloadurl;
	}

	public static String getRelativePath(String sourceDir, File file) {

		String path = file.getName();
		if (path.startsWith(File.pathSeparator)) {
			path = path.substring(1);
		}
		return path;
	}

	public static void addToZip(File directoryToZip, File file, ZipOutputStream zos)
			throws FileNotFoundException, IOException {

		FileInputStream fis = new FileInputStream(file);

		String zipFilePath = file.getCanonicalPath().substring(directoryToZip.getCanonicalPath().length() + 1,
				file.getCanonicalPath().length());

		ZipEntry zipEntry = new ZipEntry(zipFilePath);
		zos.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zos.write(bytes, 0, length);
		}

		zos.closeEntry();
		fis.close();
	}

	private void zipDirectory(File dir, String zipDirName, ZipOutputStream zos) {
		try {
			List<String> filesListInDir;
			filesListInDir = populateFilesList(dir);
			String subpath = dir.getName();
			for (String filePath : filesListInDir) {
				ZipEntry ze = new ZipEntry(
						subpath + "/" + filePath.substring(dir.getAbsolutePath().length() + 1, filePath.length()));
				zos.putNextEntry(ze);
				FileInputStream fis = new FileInputStream(filePath);
				byte[] buffer = new byte[1024];
				int len;
				while ((len = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}
				zos.closeEntry();
				fis.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<String> populateFilesList(File dir) throws IOException {
		List<String> filesListInDir;
		filesListInDir = new ArrayList<String>();
		File[] files = dir.listFiles();
		if (files != null) {

			for (File file : files) {
				if (file.isFile()) {
					filesListInDir.add(file.getAbsolutePath());
				} else {
					populateFilesList(file);
				}
			}
		}
		return filesListInDir;
	}
	/* end zip code */

	private void prepareExcelDataToSheet(int excelRowCount, StudentProfileRequestResponse studentProfileRequestResponse,
			Map<Integer, Object[]> sheetData) {

		sheetData.put(excelRowCount, new Object[] { studentProfileRequestResponse.getTitle(),
				studentProfileRequestResponse.getFirstName(), studentProfileRequestResponse.getMiddleName(),
				studentProfileRequestResponse.getLastName(), studentProfileRequestResponse.getGender(),
				studentProfileRequestResponse.getDateOfBirth(), studentProfileRequestResponse.getCityOfBirth(),
				studentProfileRequestResponse.getPermanentAddressLine1(),
				studentProfileRequestResponse.getPermanentAddressCity(),
				studentProfileRequestResponse.getPermanentAddressState(),
				studentProfileRequestResponse.getPermanentAddressZipCode(),
				studentProfileRequestResponse.getEmergencyAddressLine1(),
				studentProfileRequestResponse.getEmergencyAddressCity(),
				studentProfileRequestResponse.getEmergencyAddressState(),
				studentProfileRequestResponse.getEmergencyAddressZipCode(),
				studentProfileRequestResponse.getEmergencyContactName(),
				studentProfileRequestResponse.getEmergencyContactNo(),
				studentProfileRequestResponse.getEmergencyContactRelation(),
				studentProfileRequestResponse.getMailingAddressLine1(),
				studentProfileRequestResponse.getMailingAddressCity(),
				studentProfileRequestResponse.getMailingAddressState(),
				studentProfileRequestResponse.getMailingAddressZipCode(),
				studentProfileRequestResponse.getMaritalStatus(), studentProfileRequestResponse.getEthnicity(),
				studentProfileRequestResponse.getHomeContactNo(), studentProfileRequestResponse.getWorkContactNo() });

	}

	private void addHeaderRow(Sheet exportWorkBookSheet) {

		Row headerRow = exportWorkBookSheet.createRow(0);

		Cell cell0 = headerRow.createCell(0);
		Cell cell1 = headerRow.createCell(1);
		Cell cell2 = headerRow.createCell(2);
		Cell cell3 = headerRow.createCell(3);
		Cell cell4 = headerRow.createCell(4);
		Cell cell5 = headerRow.createCell(5);
		Cell cell6 = headerRow.createCell(6);
		Cell cell7 = headerRow.createCell(7);
		Cell cell8 = headerRow.createCell(8);
		Cell cell9 = headerRow.createCell(9);
		Cell cell10 = headerRow.createCell(10);
		Cell cell11 = headerRow.createCell(11);
		Cell cell12 = headerRow.createCell(12);
		Cell cell13 = headerRow.createCell(13);
		Cell cell14 = headerRow.createCell(14);
		Cell cell15 = headerRow.createCell(15);
		Cell cell16 = headerRow.createCell(16);
		Cell cell17 = headerRow.createCell(17);
		Cell cell18 = headerRow.createCell(18);
		Cell cell19 = headerRow.createCell(19);
		Cell cell20 = headerRow.createCell(20);
		Cell cell21 = headerRow.createCell(21);
		Cell cell22 = headerRow.createCell(22);
		Cell cell23 = headerRow.createCell(23);
		Cell cell24 = headerRow.createCell(24);
		Cell cell25 = headerRow.createCell(25);
		Cell cell26 = headerRow.createCell(26);

		cell0.setCellValue("Tilte");
		cell1.setCellValue("First Name");
		cell2.setCellValue("Middle Name");
		cell3.setCellValue("Last Name");
		cell4.setCellValue("Gender");
		cell5.setCellValue("Email Address");
		cell6.setCellValue("Date Of Birth");
		cell7.setCellValue("City Of Birth");
		cell8.setCellValue("Permanent Address");
		cell9.setCellValue("Permanent Address City");
		cell10.setCellValue("Permanent Address State");
		cell11.setCellValue("Permanent Address Zip Code");
		cell12.setCellValue("Emergeny Address");
		cell13.setCellValue("Emergency Address City");
		cell14.setCellValue("Emergency Address State");
		cell15.setCellValue("Emergency Address Zip Code");
		cell16.setCellValue("Emergency Contact Number");
		cell17.setCellValue("Emergency Contact Name");
		cell18.setCellValue("Emergency Contact Relation");
		cell19.setCellValue("Mailinig Address");
		cell20.setCellValue("Mailinig Address City");
		cell21.setCellValue("Mailinig Address State");
		cell22.setCellValue("Mailinig Address Zip Code");
		cell23.setCellValue("Marital Status");
		cell24.setCellValue("Ethnicity");
		cell25.setCellValue("Home Contact No");
		cell26.setCellValue("Working Contact No");

	}

	private void writeDataIntoSheet(Map<Integer, Object[]> sheetData, Sheet exportWorkBookSheet) {

		Set<Integer> newRows = sheetData.keySet();
		int rownum = exportWorkBookSheet.getLastRowNum() + 1;
		for (Integer key : newRows) {
			Row row = exportWorkBookSheet.createRow(rownum++);
			Object[] objArr = sheetData.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String) {
					cell.setCellValue((String) obj);
				} else if (obj instanceof Long) {
					cell.setCellValue((Long) obj);
				} else if (obj == null) {
					cell.setCellType(Cell.CELL_TYPE_BLANK);
				}
			}
		}
	}

	public void writeExcelDataIntoFile(String fileName, Workbook exportWorkBook) {
		FileOutputStream os = null;
		try {
			File exportedFile = new File(fileName);
			os = new FileOutputStream(exportedFile);
			try {
				exportWorkBook.write(os);
				os.close();
				exportWorkBook.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void downloadStudentApplicationData(String pdfURL) {

		File desktop = new File(System.getProperty("user.home"), "Desktop");

		String mainFolderName = desktop.getAbsolutePath();

		if (pdfURL != null && !pdfURL.isEmpty()) {

			URL url = null;
			try {

				url = new URL(pdfURL);
				InputStream in = url.openStream();
				Files.copy(in, Paths.get(mainFolderName + "\\" + CommonUtil.getFileNameFromURL(pdfURL)),
						StandardCopyOption.REPLACE_EXISTING);
				in.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

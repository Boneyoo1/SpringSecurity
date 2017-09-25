package com.denkensol.universaladmission.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.amazonaws.http.HttpResponse;
import com.denkensol.universaladmission.dao.AccountDao;
import com.denkensol.universaladmission.dao.CountryDao;
import com.denkensol.universaladmission.dao.LanguageDao;
import com.denkensol.universaladmission.dao.ReligiousDao;
import com.denkensol.universaladmission.dao.SchoolDao;
import com.denkensol.universaladmission.dao.SchoolDegreeDao;
import com.denkensol.universaladmission.dao.SchoolDocumentDao;
import com.denkensol.universaladmission.dao.StudentApplicationDao;
import com.denkensol.universaladmission.dao.StudentDocumentsDao;
import com.denkensol.universaladmission.dao.StudentEducationDao;
import com.denkensol.universaladmission.dao.StudentEmploymentDao;
import com.denkensol.universaladmission.dao.StudentFamilyDao;
import com.denkensol.universaladmission.dao.StudentProfileDao;
import com.denkensol.universaladmission.dao.StudentRecommenderDao;
import com.denkensol.universaladmission.dao.StudentSchoolDocumentDao;
import com.denkensol.universaladmission.dao.StudentTestingDao;
import com.denkensol.universaladmission.dao.StudentWritingDao;
import com.denkensol.universaladmission.dao.VisaTypeDao;
import com.denkensol.universaladmission.entity.Account;
import com.denkensol.universaladmission.entity.School;
import com.denkensol.universaladmission.entity.SchoolDocument;
import com.denkensol.universaladmission.entity.StudentApplication;
import com.denkensol.universaladmission.entity.StudentDocument;
import com.denkensol.universaladmission.entity.StudentEducation;
import com.denkensol.universaladmission.entity.StudentEmployement;
import com.denkensol.universaladmission.entity.StudentProfile;
import com.denkensol.universaladmission.entity.StudentRecommender;
import com.denkensol.universaladmission.entity.StudentSchoolDocument;
import com.denkensol.universaladmission.entity.StudentTesting;
import com.denkensol.universaladmission.entity.StudentWriting;
import com.denkensol.universaladmission.requestresponse.SchoolDocumentRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentDashBoardProfileResponse;
import com.denkensol.universaladmission.requestresponse.StudentDocumentResponse;
import com.denkensol.universaladmission.requestresponse.StudentEducationRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentEmployementRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentProfileRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentRecommenderRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentTestingRequestResponse;
import com.denkensol.universaladmission.requestresponse.StudentWritingRequestResponse;
import com.denkensol.universaladmission.service.StudentService;
import com.denkensol.universaladmission.template.AmazonS3Template;
import com.denkensol.universaladmission.util.CommonUtil;
import com.google.gson.Gson;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private Environment env;

	@Autowired
	StudentProfileDao studentProfileDao;

	@Autowired
	StudentDocumentsDao studentDocumentsDao;

	@Autowired
	StudentEducationDao studentEducationDao;

	@Autowired

	StudentFamilyDao studentFamilyDao;

	@Autowired
	StudentTestingDao studentTestingDao;

	@Autowired
	StudentWritingDao studentWritingDao;

	@Autowired
	AccountDao accountDao;

	@Autowired
	ReligiousDao religiousDao;

	@Autowired
	CountryDao countryDao;

	@Autowired
	VisaTypeDao visaTypeDao;

	@Autowired
	LanguageDao languageDao;

	@Autowired
	SchoolDao schoolDao;

	@Autowired
	SchoolDegreeDao schoolDegreeDao;

	@Autowired
	StudentEmploymentDao studentEmploymentDao;

	@Autowired
	StudentSchoolDocumentDao studentSchoolDocumentDao;

	@Autowired
	SchoolDocumentDao schoolDocumentDao;

	@Autowired
	StudentRecommenderDao studentRecommenderDao;

	@Autowired
	StudentApplicationDao studentApplicationDao;

	@Autowired
	AmazonS3Template amazonS3Template;

	@Override
	@Transactional
	public Long saveUpdateStudentProfile(StudentProfileRequestResponse studentProfileRequestResponse, Account account) {
		Long studentProfileGuid = null;
		StudentProfile studentProfile = new StudentProfile();
		if (studentProfileRequestResponse.getStudentProfileGuid() != null) {
			studentProfile = studentProfileDao
					.getStudentProfileByGuId(studentProfileRequestResponse.getStudentProfileGuid().longValue());
		}
		String studentProfileRequestJsonStr = new Gson().toJson(studentProfileRequestResponse);
		studentProfile = new Gson().fromJson(studentProfileRequestJsonStr, StudentProfile.class);
		if (studentProfileRequestResponse.getReligiousGuid() != null) {
			studentProfile.setReligious(
					religiousDao.getRelegiousById(studentProfileRequestResponse.getReligiousGuid().longValue()));
		}
		if (studentProfileRequestResponse.getCountryOfBirthGuid() != null) {
			studentProfile.setBirthCountry(
					countryDao.getCountryById(studentProfileRequestResponse.getCountryOfBirthGuid().longValue()));
		}
		if (studentProfileRequestResponse.getCitizenShipCountryGuid() != null) {
			studentProfile.setCitizenshipCountry(
					countryDao.getCountryById(studentProfileRequestResponse.getCitizenShipCountryGuid().longValue()));
		}
		if (studentProfileRequestResponse.getVisaTypeRequiredGuid() != null) {
			studentProfile.setVisaTypeRequired(
					visaTypeDao.getVisaTypeById(studentProfileRequestResponse.getVisaTypeRequiredGuid().longValue()));
		}
		if (studentProfileRequestResponse.getPermanentAddressCountryGuid() != null) {
			studentProfile.setPermanentAddressCountry(countryDao
					.getCountryById(studentProfileRequestResponse.getPermanentAddressCountryGuid().longValue()));
		}
		if (studentProfileRequestResponse.getMailingAddressCountryGuid() != null) {
			studentProfile.setMailingnAddressCountry(countryDao
					.getCountryById(studentProfileRequestResponse.getMailingAddressCountryGuid().longValue()));
		}
		if (studentProfileRequestResponse.getFirstLanguageGuid() != null) {
			studentProfile.setFirstLanguage(
					languageDao.getLanguageById(studentProfileRequestResponse.getFirstLanguageGuid().longValue()));
		}
		if (studentProfileRequestResponse.getSecondLanguageGuid() != null) {
			studentProfile.setSecondLanguage(
					languageDao.getLanguageById(studentProfileRequestResponse.getSecondLanguageGuid().longValue()));
		}
		if (studentProfileRequestResponse.getThirdLanguageGuid() != null) {
			studentProfile.setThirdLanguage(
					languageDao.getLanguageById(studentProfileRequestResponse.getThirdLanguageGuid().longValue()));
		}

		if (studentProfileRequestResponse.getEmergencyAddressCountryGuid() != null) {
			studentProfile.setEmergencyAddressCountry(countryDao
					.getCountryById(studentProfileRequestResponse.getEmergencyAddressCountryGuid().longValue()));
		}

		account.setEmailAddress(studentProfileRequestResponse.getEmailAddress());
		accountDao.saveAccount(account);
		studentProfile.setAccount(account);
		studentProfileGuid = studentProfileDao.saveUpdateStudentProfile(studentProfile);

		return studentProfileGuid;
	}

	@Override
	@Transactional
	public StudentProfileRequestResponse getStudentProfile(Long accountGuid) {
		StudentProfileRequestResponse studentProfileRequestResponse = studentProfileDao
				.getStudentProfileByAccountId(accountGuid);

		if (studentProfileRequestResponse != null && studentProfileRequestResponse.getDateOfBirth() != null
				&& studentProfileRequestResponse.getPermanentAddressCountryGuid() != null
				&& studentProfileRequestResponse.getMailingAddressCountryGuid() != null
				&& studentProfileRequestResponse.getHomeContactNo() != null
				&& studentProfileRequestResponse.getCountryOfBirthGuid() != null
				&& studentProfileRequestResponse.getCitizenShipCountryGuid() != null
				&& studentProfileRequestResponse.getEmergencyAddressCountryGuid() != null) {
			studentProfileRequestResponse.setProfileCompleted(true);
		}
		return studentProfileRequestResponse;
	}

	@Override
	@Transactional
	public List<StudentDocumentResponse> getStudentDocuments(Long guid) {
		List<StudentDocumentResponse> documentsList = studentDocumentsDao.getAllStudentDocuments(guid);
		String StudentDocumentPresignedUrl;
		String StudentDocumentViewPresignedUrl;

		if (CommonUtil.isListNotNullAndNotEmpty(documentsList)) {
			for (StudentDocumentResponse eachDocument : documentsList) {
				String fileExtension = CommonUtil.getExtension(eachDocument.getDocumentName());
				if (fileExtension != null && !fileExtension.isEmpty()) {
					fileExtension = "." + fileExtension;
				}

				String uploadsDir = env.getProperty("student.documents-upload-path");
				File resourceFileName = new File(uploadsDir);
				uploadsDir = resourceFileName.getAbsolutePath() + "/";
				// eachDocument.setDocumentPath(amazonS3Template.getS3ObjectUrl(eachDocument.getDocumentPathGuid()));
				StudentDocumentPresignedUrl = amazonS3Template.getStudentDocumentDownloadPresignedUrl(
						eachDocument.getDocumentPathGuid(), eachDocument.getDocumentName());
				eachDocument.setDocumentPath(StudentDocumentPresignedUrl);

				StudentDocumentViewPresignedUrl = amazonS3Template.getStudentDocumentViewPresignedUrl(
						eachDocument.getDocumentPathGuid(), eachDocument.getDocumentName());
				eachDocument.setViewDocumentUrl(StudentDocumentViewPresignedUrl);
			}
		}
		return documentsList;
	}

	@Override
	@Transactional
	public void uploadStudentDocument(MultipartHttpServletRequest request, HttpServletRequest servletRequest) {

		MultipartFile multipartFile = request.getFile("document_file");
		String documentType = request.getParameter("document_type");
		Long documentGuidLong = null;
		String documentGuid = request.getParameter("document_guid");
		if (documentGuid != null && !documentGuid.isEmpty() && !documentGuid.equalsIgnoreCase("null")) {
			documentGuidLong = Long.parseLong(documentGuid);
		}
		String documentName = multipartFile.getOriginalFilename();
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
			documentId = documentId + fileExtension;
			amazonS3Template.saveStudentDocuments(documentId, dest);
			StudentDocument studentDocument = new StudentDocument();
			if (documentGuidLong != null) {
				deleteStudentDocumentFromLocal(studentDocument);
			}
			studentDocument.setDocumentType(documentType);
			studentDocument.setDocumentName(documentName);
			studentDocument.setDocumentPathGuid(documentId);
			HttpSession session = request.getSession(true);
			Account account = (Account) session.getAttribute("account");
			studentDocument.setAccount(account);
			studentDocumentsDao.uploadStudentDocument(studentDocument);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		} finally {

			if (dest != null) {
				dest.delete();
			}
		}
	}

	private void deleteStudentDocumentFromLocal(StudentDocument studentDocument) {

		String documentName = studentDocument.getDocumentName();

		String fileExtension = CommonUtil.getExtension(documentName);
		if (fileExtension != null && !fileExtension.isEmpty()) {
			fileExtension = "." + fileExtension;
		}

		String uploadsDir = env.getProperty("student.documents-upload-path");
		if (!new File(uploadsDir).exists()) {
			new File(uploadsDir).mkdir();
		}

		File resourceFileName = new File(uploadsDir);
		uploadsDir = resourceFileName.getAbsolutePath() + "\\";

		String documentId = studentDocument.getDocumentPathGuid();
		String filePath = uploadsDir + documentId + fileExtension;
		File dest = new File(filePath);
		dest.delete();
	}

	@Override
	@Transactional
	public void deleteStudentDocument(Long studentDocumentGuid) {
		StudentDocument studentDocument = studentDocumentsDao.getStudentDocumentByGuid(studentDocumentGuid);

		String fileExtension = CommonUtil.getExtension(studentDocument.getDocumentName());
		if (fileExtension != null && !fileExtension.isEmpty()) {
			fileExtension = "." + fileExtension;
		}
		amazonS3Template.deleteDocument(studentDocument.getDocumentPathGuid() + fileExtension);

		deleteStudentDocumentFromLocal(studentDocument);
		studentDocumentsDao.deleteStudentDocument(studentDocument);
	}

	@Override
	@Transactional
	public String downloadStudentDocument(Long studentDocumentGuid) {
		StudentDocument studentDocument = studentDocumentsDao.getStudentDocumentByGuid(studentDocumentGuid);
		String fileExtension = CommonUtil.getExtension(studentDocument.getDocumentName());
		if (fileExtension != null && !fileExtension.isEmpty()) {
			fileExtension = "." + fileExtension;
		}
		return amazonS3Template.getStudentDocument(studentDocument.getDocumentPathGuid());
	}

	@Override
	@Transactional
	public List<StudentWritingRequestResponse> getStudentWritings(Long guid) {
		return studentWritingDao.getStudentWritings(guid);
	}

	@Override
	@Transactional
	public void saveStudentWriting(StudentWritingRequestResponse studentWritingRequestResponse,
			HttpServletRequest servletRequest) {
		StudentWriting studentWriting = null;
		studentWriting = new StudentWriting();
		if (studentWritingRequestResponse.getStudentWritingGuid() != null) {
			studentWriting = studentWritingDao
					.getStudentWritingByGuid(studentWritingRequestResponse.getStudentWritingGuid().longValue());
			if (studentWriting == null) {
				studentWriting = new StudentWriting();
			}
		}
		HttpSession session = servletRequest.getSession(true);
		Account account = (Account) session.getAttribute("account");
		studentWriting.setAccount(account);
		studentWriting.setWritingType(studentWritingRequestResponse.getWritingType());
		studentWriting.setWritingText(studentWritingRequestResponse.getWritingText());
		studentWritingDao.saveUpdateStudentWriting(studentWriting);
	}

	@Override
	@Transactional
	public void deleteStudentWriting(Long studentWritingGuid) {
		StudentWriting studentWriting = studentWritingDao.getStudentWritingByGuid(studentWritingGuid);
		studentWritingDao.deleteStudentWriting(studentWriting);
	}

	@Override
	@Transactional
	public void saveStudentTesting(StudentTestingRequestResponse studentTestingRequestResponse,
			HttpServletRequest servletRequest) {

		StudentTesting studentTesting = new StudentTesting();
		if (studentTestingRequestResponse.getStudentTestingGuid() != null) {
			studentTesting = studentTestingDao
					.getStudentTestByGuid(studentTestingRequestResponse.getStudentTestingGuid().longValue());
		}
		String studentProfileRequestJsonStr = new Gson().toJson(studentTestingRequestResponse);
		studentTesting = new Gson().fromJson(studentProfileRequestJsonStr, StudentTesting.class);
		HttpSession session = servletRequest.getSession(true);
		Account account = (Account) session.getAttribute("account");
		studentTesting.setAccount(account);
		studentTestingDao.saveUpdateStudentTestData(studentTesting);
	}

	@Override
	@Transactional
	public Boolean getStudentApplication(Long accountGuid) {
		boolean flag = false;
		StudentApplication studentApplication = studentApplicationDao.getStudentApplicationsByGuid(accountGuid);
		if (studentApplication != null && "X".equals(studentApplication.getIsTransferStudent())) {
			flag = true;
		}
		return flag;
	}

	@Override
	@Transactional
	public List<StudentTestingRequestResponse> getStudentTestings(Long guid) {
		return studentTestingDao.getStudentTestings(guid);
	}

	@Override
	@Transactional
	public void deleteStudentTesting(Long studentTestingGuid) {
		StudentTesting studentTesting = studentTestingDao.getStudentTestByGuid(studentTestingGuid);
		studentTestingDao.deleteStudentTesting(studentTesting);
	}

	@Override
	@Transactional
	public List<StudentEducationRequestResponse> getStudentEducations(Long accountGuid) {
		return studentEducationDao.getStudentEducations(accountGuid);

	}

	@Override
	@Transactional
	public void saveStudentEducation(StudentEducationRequestResponse studentEducationRequestResponse,
			HttpServletRequest servletRequest) {

		StudentEducation studentEducation = new StudentEducation();
		if (studentEducationRequestResponse.getStudentEducationGuid() != null) {
			studentEducation = studentEducationDao
					.getStudentEducationByGuid(studentEducationRequestResponse.getStudentEducationGuid().longValue());
		}
		String studentEducationRequestResponseJsonStr = new Gson().toJson(studentEducationRequestResponse);
		studentEducation = new Gson().fromJson(studentEducationRequestResponseJsonStr, StudentEducation.class);
		if (studentEducationRequestResponse.getDegreeSchoolGuid() != null) {
			studentEducation.setDegreeSchool(
					schoolDao.getSchoolByGuid(studentEducationRequestResponse.getDegreeSchoolGuid().longValue()));
		}
		studentEducation.setDegreeObtained(studentEducationRequestResponse.getDegreeObtained());
		HttpSession session = servletRequest.getSession(true);
		Account account = (Account) session.getAttribute("account");
		studentEducation.setAccount(account);
		studentEducationDao.saveUpdateStudentEducation(studentEducation);

	}

	@Override
	@Transactional
	public void deleteStudentEducation(Long studentEducationGuid) {
		StudentEducation studentEducation = studentEducationDao.getStudentEducationByGuid(studentEducationGuid);
		studentEducationDao.deleteStudentEducation(studentEducation);
	}

	@Override
	@Transactional
	public List<StudentEmployementRequestResponse> getStudentEmployement(Long guid) {
		return studentEmploymentDao.getStudentEmployements(guid);
	}

	@Override
	@Transactional
	public void saveStudentEmployement(StudentEmployementRequestResponse studentEmployementRequestResponse,
			HttpServletRequest servletRequest) {

		StudentEmployement studentEmployement = new StudentEmployement();
		if (studentEmployementRequestResponse.getStudentEmployementGuid() != null) {
			studentEmployement = studentEmploymentDao.getStudentEmployementByGuid(
					studentEmployementRequestResponse.getStudentEmployementGuid().longValue());
		}
		String studentEmployementRequestJsonStr = new Gson().toJson(studentEmployementRequestResponse);
		studentEmployement = new Gson().fromJson(studentEmployementRequestJsonStr, StudentEmployement.class);

		if (studentEmployementRequestResponse.getEmployementCountryGuid() != null) {
			studentEmployement.setEmployementCountry(countryDao
					.getCountryById(studentEmployementRequestResponse.getEmployementCountryGuid().longValue()));
		}
		HttpSession session = servletRequest.getSession(true);
		Account account = (Account) session.getAttribute("account");
		studentEmployement.setAccount(account);
		studentEmploymentDao.saveUpdateStudentEmployement(studentEmployement);
	}

	@Override
	@Transactional
	public void deleteStudentEmployement(Long studentEmployementGuid) {
		StudentEmployement studentEmployement = studentEmploymentDao
				.getStudentEmployementByGuid(studentEmployementGuid);
		studentEmploymentDao.deleteStudentEmployement(studentEmployement);
	}

	@Override
	@Transactional
	public StudentDashBoardProfileResponse getStudentDashBoardProfileDetails(Long guid) {
		StudentDashBoardProfileResponse studentDashBoardProfileResponse = new StudentDashBoardProfileResponse();
		StudentProfile studentProfile = studentProfileDao.getStudentProfileByAccountGuId(guid);
		int educationRecords = 0;
		int testRecords = 0;
		int writngRecords = 0;
		int documentRecords = 0;
		int employementRecords = 0;
		int recommenders = 0;

		List<StudentEducation> a = studentEducationDao.getAllStudentEducations(guid);
		if (CommonUtil.isListNotNullAndNotEmpty(a)) {
			educationRecords = a.size();
		}
		List<StudentTesting> b = studentTestingDao.getAllStudentTests(guid);
		if (CommonUtil.isListNotNullAndNotEmpty(b)) {
			testRecords = b.size();
		}
		List<StudentWriting> c = studentWritingDao.getAllStudentWritings(guid);
		if (CommonUtil.isListNotNullAndNotEmpty(c)) {
			writngRecords = c.size();
		}
		List<StudentDocumentResponse> d = studentDocumentsDao.getAllStudentDocuments(guid);
		if (CommonUtil.isListNotNullAndNotEmpty(d)) {
			documentRecords = d.size();
		}
		List<StudentEmployement> e = studentEmploymentDao.getAllStudentEmployements(guid);
		if (CommonUtil.isListNotNullAndNotEmpty(e)) {
			employementRecords = e.size();
		}
		List<StudentRecommender> f = studentRecommenderDao.getAllStudentRecommenders(guid);
		if (CommonUtil.isListNotNullAndNotEmpty(f)) {
			recommenders = f.size();
		}
		if (studentProfile != null && studentProfile.getDateOfBirth() != null
				&& studentProfile.getPermanentAddressCountry() != null
				&& studentProfile.getMailingnAddressCountry() != null && studentProfile.getHomeContactNo() != null
				&& studentProfile.getBirthCountry() != null && studentProfile.getCitizenshipCountry() != null
				&& studentProfile.getEmergencyAddressCountry() != null) {

			studentDashBoardProfileResponse.setProfileCompleted(true);

		}

		if (educationRecords > 0) {
			studentDashBoardProfileResponse.setEducationCompleted(true);
		}
		if (testRecords > 0) {
			studentDashBoardProfileResponse.setTestingCompleted(true);
		}
		if (writngRecords > 0) {
			studentDashBoardProfileResponse.setWritingCompleted(true);
		}
		if (documentRecords > 0) {
			studentDashBoardProfileResponse.setDocumentCompleted(true);
		}
		if (employementRecords > 0) {
			studentDashBoardProfileResponse.setEmployementCompleted(true);
		}
		if (recommenders > 0) {
			studentDashBoardProfileResponse.setRecommendersCompleted(true);
		}
		return studentDashBoardProfileResponse;
	}

	@Override
	@Transactional
	public void uploadStudentSchoolDocument(MultipartHttpServletRequest request, HttpServletRequest servletRequest) {
		// TODO Auto-generated method stub
		HttpSession session = servletRequest.getSession(true);

		Account account = (Account) session.getAttribute("account");
		String documentName = null;
		int indexNo = Integer.parseInt(request.getParameter("index_no"));
		MultipartFile multipartFile = request.getFile("document_file_" + indexNo);
		documentName = multipartFile.getOriginalFilename();
		String schoolDocumentGuidStr = request.getParameter("school_document_guid_" + indexNo);
		Long schoolDocumentGuid = null;
		School school = null;
		StudentApplication studentApplication = null;
		Long schoolGuid = null;
		if (!schoolDocumentGuidStr.equalsIgnoreCase("null") && schoolDocumentGuidStr != null
				&& !schoolDocumentGuidStr.isEmpty()) {
			schoolDocumentGuid = Long.parseLong(schoolDocumentGuidStr);
			Long accountGuid = account.getGuid();

			SchoolDocument schoolDocument = schoolDocumentDao.getSchoolDocumentByGuid(schoolDocumentGuid);
			if (schoolDocument != null) {
				schoolGuid = schoolDocument.getSchool().getSchoolGuid();
				school = schoolDao.getSchoolByGuid(schoolGuid);
				studentApplication = studentApplicationDao.getStudentApplicationBySchool(schoolGuid, accountGuid);
				studentApplication.setSchoolDocumentsStatus("Completed");
				studentApplicationDao.saveStudentApplication(studentApplication);

			}
		}
		Long studentSchoolDocumentGuid = null;
		String studentSchoolDocumentGuidStr = request.getParameter("student_school_document_guid_" + indexNo);
		if (!studentSchoolDocumentGuidStr.equalsIgnoreCase("null") && studentSchoolDocumentGuidStr != null
				&& !studentSchoolDocumentGuidStr.isEmpty()) {
			studentSchoolDocumentGuid = Long.parseLong(studentSchoolDocumentGuidStr);
		}

		String uploadsDir = env.getProperty("student.school.documents-upload-path");
		if (!new File(uploadsDir).exists()) {
			new File(uploadsDir).mkdir();
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
			documentId = documentId + fileExtension;
			amazonS3Template.saveStudentSchoolDocuments(documentId, dest);
			StudentSchoolDocument studentSchoolDocument = new StudentSchoolDocument();

			studentSchoolDocument.setDocumentName(documentName);
			if (studentSchoolDocumentGuid != null) {
				studentSchoolDocument = studentSchoolDocumentDao
						.getStudentSchoolDocumentByGuId(studentSchoolDocumentGuid);
			}
			if (schoolDocumentGuid != null) {
				SchoolDocument schoolDocument = schoolDocumentDao.getSchoolDocumentByGuid(schoolDocumentGuid);
				studentSchoolDocument.setSchoolDocument(schoolDocument);
			}

			if (school != null) {
				studentSchoolDocument.setSchool(school);
			}
			studentSchoolDocument.setAccount(account);
			studentSchoolDocument.setDocumentPath(amazonS3Template.getStudentSchoolDocumentS3ObjectUrl(documentId));
			studentSchoolDocumentDao.uploadStudentSchoolDocument(studentSchoolDocument);
		} catch (IllegalStateException |

				IOException e) {
			e.printStackTrace();
		} finally {

			if (dest != null) {
				dest.delete();
			}
		}

	}

	@Override
	@Transactional
	public List<SchoolDocumentRequestResponse> getSchoolDocuments(Long schoolGuid, HttpServletRequest servletRequest) {
		List<SchoolDocumentRequestResponse> schoolDocuments = new ArrayList<SchoolDocumentRequestResponse>();
		List<SchoolDocument> schollDocs = schoolDocumentDao.getSchoolDocuments(schoolGuid);
		HttpSession session = servletRequest.getSession(true);
		Account account = (Account) session.getAttribute("account");

		String SchoolDocumentPresignedUrl;
		String StudentSchoolDocumentPresignedUrl;
		String StudentSchoolDocumentViewPresignedUrl;

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
				schoolDocumentRequestResponse.setDegreeList(schoolDocument.getDegreeList());
				schoolDocumentRequestResponse.setSchoolGuid(schoolGuid);
				StudentSchoolDocument studentSchoolDocument = null;
				if (account != null) {
					studentSchoolDocument = studentSchoolDocumentDao.getDocumentByStudentAndDocumentId(
							schoolDocument.getSchoolDocumentGuid(), account.getGuid());
				} else {
					studentSchoolDocument = studentSchoolDocumentDao
							.getDocumentByStudentAndDocumentId(schoolDocument.getSchoolDocumentGuid(), (long) 1);
				}
				if (studentSchoolDocument != null) {
					SchoolDocumentPresignedUrl = amazonS3Template.getSchoolDocumentViewPresignedUrl(
							CommonUtil.getFileNameFromURL(schoolDocument.getDocumentPath()),
							schoolDocument.getDocumentName() + ".pdf");
					schoolDocumentRequestResponse.setDocumentPath(SchoolDocumentPresignedUrl);
					StudentSchoolDocumentPresignedUrl = amazonS3Template.getStudentSchoolDocumentDownloadPresignedUrl(
							CommonUtil.getFileNameFromURL(studentSchoolDocument.getDocumentPath()),
							studentSchoolDocument.getDocumentName());
					schoolDocumentRequestResponse.setUploadedDocumentName(StudentSchoolDocumentPresignedUrl);
					StudentSchoolDocumentViewPresignedUrl = amazonS3Template.getStudentSchoolDocumentViewPresignedUrl(
							CommonUtil.getFileNameFromURL(studentSchoolDocument.getDocumentPath()),
							studentSchoolDocument.getDocumentName());
					schoolDocumentRequestResponse.setViewDocumentUrl(StudentSchoolDocumentViewPresignedUrl);
					schoolDocumentRequestResponse.setStudentSchoolDocumentName(studentSchoolDocument.getDocumentName());

					schoolDocumentRequestResponse
							.setStudentSchoolDocumentGuid(studentSchoolDocument.getStudentSchoolDocumentGuid());
				}
				schoolDocuments.add(schoolDocumentRequestResponse);
			}
		}
		return schoolDocuments;
	}

	@Override
	@Transactional
	public StudentDocumentResponse getStudentDocument(Long studentDocumentGuid) {
		StudentDocument studentDocument = studentDocumentsDao.getStudentDocumentByGuid(studentDocumentGuid);
		StudentDocumentResponse studentDocumentResponse = new StudentDocumentResponse();

		studentDocumentResponse.setDocumentName(studentDocument.getDocumentName());
		studentDocumentResponse.setStudentDocumentGuid(BigInteger.valueOf(studentDocument.getStudentDocumentGuid()));
		studentDocumentResponse.setDocumentType(studentDocument.getDocumentType());
		studentDocumentResponse.setDocumentPathGuid(studentDocument.getDocumentPathGuid());
		String fileExtension = CommonUtil.getExtension(studentDocument.getDocumentName());
		if (fileExtension != null && !fileExtension.isEmpty()) {
			fileExtension = "." + fileExtension;
		}

		String uploadsDir = env.getProperty("java.io.tmpdir");
		File resourceFileName = new File(uploadsDir);
		uploadsDir = resourceFileName.getAbsolutePath() + "\\";
		try {
			amazonS3Template.deleteDocument(studentDocument.getDocumentPathGuid() + fileExtension);
		} catch (Exception e) {
			// TODO: handle exception
		}
		studentDocumentResponse.setDocumentPath(uploadsDir + studentDocument.getDocumentPathGuid() + fileExtension);

		return studentDocumentResponse;
	}

	@Override
	@Transactional
	public List<StudentRecommenderRequestResponse> getStudentRecommenders(Long guid) {
		List<StudentRecommenderRequestResponse> studentRecommendersList = new ArrayList<StudentRecommenderRequestResponse>();
		List<StudentRecommender> studentRecommenders = studentRecommenderDao.getAllStudentRecommenders(guid);
		if (CommonUtil.isListNotNullAndNotEmpty(studentRecommenders)) {
			for (StudentRecommender studentRecommender : studentRecommenders) {
				StudentRecommenderRequestResponse studentRecommenderRequestResponse = new StudentRecommenderRequestResponse();
				studentRecommenderRequestResponse
						.setStudentRecommenderGuid(studentRecommender.getStudentRecommenderGuid());
				studentRecommenderRequestResponse.setEmailAddress(studentRecommender.getEmailAddress());
				studentRecommenderRequestResponse.setOrganization(studentRecommender.getOrganization());
				studentRecommenderRequestResponse.setPosition(studentRecommender.getPosition());
				studentRecommenderRequestResponse.setRecommenderName(studentRecommender.getRecommenderName());
				studentRecommendersList.add(studentRecommenderRequestResponse);
			}
		}
		return studentRecommendersList;
	}

	@Override
	@Transactional
	public void saveStudentRecommender(StudentRecommenderRequestResponse studentRecommenderRequestResponse,
			HttpServletRequest servletRequest) {
		StudentRecommender studentRecommender = new StudentRecommender();
		if (studentRecommenderRequestResponse.getStudentRecommenderGuid() != null) {
			studentRecommender = studentRecommenderDao
					.getStudentRecommenderByGuid(studentRecommenderRequestResponse.getStudentRecommenderGuid());
		}
		HttpSession session = servletRequest.getSession(true);
		Account account = (Account) session.getAttribute("account");
		studentRecommender.setAccount(account);
		studentRecommender.setEmailAddress(studentRecommenderRequestResponse.getEmailAddress());
		studentRecommender.setOrganization(studentRecommenderRequestResponse.getOrganization());
		studentRecommender.setPosition(studentRecommenderRequestResponse.getPosition());
		studentRecommender.setRecommenderName(studentRecommenderRequestResponse.getRecommenderName());

		studentRecommenderDao.saveSaveStudentRecommender(studentRecommender);

	}

	@Override
	@Transactional
	public void deleteStudentRecommender(Long studentRecommenderGuid) {
		StudentRecommender studentRecommender = studentRecommenderDao
				.getStudentRecommenderByGuid(studentRecommenderGuid);
		studentRecommenderDao.deleteSaveStudentRecommender(studentRecommender);
	}

	@Override
	@Transactional
	public void submitSchoolApplication(Long schoolApplicationGuid) {
		StudentApplication studentApplication = studentApplicationDao
				.getStudentApplicationByGuid(schoolApplicationGuid);
		studentApplication.setAcceptTermsAndConditions("X");
		studentApplication.setApplicationStatus("Completed");
		studentApplication.setApplicationSubmissionTimeStamp(CommonUtil.getCurrentGMTTimestamp());
		studentApplicationDao.saveStudentApplication(studentApplication);
	}

}

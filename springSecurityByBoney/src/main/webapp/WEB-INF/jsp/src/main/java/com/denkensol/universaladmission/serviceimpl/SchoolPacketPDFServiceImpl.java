
package com.denkensol.universaladmission.serviceimpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.io.FileDeleteStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.util.SystemOutLogger;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.hibernate.result.Output;
import org.hibernate.validator.resourceloading.DelegatingResourceBundleLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.amazonaws.services.s3.model.S3Object;
import com.denkensol.universaladmission.dao.SchoolQuestionAnswerDao;
import com.denkensol.universaladmission.dao.SchoolQuestionAnswerOptionDao;
import com.denkensol.universaladmission.dao.SchoolQuestionDao;
import com.denkensol.universaladmission.dao.SchoolQuestionSectionDao;
import com.denkensol.universaladmission.dao.StudentApplicationDao;
import com.denkensol.universaladmission.dao.StudentDocumentsDao;
import com.denkensol.universaladmission.dao.StudentEducationDao;
import com.denkensol.universaladmission.dao.StudentEmploymentDao;
import com.denkensol.universaladmission.dao.StudentProfileDao;
import com.denkensol.universaladmission.dao.StudentRecommenderDao;
import com.denkensol.universaladmission.dao.StudentSchoolDocumentDao;
import com.denkensol.universaladmission.dao.StudentTestingDao;
import com.denkensol.universaladmission.dao.StudentWritingDao;
import com.denkensol.universaladmission.entity.Account;
import com.denkensol.universaladmission.entity.SchoolQuestion;
import com.denkensol.universaladmission.entity.SchoolQuestionAnswer;
import com.denkensol.universaladmission.entity.SchoolQuestionAnswerOption;
import com.denkensol.universaladmission.entity.SchoolQuestionSection;
import com.denkensol.universaladmission.entity.StudentApplication;
import com.denkensol.universaladmission.entity.StudentDocument;
import com.denkensol.universaladmission.entity.StudentEducation;
import com.denkensol.universaladmission.entity.StudentEmployement;
import com.denkensol.universaladmission.entity.StudentProfile;
import com.denkensol.universaladmission.entity.StudentRecommender;
import com.denkensol.universaladmission.entity.StudentSchoolDocument;
import com.denkensol.universaladmission.entity.StudentTesting;
import com.denkensol.universaladmission.entity.StudentWriting;
import com.denkensol.universaladmission.requestresponse.SchoolQuestionAnswerOptionResponse;
import com.denkensol.universaladmission.requestresponse.StudentDocumentResponse;
import com.denkensol.universaladmission.service.SchoolPacketPDFService;
import com.denkensol.universaladmission.service.StudentService;
import com.denkensol.universaladmission.template.AmazonS3Template;
import com.denkensol.universaladmission.util.CommonUtil;
import com.denkensol.universaladmission.util.EMailUtil;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfDocument;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

/**
 * 
 * @author Indrajit
 *
 */
@Service
public class SchoolPacketPDFServiceImpl implements SchoolPacketPDFService {

	@Autowired
	StudentApplicationDao studentApplicationDao;

	@Autowired
	StudentService studentService;

	@Autowired
	StudentProfileDao studentProfileDao;

	@Autowired
	StudentDocumentsDao studentDocumentsDao;

	@Autowired
	StudentEducationDao studentEductaionDao;

	@Autowired
	StudentTestingDao studentTestingDao;

	@Autowired
	StudentEmploymentDao studentEmploymentDao;

	@Autowired
	StudentRecommenderDao studentRecommenderDao;

	@Autowired
	StudentWritingDao studentWritingDao;

	@Autowired
	SchoolQuestionDao schoolQuestionDao;

	@Autowired
	SchoolQuestionSectionDao schoolQuestionSectionDao;

	@Autowired
	StudentSchoolDocumentDao studentSchoolDocumentDao;

	@Autowired
	SchoolQuestionAnswerDao schoolQuestionAnswerDao;

	@Autowired
	SchoolQuestionAnswerOptionDao schoolQuestionAnswerOptionDao;

	@Autowired
	AmazonS3Template amazonS3Template;

	@Autowired
	EMailUtil emailUtil;

	private static final String themeColor = "#F2545B";
	private static final String subThemeColor = "#f8f8ff";
	private static final String fontColor = "#000000";
	private static final String borderColor = "#ccc";
	private static final String backgroundColor = "#ffffff";

	@Transactional
	@Override
	public String getApplicationPDF(Long schoolApplicationGuid, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		StudentApplication studentApplication = studentApplicationDao
				.getStudentApplicationByGuid(schoolApplicationGuid);
		StudentProfile studentProfile = studentProfileDao.getStudentProfileByAccountGuId(account.getGuid());
		String documentKey = studentApplication.getSchoolAppPacketPDF();
		return amazonS3Template.getStudentPDFPresignedURL(
				studentProfile.getFirstName() + studentProfile.getLastName() + ".pdf", documentKey);

	}

	@Transactional
	@Override
	public String generatePdfPacket(Long schoolApplicationGuid, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		StudentApplication studentApplication = studentApplicationDao
				.getStudentApplicationByGuid(schoolApplicationGuid);
		String documentKey = generateStudentInfoPdf(account.getGuid(), schoolApplicationGuid);
		studentApplication.setSchoolAppPacketPDF(documentKey);
		StudentProfile studentProfile = studentProfileDao.getStudentProfileByAccountGuId(account.getGuid());
		return amazonS3Template.getStudentPDFPresignedURL(
				studentProfile.getFirstName() + studentProfile.getLastName() + ".pdf", documentKey);

	}

	/**
	 * Merge multiple pdf into one pdf
	 * 
	 * @param list
	 *            of pdf input stream
	 * @param outputStream
	 *            output file output stream
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void doMerge(List<InputStream> list, OutputStream outputStream) throws DocumentException, IOException {
		System.out.println(System.currentTimeMillis() + " 1");

		PDFMergerUtility pdfMergerUtility = null;
		if (CommonUtil.isListNotNullAndNotEmpty(list)) {
			try {
				pdfMergerUtility = new PDFMergerUtility();
				try {
					pdfMergerUtility.addSources(list);
					pdfMergerUtility.setDestinationStream(outputStream);
					pdfMergerUtility.mergeDocuments();
				} catch (Exception e) {
				}
			} finally {
				outputStream.flush();
				outputStream.close();
			}
		}
		System.out.println(System.currentTimeMillis() + " 1");

	}

	@Transactional
	@Override
	public String generateStudentInfoPdf(Long accountGuid, Long schoolApplicationGuid) {
		System.out.println(System.currentTimeMillis() + " Genrate");

		final String Default_Value = "";
		StringBuffer buffer = null;
		FileOutputStream generatPDFOutputSreamObject = null;
		FileInputStream generatPDFInputSreamObject = null;
		String result = null;
		File file = null;
		File mergeFile = null;
		File pdfFile = null;
		List<InputStream> inputFiles = null;
		String fileExtention = ".pdf";
		StudentProfile studentProfile = studentProfileDao.getStudentProfileByAccountGuId(accountGuid);
		String documentName = studentProfile.getFirstName().trim();
		String mergePDFDocumentName = "student-pdf-Documents/" + CommonUtil.generateSalt(10);
		List<StudentDocumentResponse> studentDocumentResponses = new ArrayList<StudentDocumentResponse>();
		studentDocumentResponses = studentService.getStudentDocuments(accountGuid);
		inputFiles = amazonS3Template.getAllDocuments(studentDocumentResponses);
		StudentApplication studentApplication = studentApplicationDao
				.getStudentApplicationByGuid(schoolApplicationGuid);
		Long schoolGuid = studentApplication.getSchool().getSchoolGuid();
		List<StudentSchoolDocument> studentSchoolDocumets = studentSchoolDocumentDao
				.getDocumentByStudentAndSchoolId(accountGuid, schoolGuid);
		List<InputStream> studentSchoolDocumentFile = amazonS3Template
				.getAllStudentSchoolDocuments(studentSchoolDocumets);
		if (CommonUtil.isListNotNullAndNotEmpty(studentSchoolDocumentFile)) {
			for (InputStream studentSchoolInputFile : studentSchoolDocumentFile) {
				inputFiles.add(studentSchoolInputFile);
			}
		}
		if (studentApplication.getSchoolAppPacketPDF() != null) {
			amazonS3Template.deleteDocument(studentApplication.getSchoolAppPacketPDF());
		}

		try {
			file = File.createTempFile(documentName, fileExtention);
			file.createNewFile();
			try {
				mergeFile = File.createTempFile(mergePDFDocumentName, fileExtention);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (studentProfile != null && studentProfile.getCitizenshipCountry() != null
					&& studentProfile.getBirthCountry() != null) {
				buffer = new StringBuffer();

				buffer.append("<html>");
				buffer.append("<head>");
				buffer.append("<style type='text/css'>");
				buffer.append(
						"div.header {margin-top:0px;position:fixed; top: 0;   left: 0;   right: 0;	    height: 20%;display: block;text-align: center;position: running(header);}");
				buffer.append(
						"div.footer {position: fixed;	    top: 50px;    left: 0;    bottom: 0;    right: 0;	display: block;position: running(footer);}");
				buffer.append("@page {margin-top: 24mm; background-color: " + backgroundColor
						+ ";@top-center { content:element(header)}}@page { @bottom-center { content:element(footer)}}");

				buffer.append(
						"td,th,table{font-size: small; font-style: small; font: small; font-family: sans-serif;height:25px;}");

				buffer.append(
						"body {font-family=sans-serif;font-style: normal|italic|oblique|initial|inherit;font-weight:normal;}");
				buffer.append(
						"div.footer { font-size: 50%; font-style: italic;  position: running(footer); top: 0; left: 0; }");

				buffer.append("#pagenumber:before { content: counter(page); } ");
				buffer.append("#pagecount:before { content: counter(pages); } ");
				buffer.append("@page {margin-top: 22mm; background-color: white;}");
				buffer.append("div.pdfPage { page-break-after: always;}");
				buffer.append("@page {size: letter;}</style></head>");
				buffer.append("<body style='bgcolor:" + backgroundColor + ";color:" + fontColor + ";'>");
				buffer.append("<div class='header'>");

				buffer.append(
						"<left><table  border='0' style='border-collapse: collapse;margin-top:-4px;margin-bottom:0px;margin-left:-5px;border-bottom:0px;table-layout:fixed;'><tr height='3px'><td style='margin-bottom:0px; min-width: 30px;text-align:left;color: "
								+ fontColor
								+ "; font-size: x-large; font-family: Georgia;'>SKOOLVILLE</td><td style=' min-width: 15px;color: "
								+ fontColor
								+ ";font-size: small; font-style: medium; font: medium; font-family:sans-serif; text-align:center;'>");
				buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getFirstName(), Default_Value) + ", ");
				buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getLastName(), Default_Value));
				buffer.append("</td><td style=' min-width: 20px;color: " + fontColor
						+ ";font-size: small; font-style: small; font: small; font-family: sans-serif;text-align:right;' >Application Status: Complete</td></tr>");
				buffer.append("</table>");
				buffer.append("</left>");
				buffer.append("<p style='margin-top:1px;margin-bottom:0px;'> <b align='left' style='color: " + fontColor
						+ ";font-size: normal; font-style: small; font: small; font-family: sans-serif;text-align:left;float:left;'>");
				buffer.append(result = StringUtils.defaultIfEmpty(studentApplication.getSchool().getSchoolName(), ""));
				String submissionDate = new SimpleDateFormat("MM/dd/yyyy")
						.format(studentApplication.getApplicationSubmissionTimeStamp());
				buffer.append("</b><b align='right' style='margin-top:0px;color: " + fontColor
						+ ";font-size: normal; font-style: small; font: small; font-family: sans-serif;text-align:right;float:right;'>Submission Date: "
						+ submissionDate + "</b>");

				buffer.append("</p>");
				buffer.append("<p><b align='left' style='color: " + fontColor
						+ ";font-size: small;font-style: medium; font: small; font-family: sans-serif;text-align:left;float:left;'>");
				buffer.append(result = StringUtils
						.defaultIfEmpty(studentApplication.getSchoolDegree().getDegreeOffered(), ""));
				buffer.append("</b><b align='right' style='color: " + fontColor
						+ ";font-size: normal; font-style: small; font: small; font-family: sans-serif;text-align:right;float:right;'>");
				buffer.append(studentApplication.getSchoolTerm().getTermYear() + " "
						+ studentApplication.getSchoolTerm().getTerm() + "</b>");

				buffer.append("</p>");
				buffer.append("<p style='margin-top:1px;margin-bottom:0px;'> <b align='left' style='color: " + fontColor
						+ ";font-size: normal; font-style: small; font: small; font-family: sans-serif;text-align:left;float:left;'>Student Type: ");

				String isTransfer = "Initial";
				if ("X".equals(studentApplication.getIsTransferStudent())) {
					isTransfer = "Transfer";
				}
				buffer.append(result = StringUtils.defaultIfEmpty(isTransfer, Default_Value));
				buffer.append("</b></p>");
				buffer.append("</div>");
				buffer.append(
						"<div class='footer'><h3 style='text-align:center;float:center;'><span id='pagenumber' /></h3> <p style='font-size:small;text-margin:right;float:right;'> Generated: "
								+ new SimpleDateFormat().format(new java.util.Date()) + "  </p></div>");

				buffer.append("<div class='pdfPage'>");
				buffer.append("<table  width ='100%'   style='border-spacing: 0px;border-top: 2px solid " + borderColor
						+ ";border-left: 2px solid " + borderColor + ";border-right:2px solid " + borderColor
						+ ";font-family:sans-serif;margin-bottom:0px;-fs-table-paginate: paginate;'>");
				buffer.append("<thead bgcolor='" + themeColor + "' style='background-color:" + themeColor + ";' ><tr>");
				buffer.append(
						"<td colspan='20' height='30px' style='text-align: left;font-weight:bold;font-size:20px;'>Biographic Information</td>");
				buffer.append("<td ></td>");
				buffer.append("<td ></td>");
				buffer.append("<td ></td>");

				buffer.append("</tr></thead><thead style='background-color:" + subThemeColor + ";'>");
				buffer.append("<tr>");
				buffer.append(
						"<td colspan='20' height='30px' style='text-align: left;font-weight:bold;font-size:15px;'>Profile</td>");
				buffer.append("<td height='30px'></td>");
				buffer.append("<td height='30px'></td>");
				buffer.append("<td height='30px'></td>");

				buffer.append("</tr></thead><tbody>");
				buffer.append("<tr>");
				buffer.append("<td colspan='7'><b>Title: </b></td>");
				buffer.append("<td ></td>");
				buffer.append("</tr>");
				buffer.append("<tr>");
				buffer.append("<td colspan='7'><b>First Name: </b></td>");
				buffer.append("<td  style='text-align:left;'>");
				buffer.append(
						result = StringUtils.defaultIfEmpty(studentProfile.getFirstName(), Default_Value) + " </td>");
				buffer.append("</tr>");
				buffer.append("<tr>");

				buffer.append("<td colspan='7'><b>Middle Name: </b></td>");
				buffer.append("<td  style='text-align:left;'>");
				buffer.append(
						result = StringUtils.defaultIfEmpty(studentProfile.getMiddleName(), Default_Value) + "</td>");

				buffer.append("</tr>");
				buffer.append("<tr>");
				buffer.append("<td colspan='7'><b>Last Name: </b></td>");
				buffer.append("<td style='text-align:left;'>");
				buffer.append(
						result = StringUtils.defaultIfEmpty(studentProfile.getLastName(), Default_Value) + "</td>");
				buffer.append("</tr>");
				buffer.append("<tr>");
				buffer.append("<td colspan='7'><b>Suffix: </b></td>");
				buffer.append("<td  style='text-align:left;'>");
				buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getSuffix(), "") + "</td>");
				buffer.append("</tr>");
				buffer.append("<tr>");
				buffer.append("<td colspan='7'><b>Gender or Sex: </b></td>");
				buffer.append("<td >");

				buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getGender(), Default_Value) + "</td>");
				buffer.append("<td ><b>Date Of Birth: </b></td>");
				buffer.append("<td >");
				buffer.append(
						result = StringUtils.defaultIfEmpty(studentProfile.getDateOfBirth(), Default_Value) + "</td>");
				buffer.append("</tr>");
				buffer.append("</tbody>");
				buffer.append("</table>");
				buffer.append("");

				buffer.append("<table  width ='100%'   style='border-spacing: 0px;border-left: 2px solid " + borderColor
						+ ";border-right:2px solid " + borderColor
						+ ";font-family:sans-serif;margin-top:0px;margin-bottom:0px;table-layout:fixed;-fs-table-paginate: paginate;'>");
				buffer.append("<thead style='background-color:" + subThemeColor + ";'><tr >");
				buffer.append("<td colspan='4' style='text-align: left;font-weight:bold;font-size:15px;' bgcolor='"
						+ subThemeColor + "' >Contact Information</td>");
				buffer.append("<td colspan='4' bgcolor='" + subThemeColor + "'></td>");
				buffer.append("<td colspan='4' bgcolor='" + subThemeColor + "'></td>");
				buffer.append("<td colspan='4' bgcolor='" + subThemeColor + "'></td>");

				buffer.append("</tr></thead>");
				buffer.append("<tbody><tr>");
				buffer.append("<td colspan='4'><b>Address Type</b></td>");
				buffer.append("<td colspan='4'></td>");
				buffer.append("<td colspan='4'><b>Address Type</b></td>");
				buffer.append("<td colspan='4'></td>");
				buffer.append("</tr><tr>");
				buffer.append("<td colspan='4'><b>Permanent</b></td>");
				buffer.append("<td colspan='4'></td>");
				buffer.append("<td colspan='4'><b>Mailing</b></td>");
				buffer.append("</tr>");
				buffer.append("<tr>");
				buffer.append("<td colspan='4'><b>Address: </b></td>");
				buffer.append("<td colspan='4'>");
				if (studentProfile.getPermanentAddressLine1() != null) {
					buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getPermanentAddressLine1(), ""));
					if (!("".equals(studentProfile.getPermanentAddressLine2()))
							&& studentProfile.getPermanentAddressLine2() != null) {

						buffer.append(", ");
						buffer.append(
								result = StringUtils.defaultIfEmpty(studentProfile.getPermanentAddressLine2(), ""));
					} else {
						buffer.append(
								result = StringUtils.defaultIfEmpty(studentProfile.getPermanentAddressLine2(), ""));
					}
					System.out.println(studentProfile.getPermanentAddressLine3());
					if (!("".equals(studentProfile.getPermanentAddressLine3()))
							&& studentProfile.getPermanentAddressLine3() != null) {
						buffer.append(", ");
						buffer.append(
								result = StringUtils.defaultIfEmpty(studentProfile.getPermanentAddressLine3(), ""));
					} else {
						buffer.append(
								result = StringUtils.defaultIfEmpty(studentProfile.getPermanentAddressLine3(), ""));
					}
				}
				buffer.append("</td>");
				buffer.append("<td colspan='4'><b>Address: </b></td>");
				buffer.append("<td colspan='4'>");
				if (studentProfile.getMailingAddressLine1() != null) {
					buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getMailingAddressLine1(), ""));
					if (!("".equals(studentProfile.getMailingAddressLine2()))
							&& studentProfile.getMailingAddressLine2() != null) {
						buffer.append(", ");
						buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getMailingAddressLine2(), ""));
					} else {
						buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getMailingAddressLine2(), ""));
					}

					if (!("".equals(studentProfile.getMailingAddressLine3()))
							&& studentProfile.getMailingAddressLine3() != null) {
						buffer.append(", ");
						buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getMailingAddressLine3(), ""));
					} else {
						buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getMailingAddressLine3(), ""));
					}
				}
				buffer.append("</td>");
				buffer.append("</tr>");
				buffer.append("<tr>");
				buffer.append("<td colspan='4'><b>City: </b></td>");
				buffer.append("<td colspan='4'>");
				buffer.append(
						result = StringUtils.defaultIfEmpty(studentProfile.getPermanentAddressCity(), Default_Value)
								+ "</td>");
				buffer.append("<td colspan='4'><b>City: </b></td>");
				buffer.append("<td colspan='4'>");
				buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getMailingAddressCity(), Default_Value)
						+ "</td>");
				buffer.append("</tr>");
				buffer.append("<tr>");
				buffer.append("<td colspan='4'><b>Zip Code: </b></td>");
				buffer.append("<td colspan='4'>");
				buffer.append(
						result = StringUtils.defaultIfEmpty(studentProfile.getPermanentAddressZipCode(), Default_Value)
								+ "</td>");
				buffer.append("<td colspan='4'><b>Zip Code: </b></td>");
				buffer.append("<td colspan='4'>");
				buffer.append(
						result = StringUtils.defaultIfEmpty(studentProfile.getMailingAddressZipCode(), Default_Value)
								+ "</td>");
				buffer.append("</tr>");

				buffer.append("<tr>");
				buffer.append("<td colspan='4'><b>Country: </b></td>");
				buffer.append("<td colspan='4'>");
				buffer.append(result = StringUtils.defaultIfEmpty(
						studentProfile.getPermanentAddressCountry().getCountryName(), Default_Value) + "</td>");
				buffer.append("<td colspan='4'><b>Country: </b></td>");
				buffer.append("<td colspan='4'>");
				buffer.append(
						result = StringUtils.defaultIfEmpty(studentProfile.getMailingnAddressCountry().getCountryName(),
								Default_Value) + "</td>");
				buffer.append("</tr><tr>");
				buffer.append("<td colspan='4'><b>Home Phone: </b></td>");
				buffer.append("<td colspan='4'>");
				buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getEmergencyContactNo(), Default_Value)
						+ "</td>");
				buffer.append("<td colspan='4'><b>Work Phone: </b></td>");
				buffer.append("<td colspan='4'>");
				buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getWorkContactNo(), Default_Value)
						+ "</td>");

				buffer.append("</tr><tr>");
				buffer.append("<td colspan='4'><b>Email: </b></td>");
				buffer.append("<td colspan='4'>");
				buffer.append(
						result = StringUtils.defaultIfEmpty(studentProfile.getEmailAddress(), Default_Value) + "</td>");

				buffer.append("</tr></tbody></table>");

				buffer.append("<table  width ='100%'   style='border-spacing: 0px;border-left: 2px solid " + borderColor
						+ ";border-right:2px solid " + borderColor
						+ ";font-family:sans-serif;margin-top:0px;margin-bottom:0px;-fs-table-paginate: paginate;'>");

				buffer.append("");
				buffer.append("<thead  bgcolor='" + subThemeColor + "' style='background-color:" + subThemeColor
						+ ";'> <tr>");
				buffer.append("<td colspan='10' style='text-align: left; font-size:15px;color:black;' bgcolor='"
						+ subThemeColor + "' ><b>Geography and Citizenship</b></td>");
				buffer.append("<td colspan='10' bgcolor='" + subThemeColor + "'></td>");
				buffer.append("<td colspan='10' bgcolor='" + subThemeColor + "'></td>");
				buffer.append("<td colspan='10' bgcolor='" + subThemeColor + "'></td>");

				buffer.append("</tr></thead><tbody><tr>");
				buffer.append("<td colspan='10'><b>Citizenship Status: </b></td>");
				String usCitizenship = null;
				if ("Yes".equals(studentProfile.getUsCitizen())) {
					usCitizenship = "Citizen";
				} else {
					usCitizenship = "Non-US Citizen";
				}

				buffer.append("<td colspan='10'>" + usCitizenship + "</td>");
				buffer.append("<td colspan='10'><b>Country of Citizenship: </b></td>");
				buffer.append("<td colspan='10'>");
				buffer.append(
						result = StringUtils.defaultIfEmpty(studentProfile.getCitizenshipCountry().getCountryName(),
								Default_Value) + "</td>");

				buffer.append("</tr><tr>");
				buffer.append("<td colspan='10'><b>US Visa Type: </b></td>");
				buffer.append("<td colspan='10'>");
				if (studentProfile.getVisaTypeRequired() == null && "No".equals(studentProfile.getUsCitizen())) {
					buffer.append(" F-1  </td>");
				} else if (studentProfile.getVisaTypeRequired() == null
						&& "Yes".equals(studentProfile.getUsCitizen())) {
					buffer.append(" N/A  </td>");

				} else {
					buffer.append(
							result = StringUtils.defaultIfEmpty(studentProfile.getVisaTypeRequired().getVisaTypeName(),
									Default_Value) + "</td>");
				}
				buffer.append("<td colspan='10'><b>City of Birth: </b></td>");
				buffer.append("<td colspan='10'>");
				buffer.append(
						result = StringUtils.defaultIfEmpty(studentProfile.getCityOfBirth(), Default_Value) + "</td>");

				buffer.append("</tr>");
				buffer.append("<tr>");
				buffer.append("<td colspan='10'><b>Country of Birth: </b></td>");
				buffer.append("<td colspan='10'>");
				buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getBirthCountry().getCountryName(),
						Default_Value) + "</td>");
				buffer.append("</tr>");
				buffer.append("<tr>");
				buffer.append("<td colspan='10'><b>Ethnicity: </b></td>");
				buffer.append("<td colspan='10'>");
				buffer.append(
						result = StringUtils.defaultIfEmpty(studentProfile.getEthnicity(), Default_Value) + "</td>");
				buffer.append("</tr>");
				buffer.append("<tr>");
				buffer.append("<td colspan='10'><b>Religious: </b></td>");
				buffer.append("<td colspan='10'>");
				buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getReligious().getReligiousName(),
						Default_Value) + "</td>");
				buffer.append("</tr></tbody>");

				buffer.append("</table>");

				buffer.append("<table  width ='100%'   style='border-spacing: 0px;border-bottom: 2px solid "
						+ borderColor + ";border-left: 2px solid " + borderColor + ";border-right:2px solid "
						+ borderColor
						+ ";font-family:sans-serif;margin-top:0px;margin-bottom:0px;-fs-table-paginate: paginate;'>");

				buffer.append("<thead  style='background-color:" + subThemeColor + ";' ><tr>");
				buffer.append("<td colspan='10' style='text-align: left;font-size:15px;' bgcolor='" + subThemeColor
						+ "' ><b>Other Information</b></td>");
				buffer.append("<td colspan='10' bgcolor='" + subThemeColor + "'></td>");

				buffer.append("");
				buffer.append("</tr></thead>");
				buffer.append("<tbody><tr>");
				buffer.append("<td colspan='10'><b>First Language: </b></td>");
				buffer.append("<td colspan='10'>");
				buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getFirstLanguage().getLanguageName(),
						Default_Value) + "</td>");
				buffer.append("</tr>");
				buffer.append("<tr>");
				buffer.append("<td colspan='10'><b>Second Language: </b></td>");
				buffer.append("<td colspan='10'>");
				buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getSecondLanguage().getLanguageName(),
						Default_Value) + "</td>");
				buffer.append("</tr>");
				buffer.append("<tr>");
				buffer.append("<td colspan='10'><b>Third Language: </b></td>");
				buffer.append("<td colspan='10'>");
				buffer.append(result = StringUtils.defaultIfEmpty(studentProfile.getThirdLanguage().getLanguageName(),
						Default_Value) + "</td>");
				buffer.append("</tr>");
				buffer.append("</tbody>");
				buffer.append("</table>");
				buffer.append("</div>");

				buffer.append("<div class='pdfPage'>");
				List<StudentEducation> studentEductaions = studentEductaionDao.getAllStudentEducations(accountGuid);
				if (CommonUtil.isListNotNullAndNotEmpty(studentEductaions)) {
					buffer.append("<table  width ='100%'   style='border-spacing: 0px;border-top: 2px solid "
							+ borderColor + ";border-left: 2px solid " + borderColor + ";border-right:2px solid "
							+ borderColor + ";font-family:sans-serif;margin-top:0px;margin-bottom:0px;'>");

					buffer.append("<thead  style='background-color:" + themeColor + ";' >");
					buffer.append("<tr>");
					buffer.append(
							"<td colspan='30' style='text-align: left;font-weight:bold;font-size:20px;'>Education</td>");
					buffer.append("<td bgcolor='" + themeColor + "' colspan='10'></td>");
					buffer.append("<td bgcolor='" + themeColor + "' colspan='10'></td>");
					buffer.append("<td bgcolor='" + themeColor + "' colspan='10'></td>");
					buffer.append("<td bgcolor='" + themeColor + "' colspan='10'></td>");

					buffer.append("");
					buffer.append("</tr></thead>");
					for (StudentEducation studentEducation : studentEductaions) {
						buffer.append("<thead  style='background-color:" + subThemeColor + ";' >");

						buffer.append("<tr>");
						buffer.append("<td colspan='30' style='text-align: left;font-size:15px;' bgcolor='"
								+ subThemeColor + "' ><b>");
						buffer.append(
								result = StringUtils.defaultIfEmpty(studentEducation.getSchoolName(), Default_Value)
										+ "</b></td>");
						buffer.append("<td bgcolor='" + subThemeColor + "' colspan='10'></td>");
						buffer.append("<td bgcolor='" + subThemeColor + "' colspan='10'></td>");
						buffer.append("<td bgcolor='" + subThemeColor + "' colspan='10'></td>");
						buffer.append("<td bgcolor='" + subThemeColor + "' colspan='10'></td>");

						buffer.append("</tr></thead>");
						buffer.append("<tbody><tr>");
						buffer.append("<td colspan='10'><b>Start Date</b></td>");
						buffer.append("<td colspan='10'><b>End Date</b></td>");
						buffer.append("<td colspan='10'><b>Degree Obtained</b></td>");
						buffer.append("<td colspan='10'><b>Major</b></td>");
						buffer.append("<td colspan='10'><b>CGPA Obtained</b></td>");
						buffer.append("</tr>");
						buffer.append("<tr>");

						buffer.append("<td colspan='10'>");
						buffer.append(
								result = StringUtils.defaultIfEmpty(studentEducation.getStartDate(), Default_Value)
										+ "</td>");

						buffer.append("<td colspan='10'>");
						buffer.append(result = StringUtils.defaultIfEmpty(studentEducation.getEndDate(), Default_Value)
								+ "</td>");

						buffer.append("<td colspan='10'>");
						buffer.append(
								result = StringUtils.defaultIfEmpty(studentEducation.getDegreeObtained(), Default_Value)
										+ "</td>");

						buffer.append("<td colspan='10'>");
						buffer.append(result = StringUtils.defaultIfEmpty(studentEducation.getMajor(), Default_Value)
								+ "</td>");
						buffer.append("<td colspan='10'>");
						buffer.append(
								result = StringUtils.defaultIfEmpty(studentEducation.getCgpaObtained(), Default_Value)
										+ "</td>");

						buffer.append("</tr></tbody>");

					}
					buffer.append("</table>");
				}

				List<StudentTesting> studentTestings = studentTestingDao.getAllStudentTests(accountGuid);
				if (CommonUtil.isListNotNullAndNotEmpty(studentTestings)) {

					buffer.append("<table  width ='100%'   style='border-spacing: 0px;border-left: 2px solid "
							+ borderColor + ";;border-bottom: 2px solid " + borderColor + ";border-right:2px solid "
							+ borderColor
							+ ";font-family:sans-serif;margin-top:0px;margin-bottom:0px;-fs-table-paginate: paginate;'>");

					buffer.append("<thead  style='background-color:" + themeColor + ";' >");
					buffer.append("<tr>");
					buffer.append(
							"<td colspan='10' style='text-align: left;font-weight:bold;font-size:20px;'>Testing</td>");
					buffer.append("<td colspan='10'  bgcolor='" + themeColor + "'></td>");
					buffer.append("<td colspan='10'  bgcolor='" + themeColor + "'></td>");
					buffer.append("<td colspan='10'  bgcolor='" + themeColor + "'></td>");
					buffer.append("<td colspan='10'  bgcolor='" + themeColor + "'></td>");

					buffer.append("");
					buffer.append("</tr></thead>");

					for (StudentTesting studentTesting : studentTestings) {
						String testType = studentTesting.getTestType().trim();

						if (!"GMAT".equals(testType)) {
							buffer.append("<thead  style='background-color:" + subThemeColor + ";' >");

							buffer.append("<tr>");
							buffer.append("<td colspan='10' style='text-align: left;font-size:15px;' bgcolor='"
									+ subThemeColor + "' ><b>");
							buffer.append(result = StringUtils.defaultIfEmpty(testType, Default_Value) + "</b></td>");
							buffer.append("<td colspan='10'  bgcolor='" + subThemeColor + "'></td>");
							buffer.append("<td colspan='10'  bgcolor='" + subThemeColor + "'></td>");
							buffer.append("<td colspan='10'  bgcolor='" + subThemeColor + "'></td>");
							buffer.append("<td colspan='10'  bgcolor='" + subThemeColor + "'></td>");

							buffer.append("");
							buffer.append("</tr></thead>");
						}

						if ("GRE".equals(testType)) {

							buffer.append("<tbody><tr>");
							buffer.append("<td colspan='10' style=' font-weight: bold;'>Test Taken on</td>");
							buffer.append("<td colspan='10' style='font-weight: bold;'>Verbal Score</td>");
							buffer.append("<td colspan='10' style='font-weight: bold;'>Quantitative Score</td>");
							buffer.append("<td colspan='10' style='font-weight: bold;'>Analytical Score</td>");
							buffer.append("<td colspan='10' style='font-weight: bold;'>Total Score</td>");
							buffer.append("</tr>");
							buffer.append("<tr>");
							buffer.append("<td colspan='10' >");
							buffer.append(
									result = StringUtils.defaultIfEmpty(studentTesting.getTestTakenOn(), Default_Value)
											+ "</td >");
							buffer.append("<td colspan='10' >");
							buffer.append(
									result = StringUtils.defaultIfEmpty(studentTesting.getVerbalScore(), Default_Value)
											+ "</td>");
							buffer.append("<td colspan='10' >");
							buffer.append(
									result = StringUtils.defaultIfEmpty(studentTesting.getQuantScore(), Default_Value)
											+ "</td>");
							buffer.append("<td colspan='10' > ");
							buffer.append(result = StringUtils.defaultIfEmpty(studentTesting.getAnalyticalScore(),
									Default_Value) + "</td>");
							buffer.append("<td colspan='10' > ");
							buffer.append(result = StringUtils.defaultIfEmpty(studentTesting.getOverAllBandScore(),
									Default_Value) + "</td>");
							buffer.append("</tr></tbody>");

						} else if ("TOEFL".equals(testType)) {

							buffer.append("<tbody><tr>");
							buffer.append("<td colspan='10' style=' font-weight: bold;'>Test Taken on</td>");
							buffer.append("<td colspan='10' style='font-weight: bold;'>Reading Score</td>");
							buffer.append("<td colspan='10' style='font-weight: bold;'>Listening Score</td>");
							buffer.append("<td colspan='10' style='font-weight: bold;'>Speaking Score</td>");
							buffer.append("<td colspan='10' style='font-weight: bold;'>Total Score</td>");
							buffer.append("</tr>");
							buffer.append("<tr>");
							buffer.append("<td colspan='10' >");
							buffer.append(
									result = StringUtils.defaultIfEmpty(studentTesting.getTestTakenOn(), Default_Value)
											+ "</td >");
							buffer.append("<td colspan='10' >");
							buffer.append(
									result = StringUtils.defaultIfEmpty(studentTesting.getReadingScore(), Default_Value)
											+ "</td>");
							buffer.append("<td colspan='10' >");
							buffer.append(result = StringUtils.defaultIfEmpty(studentTesting.getListeningScore(),
									Default_Value) + "</td>");
							buffer.append("<td colspan='10' > ");
							buffer.append(result = StringUtils.defaultIfEmpty(studentTesting.getSpeakingScore(),
									Default_Value) + "</td>");
							buffer.append("<td colspan='10' > ");
							buffer.append(result = StringUtils.defaultIfEmpty(studentTesting.getOverAllBandScore(),
									Default_Value) + "</td>");
							buffer.append("</tr></tbody>");

						}

					}
					buffer.append("</table>");
				}

				StringBuffer GMATBufferHtml = null;
				if (CommonUtil.isListNotNullAndNotEmpty(studentTestings)) {
					GMATBufferHtml = new StringBuffer();
					for (StudentTesting studentTesting : studentTestings) {
						String testType = studentTesting.getTestType().trim();
						if ("GMAT".equals(testType)) {
							GMATBufferHtml.append("<thead  style='background-color:" + subThemeColor + ";'>");
							GMATBufferHtml.append("<tr style='color: #A10A10F5;'>");
							GMATBufferHtml.append("<td colspan='8' style='text-align: left;font-size:15px;' bgcolor='"
									+ subThemeColor + "' ><b>");
							GMATBufferHtml
									.append(result = StringUtils.defaultIfEmpty(testType, Default_Value) + "</b></td>");
							GMATBufferHtml.append("<td colspan='8'  bgcolor='" + subThemeColor + "'></td>");
							GMATBufferHtml.append("<td colspan='8'  bgcolor='" + subThemeColor + "'></td>");
							GMATBufferHtml.append("<td colspan='8'  bgcolor='" + subThemeColor + "'></td>");
							GMATBufferHtml.append("<td colspan='8'  bgcolor='" + subThemeColor + "'></td>");
							GMATBufferHtml.append("<td colspan='8'  bgcolor='" + subThemeColor + "'></td>");

							GMATBufferHtml.append("");
							GMATBufferHtml.append("</tr></thead>");

							GMATBufferHtml.append("<tbody><tr>");
							GMATBufferHtml.append("<td colspan='8' style=' font-weight: bold;'>Test Taken on</td>");
							GMATBufferHtml.append("<td colspan='8' style='font-weight: bold;'>Verbal Score</td>");
							GMATBufferHtml.append("<td colspan='8' style='font-weight: bold;'>Quantitative Score</td>");
							GMATBufferHtml.append("<td colspan='8' style='font-weight: bold;'>Analytical Score</td>");
							GMATBufferHtml.append("<td colspan='8' style='font-weight: bold;'>Reasoning Score</td>");
							GMATBufferHtml.append("<td colspan='8' style='font-weight: bold;'>Total Score</td>");
							GMATBufferHtml.append("</tr>");
							GMATBufferHtml.append("<tr>");
							GMATBufferHtml.append("<td colspan='8' >");
							GMATBufferHtml.append(
									result = StringUtils.defaultIfEmpty(studentTesting.getTestTakenOn(), Default_Value)
											+ "</td >");
							GMATBufferHtml.append("<td colspan='8' style='text-align:center;'>");
							GMATBufferHtml.append(
									result = StringUtils.defaultIfEmpty(studentTesting.getVerbalScore(), Default_Value)
											+ "</td>");
							GMATBufferHtml.append("<td colspan='8' style='text-align:center;'>");
							GMATBufferHtml.append(
									result = StringUtils.defaultIfEmpty(studentTesting.getQuantScore(), Default_Value)
											+ "</td>");
							GMATBufferHtml.append("<td colspan='8' style='text-align:center;'> ");
							GMATBufferHtml.append(result = StringUtils
									.defaultIfEmpty(studentTesting.getAnalyticalScore(), Default_Value) + "</td>");

							GMATBufferHtml.append("<td colspan='8' style='text-align:center;'> ");
							GMATBufferHtml.append(
									result = StringUtils.defaultIfEmpty(studentTesting.getIntegratedReasoningScore(),
											Default_Value) + "</td>");
							GMATBufferHtml.append("<td colspan='8' style='text-align:center;'> ");
							GMATBufferHtml.append(result = StringUtils
									.defaultIfEmpty(studentTesting.getOverAllBandScore(), Default_Value) + "</td>");
							GMATBufferHtml.append("</tr></tbody>");

						}
					}

					if (GMATBufferHtml != null && GMATBufferHtml.length() != 0) {
						buffer.append("<table  width ='100%'   style='border-spacing: 0px;border-bottom: 2px solid "
								+ borderColor + ";border-left: 2px solid " + borderColor + ";border-right:2px solid "
								+ borderColor + ";font-family:sans-serif;margin-top:0px;margin-bottom:0px;'>");
						buffer.append(GMATBufferHtml);
						buffer.append("</table>");
					}

				}
				List<StudentEmployement> studentEmployments = studentEmploymentDao
						.getAllStudentEmployements(accountGuid);
				if (CommonUtil.isListNotNullAndNotEmpty(studentEmployments)) {
					buffer.append("<table  width ='100%'   style='border-spacing: 0px;border-bottom: 2px solid "
							+ borderColor + ";border-left: 2px solid " + borderColor + ";border-right:2px solid "
							+ borderColor + ";font-family:sans-serif;margin-top:0px;margin-bottom:0px;'>");
					buffer.append("<thead  style='background-color:" + themeColor + ";'>");

					buffer.append("<tr style='color: sky" + borderColor + "; background-color:" + themeColor + ";' >");
					buffer.append(
							"<td colspan='10' style='text-align: left;font-weight:bold;font-size:20px;'>Employment</td>");
					buffer.append("<td colspan='10' bgcolor='" + themeColor + "'></td>");
					buffer.append("<td colspan='10' bgcolor='" + themeColor + "'></td>");
					buffer.append("<td colspan='10' bgcolor='" + themeColor + "'></td>");
					buffer.append("<td colspan='10' bgcolor='" + themeColor + "'></td>");
					buffer.append("<td colspan='10' bgcolor='" + themeColor + "'></td>");
					buffer.append("<td colspan='10' bgcolor='" + themeColor + "'></td>");

					buffer.append("");
					buffer.append("</tr></thead>");
					for (StudentEmployement studentEmployment : studentEmployments) {
						buffer.append("<thead  style='background-color:" + subThemeColor + ";'>");

						buffer.append("<tr style='color: #A10A10F5;'>");
						buffer.append("<td colspan='10' style='text-align: left;font-size:15px;' bgcolor='"
								+ subThemeColor + "' ><b>");
						buffer.append(
								result = StringUtils.defaultIfEmpty(studentEmployment.getEmployer(), Default_Value)
										+ "</b></td>");
						buffer.append("<td colspan='10' bgcolor='" + subThemeColor + "'></td>");
						buffer.append("<td colspan='10' bgcolor='" + subThemeColor + "'></td>");
						buffer.append("<td colspan='10' bgcolor='" + subThemeColor + "'></td>");
						buffer.append("<td colspan='10' bgcolor='" + subThemeColor + "'></td>");
						buffer.append("<td colspan='10' bgcolor='" + subThemeColor + "'></td>");
						buffer.append("<td colspan='10' bgcolor='" + subThemeColor + "'></td>");

						buffer.append("");
						buffer.append("</tr></thead>");
						buffer.append("<tbody><tr>");
						buffer.append("<td colspan='10' style=' font-weight: bold;'>Start Date</td>");
						buffer.append("<td colspan='10' style='font-weight: bold;'>End Date</td>");
						buffer.append("<td colspan='10' style='font-weight: bold;'>Position Title</td>");
						buffer.append("<td colspan='10' style='font-weight: bold;'>City</td>");
						buffer.append("<td colspan='10' style='font-weight: bold;'>State</td>");
						buffer.append("<td colspan='10' style='font-weight: bold;'>Country</td>");
						buffer.append("<td colspan='10' style='font-weight: bold;'>Supervisor Name</td>");
						buffer.append("</tr>");
						buffer.append("<tr>");
						buffer.append("<td colspan='10'>");
						buffer.append(
								result = StringUtils.defaultIfEmpty(studentEmployment.getStartDate(), Default_Value)
										+ "</td >");
						buffer.append("<td colspan='10'>");

						buffer.append(result = StringUtils.defaultIfEmpty(studentEmployment.getEndDate(), Default_Value)
								+ "</td>");
						buffer.append("<td colspan='10'>");
						buffer.append(
								result = StringUtils.defaultIfEmpty(studentEmployment.getPositionTitle(), Default_Value)
										+ "</td>");
						buffer.append("<td colspan='10'> ");
						buffer.append(result = StringUtils.defaultIfEmpty(studentEmployment.getCity(), Default_Value)
								+ "</td>");
						buffer.append("<td colspan='10'> ");
						buffer.append(result = StringUtils.defaultIfEmpty(studentEmployment.getState(), Default_Value)
								+ "</td>");
						buffer.append("<td colspan='10'> ");
						buffer.append(result = StringUtils.defaultIfEmpty(
								studentEmployment.getEmployementCountry().getCountryName(), Default_Value) + "</td>");
						buffer.append("<td colspan='10'> ");
						buffer.append(result = StringUtils.defaultIfEmpty(studentEmployment.getSupervisorName(),
								Default_Value) + "</td>");
						buffer.append("</tr></tbody>");

					}

					buffer.append("");

					buffer.append("</table>");

				}
				buffer.append("</div>");
				buffer.append(
						"<div class='footer'><h3 style='text-align:center;float:center;'><span id='pagenumber' /></h3> <p style='font-size:small;text-margin:right;float:right;'> Generated: "
								+ new SimpleDateFormat().format(new java.util.Date()) + "  </p></div>");
				buffer.append("<div class='pdfPage'>");

				buffer.append("<table  width ='100%'   style='border-spacing: 0px;border-top: 2px solid " + borderColor
						+ ";border-left: 2px solid " + borderColor + ";border-right:2px solid " + borderColor
						+ ";font-family:sans-serif;margin-top:0px;margin-bottom:0px;table-layout:fixed;'>");
				buffer.append("<thead  style='background-color:" + themeColor + ";' >");
				buffer.append("<tr style='color: sky" + borderColor + "; background-color:" + themeColor + ";' >");
				buffer.append(
						"<td style='text-align: left;font-weight:bold;font-size:20px;' >Statement of Purpose/Personal Essay</td>");
				buffer.append("");
				buffer.append("</tr></thead>");
				List<StudentWriting> studentWritings = studentWritingDao.getAllStudentWritings(accountGuid);
				if (CommonUtil.isListNotNullAndNotEmpty(studentWritings)) {
					for (StudentWriting studentWriting : studentWritings) {
						String writingText = studentWriting.getWritingText();
						StrBuilder str = new StrBuilder(writingText);
						str.replaceAll("</o:p>", "</p>");
						str.replaceAll("<o:p>", "<p>");
						str.replaceAll("&nbsp;", " ");
						str.replaceAll(Character.toString('"'), "'");
						str.replaceAll("nbsp", " ");
						str.replaceAll("&", "and");
						buffer.append("<tbody><tr>");
						buffer.append("<td>" + str.toString() + "</td>");
						buffer.append("</tr></tbody>");

					}
				}

				buffer.append("</table>");

				buffer.append("<table  width ='100%'   style='border-spacing: 0px;border-left: 2px solid " + borderColor
						+ ";border-bottom: 2px solid " + borderColor + ";border-right:2px solid " + borderColor
						+ ";font-family:sans-serif;margin-top:0px;margin-bottom:0px;'>");
				buffer.append("<thead  style='background-color:" + themeColor + ";'>");
				buffer.append("<tr style='color: sky" + borderColor + "; background-color:" + themeColor + ";'>");
				buffer.append(
						"<td colspan='10' style='text-align: left;font-weight:bold;font-size:20px;'>Recommdenders</td>");
				buffer.append("<td colspan='10' bgcolor='" + themeColor + "'></td>");
				buffer.append("<td colspan='10' bgcolor='" + themeColor + "'></td>");
				buffer.append("<td colspan='10' bgcolor='" + themeColor + "'></td>");

				buffer.append("");
				buffer.append("</tr></thead>");
				buffer.append("<thead><tr>");
				buffer.append("<th colspan='10' style=' font-weight: bold;'>Recommender Name</th>");
				buffer.append("<th colspan='10' style='font-weight: bold;'> Organization</th>");
				buffer.append("<th colspan='10' style='font-weight: bold;'>Position</th>");
				buffer.append("<th colspan='10' style='font-weight: bold;'>Email</th>");
				buffer.append("</tr></thead>");

				List<StudentRecommender> studentRecommenders = studentRecommenderDao
						.getAllStudentRecommenders(accountGuid);
				if (CommonUtil.isListNotNullAndNotEmpty(studentRecommenders)) {
					for (StudentRecommender studentRecommender : studentRecommenders) {
						buffer.append("<tbody><tr>");
						buffer.append("<td colspan='10'>");
						buffer.append(result = StringUtils.defaultIfEmpty(studentRecommender.getRecommenderName(),
								Default_Value) + "</td >");
						buffer.append("<td colspan='10'>");
						buffer.append(
								result = StringUtils.defaultIfEmpty(studentRecommender.getOrganization(), Default_Value)
										+ "</td >");
						buffer.append("<td colspan='10'>");
						buffer.append(
								result = StringUtils.defaultIfEmpty(studentRecommender.getPosition(), Default_Value)
										+ "</td >");
						buffer.append("<td colspan='10'>");
						buffer.append(
								result = StringUtils.defaultIfEmpty(studentRecommender.getEmailAddress(), Default_Value)
										+ "</td >");

						buffer.append("</tr></tbody>");

					}

				}
				buffer.append("</table>");
				buffer.append("</div>");
				buffer.append("<div>");
				List<SchoolQuestionSection> schoolQuestionSections = schoolQuestionSectionDao
						.getSchoolSectionQuestions(schoolGuid);
				if (CommonUtil.isListNotNullAndNotEmpty(schoolQuestionSections)) {

					buffer.append("<table  width ='100%'   style='border-spacing: 0px;border-bottom: 2px solid "
							+ borderColor + ";border-top: 2px solid " + borderColor + ";border-left: 2px solid "
							+ borderColor + ";border-right:2px solid " + borderColor
							+ ";font-family:sans-serif;margin-top:0px;margin-bottom:0px;-fs-table-paginate: paginate;'>");
					buffer.append("<thead  style='background-color:" + themeColor + ";'>");

					buffer.append("<tr style='color: sky" + borderColor + ";'>");
					buffer.append(
							"<td colspan='10' style='text-align: left;font-weight:bold;font-size:20px;'>School Specific Questions</td>");
					buffer.append("");
					buffer.append("</tr></thead>");
					for (SchoolQuestionSection schoolQuestionSection : schoolQuestionSections) {
						buffer.append("<thead  style='background-color:" + subThemeColor + ";'>");

						buffer.append("<tr style='color: #A10A10F5;'>");
						buffer.append("<td colspan='10' style='text-align: left;font-size:15px;' bgcolor='"
								+ subThemeColor + "' ><b style='font-family:sans-serif;'>");
						buffer.append(result = StringUtils.defaultIfEmpty(schoolQuestionSection.getSectionName(),
								Default_Value) + "</b></td>");
						buffer.append("</tr></thead>");
						List<SchoolQuestion> schoolQuestions = schoolQuestionSection.getSchoolQuestions();

						Long schoolDegreeGuid = studentApplication.getSchoolDegree().getSchoolDegreeGuid();
						int schoolDegreeIntGuid = schoolDegreeGuid.intValue();
						int questionCount = 1;
						if (CommonUtil.isListNotNullAndNotEmpty(schoolQuestions)) {
							List<SchoolQuestion> schoolQuestionDegreeList = new ArrayList<SchoolQuestion>();
							for (SchoolQuestion schoolQuestion : schoolQuestions) {
								String degreeList = schoolQuestion.getDegreeList();
								if ("ALL".equals(degreeList)) {
									schoolQuestionDegreeList.add(schoolQuestion);
								} else if (degreeList != null && degreeList.indexOf(schoolDegreeIntGuid) > -1) {
									schoolQuestionDegreeList.add(schoolQuestion);
								}
							}

							for (SchoolQuestion schoolQuestion : schoolQuestionDegreeList) {
								buffer.append("<tbody><tr>");

								if ("Yes".equals(schoolQuestion.getQuestionMandatory())) {
									buffer.append(
											"<td  style='text-align: center;font-size:12px;'><b style='font-family:sans-serif;'>"
													+ questionCount + ".*  </b></td>");
								} else {
									buffer.append(
											"<td style='text-align: center;font-size:12px;font-family:sans-serif;'>"
													+ questionCount + " . </td>");

								}
								buffer.append(
										"<td style='text-align: left;font-size:12px;' ><b style='font-family:sans-serif;'>");
								buffer.append(result = StringUtils.defaultIfEmpty(schoolQuestion.getQuestionTitle(),
										Default_Value) + "</b></td>");
								buffer.append("</tr>");
								SchoolQuestionAnswer schoolQuestionAnswer = schoolQuestionAnswerDao
										.getSchoolQuestionAnswerByQuestionIdAndStudentId(
												schoolQuestion.getSchoolQuestionGuid(), accountGuid);
								if (schoolQuestionAnswer != null) {

									buffer.append("<tr>");
									buffer.append(
											"<td style='text-align: left;font-size:12px;'><b style='font-family:sans-serif;'> Answer: </b></td>");
									buffer.append("<td style='text-align: left;font-size:12px;' >");

									if ("Drop Down".equals(schoolQuestion.getQuestionType())) {
										if (!("".equals(schoolQuestionAnswer.getAnswer()))) {
											SchoolQuestionAnswerOption schoolQuestionAnswerOption = schoolQuestionAnswerOptionDao
													.getSchoolQuestionAnswerOptionByGuid(
															new Long(schoolQuestionAnswer.getAnswer()));
											buffer.append(result = StringUtils.defaultIfEmpty(
													schoolQuestionAnswerOption.getAnswer(), Default_Value) + "</td>");
										} else {
											buffer.append(" - </td>");
										}

									} else {
										buffer.append(
												result = StringUtils.defaultIfEmpty(schoolQuestionAnswer.getAnswer(),
														Default_Value) + "</td>");

									}

									buffer.append("</tr>");
								}
								questionCount++;
								buffer.append("</tbody>");
							}
						}

					}

					buffer.append("</table>");
				}

				List<StudentDocumentResponse> studentDocuments = studentDocumentsDao
						.getAllStudentDocuments(accountGuid);
				if (CommonUtil.isListNotNullAndNotEmpty(studentDocuments)) {

					buffer.append("<table width ='100%'   style='border-spacing: 0px;border-top: 2px solid "
							+ borderColor + ";border-bottom: 2px solid " + borderColor + ";border-left: 2px solid "
							+ borderColor + ";border-right:2px solid " + borderColor
							+ ";font-family:sans-serif;margin-top:0px;margin-bottom:0px;-fs-table-paginate: paginate;'>");
					buffer.append("<thead  style='background-color:" + themeColor + ";'>");
					buffer.append("<tr style='color: sky" + borderColor + "; '>");
					buffer.append(
							"<td  colspan='100%' style='text-align: left;font-weight:bold;font-size:20px;'>Documents</td>");
					buffer.append("<td></td>");
					buffer.append("</tr></thead>");

					buffer.append("<thead  style='background-color:" + subThemeColor + ";'>");

					buffer.append("<tr style='color: #A10A10F5;'>");
					buffer.append("<td colspan='100%' style='text-align: left;font-size:15px;' bgcolor='"
							+ subThemeColor + "' ><b>Student Documents</b></td>");
					buffer.append("<td></td>");
					buffer.append("</tr></thead>");
					buffer.append("<thead><tr>");
					buffer.append("<td  style=' font-weight: bold;'>Document Type</td>");
					buffer.append("<td  style='font-weight: bold;'>Document Name</td>");
					buffer.append("</tr></thead>");
					for (StudentDocumentResponse studentDocument : studentDocuments) {
						buffer.append("<tbody><tr>");
						buffer.append("<td>");
						buffer.append(
								result = StringUtils.defaultIfEmpty(studentDocument.getDocumentType(), Default_Value)
										+ "</td >");
						buffer.append("<td >");
						buffer.append(
								result = StringUtils.defaultIfEmpty(studentDocument.getDocumentName(), Default_Value)
										+ "</td >");
						buffer.append("</tr></tbody>");
					}
					buffer.append("</table>");

				}
				List<StudentSchoolDocument> studentSchoolDocuemts = studentSchoolDocumentDao
						.getDocumentByStudentAndSchoolId(accountGuid, schoolGuid);
				if (CommonUtil.isListNotNullAndNotEmpty(studentSchoolDocuemts)) {
					buffer.append("<table  width ='100%'   style='border-spacing: 0px;border-bottom: 2px solid "
							+ borderColor + ";border-left: 2px solid " + borderColor + ";border-right:2px solid "
							+ borderColor
							+ ";font-family:sans-serif;margin-top:0px;margin-bottom:0px;-fs-table-paginate: paginate;'>");
					buffer.append("<thead  style='background-color:" + subThemeColor + ";'>");
					buffer.append("<tr style='color: #A10A10F5;'>");
					buffer.append("<td  colspan='100%' style='text-align: left;font-size:15px;' bgcolor='"
							+ subThemeColor + "' ><b>School Specific Documents</b></td>");
					buffer.append("<td></td>");

					buffer.append("");
					buffer.append("</tr></thead>");
					buffer.append("<thead><tr>");
					buffer.append("<td  style=' font-weight: bold;'>Document Type</td>");
					buffer.append("<td  style='font-weight: bold;'>Document Name</td>");
					buffer.append("</tr></thead>");
					for (StudentSchoolDocument studentSchoolDocument : studentSchoolDocuemts) {
						buffer.append("<tbody><tr>");
						buffer.append("<td >");
						buffer.append(result = StringUtils.defaultIfEmpty(
								studentSchoolDocument.getSchoolDocument().getDocumentName(), Default_Value) + "</td >");
						buffer.append("<td >");
						buffer.append(result = StringUtils.defaultIfEmpty(
								studentSchoolDocument.getSchoolDocument().getDocumentName(), Default_Value) + "</td >");
						buffer.append("</tr></tbody>");

					}
					buffer.append("</table>");
				}

				buffer.append("</div>");

				buffer.append(
						"<div class='footer'><h3 style='text-align:center;float:center;'><span id='pagenumber' /></h3> <p style='font-size:small;text-margin:right;float:right;'> Generated: "
								+ new SimpleDateFormat().format(new java.util.Date()) + "  </p></div>");

				buffer.append("</body>");
				buffer.append("</html>");
				String content = buffer.toString();
				ITextRenderer iTextRenderer = new ITextRenderer();
				iTextRenderer.setDocumentFromString(content);
				iTextRenderer.layout();
				generatPDFOutputSreamObject = new FileOutputStream(file);
				try {

					iTextRenderer.createPDF(generatPDFOutputSreamObject);

				} catch (DocumentException e1) {
					e1.printStackTrace();
				}
				iTextRenderer.finishPDF();
				try {
					generatPDFInputSreamObject = new FileInputStream(file);
					inputFiles.add(0, generatPDFInputSreamObject);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					doMerge(inputFiles, new FileOutputStream(mergeFile));
				} catch (DocumentException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(System.currentTimeMillis() + " mail");

			emailUtil.sendMail(studentApplication.getSchool().getEmailAddress(), mergeFile.getAbsolutePath());
			System.out.println(System.currentTimeMillis() + " save amazon");

			amazonS3Template.saveStudentDocuments(mergePDFDocumentName + fileExtention, mergeFile);
			System.out.println(System.currentTimeMillis() + " saved");

		} catch (

		IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (generatPDFInputSreamObject != null) {
				try {
					generatPDFInputSreamObject.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (generatPDFOutputSreamObject != null) {
				try {
					generatPDFOutputSreamObject.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (file != null) {
				try {
					FileDeleteStrategy.FORCE.delete(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (mergeFile != null) {
				try {
					FileDeleteStrategy.FORCE.delete(mergeFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pdfFile != null) {
				try {
					FileUtils.forceDelete(pdfFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println(System.currentTimeMillis() + " Return");

		return mergePDFDocumentName + fileExtention;
	}

	public InputStream generateImagePDFFromS3InputStream(InputStream inputStram) {
		File pdfFile = null;
		InputStream fileInputSream = null;
		PDDocument pdfDocument = null;
		String fileName = CommonUtil.generateSalt(10);
		String imageFileName = CommonUtil.generateSalt(10);
		try {
			pdfDocument = new PDDocument();
			pdfFile = File.createTempFile(fileName, ".pdf");
			File imageFile = File.createTempFile(imageFileName, ".jpg");
			FileUtils.copyInputStreamToFile(inputStram, imageFile);
			PDPage currentPage = new PDPage();
			pdfDocument.addPage(currentPage);
			PDImageXObject pdImage = null;
			try {
				PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, currentPage);
				pdImage = PDImageXObject.createFromFileByContent(imageFile, pdfDocument);
				contentStream.drawImage(pdImage, 200, 300, 400, 400);
				contentStream.close();
				pdfDocument.save(pdfFile);
				fileInputSream = new FileInputStream(pdfFile);
				pdfDocument.close();
			} catch (IOException ioException) {
				// TODO Auto-generated catch block
				ioException.printStackTrace();
			} finally {
				if (imageFile != null) {
					FileUtils.forceDeleteOnExit(imageFile);
				}
				if (pdfFile != null) {
					FileUtils.forceDeleteOnExit(pdfFile);
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileInputSream;
	}
}

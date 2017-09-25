package com.denkensol.universaladmission.template;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.amazonaws.services.s3.model.S3Object;
import com.denkensol.universaladmission.constant.CredentialsConstants;
import com.denkensol.universaladmission.entity.StudentSchoolDocument;
import com.denkensol.universaladmission.requestresponse.StudentDocumentResponse;
import com.denkensol.universaladmission.service.SchoolPacketPDFService;
import com.denkensol.universaladmission.util.CommonUtil;

/**
 * @author Indrajit
 * 
 *         AWS
 * 
 */

@Component
public class AmazonS3Template {

	@Autowired
	SchoolPacketPDFService schoolPacketPDFService;

	public AmazonS3 getAmazonS3Client() {
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain())
				.withRegion(Regions.US_WEST_2).build();

		return s3Client;

	}

	public List<InputStream> getAllDocuments(List<StudentDocumentResponse> studentDocumentResponses) {
		List<InputStream> inputFile = new ArrayList<InputStream>();
		try {
			for (StudentDocumentResponse studentDocumentResponse : studentDocumentResponses) {
				String documentKey = studentDocumentResponse.getDocumentPathGuid();
				GetObjectRequest getObjectRequest = new GetObjectRequest(CredentialsConstants.AWS_S3_BUCKET_NAME,
						"student-documents/" + documentKey);
				String fileExtension = CommonUtil.getExtension(documentKey);
				if (!("pdf").equals(fileExtension)) {

					S3Object s3Object = getAmazonS3Client().getObject(getObjectRequest);
					inputFile
							.add(schoolPacketPDFService.generateImagePDFFromS3InputStream(s3Object.getObjectContent()));
				} else {
					S3Object s3Object = getAmazonS3Client().getObject(getObjectRequest);
					inputFile.add(s3Object.getObjectContent());
				}
			}
		} catch (AmazonS3Exception e) {
			// TODO: handle exception
		}
		return inputFile;
	}

	public List<InputStream> getAllStudentSchoolDocuments(List<StudentSchoolDocument> studentSchoolDocuments) {
		List<InputStream> inputFile = new ArrayList<InputStream>();
		try {
			if (CommonUtil.isListNotNullAndNotEmpty(studentSchoolDocuments)) {
				for (StudentSchoolDocument studentSchoolDocument : studentSchoolDocuments) {
					String documentpath = studentSchoolDocument.getDocumentPath();
					String documentKey = documentpath;
					documentKey = documentKey.substring(documentKey.lastIndexOf("/") + 1);
					GetObjectRequest getObjectRequest = new GetObjectRequest(CredentialsConstants.AWS_S3_BUCKET_NAME,
							"student-school-documents/" + documentKey);
					S3Object s3Object = getAmazonS3Client().getObject(getObjectRequest);
					inputFile.add(s3Object.getObjectContent());
				}
			}
		} catch (AmazonS3Exception e) {
			// TODO: handle exception
		}
		return inputFile;
	}

	@Async
	public void saveStudentDocuments(String documentKey, File file) {
		getAmazonS3Client().putObject(
				new PutObjectRequest(CredentialsConstants.AWS_S3_BUCKET_NAME, "student-documents/" + documentKey, file)
						.withCannedAcl(CannedAccessControlList.Private));

	}

	public void saveSchoolDocuments(String documentKey, File file) {

		getAmazonS3Client().putObject(
				new PutObjectRequest(CredentialsConstants.AWS_S3_BUCKET_NAME, "school-documents/" + documentKey, file)
						.withCannedAcl(CannedAccessControlList.Private));

	}

	public void saveStudentSchoolDocuments(String documentKey, File file) {

		getAmazonS3Client().putObject(new PutObjectRequest(CredentialsConstants.AWS_S3_BUCKET_NAME,
				"student-school-documents/" + documentKey, file).withCannedAcl(CannedAccessControlList.Private));

	}

	/*
	 * 
	 * @Author Indrajit
	 * 
	 * To check S3 Object is present or not
	 */
	public boolean isFileExist(String newDocumentKey, String oldDocumentkey) {

		return getAmazonS3Client().doesObjectExist(newDocumentKey, oldDocumentkey);
	}

	/*
	 * @Author Indrajit
	 * 
	 * Create Empty S3 Object
	 */

	public void createEmptyObject(String pdfDocumentKey) {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);
		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);

		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(CredentialsConstants.AWS_S3_BUCKET_NAME,
				"student-pdf-document/" + pdfDocumentKey, emptyContent, metadata);

		// send request to S3 to create folder
		getAmazonS3Client().putObject(putObjectRequest);
	}

	/*
	 * @Author Indrajit
	 * 
	 * get S3 bucket Object url now Removed not
	 */

	public String getS3ObjectUrl(String documentKey) {
		String _finalUrl = "https://s3-us-west-2.amazonaws.com/" + CredentialsConstants.AWS_S3_BUCKET_NAME
				+ "/student-documents/" + documentKey;
		return _finalUrl;

	}
	/*
	 * @Author Indrajit
	 * 
	 * get S3 bucket Object url
	 */

	public String getSchoolDocumentS3ObjectUrl(String documentKey) {
		String _finalUrl = "https://s3-us-west-2.amazonaws.com/" + CredentialsConstants.AWS_S3_BUCKET_NAME
				+ "/school-documents/" + documentKey;
		return _finalUrl;

	}

	public String getStudentSchoolDocumentS3ObjectUrl(String documentKey) {
		String _finalUrl = "https://s3-us-west-2.amazonaws.com/" + CredentialsConstants.AWS_S3_BUCKET_NAME
				+ "/student-school-documents/" + documentKey;
		return _finalUrl;

	}
	/*
	 * @Author Indrajit
	 * 
	 * get S3 bucket Object url
	 */

	public String getStudentDocument(String documentKey) {
		String _finalUrl = "https://s3-us-west-2.amazonaws.com/" + CredentialsConstants.AWS_S3_BUCKET_NAME
				+ "/student-documents/" + documentKey;
		return _finalUrl;
	}

	/*
	 * @Author Indrajit
	 * 
	 * This Method generate Presigned url for
	 * 
	 * while Submitting student Application &
	 * 
	 * Downloading the Apllication
	 * 
	 * 
	 */
	public String getStudentPDFPresignedURL(String documentName, String documentKey) {
		documentName = Character.toUpperCase(documentName.charAt(0)) + documentName.substring(1);
		String _finalPresignedURL = null;
		Date expiration = null;
		try {
			expiration = new Date();
			long msec = expiration.getTime();
			msec += 1000 * 60 * 60; // 1 hour.
			expiration.setTime(msec);
			GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
					CredentialsConstants.AWS_S3_BUCKET_NAME, "student-documents/" + documentKey);
			generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
			generatePresignedUrlRequest.setResponseHeaders(
					new ResponseHeaderOverrides().withContentDisposition("inline; filename=" + documentName));
			generatePresignedUrlRequest.setExpiration(expiration);
			URL url = getAmazonS3Client().generatePresignedUrl(generatePresignedUrlRequest);
			_finalPresignedURL = url.toString();
		} catch (AmazonServiceException exception) {
			exception.printStackTrace();
		}
		return _finalPresignedURL;
	}

	/*
	 * @Author Indrajit Delete S3 Object from S3 Bucket using documentKey
	 */
	public void deleteDocument(String documentKey) {
		getAmazonS3Client().deleteObject(CredentialsConstants.AWS_S3_BUCKET_NAME, "student-documents/" + documentKey);
	}

	public String getStudentSchoolDocumentDownloadPresignedUrl(String documentKey, String documentName) {

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain())
				.withRegion(Regions.US_WEST_2).build();

		Date expiration = new Date();
		long msec = expiration.getTime();
		msec += 1000 * 60 * 60; // 1 hour.
		expiration.setTime(msec);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				CredentialsConstants.AWS_S3_BUCKET_NAME + "/student-school-documents", documentKey);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
		generatePresignedUrlRequest.setExpiration(expiration);

		ResponseHeaderOverrides responseHeader = new ResponseHeaderOverrides();
		responseHeader.setContentDisposition("attachment; filename=" + documentName);

		generatePresignedUrlRequest.setResponseHeaders(responseHeader);
		URL PresignedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

		return PresignedUrl.toString();

	}

	public String getStudentSchoolDocumentViewPresignedUrl(String documentKey, String documentName) {
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain())
				.withRegion(Regions.US_WEST_2).build();

		Date expiration = new Date();
		long msec = expiration.getTime();
		msec += 1000 * 60 * 60; // 1 hour.
		expiration.setTime(msec);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				CredentialsConstants.AWS_S3_BUCKET_NAME + "/student-school-documents", documentKey);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
		generatePresignedUrlRequest.setExpiration(expiration);

		ResponseHeaderOverrides responseHeader = new ResponseHeaderOverrides();
		responseHeader.setContentDisposition("inline; filename=" + documentName);

		generatePresignedUrlRequest.setResponseHeaders(responseHeader);
		URL PresignedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

		return PresignedUrl.toString();

	}

	public String getSchoolDocumentDownloadPresignedUrl(String documentKey, String documentName) {

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain())
				.withRegion(Regions.US_WEST_2).build();

		Date expiration = new Date();
		long msec = expiration.getTime();
		msec += 1000 * 60 * 60; // 1 hour.
		expiration.setTime(msec);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				CredentialsConstants.AWS_S3_BUCKET_NAME + "/school-documents", documentKey);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
		generatePresignedUrlRequest.setExpiration(expiration);

		ResponseHeaderOverrides responseHeader = new ResponseHeaderOverrides();
		responseHeader.setContentDisposition("attachment; filename=" + documentName);

		generatePresignedUrlRequest.setResponseHeaders(responseHeader);
		URL PresignedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

		return PresignedUrl.toString();

	}

	public String getSchoolDocumentViewPresignedUrl(String documentKey, String documentName) {
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain())
				.withRegion(Regions.US_WEST_2).build();

		Date expiration = new Date();
		long msec = expiration.getTime();
		msec += 1000 * 60 * 60; // 1 hour.
		expiration.setTime(msec);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				CredentialsConstants.AWS_S3_BUCKET_NAME + "/school-documents", documentKey);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
		generatePresignedUrlRequest.setExpiration(expiration);

		ResponseHeaderOverrides responseHeader = new ResponseHeaderOverrides();
		responseHeader.setContentDisposition("inline; filename=" + documentName);

		generatePresignedUrlRequest.setResponseHeaders(responseHeader);
		URL PresignedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

		return PresignedUrl.toString();

	}

	public String getStudentDocumentDownloadPresignedUrl(String documentKey, String documentName) {

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain())
				.withRegion(Regions.US_WEST_2).build();

		Date expiration = new Date();
		long msec = expiration.getTime();
		msec += 1000 * 60 * 60; // 1 hour.
		expiration.setTime(msec);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				CredentialsConstants.AWS_S3_BUCKET_NAME + "/student-documents", documentKey);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
		generatePresignedUrlRequest.setExpiration(expiration);

		ResponseHeaderOverrides responseHeader = new ResponseHeaderOverrides();
		responseHeader.setContentDisposition("attachment; filename=" + documentName);

		generatePresignedUrlRequest.setResponseHeaders(responseHeader);
		URL PresignedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

		return PresignedUrl.toString();

	}

	public String getStudentDocumentViewPresignedUrl(String documentKey, String documentName) {

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain())
				.withRegion(Regions.US_WEST_2).build();

		Date expiration = new Date();
		long msec = expiration.getTime();
		msec += 1000 * 60 * 60; // 1 hour.
		expiration.setTime(msec);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				CredentialsConstants.AWS_S3_BUCKET_NAME + "/student-documents", documentKey);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
		generatePresignedUrlRequest.setExpiration(expiration);

		ResponseHeaderOverrides responseHeader = new ResponseHeaderOverrides();
		responseHeader.setContentDisposition("inline; filename=" + documentName);

		generatePresignedUrlRequest.setResponseHeaders(responseHeader);
		URL PresignedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

		return PresignedUrl.toString();

	}

	public String getStudentPDFDocumentDownloadPresignedUrl(String documentKey, String documentName) {

		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain())
				.withRegion(Regions.US_WEST_2).build();

		Date expiration = new Date();
		long msec = expiration.getTime();
		msec += 1000 * 60 * 60; // 1 hour.
		expiration.setTime(msec);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				CredentialsConstants.AWS_S3_BUCKET_NAME + "/student-documents/student-pdf-Documents", documentKey);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
		generatePresignedUrlRequest.setExpiration(expiration);

		ResponseHeaderOverrides responseHeader = new ResponseHeaderOverrides();
		responseHeader.setContentDisposition("attachment; filename=" + documentName);

		generatePresignedUrlRequest.setResponseHeaders(responseHeader);
		URL PresignedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

		return PresignedUrl.toString();

	}

	public String getStudentPDFDocumentViewPresignedUrl(String documentKey, String documentName) {
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain())
				.withRegion(Regions.US_WEST_2).build();

		Date expiration = new Date();
		long msec = expiration.getTime();
		msec += 1000 * 60 * 60; // 1 hour.
		expiration.setTime(msec);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
				CredentialsConstants.AWS_S3_BUCKET_NAME + "/student-documents/student-pdf-Documents", documentKey);
		generatePresignedUrlRequest.setMethod(HttpMethod.GET); // Default.
		generatePresignedUrlRequest.setExpiration(expiration);

		ResponseHeaderOverrides responseHeader = new ResponseHeaderOverrides();
		responseHeader.setContentDisposition("inline; filename=" + documentName);

		generatePresignedUrlRequest.setResponseHeaders(responseHeader);
		URL PresignedUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

		return PresignedUrl.toString();

	}

}

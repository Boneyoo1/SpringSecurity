package com.denkensol.universaladmission.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.lowagie.text.DocumentException;

public interface SchoolPacketPDFService {

	public String getApplicationPDF(Long schoolApplicationGuid, HttpServletRequest request);

	void doMerge(List<InputStream> list, OutputStream outputStream) throws DocumentException, IOException;

	String generatePdfPacket(Long schoolGuid, HttpServletRequest request);

	String generateStudentInfoPdf(Long accountGuid, Long schoolApplicationGuid);

	InputStream generateImagePDFFromS3InputStream(InputStream inputStram);
}

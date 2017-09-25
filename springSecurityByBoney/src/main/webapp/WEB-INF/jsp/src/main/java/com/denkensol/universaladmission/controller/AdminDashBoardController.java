package com.denkensol.universaladmission.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.denkensol.universaladmission.entity.Account;
import com.denkensol.universaladmission.requestresponse.SchoolApplicantsResponse;
import com.denkensol.universaladmission.requestresponse.SchoolProspectsResponse;
import com.denkensol.universaladmission.service.SchoolService;

@Controller
@RequestMapping("/adminDashBoard")
public class AdminDashBoardController {

	@Autowired
	SchoolService schoolService;

	@GetMapping(value = "/getSchoolApplicants")
	public ResponseEntity<List<SchoolApplicantsResponse>> getSchoolApplicants(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
	
		String accountEmailAddress = account.getEmailAddress();
		String domainName = accountEmailAddress.substring(accountEmailAddress.indexOf("@") + 1);
		List<SchoolApplicantsResponse> schoolApplicants = schoolService.getSchoolApplicants(domainName);
		return new ResponseEntity<List<SchoolApplicantsResponse>>(schoolApplicants, HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/getSchoolProspects")
	public ResponseEntity<List<SchoolProspectsResponse>> getSchoolProspects(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		String accountEmailAddress = account.getEmailAddress();
		String domainName = accountEmailAddress.substring(accountEmailAddress.indexOf("@") + 1);
		List<SchoolProspectsResponse> schoolProspects = schoolService.getSchoolProspects(domainName);
		return new ResponseEntity<List<SchoolProspectsResponse>>(schoolProspects, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/downloadApplicationData")
	public ResponseEntity<String> downloadApplicationData(HttpServletRequest request) {
		String zipdownloadurl="";
		HttpSession session = request.getSession(true);
		Account account = (Account) session.getAttribute("account");
		String accountEmailAddress = account.getEmailAddress();
		String domainName = accountEmailAddress.substring(accountEmailAddress.indexOf("@") + 1);
		zipdownloadurl=schoolService.downloadApplicationData(domainName);
//		return new ResponseEntity<String>("Application Data Downloaded To Desktop Successfully", HttpStatus.ACCEPTED);
		return new ResponseEntity<String>(zipdownloadurl, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/downloadStudentApplicationData")
	public ResponseEntity<String> downloadStudentApplicationData(@RequestBody String pdfURL) {

		try {
			pdfURL = URLDecoder.decode(pdfURL, "UTF-8");
			if (pdfURL != null && pdfURL.length() > 0 && pdfURL.charAt(pdfURL.length() - 1) == '=') {
				pdfURL = pdfURL.substring(0, pdfURL.length() - 1);
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		schoolService.downloadStudentApplicationData(pdfURL);
		return new ResponseEntity<String>("File Downloaded To Desktop Successfully", HttpStatus.ACCEPTED);
//		return new ResponseEntity<String>("File Downloaded Successfully", HttpStatus.ACCEPTED);
	}
}

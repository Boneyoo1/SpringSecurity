package com.denkensol.universaladmission.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.denkensol.universaladmission.entity.Account;
import com.denkensol.universaladmission.exception.NotFoundException;
import com.denkensol.universaladmission.requestresponse.RegisterRequest;
import com.denkensol.universaladmission.requestresponse.SchoolSearchResponse;
import com.denkensol.universaladmission.service.AccountService;
import com.denkensol.universaladmission.service.CountryService;
import com.denkensol.universaladmission.service.LanguageService;
import com.denkensol.universaladmission.service.ReligiousService;
import com.denkensol.universaladmission.service.SchoolService;
import com.denkensol.universaladmission.service.VisaTypeService;

@Controller
public class AccountController {

	@Autowired
	AccountService accountService;

	@Autowired
	ReligiousService religiousService;

	@Autowired
	CountryService countryService;

	@Autowired
	LanguageService languageService;

	@Autowired
	VisaTypeService visaTypeService;

	@Autowired
	SchoolService schoolService;

	@RequestMapping("/login")
	public String login(Model model) {
		return "login";
	}

	@RequestMapping("/appDoc")
	public String appDoc(Model model) {
		return "schoolAppPDFPacket";
	}

	@RequestMapping("/register")
	public String register(Model model) {
		return "register";
	}

	@RequestMapping("/apply")
	public String apply(Model model) {
		return "apply";
	}

	@RequestMapping("/profile")
	public String profile(HttpServletRequest request) {
		String tabName = request.getParameter("tab");
		request.setAttribute("tabName", tabName);
		return "portfolio";
	}

	@RequestMapping("/collegeSearch")
	public String collegeSearch(Model model) {
		return "collegeSearch";
	}

	@RequestMapping("/collegeSetup")
	public String collegeSetup(Model model) {
		return "collegeSetup";
	}

	@RequestMapping("/myColleges")
	public String myColleges(HttpServletRequest request) {
		String school = request.getParameter("school");
		request.setAttribute("school", school);
		return "myColleges";
	}

	@RequestMapping("/dashboard")
	public String dashboard(Model model) {
		return "dashboard";
	}

	@RequestMapping("/adminDashboard")
	public String adminDashboard(Model model) {
		return "applicants";
	}

	@RequestMapping("/applicants")
	public String applicants(Model model) {
		return "applicants";
	}

	@RequestMapping("/prospects")
	public String prospects(Model model) {
		return "prospects";
	}

	@RequestMapping("/signOut")
	public String signOut(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.invalidate();
		return "index";
	}

	@PostMapping(value = "/signOff")
	public ResponseEntity<String> signOff(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		session.invalidate();
		return new ResponseEntity<String>("Session is invalid", HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/isExists")
	public ResponseEntity<Boolean> isExists(@RequestBody RegisterRequest registerRequest) {
		return new ResponseEntity<Boolean>(accountService.isRegistered(registerRequest), HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/isSchoolAdmin")
	public ResponseEntity<SchoolSearchResponse> isSchoolAdmin(@RequestBody RegisterRequest registerRequest,
			HttpServletRequest request) {
		SchoolSearchResponse schoolData = new SchoolSearchResponse();
		schoolData.setSchoolName(accountService.isSchoolAdmin(registerRequest).getSchoolName());
		return new ResponseEntity<SchoolSearchResponse>(schoolData, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/registerAccount")
	public ResponseEntity<Boolean> registerAccount(@RequestBody RegisterRequest registerRequest,
			HttpServletRequest request) {
		Account account = null;
		try {
			account = accountService.register(registerRequest);
			HttpSession session = request.getSession(true);
			session.setAttribute("account", account);
			if ("SCHOOL_ADMIN".equals(registerRequest.getAccountType())) {
				return new ResponseEntity<Boolean>(false, HttpStatus.OK);
			}

		} catch (NotFoundException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ErrorText", e.getExceptionMsg());
			return new ResponseEntity<Boolean>(null, responseHeaders, e.getCode());
		}

		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/loginAccount")
	public ResponseEntity<Boolean> loginAccount(@RequestBody RegisterRequest registerRequest,
			HttpServletRequest request) {
		Account account = null;

		try {
			account = accountService.login(registerRequest);
			HttpSession session = request.getSession(true);
			session.setAttribute("account", account);
			if ("SCHOOL_ADMIN".equals(account.getType())) {
				return new ResponseEntity<Boolean>(false, HttpStatus.OK);

			}
		} catch (NotFoundException e) {
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.set("ErrorText", e.getExceptionMsg());
			return new ResponseEntity<Boolean>(null, responseHeaders, e.getCode());
		}

		return new ResponseEntity<Boolean>(true, HttpStatus.ACCEPTED);
	}

	@RequestMapping("/schoolInfo")
	public String schoolInfo(HttpServletRequest request) {
		String schoolGuId = request.getParameter("school");
		request.setAttribute("schoolGuId", schoolGuId);
		return "collegeInfo";
	}

	@RequestMapping("/schoolAppPDFPacket")
	public String schoolAppPDFPacket(Model model) {
		return "schoolAppPDFPacket";
	}

}

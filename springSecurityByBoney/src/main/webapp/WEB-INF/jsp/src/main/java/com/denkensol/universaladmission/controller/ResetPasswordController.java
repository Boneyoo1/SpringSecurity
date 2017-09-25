package com.denkensol.universaladmission.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.denkensol.universaladmission.exception.NotFoundException;
import com.denkensol.universaladmission.requestresponse.PasswordResetRequestResponse;
import com.denkensol.universaladmission.service.ResetPasswordService;

@Controller
public class ResetPasswordController {
	@Autowired
	ResetPasswordService resetPasswordService;

	@PostMapping(value = "/generatePassword")
	public ResponseEntity<Boolean> resetPassword(@RequestBody PasswordResetRequestResponse passwordResetRequestResponse)
			throws NotFoundException {

		return new ResponseEntity<Boolean>(
				resetPasswordService.resetPassword(passwordResetRequestResponse.getEmailAddress()), HttpStatus.OK);
	}

	@GetMapping(value = "/resetPassword")
	public String createNewPassword(@RequestParam("id") String resetPasswordGuid, Model model) {
		String viewName = "passwordResetError";
		if (resetPasswordService.isPasswordResetExpire(resetPasswordGuid)) {
			model.addAttribute("emailAddress",
					resetPasswordService.getPasswordResetByGuid(resetPasswordGuid).getEmailAddress());
			viewName = "passwordReset";

		}
		return viewName;

	}

	@PostMapping(value = "/resetPassword")
	public ResponseEntity<Boolean> updateNewPassword(
			@RequestBody PasswordResetRequestResponse passwordResetRequestResponse) {
		resetPasswordService.updatePassword(passwordResetRequestResponse);
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);

	}
}

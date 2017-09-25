package com.denkensol.universaladmission.service;

import com.denkensol.universaladmission.entity.PasswordReset;
import com.denkensol.universaladmission.exception.NotFoundException;
import com.denkensol.universaladmission.requestresponse.PasswordResetRequestResponse;

public interface ResetPasswordService {
	Boolean resetPassword(String emailAddress) throws NotFoundException;

	Boolean isPasswordResetExpire(String resetPasswordGuid);

	PasswordResetRequestResponse getPasswordResetByGuid(String resetPasswordGuid);

	void updatePassword(PasswordResetRequestResponse passwordResetRequestResponse);

	void deletePasswordReset(PasswordReset passwordReset);

}

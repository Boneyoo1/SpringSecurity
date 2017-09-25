package com.denkensol.universaladmission.dao;

import com.denkensol.universaladmission.entity.PasswordReset;

public interface ResetPasswordDao {

	void resetPassword(PasswordReset passwordReset);

	PasswordReset getPasswordResetByGuid(String resetPasswordGuid);

	PasswordReset getPasswordResetByEmail(String emailAddress);

	void deletePasswordReset(PasswordReset passwordReset);

}

package com.denkensol.universaladmission.serviceimpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.denkensol.universaladmission.dao.AccountDao;
import com.denkensol.universaladmission.dao.ResetPasswordDao;
import com.denkensol.universaladmission.entity.Account;
import com.denkensol.universaladmission.entity.PasswordReset;
import com.denkensol.universaladmission.exception.NotFoundException;
import com.denkensol.universaladmission.requestresponse.PasswordResetRequestResponse;
import com.denkensol.universaladmission.service.ResetPasswordService;
import com.denkensol.universaladmission.util.CommonUtil;
import com.denkensol.universaladmission.util.EMailUtil;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {

	@Autowired
	ResetPasswordDao resetPasswordDao;

	@Autowired
	AccountDao accountDao;

	@Autowired
	EMailUtil emailUtil;

	@Transactional
	@Override
	public Boolean resetPassword(String emailAddress) throws NotFoundException {
		Boolean flag = false;
		Account studentAccount = accountDao.getAccountByEmailAddress(emailAddress);
		if (studentAccount != null) {
			PasswordReset existPasswordReset = resetPasswordDao.getPasswordResetByEmail(emailAddress);
			if (existPasswordReset != null) {
				if ((CommonUtil.getCurrentGMTTimestamp().getTime()
						- existPasswordReset.getResetTimeStamp().getTime()) < (5 * 60 * 1000)) {
					emailUtil.sendResetPasswordLinkEmail(emailAddress, existPasswordReset.getResetGUID());

					flag = true;

				} else {
					deletePasswordReset(existPasswordReset);
					saveResetPassword(emailAddress);
					flag = true;
				}
			} else if (existPasswordReset == null) {
				saveResetPassword(emailAddress);
				flag = true;
			}
		}
		return flag;
	}

	private void saveResetPassword(String emailAddress) {
		PasswordReset passwordReset = null;
		String resetGUID = CommonUtil.generateSalt(20);
		String resetPasswordHashCode = CommonUtil.hashPassword(resetGUID);
		passwordReset = new PasswordReset();
		passwordReset.setResetGUID(resetPasswordHashCode);
		passwordReset.setEmailAddress(emailAddress);
		passwordReset.setResetTimeStamp(CommonUtil.getCurrentGMTTimestamp());
		resetPasswordDao.resetPassword(passwordReset);
		emailUtil.sendResetPasswordLinkEmail(emailAddress, resetPasswordHashCode);

	}

	@Transactional
	@Override
	public PasswordResetRequestResponse getPasswordResetByGuid(String resetPasswordGuid) {
		PasswordResetRequestResponse passwordResetRequestResponse = null;
		PasswordReset resetPassword = resetPasswordDao.getPasswordResetByGuid(resetPasswordGuid);
		if (resetPassword != null) {
			passwordResetRequestResponse = new PasswordResetRequestResponse();
			passwordResetRequestResponse.setEmailAddress(resetPassword.getEmailAddress());
		}
		return passwordResetRequestResponse;
	}

	@Transactional
	@Override
	public Boolean isPasswordResetExpire(String resetPasswordGuid) {
		Boolean flag = false;
		PasswordReset reset = resetPasswordDao.getPasswordResetByGuid(resetPasswordGuid);
		if (reset != null) {
			if ((CommonUtil.getCurrentGMTTimestamp().getTime() - reset.getResetTimeStamp().getTime()) <= (5 * 60
					* 1000)) {
				flag = true;
			}
		}
		return flag;
	}

	@Transactional
	@Override
	public void updatePassword(PasswordResetRequestResponse passwordResetRequestResponse) {
		Account account = null;
		account = accountDao.getAccountByEmailAddress(passwordResetRequestResponse.getEmailAddress());
		account.setPassword(CommonUtil.hashPassword(
				CommonUtil.saltPassword(passwordResetRequestResponse.getConfirmPassword(), account.getSalt())));
		accountDao.saveAccount(account);
		resetPasswordDao.deletePasswordReset(
				resetPasswordDao.getPasswordResetByEmail(passwordResetRequestResponse.getEmailAddress()));
	}

	@Override
	@Transactional
	public void deletePasswordReset(PasswordReset passwordReset) {
		resetPasswordDao.deletePasswordReset(passwordReset);
	}

}

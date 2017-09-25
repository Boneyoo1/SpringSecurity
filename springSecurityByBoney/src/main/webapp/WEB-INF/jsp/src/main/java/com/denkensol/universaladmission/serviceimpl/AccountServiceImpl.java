package com.denkensol.universaladmission.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.denkensol.universaladmission.constant.ExceptionConstants;
import com.denkensol.universaladmission.dao.AccountDao;
import com.denkensol.universaladmission.dao.SchoolDao;
import com.denkensol.universaladmission.entity.Account;
import com.denkensol.universaladmission.entity.School;
import com.denkensol.universaladmission.exception.NotFoundException;
import com.denkensol.universaladmission.requestresponse.RegisterRequest;
import com.denkensol.universaladmission.service.AccountService;
import com.denkensol.universaladmission.util.CommonUtil;
import com.denkensol.universaladmission.util.EMailUtil;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountDao accountDao;

	@Autowired
	SchoolDao schoolDao;

	@Override
	@Transactional(readOnly = true)
	public Boolean isRegistered(RegisterRequest registerRequest) {
		Account account = null;
		boolean flag = false;
		try {
			account = accountDao.getAccountByEmailAddress(registerRequest.getEmailAddress());
			if (account == null) {
				flag = false;
			} else {
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	@Override
	@Transactional
	public School isSchoolAdmin(RegisterRequest registerRequest) {
		String domain = registerRequest.getEmailAddress();
		String domainName = domain.substring(domain.indexOf("@") + 1);
		return schoolDao.getSchoolBySchoolDomain(domainName);
	}

	@Override
	@Transactional
	public Account register(RegisterRequest registerRequest) throws NotFoundException {
		Account account = null;
		Account studentAccount = accountDao.getAccountByEmailAddress(registerRequest.getEmailAddress());
		if (studentAccount != null) {
			throw new NotFoundException(HttpStatus.BAD_REQUEST, ExceptionConstants.REGISTERED_ACCOUNT_FOUND_EMAIL);
		} else {
			account = new Account();
			account.setEmailAddress(registerRequest.getEmailAddress());
			account.setSalt(CommonUtil.generateSalt(32));
			account.setPassword(
					CommonUtil.hashPassword(CommonUtil.saltPassword(registerRequest.getPassword(), account.getSalt())));
			account.setCreatedTimeStamp(CommonUtil.getCurrentGMTTimestamp());
			Long accountGuid = accountDao.register(account);
			account.setGuid(accountGuid);
			account.setType(registerRequest.getAccountType());
		}
		return account;
	}

	@Override
	@Transactional(readOnly = true)
	public Account login(RegisterRequest registerRequest) throws NotFoundException {
		Account account = accountDao.getAccountByEmailAddress(registerRequest.getEmailAddress());
		if (account == null) {
			throw new NotFoundException(HttpStatus.NOT_FOUND, ExceptionConstants.LOGIN_ACCOUNT_NOT_FOUND_USER_NAME);
		} else {
			account = accountDao.getAccountByUserNameAndPassword(registerRequest.getEmailAddress(), account.getSalt(),
					registerRequest.getPassword());
			if (account == null) {
				throw new NotFoundException(HttpStatus.NOT_FOUND, ExceptionConstants.LOGIN_ACCOUNT_NOT_FOUND_PASSWORD);
			}
		}
		return account;
	}

}

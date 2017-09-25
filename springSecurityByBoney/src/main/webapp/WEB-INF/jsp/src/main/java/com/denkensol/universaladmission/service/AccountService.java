package com.denkensol.universaladmission.service;

import com.denkensol.universaladmission.entity.Account;
import com.denkensol.universaladmission.entity.School;
import com.denkensol.universaladmission.exception.NotFoundException;
import com.denkensol.universaladmission.requestresponse.RegisterRequest;

public interface AccountService {

	Account register(RegisterRequest registerRequest) throws NotFoundException;

	Account login(RegisterRequest registerRequest) throws NotFoundException;

	Boolean isRegistered(RegisterRequest registerRequest);

	School isSchoolAdmin(RegisterRequest registerRequest);

}

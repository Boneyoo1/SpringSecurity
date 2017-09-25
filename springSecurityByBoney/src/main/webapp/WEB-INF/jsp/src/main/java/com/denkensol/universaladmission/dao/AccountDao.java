package com.denkensol.universaladmission.dao;

import com.denkensol.universaladmission.entity.Account;

public interface AccountDao {

	Long register(Account account);

	Account getAccountByEmailAddress(String userName);

	Account getAccountByUserNameAndPassword(String userName, String salt, String password);

	Account getAccountById(Long accountGuid);

	void saveAccount(Account account);

}

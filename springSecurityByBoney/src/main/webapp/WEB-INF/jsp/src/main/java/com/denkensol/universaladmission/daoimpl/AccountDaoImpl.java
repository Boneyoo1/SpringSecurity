package com.denkensol.universaladmission.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.AccountDao;
import com.denkensol.universaladmission.entity.Account;
import com.denkensol.universaladmission.entity.PasswordReset;
import com.denkensol.universaladmission.util.CommonUtil;
import com.denkensol.universaladmission.util.EMailUtil;

@Repository
public class AccountDaoImpl extends BaseDaoImpl<Long, Account> implements AccountDao {

	@Override
	public Long register(Account account) {
		return save(account);
	}

	@Override
	public Account getAccountByEmailAddress(String emailAddress) {
		Criterion criterionObj = Restrictions.eq("emailAddress", emailAddress);
		return getByCondition(criterionObj);
	}

	@Override
	public Account getAccountByUserNameAndPassword(String emailAddress, String salt, String password) {
		Criterion criterionObj = Restrictions.eq("emailAddress", emailAddress);
		Criterion criterionObjOne = Restrictions.eq("password",
				CommonUtil.hashPassword(CommonUtil.saltPassword(password, salt)));
		List<Criterion> criterions = new ArrayList<Criterion>();
		criterions.add(criterionObj);
		criterions.add(criterionObjOne);
		return getByConditions(criterions);
	}

	@Override
	public Account getAccountById(Long accountGuid) {
		return getById(accountGuid);
	}

	@Override
	public void saveAccount(Account account) {
		saveOrUpdate(account);

	}


}

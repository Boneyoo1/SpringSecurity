package com.denkensol.universaladmission.daoimpl;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.denkensol.universaladmission.dao.ResetPasswordDao;
import com.denkensol.universaladmission.entity.PasswordReset;

@Repository
public class ResetPasswordDaoImpl extends BaseDaoImpl<Long, PasswordReset> implements ResetPasswordDao {

	@Override
	public void resetPassword(PasswordReset passwordReset) {
		saveOrUpdate(passwordReset);
	}

	@Override
	public PasswordReset getPasswordResetByGuid(String resetPasswordGuid) {
		Criterion criterionObj = Restrictions.eq("resetGUID", resetPasswordGuid);
		return getByCondition(criterionObj);

	}

	@Override
	public PasswordReset getPasswordResetByEmail(String emailAddress) {
		Criterion criterionObj = Restrictions.eq("emailAddress", emailAddress);
		return getByCondition(criterionObj);

	}

	@Override
	public void deletePasswordReset(PasswordReset passwordReset) {
		delete(passwordReset);
	}

}

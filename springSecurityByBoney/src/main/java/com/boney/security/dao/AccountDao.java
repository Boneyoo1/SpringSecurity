package com.boney.security.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boney.security.model.Account;

@Repository
public interface AccountDao extends JpaRepository<Account, Long> {

	Account findByEmailAddress(String usrname);
}

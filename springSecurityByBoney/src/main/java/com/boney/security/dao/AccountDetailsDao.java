package com.boney.security.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boney.security.model.Account;

@Repository
public interface AccountDetailsDao extends JpaRepository<Account, Long> {

	Optional<Account> findByEmailAddress(String emailAddress);

	

}

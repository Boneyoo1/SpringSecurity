package com.denkensol.universaladmission.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENT_WRITING")
@NamedNativeQueries({
		@NamedNativeQuery(name = "GET_ALL_STUDENT_WRITINGS", query = "select STUDENT_WRITING_GUID as studentWritingGuid,WRITING_TYPE as writingType,WRITING_TEXT as writingText from STUDENT_WRITING where account_guid=:accountGuid "), })

public class StudentWriting implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "STUDENT_WRITING_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentWritingGuid;

	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCOUNT_GUID")
	private Account account;

	@Column(name = "WRITING_TYPE")
	private String writingType;

	@Column(name = "WRITING_TEXT", columnDefinition = "LONGTEXT")
	private String writingText;

	public Long getStudentWritingGuid() {
		return studentWritingGuid;
	}

	public void setStudentWritingGuid(Long studentWritingGuid) {
		this.studentWritingGuid = studentWritingGuid;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getWritingType() {
		return writingType;
	}

	public void setWritingType(String writingType) {
		this.writingType = writingType;
	}

	public String getWritingText() {
		return writingText;
	}

	public void setWritingText(String writingText) {
		this.writingText = writingText;
	}

}

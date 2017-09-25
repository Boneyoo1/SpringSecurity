package com.denkensol.universaladmission.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "LANGUAGE")
@NamedNativeQueries({
		@NamedNativeQuery(name = "GET_ALL_LANGUAGES", query = "select LANGUAGE_GUID as guid,LANGUAGE_NAME as languageName from LANGUAGE ORDER BY LANGUAGE_NAME") })

public class Language implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4049384300449638271L;

	@Id
	@Column(name = "LANGUAGE_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long guid;

	@Column(name = "LANGUAGE_NAME")
	private String languageName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "firstLanguage")
	private List<StudentProfile> primaryLanguageStudentProfiles = new ArrayList<StudentProfile>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "secondLanguage")
	private List<StudentProfile> secondLanguageStudentProfiles = new ArrayList<StudentProfile>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "thirdLanguage")
	private List<StudentProfile> thirdLanguageStudentProfiles = new ArrayList<StudentProfile>();

	public Long getGuid() {
		return guid;
	}

	public void setGuid(Long guid) {
		this.guid = guid;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public List<StudentProfile> getPrimaryLanguageStudentProfiles() {
		return primaryLanguageStudentProfiles;
	}

	public void setPrimaryLanguageStudentProfiles(List<StudentProfile> primaryLanguageStudentProfiles) {
		this.primaryLanguageStudentProfiles = primaryLanguageStudentProfiles;
	}

	public List<StudentProfile> getSecondLanguageStudentProfiles() {
		return secondLanguageStudentProfiles;
	}

	public void setSecondLanguageStudentProfiles(List<StudentProfile> secondLanguageStudentProfiles) {
		this.secondLanguageStudentProfiles = secondLanguageStudentProfiles;
	}

	public List<StudentProfile> getThirdLanguageStudentProfiles() {
		return thirdLanguageStudentProfiles;
	}

	public void setThirdLanguageStudentProfiles(List<StudentProfile> thirdLanguageStudentProfiles) {
		this.thirdLanguageStudentProfiles = thirdLanguageStudentProfiles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((languageName == null) ? 0 : languageName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Language other = (Language) obj;
		if (languageName == null) {
			if (other.languageName != null)
				return false;
		} else if (!languageName.equals(other.languageName))
			return false;
		return true;
	}

}

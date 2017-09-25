package com.denkensol.universaladmission.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "RELIGIOUS")
@NamedNativeQueries({
		@NamedNativeQuery(name = "GET_ALL_RELEGIOUS", query = "select RELIGIOUS_GUID as guid,RELIGIOUS_NAME as religiousName from RELIGIOUS") })

public class Religious implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5912040486800312124L;

	@Id
	@Column(name = "RELIGIOUS_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long guid;

	@Column(name = "RELIGIOUS_NAME")
	private String religiousName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "religious", cascade = CascadeType.ALL)
	private List<StudentProfile> studentProfiles = new ArrayList<StudentProfile>();

	public Long getGuid() {
		return guid;
	}

	public void setGuid(Long guid) {
		this.guid = guid;
	}

	public String getReligiousName() {
		return religiousName;
	}

	public void setReligiousName(String religiousName) {
		this.religiousName = religiousName;
	}

	public List<StudentProfile> getStudentProfiles() {
		return studentProfiles;
	}

	public void setStudentProfiles(List<StudentProfile> studentProfiles) {
		this.studentProfiles = studentProfiles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((religiousName == null) ? 0 : religiousName.hashCode());
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
		Religious other = (Religious) obj;
		if (religiousName == null) {
			if (other.religiousName != null)
				return false;
		} else if (!religiousName.equals(other.religiousName))
			return false;
		return true;
	}

}

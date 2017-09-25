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
@Table(name = "VISA_TYPE")
@NamedNativeQueries({
		@NamedNativeQuery(name = "GET_ALL_VISA_TYPES", query = "select VISA_TYPE_GUID as guid,VISA_TYPE_NAME as visaTypeName from VISA_TYPE") })

public class VisaType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8700517602282498907L;

	@Id
	@Column(name = "VISA_TYPE_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long guid;

	@Column(name = "VISA_TYPE_NAME")
	private String visaTypeName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "visaTypeRequired", cascade = CascadeType.ALL)
	private List<StudentProfile> studentProfiles = new ArrayList<StudentProfile>();

	public Long getGuid() {
		return guid;
	}

	public void setGuid(Long guid) {
		this.guid = guid;
	}

	public String getVisaTypeName() {
		return visaTypeName;
	}

	public void setVisaTypeName(String visaTypeName) {
		this.visaTypeName = visaTypeName;
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
		result = prime * result + ((visaTypeName == null) ? 0 : visaTypeName.hashCode());
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
		VisaType other = (VisaType) obj;
		if (visaTypeName == null) {
			if (other.visaTypeName != null)
				return false;
		} else if (!visaTypeName.equals(other.visaTypeName))
			return false;
		return true;
	}

}

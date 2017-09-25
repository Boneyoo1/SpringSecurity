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
@Table(name = "COUNTRY")

@NamedNativeQueries({
		@NamedNativeQuery(name = "GET_ALL_COUNTRIES", query = "select COUNTRY_GUID as guid,COUNTRY_NAME as countryName from COUNTRY") })
public class Country implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2473827156827438878L;

	@Id
	@Column(name = "COUNTRY_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long guid;

	@Column(name = "COUNTRY_NAME")
	private String countryName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "birthCountry")
	private List<StudentProfile> studentsByBirthCountry = new ArrayList<StudentProfile>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "citizenshipCountry")
	private List<StudentProfile> studentsByCitizenshipCountry = new ArrayList<StudentProfile>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "permanentAddressCountry")
	private List<StudentProfile> studentsByPermanentAddressCountry = new ArrayList<StudentProfile>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mailingnAddressCountry")
	private List<StudentProfile> studentsByMailingAddressCountry = new ArrayList<StudentProfile>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "emergencyAddressCountry")
	private List<StudentProfile> studentsByEmergencyAddressCountry = new ArrayList<StudentProfile>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "schoolCountry")
	private List<School> schoolsByCountry = new ArrayList<School>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employementCountry")
	private List<StudentEmployement> employementByCountry = new ArrayList<StudentEmployement>();

	public Long getGuid() {
		return guid;
	}

	public void setGuid(Long guid) {
		this.guid = guid;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public List<StudentProfile> getStudentsByBirthCountry() {
		return studentsByBirthCountry;
	}

	public void setStudentsByBirthCountry(List<StudentProfile> studentsByBirthCountry) {
		this.studentsByBirthCountry = studentsByBirthCountry;
	}

	public List<StudentProfile> getStudentsByCitizenshipCountry() {
		return studentsByCitizenshipCountry;
	}

	public void setStudentsByCitizenshipCountry(List<StudentProfile> studentsByCitizenshipCountry) {
		this.studentsByCitizenshipCountry = studentsByCitizenshipCountry;
	}

	public List<StudentProfile> getStudentsByPermanentAddressCountry() {
		return studentsByPermanentAddressCountry;
	}

	public void setStudentsByPermanentAddressCountry(List<StudentProfile> studentsByPermanentAddressCountry) {
		this.studentsByPermanentAddressCountry = studentsByPermanentAddressCountry;
	}

	public List<StudentProfile> getStudentsByMailingAddressCountry() {
		return studentsByMailingAddressCountry;
	}

	public void setStudentsByMailingAddressCountry(List<StudentProfile> studentsByMailingAddressCountry) {
		this.studentsByMailingAddressCountry = studentsByMailingAddressCountry;
	}

	public List<School> getSchoolsByCountry() {
		return schoolsByCountry;
	}

	public void setSchoolsByCountry(List<School> schoolsByCountry) {
		this.schoolsByCountry = schoolsByCountry;
	}

	public List<StudentProfile> getStudentsByEmergencyAddressCountry() {
		return studentsByEmergencyAddressCountry;
	}

	public void setStudentsByEmergencyAddressCountry(List<StudentProfile> studentsByEmergencyAddressCountry) {
		this.studentsByEmergencyAddressCountry = studentsByEmergencyAddressCountry;
	}

	public List<StudentEmployement> getEmployementByCountry() {
		return employementByCountry;
	}

	public void setEmployementByCountry(List<StudentEmployement> employementByCountry) {
		this.employementByCountry = employementByCountry;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((countryName == null) ? 0 : countryName.hashCode());
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
		Country other = (Country) obj;
		if (countryName == null) {
			if (other.countryName != null)
				return false;
		} else if (!countryName.equals(other.countryName))
			return false;
		return true;
	}

}

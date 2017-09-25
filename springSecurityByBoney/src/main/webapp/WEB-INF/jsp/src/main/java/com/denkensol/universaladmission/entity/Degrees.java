package com.denkensol.universaladmission.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name = "DEGREES")
@NamedNativeQueries({
		@NamedNativeQuery(name = "GET_ALL_DEGREES", query = "select DEGREE_GUID as guid,DEGREE_NAME as degreeName from DEGREES") })


public class Degrees implements Serializable {

	private static final long serialVersionUID = 6452257846899915435L;

	@Id
	@Column(name = "DEGREE_GUID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long guid;

	@Column(name = "DEGREE_NAME")
	private String degreeName;

	public Long getGuid() {
		return guid;
	}

	public void setGuid(Long guid) {
		this.guid = guid;
	}

	public String getDegreeName() {
		return degreeName;
	}

	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}
	
	
}

package com.denkensol.universaladmission.dto;

import java.io.Serializable;

public class SortCriteriaData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7871920867994927775L;

	private String property;
	private Boolean isAscending;

	public SortCriteriaData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SortCriteriaData(String property, Boolean isAscending) {
		super();
		this.property = property;
		this.isAscending = isAscending;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Boolean getIsAscending() {
		return isAscending;
	}

	public void setIsAscending(Boolean isAscending) {
		this.isAscending = isAscending;
	}

}

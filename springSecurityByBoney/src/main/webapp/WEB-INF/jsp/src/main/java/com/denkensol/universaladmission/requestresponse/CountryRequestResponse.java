package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.math.BigInteger;

public class CountryRequestResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6726288004610549607L;
	private BigInteger guid;
	private String countryName;

	public BigInteger getGuid() {
		return guid;
	}

	public void setGuid(BigInteger guid) {
		this.guid = guid;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

}

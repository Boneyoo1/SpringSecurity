package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.math.BigInteger;

public class ReligoesRequestResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6726288004610549607L;
	private BigInteger guid;
	private String religiousName;

	public BigInteger getGuid() {
		return guid;
	}

	public void setGuid(BigInteger guid) {
		this.guid = guid;
	}

	public String getReligiousName() {
		return religiousName;
	}

	public void setReligiousName(String religiousName) {
		this.religiousName = religiousName;
	}

}

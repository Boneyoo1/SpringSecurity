package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.math.BigInteger;

public class LanguageRequestResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6726288004610549607L;
	private BigInteger guid;
	private String languageName;

	public BigInteger getGuid() {
		return guid;
	}

	public void setGuid(BigInteger guid) {
		this.guid = guid;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

}

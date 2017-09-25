package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.math.BigInteger;

public class VisaTypeRequestResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6726288004610549607L;
	private BigInteger guid;
	private String visaTypeName;

	public BigInteger getGuid() {
		return guid;
	}

	public void setGuid(BigInteger guid) {
		this.guid = guid;
	}

	public String getVisaTypeName() {
		return visaTypeName;
	}

	public void setVisaTypeName(String visaTypeName) {
		this.visaTypeName = visaTypeName;
	}

}

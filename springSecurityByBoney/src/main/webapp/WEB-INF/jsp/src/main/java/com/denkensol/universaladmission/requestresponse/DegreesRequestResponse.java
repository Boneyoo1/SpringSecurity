package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.math.BigInteger;

public class DegreesRequestResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -266720951540975252L;
	/**
	 * 
	 */
	private BigInteger guid;
	private String degreeName;
	
	public BigInteger getGuid() {
		return guid;
	}
	public void setGuid(BigInteger guid) {
		this.guid = guid;
	}
	public String getDegreeName() {
		return degreeName;
	}
	public void setDegreeName(String degreeName) {
		this.degreeName = degreeName;
	}
	
	
}

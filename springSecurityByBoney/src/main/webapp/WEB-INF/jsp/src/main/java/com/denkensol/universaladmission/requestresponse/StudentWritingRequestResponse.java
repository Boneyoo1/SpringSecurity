package com.denkensol.universaladmission.requestresponse;

import java.io.Serializable;
import java.math.BigInteger;

public class StudentWritingRequestResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 325164318780693379L;
	private BigInteger studentWritingGuid;
	private String writingType;
	private String writingText;

	public BigInteger getStudentWritingGuid() {
		return studentWritingGuid;
	}

	public void setStudentWritingGuid(BigInteger studentWritingGuid) {
		this.studentWritingGuid = studentWritingGuid;
	}

	public String getWritingType() {
		return writingType;
	}

	public void setWritingType(String writingType) {
		this.writingType = writingType;
	}

	public String getWritingText() {
		return writingText;
	}

	public void setWritingText(String writingText) {
		this.writingText = writingText;
	}

}

package com.denkensol.universaladmission.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 138657465578480715L;

	private String exceptionMsg;
	private HttpStatus code;

	public NotFoundException(HttpStatus notFound, String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
		this.code = notFound;
	}

	public HttpStatus getCode() {
		return code;
	}

	public void setCode(HttpStatus code) {
		this.code = code;
	}

	public String getExceptionMsg() {
		return this.exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}

}

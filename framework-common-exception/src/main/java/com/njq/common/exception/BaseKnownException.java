package com.njq.common.exception;

public class BaseKnownException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String errorMsg;
	public BaseKnownException() {
		
	}
	public BaseKnownException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	public BaseKnownException(String errorMsg) {
		super(errorMsg);
		this.errorCode = "39999";
		this.errorMsg = errorMsg;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}

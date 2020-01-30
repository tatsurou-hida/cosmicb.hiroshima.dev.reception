package com.example.demo;

public class CustomException extends Exception {

	private String code;
	private String argString;

	/**
	 * @param _code
	 * @param _arg
	 */
	public CustomException(String _code, String _arg) {
		setCode(_code);
		setArgString(_arg);
	}

	public String getArgString() {
		return argString;
	}

	public void setArgString(String argString) {
		this.argString = argString;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

};
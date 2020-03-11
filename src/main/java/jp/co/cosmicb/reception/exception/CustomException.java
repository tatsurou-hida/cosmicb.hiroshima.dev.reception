package jp.co.cosmicb.reception.exception;

public class CustomException extends Exception {

	private String code;
	private String argString;

	/**
	 * @param _code
	 * @param _arg
	 */
	public CustomException(String _code, String _argString) {
		this.code = _code;
		this.argString = _argString;
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
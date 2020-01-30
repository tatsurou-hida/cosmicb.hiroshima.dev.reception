package com.example.demo;

public class DatabaseException extends Exception {

	private String code;
	private String argString;

	/**ファイルに書き込みができない場合のカスタム例外
	 * @param _code
	 * @param _argString
	 */
	public DatabaseException(String _code, String _argString) {

		setCode(_code);
		setArgString(_argString);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getArgString() {
		return argString;
	}

	public void setArgString(String argString) {
		this.argString = argString;
	}

}

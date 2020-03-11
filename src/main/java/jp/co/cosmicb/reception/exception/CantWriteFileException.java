package jp.co.cosmicb.reception.exception;

public class CantWriteFileException extends Exception {

	private String code;
	private String argString;

	/**ファイルに書き込みができない場合のカスタム例外
	 * @param _code
	 * @param _argString
	 */
	public CantWriteFileException(String _code, String _argString) {
		this.code = _code;
		this.argString = _argString;
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

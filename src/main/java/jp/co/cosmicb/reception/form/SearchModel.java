package jp.co.cosmicb.reception.form;

public class SearchModel {

	private String inputMinDate;
	private String inputMaxDate;
	private boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getInputMinDate() {
		return inputMinDate;
	}

	public void setInputMinDate(String inputMinDate) {
		this.inputMinDate = inputMinDate;
	}

	public String getInputMaxDate() {
		return inputMaxDate;
	}

	public void setInputMaxDate(String inputMaxDate) {
		this.inputMaxDate = inputMaxDate;
	}

}

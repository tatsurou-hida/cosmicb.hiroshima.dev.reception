package com.example.demo;

import java.util.List;


public class SearchModel {

	private String inputMinDate;
	private String inputMaxDate;
	private boolean checked;
	private List<OfficeVisit> resultSearchList;



	public List<OfficeVisit> getResultSearchList() {
		return resultSearchList;
	}

	public void setResultSearchList(List<OfficeVisit> resultSearchList) {
		this.resultSearchList = resultSearchList;
	}

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

package com.example.demo;

public class RowDataModel {

	private String _id;
	private String in_DateTime;
	private String company;
	private String name;
	private String out_DateTime;
	private String person_to_visit;
	private String diffTime;
	private int visitor_Count;

	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getIn_DateTime() {
		return in_DateTime;
	}
	public void setIn_DateTime(String in_DateTime) {
		this.in_DateTime = in_DateTime;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOut_DateTime() {
		return out_DateTime;
	}
	public void setOut_DateTime(String out_DateTime) {
		this.out_DateTime = out_DateTime;
	}
	public String getPerson_to_visit() {
		return person_to_visit;
	}
	public void setPerson_to_visit(String person_to_visit) {
		this.person_to_visit = person_to_visit;
	}
	public String getDiffTime() {
		return diffTime;
	}
	public void setDiffTime(String diffTime) {
		this.diffTime = diffTime;
	}
	public int getVisitor_Count() {
		return visitor_Count;
	}
	public void setVisitor_Count(int visitor_Count) {
		this.visitor_Count = visitor_Count;
	}



}

package com.example.demo.form;

public class RowDataModel {

	private String _id;
	private String visitor_name;
	private String visitor_org;
	private String visited_at;
	private String left_at;
	private String person_to_visit;
	private String diffTime;

	public String get_id() {
		return _id;
	}
	public String getVisitor_name() {
		return visitor_name;
	}
	public void setVisitor_name(String visitor_name) {
		this.visitor_name = visitor_name;
	}
	public void set_id(String _id) {
		this._id = _id;
	}

	public String getVisitor_org() {
		return visitor_org;
	}
	public void setVisitor_org(String visitor_org) {
		this.visitor_org = visitor_org;
	}
	public String getVisited_at() {
		return visited_at;
	}
	public void setVisited_at(String visited_at) {
		this.visited_at = visited_at;
	}
	public String getLeft_at() {
		return left_at;
	}
	public void setLeft_at(String left_at) {
		this.left_at = left_at;
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
	public int getVisitor_count() {
		return visitor_count;
	}
	public void setVisitor_count(int visitor_count) {
		this.visitor_count = visitor_count;
	}
	private int visitor_count;


}

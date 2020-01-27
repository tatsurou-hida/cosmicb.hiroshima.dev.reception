package com.example.demo;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "officeVisit")
public class OfficeVisit {

	@Id
	private String _id;

	@Field("visitor_name")
	private String visitor_name; //訪問者
	@Field("visitor_org")
	private String visitor_org; //所属・会社名
	@Field("visitor_count")
	private int visitor_count; //人数
	@Field("visited_at")
	private LocalDateTime visited_at; //来訪日時
	@Field("person_to_visit")
	private String person_to_visit; //訪問先
	@Field("left_at")
	private LocalDateTime left_at; //退室日時

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getVisitor_name() {
		return visitor_name;
	}

	public void setVisitor_name(String name) {
		this.visitor_name = name;
	}

	public String getVisitor_org() {
		return visitor_org;
	}

	public void setVisitor_org(String org) {
		this.visitor_org = org;
	}

	public int getVisitor_count() {
		return visitor_count;
	}

	public void setVisitor_count(Integer num) {
		this.visitor_count = num;
	}

	public LocalDateTime getVisited_at() {
		return visited_at;
	}

	public void setVisited_at(LocalDateTime date) {
		this.visited_at = date;
	}

	public String getPerson_to_visit() {
		return person_to_visit;
	}

	public void setPerson_to_visit(String name) {
		this.person_to_visit = name;
	}

	public LocalDateTime getLeft_at() {
		return left_at;
	}

	public void setLeft_at(LocalDateTime date) {
		this.left_at = date;
	}

	public String getDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(visited_at);
	}

	@Override
	public String toString() {
		return "_id: " + _id
				+ " visitor_name: " + visitor_name
				+ " visitor_org: " + visitor_org
				+ " visitor_count: " + visitor_count
				+ " visited_at: " + visited_at.toString()
				+ " person_to_visit: " + person_to_visit
				+ " left_at: " + left_at.toString();
	}
}
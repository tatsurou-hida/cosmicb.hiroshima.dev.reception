package com.example.demo.entity;

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
	private String visitorName; //訪問者
	@Field("visitor_org")
	private String visitorOrg; //所属・会社名
	@Field("visitor_count")
	private int visitorCount; //人数
	@Field("visited_at")
	private LocalDateTime visitedAt; //来訪日時
	@Field("person_to_visit")
	private String personToVisit; //訪問先
	@Field("left_at")
	private LocalDateTime leftAt; //退室日時

	public String get_id() {
<<<<<<< HEAD

=======
>>>>>>> refs/heads/master
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getVisitor_name() {
		return visitorName;
	}

	public void setVisitor_name(String name) {
		this.visitorName = name;
	}

	public String getVisitor_org() {
		return visitorOrg;
	}

	public void setVisitor_org(String org) {
		this.visitorOrg = org;
	}

	public int getVisitor_count() {
		return visitorCount;
	}

	public void setVisitor_count(Integer num) {
		this.visitorCount = num;
	}

	public LocalDateTime getVisited_at() {
		return visitedAt;
	}

	public void setVisited_at(LocalDateTime date) {
		this.visitedAt = date;
	}

	public String getPerson_to_visit() {
		return personToVisit;
	}

	public void setPerson_to_visit(String name) {
		this.personToVisit = name;
	}

	public LocalDateTime getLeft_at() {
		return leftAt;
	}

	public void setLeft_at(LocalDateTime date) {
		this.leftAt = date;
	}

	public String getDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(visitedAt);
	}

	@Override
	public String toString() {
		return "OfficeVisit [_id=" + _id + ", visitor_name=" + visitorName + ", visitor_org=" + visitorOrg
				+ ", visitor_count=" + visitorCount + ", visited_at=" + visitedAt + ", person_to_visit="
				+ personToVisit + ", left_at=" + leftAt + "]";
	}
}
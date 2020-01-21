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
	private String visitor_name;	//訪問者
	@Field("visitor_org")
	private String visitor_org;		//所属・会社名
	@Field("visitor_count")
	private int visitor_count;		//人数
	@Field("visited_at")
	private LocalDateTime visited_at;		//来訪日時
	@Field("person_to_visit")
	private String person_to_visit;	//訪問先
	@Field("left_at")
	private LocalDateTime left_at;			//退室日時

	public OfficeVisit(
			String _id,
			String visitor_name,
			String visitor_org,
			int visitor_count,
			String person_to_visit,
			LocalDateTime visited_at,
			LocalDateTime left_at) {

		this._id = _id;
		this.visitor_name = visitor_name;
		this.visitor_org = visitor_org;
		this.visitor_count = visitor_count;
		this.person_to_visit = person_to_visit;
		this.visited_at = visited_at;
		this.left_at = left_at;


	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getVisitor_name() {
		return visitor_name;
	}

	public String getVisitor_org() {
		return visitor_org;
	}

	public int getVisitor_count() {
		return visitor_count;
	}

	public LocalDateTime getVisited_at() {
		return visited_at;
	}

	public String getPerson_to_visit() {
		return person_to_visit;
	}

	public LocalDateTime getLeft_at() {
		return left_at;
	}

	public String getDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(visited_at);
	}

	@Override
	public String toString() {
		return "OfficeVisit [_id=" + _id + ", visitor_name=" + visitor_name + ", visitor_org=" + visitor_org
				+ ", visitor_count=" + visitor_count + ", visited_at=" + visited_at + ", person_to_visit="
				+ person_to_visit + ", left_at=" + left_at + "]";
	}

}

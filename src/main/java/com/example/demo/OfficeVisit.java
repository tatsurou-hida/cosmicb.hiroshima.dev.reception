package com.example.demo;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.data.annotation.Id;

public class OfficeVisit {

	@Id
	private String _id;

	private String visitor_name;	//訪問者
	private String visitor_org;		//所属・会社名
	private int visitor_count;		//人数
	private Date visited_at;		//来訪日時
	private String person_to_visit;	//訪問先
	private Date left_at;			//退室日時

	public OfficeVisit(String visitor_name, String visitor_org, int visitor_count, String person_to_visit) {

		this.visitor_name = visitor_name;
		this.visitor_org = visitor_org;
		this.visitor_count = visitor_count;
		this.person_to_visit = person_to_visit;
		this.visited_at = (Date) Calendar.getInstance().getTime();


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

	public Date getVisited_at() {
		return visited_at;
	}

	public String getPerson_to_visit() {
		return person_to_visit;
	}

	public Date getLeft_at() {
		return left_at;
	}

	public String getDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(visited_at);
	}


}

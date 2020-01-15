package com.example.demo;

import java.sql.Date;
import java.text.SimpleDateFormat;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection = "visitor_list")
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
	private Date visited_at;		//来訪日時
	@Field("person_to_visit")
	private String person_to_visit;	//訪問先
	@Field("left_at")
	private Date left_at;			//退室日時

	public OfficeVisit(
			String visitor_name,
			String visitor_org,
			int visitor_count,
			String person_to_visit,
			java.util.Date visited_at,
			java.util.Date left_at) {

		this.visitor_name = visitor_name;
		this.visitor_org = visitor_org;
		this.visitor_count = visitor_count;
		this.person_to_visit = person_to_visit;
		this.visited_at = (Date) visited_at;
		this.left_at = (Date) left_at;


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

	@Override
    public String toString(){
        return "visitor_name: " + visitor_name
        		+ " visitor_org: " + visitor_org
        		+ " visitor_count: " + visitor_count
        		+ " visited_at: " + visited_at.toString()
        		+ " person_to_visit: " + person_to_visit
        		+ " left_at: " + left_at.toString();
	}

}

package jp.co.cosmicb.reception.entity;

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
	@Field("visitor_name_image_url")
	private String visitorNameImageUrl; //訪問者手書き入力画像URL
	@Field("visitor_org")
	private String visitorOrg; //所属・会社名
	@Field("visitor_org_image_url")
	private String visitorOrgImageUrl; //所属・会社名手書き入力画像URL
	@Field("visitor_count")
	private int visitorCount; //人数
	@Field("visited_at")
	private LocalDateTime visitedAt; //来訪日時
	@Field("person_to_visit")
	private String personToVisit; //訪問先
	@Field("left_at")
	private LocalDateTime leftAt; //退室日時

	public String getId() {
		return _id;
	}

	public void setId(String _id) {
		this._id = _id;
	}

	public String getVisitorName() {
		return visitorName;
	}

	public void setVisitorName(String name) {
		this.visitorName = name;
	}

	public String getVisitorOrg() {
		return visitorOrg;
	}

	public void setVisitorOrg(String org) {
		this.visitorOrg = org;
	}

	public int getVisitorCount() {
		return visitorCount;
	}

	public void setVisitorCount(Integer num) {
		this.visitorCount = num;
	}

	public LocalDateTime getVisitedAt() {
		return visitedAt;
	}

	public void setVisitedAt(LocalDateTime date) {
		this.visitedAt = date;
	}

	public String getPersonToVisit() {
		return personToVisit;
	}

	public void setPersonToVisit(String name) {
		this.personToVisit = name;
	}

	public LocalDateTime getLeftAt() {
		return leftAt;
	}

	public void setLeftAt(LocalDateTime date) {
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

	/**
	 * @return visitorNameImageUrl
	 */
	public String getVisitorNameImageUrl() {
		return visitorNameImageUrl;
	}

	/**
	 * @param visitorNameImageUrl セットする visitorNameImageUrl
	 */
	public void setVisitorNameImageUrl(String visitorNameImageUrl) {
		this.visitorNameImageUrl = visitorNameImageUrl;
	}

	/**
	 * @return visitorOrgImageUrl
	 */
	public String getVisitorOrgImageUrl() {
		return visitorOrgImageUrl;
	}

	/**
	 * @param visitorOrgImageUrl セットする visitorOrgImageUrl
	 */
	public void setVisitorOrgImageUrl(String visitorOrgImageUrl) {
		this.visitorOrgImageUrl = visitorOrgImageUrl;
	}
}
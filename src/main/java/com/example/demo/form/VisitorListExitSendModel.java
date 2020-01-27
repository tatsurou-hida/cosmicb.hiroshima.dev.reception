package com.example.demo.form;

import java.time.LocalDateTime;

public class VisitorListExitSendModel {

	private String _id;
	private String person_to_visit;
	private LocalDateTime left_at;

	public String get_id() {
		return _id;
	}

	public LocalDateTime getLeft_at() {
		return left_at;
	}

	public void setLeft_at(LocalDateTime left_at) {
		this.left_at = left_at;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getPerson_to_visit() {
		return person_to_visit;
	}

	public void setPerson_to_visit(String person_to_visit) {
		this.person_to_visit = person_to_visit;
	}

}

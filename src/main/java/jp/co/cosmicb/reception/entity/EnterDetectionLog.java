package jp.co.cosmicb.reception.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "enter_detect_log")
public class EnterDetectionLog {

	/**
	 * MongoDB Object ID
	 */
	@Id
	private String _id;

	/**
	 * 入室検知時刻
	 */
	@Field("entered_at")
	private LocalDateTime enteredAt;

	public EnterDetectionLog(LocalDateTime enteredAt) {
		this.setEnteredAt(enteredAt);
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public LocalDateTime getEnteredAt() {
		return enteredAt;
	}

	public void setEnteredAt(LocalDateTime enteredAt) {
		this.enteredAt = enteredAt;
	}

	@Override
	public String toString() {
		return "EnterDetectionLog [_id=" + _id + ", enteredAt=" + enteredAt + "]";
	}

}

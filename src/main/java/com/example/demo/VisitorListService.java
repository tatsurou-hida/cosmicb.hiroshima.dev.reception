package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorListService {
	@Autowired
	private VisitorListRepository visitorListRepository;

	private String minDate, maxDate;

	//TODO:サービスで日付計算させる
	private VisitorListService() {

		//Date型のフォーマット設定
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);		//1週間前の日付を入れる

		minDate = sdf.format(cal.getTime());
		maxDate = sdf.format(new Date());

	}



	private void search() {
		//カレンダーおよび未退室チェックボックスの情報から検索する



	}

	private void updateVisitorLeft(int id) {





	}

	public String getMessage() {
		System.out.println("★★★★★ Service called.");
		return "Hello. Hello " + visitorListRepository.getCustomer() + ".";

	}



	public String getMinDate() {
		return minDate;
	}



	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}



	public String getMaxDate() {
		return maxDate;
	}



	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}



}

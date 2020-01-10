package com.example.demo;

import java.sql.Date;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisitorListService {
	@Autowired
	private VisitorListRepository visitorListRepository;

	private Date minDate,maxDate;

	//TODO:サービスで日付計算させる
	private void VisitorListController() {
		Calendar calendar = Calendar.getInstance();
		minDate = calendar.add(Calendar.DATE, -7);

	}

	private String search() {
		//カレンダーおよび未退室チェックボックスの情報から検索する



	}

	private String updateVisitorLeft(int id) {





	}

	public String getMessage() {
		System.out.println("★★★★★ Service called.");
		return "Hello. Hello " + visitorListRepository.getCustomer() + ".";

	}

}

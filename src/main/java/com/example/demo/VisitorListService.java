package com.example.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoClients;

@Service
public class VisitorListService {
	@Autowired
	private VisitorListRepository visitorListRepository;

	private String minDate, maxDate;
	final String mongoUri = "mongodb+srv://app:kAz54fgSlnACwxIi@cluster0-cf1b0.gcp.mongodb.net/test?retryWrites=true&w=majority";

	SearchModel searchM;

	//TODO:サービスで日付計算させる
	private VisitorListService() {

		searchM = new SearchModel();

		//Date型のフォーマット設定
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);		//1週間前の日付

		minDate = sdf.format(cal.getTime());
		maxDate = sdf.format(new Date());

		searchM.setMinDate(minDate);
		searchM.setMaxDate(maxDate);
		searchM.setChecked(true);


	}

	public static void main(String[] args)  {

		System.out.println("★★★★★ ServiceMain called.");
		String uri = "mongodb+srv://app:kAz54fgSlnACwxIi@cluster0-cf1b0.gcp.mongodb.net/test?retryWrites=true&w=majority";

//	    MongoOperations mongoOps = new MongoTemplate(MongoClients.create(uri), "test");
//	    mongoOps.insert(new Person("Joe", 34));
//
//	    System.out.println(mongoOps.findOne(new Query(where("name").is("Joe")), Person.class));

	  }

	public void search() {
		//カレンダーおよび未退室チェックボックスの情報から検索する

		String minDate = searchM.getMinDate();
		String maxDate = searchM.getMaxDate();
		boolean checked = searchM.isChecked();

		MongoOperations mongoOps = new MongoTemplate(MongoClients.create(mongoUri), "test");

		//TODO:検索するクエリを記述する


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

	public SearchModel getSearchM() {
		return searchM;
	}

	public void setSearchM(SearchModel searchM) {
		this.searchM = searchM;
	}



}

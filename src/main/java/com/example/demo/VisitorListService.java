package com.example.demo;

import static org.springframework.data.mongodb.core.query.Criteria.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoClients;

@Service
public class VisitorListService {

	@Autowired
	private VisitorListRepository visitorListRepository;

	final String MONGO_URI = "mongodb+srv://app:kAz54fgSlnACwxIi@cluster0-cf1b0.gcp.mongodb.net/test?retryWrites=true&w=majority";

	public void startPageInitialize(SearchModel searchM) {

		LocalDate maxDate = LocalDate.now();
		LocalDate minDate = maxDate.minusDays(7);

		searchM.setInputMinDate(minDate.toString());
		searchM.setInputMaxDate(maxDate.toString());
		searchM.setChecked(true);
	}

	//TODO:テスト用後で消す
	public void databaseTest()  {


		System.out.println("★★★★★ ServiceMain called.");
		String uri = "mongodb+srv://app:kAz54fgSlnACwxIi@cluster0-cf1b0.gcp.mongodb.net/test?retryWrites=true&w=majority";

	    MongoOperations mongoOps = new MongoTemplate(MongoClients.create(uri), "test");
	    mongoOps.insert(new Person("Joe", 34));

	    System.out.println(mongoOps.findOne(new Query(where("name").is("Joe")), Person.class));

	  }

	public void search(SearchModel searchM) {
		//カレンダーおよび未退室チェックボックスの情報から検索する
		System.out.println("★★★★★ Service - search called.");

		LocalDateTime inputMaxDate = toLocalDateTime(searchM.getInputMinDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
		LocalDateTime inputMinDate = toLocalDateTime(searchM.getInputMaxDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
		boolean checked = searchM.isChecked();

		System.out.println(inputMaxDate);
		System.out.println(inputMinDate);
		System.out.println(checked);

		Query query = new Query();

		if (checked == true) {

			//TODO:この書き方だと「ソッドを起動中に com.sun.jdi.InvocationException: Exception occurred in target VM が発生しました。」が表示される
			query.addCriteria(Criteria.
						where("person_to_visit").is("").
						and("visited_at").gte(inputMinDate.toString()).
						and("visited_at").lt(inputMaxDate.plusDays(1).toString()));

			//query.addCriteria(Criteria.where("person_to_visit").is("").);
			MongoOperations mongoOps = new MongoTemplate(MongoClients.create(MONGO_URI), "database");

			//TODO:検索するクエリを記述する
			List<OfficeVisit> retList = mongoOps.find(query, OfficeVisit.class);
			System.out.println(mongoOps.find(query, OfficeVisit.class));

		} else {



		}


	}

	private void updateVisitorLeft(int id) {





	}


	public static LocalDateTime toLocalDateTime(String date, String format) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(date, dtf);
    }

	public String getMessage() {
		System.out.println("★★★★★ Service called.");
		return "Hello. Hello " + visitorListRepository.getCustomer() + ".";

	}




}

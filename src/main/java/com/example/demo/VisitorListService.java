package com.example.demo;

import static org.springframework.data.mongodb.core.query.Criteria.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

		List<OfficeVisit> resultSearchList;

		LocalDateTime inputMinDateTime = toLocalDateTime(searchM.getInputMinDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
		LocalDateTime inputMaxDateTime = toLocalDateTime(searchM.getInputMaxDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
		boolean checked = searchM.isChecked();

		System.out.println(inputMaxDateTime);
		System.out.println(inputMinDateTime);
		System.out.println(checked);

		//クエリ上の都合でinputMaxDateTimeに＋1日する
		inputMaxDateTime = inputMaxDateTime.plusDays(1);
		System.out.println(localDatetimeToDate(inputMaxDateTime));
		System.out.println(localDatetimeToDate(inputMinDateTime));

		Query query = new Query();

		if (checked == true) {
			//入室者検索

			query.addCriteria(Criteria.
					where("person_to_visit").is("")
					.andOperator(
						Criteria.where("visited_at").gte(inputMinDateTime),
						Criteria.where("visited_at").lt(inputMaxDateTime)
					));


			MongoOperations mongoOps = new MongoTemplate(MongoClients.create(MONGO_URI), "database");

			resultSearchList = mongoOps.find(query, OfficeVisit.class);

		} else {
			//全件検索
			query.addCriteria(new Criteria()
					.andOperator(
						Criteria.where("visited_at").gte(inputMinDateTime),
						Criteria.where("visited_at").lt(inputMaxDateTime)
					));

			MongoOperations mongoOps = new MongoTemplate(MongoClients.create(MONGO_URI), "database");

			resultSearchList = mongoOps.find(query, OfficeVisit.class);

		}

		System.out.println(resultSearchList);

		searchM.setResultSearchList(resultSearchList);

	}

	private void updateVisitorLeft(int id) {





	}


	public LocalDateTime toLocalDateTime(String date, String format) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(date, dtf);
    }

	public Date localDatetimeToDate(LocalDateTime localdatetime) {

		localdatetime = localdatetime.minusHours(9);
		ZoneId zone = ZoneId.systemDefault();
		ZonedDateTime zonedDateTime = ZonedDateTime.of(localdatetime, zone);
		Instant instant = zonedDateTime.toInstant();

		return Date.from(instant);
	}

	public String getMessage() {
		System.out.println("★★★★★ Service called.");
		return "Hello. Hello " + visitorListRepository.getCustomer() + ".";

	}




}

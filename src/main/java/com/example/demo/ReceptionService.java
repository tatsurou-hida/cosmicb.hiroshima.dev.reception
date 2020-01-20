package com.example.demo;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoClients;

@Service
public class ReceptionService {
	String	name;
	String	org;
	Integer	number;
	Date	date;
	MongoOperations mongoOps;

	public ReceptionService() { // constractor なので void不要

	}

	public void insertVisitor( String org, String name, Integer number ) {
		//
		DbConnection();
		//
		DBinsert(name, org, number);

//		System.out.println(name);
//		System.out.println(org);
//		System.out.println(number);
	}

	// ----------------------------------------------------------
	// add record.
	// ----------------------------------------------------------
	public void DBinsert(String name, String org, Integer number) {
		LocalDateTime date = LocalDateTime.now();
		LocalDateTime epoch = LocalDateTime.of(2000, 1, 1, 0, 0, 0);

//		String target = "2019-02-17T04:33:37.00449Z";
//		ZonedDateTime utc = ZonedDateTime.parse(target);
//		ZonedDateTime jst = withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
//		System.out.println(jst.toLocalDate());

		System.out.println("LocalDate" + date);
//		ZonedDateTime date = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
//		ZonedDateTime epoch = ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0, ZoneId.of("Asia/Tokyo"));

		//
		mongoOps.insert(new OfficeVisit(name, org, number, date, "", epoch));

		System.out.println("===== レコード追加 =====");
		System.out.println(name);
		System.out.println(org);
		System.out.println(number);
	}

	// ----------------------------------------------------------
	// DB connection.
	// ----------------------------------------------------------
	public void DbConnection() {
		String uri = "mongodb+srv://app:kAz54fgSlnACwxIi@cluster0-cf1b0.gcp.mongodb.net/test?retryWrites=true&w=majority";

		mongoOps = new MongoTemplate(MongoClients.create(uri), "database");

	}
}
package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.entity.OfficeVisit;
import com.mongodb.client.MongoClients;

@Service
public class ReceptionService {
	String name;
	String org;
	Integer number;
	Date date;
	MongoOperations mongoOps;

	public ReceptionService() { // constractor なので void不要

	}

	public void insertVisitor(String org, String name, Integer number) {
		//
		DbConnection();
		//
		DBinsert(name, org, number);

	}

	// ----------------------------------------------------------
	// add record.
	// ----------------------------------------------------------
	public void DBinsert(String name, String org, Integer num) {
		LocalDateTime date = LocalDateTime.now();
		LocalDateTime epoch = LocalDateTime.of(2000, 1, 1, 0, 0, 0);

		System.out.println("LocalDate" + date);

		//
		OfficeVisit officeVisit = new OfficeVisit();

		officeVisit.setVisitor_name(name);
		officeVisit.setVisitor_org(org);
		officeVisit.setVisitor_count(num);
		officeVisit.setVisited_at(date);
		officeVisit.setPerson_to_visit("");
		officeVisit.setLeft_at(epoch);

		mongoOps.insert(officeVisit);

		System.out.println("===== レコード追加 =====");
		System.out.println(name);
		System.out.println(org);
		System.out.println(num);

	}

	// ----------------------------------------------------------
	// DB connection.
	// ----------------------------------------------------------
	public void DbConnection() {
		String uri = "mongodb+srv://app:kAz54fgSlnACwxIi@cluster0-cf1b0.gcp.mongodb.net/test?retryWrites=true&w=majority";
			mongoOps = new MongoTemplate(MongoClients.create(uri), "database");
	}
}
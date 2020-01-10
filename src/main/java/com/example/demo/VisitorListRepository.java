package com.example.demo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface VisitorListRepository extends MongoRepository<OfficeVisit, String> {

	//https://www.tuyano.com/index3?id=10586003&page=6を参考

	public List<OfficeVisit> findByVisitedAtDesc();				//入室中検索

	public List<OfficeVisit> findByVisiterAtAndLeftatDesc();	//全数検索

	public int updateVisitorLeft(String _id, String person_to_visit);	//更新

	public String getCustomer();

}

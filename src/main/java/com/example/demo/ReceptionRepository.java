package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptionRepository extends MongoRepository<OfficeVisit, String> {
//	public List<OfficeVisit> findByvisitor_name(String visitor_name);
//	public List<OfficeVisit> findByvisitor_org(String visitor_org);
//	public List<OfficeVisit> findByvisitor_count(Integer visitor_count);
//	public List<OfficeVisit> findByvisited_at(Date visited_at);
//	public List<OfficeVisit> findByperson_to_visit(String person_to_visit);
//	public List<OfficeVisit> findByleft_at(Date left_at);
}
package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.OfficeVisit;

public interface VisitorListRepository extends MongoRepository<OfficeVisit, String> {

	public List<OfficeVisit> findByVisitedAtBetweenOrderByVisitedAtDesc(LocalDateTime startDate, LocalDateTime endDate);

	public List<OfficeVisit> findByVisitedAtBetweenAndPersonToVisitIsOrderByVisitedAtDesc(LocalDateTime startDate,
			LocalDateTime endDate, String personToVisit);

	public List<OfficeVisit> findByVisitedAtLessThanAndPersonToVisitNot(LocalDateTime startDate, String personToVisit);

	public void deleteById(String id);

	public OfficeVisit save(OfficeVisit entity);

	//public <S extends OfficeVisit> S save(S OfficeVisit);

}

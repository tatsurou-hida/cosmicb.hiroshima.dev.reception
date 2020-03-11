package jp.co.cosmicb.reception.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import jp.co.cosmicb.reception.entity.OfficeVisit;

public interface VisitorListRepository extends MongoRepository<OfficeVisit, String> {

	public List<OfficeVisit> findByVisitedAtBetweenOrderByVisitedAtDesc(LocalDateTime startDate, LocalDateTime endDate);

	public List<OfficeVisit> findByVisitedAtBetweenAndPersonToVisitIsOrderByVisitedAtDesc(LocalDateTime startDate,
			LocalDateTime endDate, String personToVisit);

	public Optional<OfficeVisit> findById(String _id);

	//public List<OfficeVisit> findOne(String id);

	public List<OfficeVisit> findByVisitedAtLessThanAndPersonToVisitNot(LocalDateTime startDate, String personToVisit);

	public void deleteById(String id);

	public OfficeVisit save(Optional<OfficeVisit> entity);

}

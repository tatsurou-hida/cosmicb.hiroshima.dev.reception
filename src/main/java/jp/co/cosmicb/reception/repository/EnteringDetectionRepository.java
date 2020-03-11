package jp.co.cosmicb.reception.repository;

import org.springframework.data.repository.Repository;

import jp.co.cosmicb.reception.entity.EnterDetectionLog;

public interface EnteringDetectionRepository extends Repository<EnterDetectionLog, Long> {

	EnterDetectionLog save(EnterDetectionLog entity);

	EnterDetectionLog findFirstByOrderByEnteredAtDesc();

}

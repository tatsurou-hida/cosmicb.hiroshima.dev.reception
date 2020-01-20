package com.example.demo.repository;

import org.springframework.data.repository.Repository;

import com.example.demo.entity.EnterDetectionLog;

public interface EnteringDetectionRepository extends Repository<EnterDetectionLog, Long> {

	EnterDetectionLog save(EnterDetectionLog entity);

	EnterDetectionLog findFirstByOrderByEnteredAtDesc();

}

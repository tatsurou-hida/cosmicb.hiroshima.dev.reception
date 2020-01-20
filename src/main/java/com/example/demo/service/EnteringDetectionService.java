package com.example.demo.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.EnterDetectionLog;
import com.example.demo.repository.EnteringDetectionRepository;

@Service
public class EnteringDetectionService {

	protected final static Logger logger = LoggerFactory.getLogger(EnteringDetectionService.class);

	@Autowired
	private EnteringDetectionRepository repository;

	/**
	 * データベースに現在時刻を保存する
	 * @return 保存された現在時刻
	 */
	public String register() {

		EnterDetectionLog inserted = repository.save(new EnterDetectionLog(LocalDateTime.now()));
		logger.info("Saving succeeded: " + inserted.toString());

		return inserted.getEnteredAt().toString();
	}

	/**
	 * 前回入室検知からの経過時間（秒）を取得する
	 * @return 前回入室検知からの経過時間（秒）
	 */
	public long getElapsedTimeFromLastEntered() {

		EnterDetectionLog entity = repository.findFirstByOrderByEnteredAtDesc();
		if (entity == null) {
			logger.warn("Nothing has been fetched.");
			return 0;
		}

		logger.info("Fetched: " + entity.toString());

		long intervalSeconds = entity.getEnteredAt().until(LocalDateTime.now(), ChronoUnit.SECONDS);
		return intervalSeconds;
	}

}

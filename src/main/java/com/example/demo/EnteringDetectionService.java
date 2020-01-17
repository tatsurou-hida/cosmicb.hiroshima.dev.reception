package com.example.demo;

import org.springframework.stereotype.Service;

@Service
public class EnteringDetectionService {

	/**
	 * データベースに現在時刻を保存する
	 * @return 保存された現在時刻
	 */
	public String register() {
		// TODO: 実装する
		return "test";
	}

	/**
	 * 前回入室検知からの経過時間（秒）を取得する
	 * @return 前回入室検知からの経過時間（秒）を
	 */
	public int getElapsedTimeFromLastEntered() {
		// TODO: 実装する
		return 120;
	}

}

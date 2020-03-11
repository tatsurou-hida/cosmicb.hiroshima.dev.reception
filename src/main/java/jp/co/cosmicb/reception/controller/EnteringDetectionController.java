package jp.co.cosmicb.reception.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jp.co.cosmicb.reception.service.EnteringDetectionService;

@RestController
public class EnteringDetectionController {

	protected final static Logger logger = LoggerFactory.getLogger(EnteringDetectionController.class);

	@Autowired
	private EnteringDetectionService service;

	/**
	 * 入室検知をデータベースに登録するAPI
	 * @return 登録した入室検知時刻（アプリケーションサーバ時間）
	 */
	@RequestMapping(value = "/api/enter/detect", method = { RequestMethod.POST, RequestMethod.GET })
	public String registerEnterDetect() {
		logger.trace("request received");
		return service.register();
	}

	/**
	 * 前回入室検知からの経過時間（秒）を取得するAPI
	 * @return 前回入室検知からの経過時間（秒）
	 */
	@RequestMapping(value = "/api/enter/elapsed", method = RequestMethod.GET)
	public long getElapsedTimeFromLastEntered() {
		logger.trace("request received");
		return service.getElapsedTimeFromLastEntered();
	}

}

package jp.co.cosmicb.reception.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Web アプリケーション全体のエラーコントローラー。
 * ErrorController インターフェースの実装クラス。
 */
@Controller
@RequestMapping("/error")
public class ErrorControllerImpl implements ErrorController {

	/**
	 * エラーページのパスを返す。
	 *
	 * @return エラーページのパス
	 */
	@Override
	public String getErrorPath() {
		return "/error";
	}

	/**
	 * HTML レスポンス用の ModelAndView オブジェクトを返す。
	 *
	 * @param req リクエスト情報
	 * @param mav レスポンス情報
	 * @return HTML レスポンス用の ModelAndView オブジェクト
	 */
	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView myErrorHtml(HttpServletRequest req, ModelAndView mav) {

		// HTTP ステータスを決める
		HttpStatus status = getHttpStatus(req);

		// HTTP ステータスをセットする
		mav.setStatus(status);

		// ビュー名を指定する
		// Thymeleaf テンプレートの場合は src/main/resources/templates/error.html
		mav.setViewName("error");

		// 出力したい情報をセットする
		mav.addObject("statusCode", status.value());
		mav.addObject("statusName", status.toString());

		return mav;
	}

	/**
	 * JSON レスポンス用の ResponseEntity オブジェクトを返す。
	 *
	 * @param req リクエスト情報
	 * @return JSON レスポンス用の ResponseEntity オブジェクト
	 */
	@RequestMapping
	public ResponseEntity<Map<String, Object>> myErrorJson(HttpServletRequest req) {

		// HTTP ステータスを決める
		HttpStatus status = getHttpStatus(req);

		// 出力したい情報をセットする
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("status", status.value());

		// 情報を JSON で出力する
		return new ResponseEntity<>(body, status);
	}

	/**
	 * レスポンス用の HTTP ステータスを決める。
	 *
	 * @param req リクエスト情報
	 * @return レスポンス用 HTTP ステータス
	 */
	private static HttpStatus getHttpStatus(HttpServletRequest req) {
		// HTTP ステータスを決める
		// ここでは 404 以外は全部 500 にする
		Object statusCode = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		if (statusCode != null && statusCode.toString().equals("404")) {
			status = HttpStatus.NOT_FOUND;
		}
		return status;
	}

}

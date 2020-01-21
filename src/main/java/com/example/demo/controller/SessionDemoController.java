package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.demo.form.SessionDemoForm;

@Controller
@SessionAttributes("form") // value（名前）で指定
public class SessionDemoController {

	protected final static Logger logger = LoggerFactory.getLogger(SessionDemoController.class);

	@ModelAttribute("form") // @SessionAttributesで指定したvalue（名前）が必要
	public SessionDemoForm getNewInstance() {

		logger.info("=================================");
		logger.info("Create new instance for a session");

		// 初期化
		SessionDemoForm init = new SessionDemoForm();
		init.setField1("init val for field1");
		init.setField2("init val for field2");

		// 初期化処理を特に書かず、空のインスタンスを返しても可
		// （インスタンス化がaction直叩きの場合でも初期値を設定しておきたいなら、このメソッドで初期化すればよい）
		// return new SessionDemoForm();

		logger.info(init.toString());

		return init; // メソッドのアノテーション@ModelAttributeで指定したvalue（名前）でaddAttributeされる

	}

	@RequestMapping(value = "/sessiondemo", method = RequestMethod.GET)
	public String index(Model model) {

		logger.info("=================================");
		logger.info("Controller handles GET reqeust for /sessiondemo");

		// もしここでSessionAttributeの内容を変更したい場合は、
		// 引数に @ModelAttribute("form") SessionDemoForm form を追加する
		//
		// public String index(@ModelAttribute("form") SessionDemoForm form, Model model) {
		//     form.setField1("aaa");
		//     return "sessiondemo";
		// }

		// model.setAttribute("form", form) は不要
		// modelにattributeが存在することを確認
		logger.info(model.getAttribute("form").toString());

		return "sessiondemo";

	}

	@RequestMapping(value = "/sessiondemo/postform", method = RequestMethod.POST)
	public String post(@ModelAttribute("form") SessionDemoForm form, Model model) {

		// 引数として受け取る場合（postされた結果など）は
		// @ModelAttribute("form") SessionDemoForm hoge を引数に追加する

		logger.info("=================================");
		logger.info("Controller handles POST reqeust for /sessiondemo/postform");
		logger.info(form.toString());

		// model.setAttribute("form", form) は不要

		return "sessiondemo";

	}

	@RequestMapping(value = "/sessiondemo/other", method = RequestMethod.GET)
	public String other(Model model) {

		logger.info("=================================");
		logger.info("Controller handles GET reqeust for /sessiondemo/other");

		// formから情報が送信されない場合でもsessionから取得できる
		logger.info(model.getAttribute("form").toString());

		return "sessiondemo";

	}

	@RequestMapping(value = "/sessiondemo/complete", method = RequestMethod.GET)
	public String complete(SessionStatus sessionStatus, Model model) {

		logger.info("=================================");
		logger.info("Controller handles GET reqeust for /sessiondemo/complete");

		sessionStatus.setComplete();

		// setComplete()が呼ばれてすぐにセッション情報が破棄されるわけではなく
		// このメソッドの終了後にフレームワークによって破棄される
		// したがって、このメソッド内ではまだセッション情報を参照できるし、
		// viewも残ったattributeによってレンダリングされる
		logger.info(model.getAttribute("form").toString());

		return "sessiondemo";

	}
}

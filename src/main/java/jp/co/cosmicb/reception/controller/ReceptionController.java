package jp.co.cosmicb.reception.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.co.cosmicb.reception.service.ReceptionService;

@Controller
@EnableAutoConfiguration

public class ReceptionController {
	@Autowired
	private ReceptionService service;

	@RequestMapping(value = "/reception", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView mv) {

		mv.setViewName("reception");

		return mv;
	}

	//
	@RequestMapping(value = "/result", method = RequestMethod.POST)
	public ModelAndView result(
			@RequestParam("inputName") String inputName,
			@RequestParam("inputCompany") String inputCompany,
			@RequestParam("inputNum") Integer inputNum,
					ModelAndView mv) {

		service.insertVisitor(inputCompany,inputName,inputNum);

		mv.setViewName("reception");	// HTML

		return mv;
	}
}
package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
			@RequestParam("input_comp_name") String input_comp_name,
			@RequestParam("input_visitor_name") String input_visitor_name,
			@RequestParam("select_number") Integer select_number,
			ModelAndView mv) {

		service.insertVisitor(input_comp_name, input_visitor_name, select_number);

		mv.setViewName("reception"); // HTML

		return mv;
	}
}
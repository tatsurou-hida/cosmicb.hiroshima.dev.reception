package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@EnableAutoConfiguration
@ComponentScan
public class ReceptionController {
	@Autowired
	private ReceptionService service;

	@RequestMapping(value="/reception", method=RequestMethod.GET)
	public ModelAndView index(ModelAndView mv) {
//		Date date = new Date();
//		SimpleDateFormat dformat = new SimpleDateFormat("yyyy年MM月dd日 E曜日 H時mm分");
//		String formattedDate = dformat.format(date);
//		long millisec = date.getTime();
//		String msstring = String.valueOf(millisec);

		mv.setViewName("reception");
//		mv.addObject("serverTime", formattedDate);
//		mv.addObject("milliSec", millisec);
//		mv.addObject("msString", msstring);
		return mv;
	}

	//
	@RequestMapping(value="/result", method=RequestMethod.POST)
	public ModelAndView result(
			@RequestParam("input_comp_name") String input_comp_name,
			@RequestParam("input_visitor_name") String input_visitor_name,
			@RequestParam("select_number") Integer select_number,
					ModelAndView mv) {

		service.insertVisitor(input_comp_name,input_visitor_name,select_number);

		mv.setViewName("reception");	// HTML

		return mv;
	}
}
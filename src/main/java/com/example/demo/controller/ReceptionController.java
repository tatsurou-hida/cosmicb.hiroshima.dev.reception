package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.ReceptionService;

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

		// 強制的に止める
		try{
			Thread.sleep(3000); //3秒Sleepする

		}catch(InterruptedException e){
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );

		}finally {
			mv.setViewName("reception");	// HTML
		}

		return mv;
	}
}
package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VisitorListController {

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView index(ModelAndView mv) {
		mv.setViewName("VisitorList");
		return mv;
	}

	//TODO:上に統合したい
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String test(Model model){

		List<String> visitList = new ArrayList<String>();

		visitList.add("100, コスミックビジネス, 岡, 3");
		visitList.add("101, コスミックビジネス, 白幡, 4");
		visitList.add("102, コスミックビジネス, 畑上, 5");

		model.addAttribute("visitList" , visitList);


		return "VisitorList";

	}


}

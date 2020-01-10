package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@EnableAutoConfiguration
@ComponentScan
public class VisitorListController {

	@Autowired
	private VisitorListService visitorListService;
	//@Autowired
	//private VisitorListRepository visitorListRepository;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView mv) {

		System.out.println("★★★★★ Controller called.");


		//カレンダーをセットする
		mv.addObject("minDate",visitorListService.getMinDate());

		mv.addObject("visitList", visitorList);
		mv.setViewName("VisitorList");
		return mv;
	}

//	@RequestMapping(value = "/list")
//	@ResponseBody
	public String helloControl() {

		System.out.println("★★★★★ 'helloControl' called.");
		return visitorListService.getMessage();



	}




}

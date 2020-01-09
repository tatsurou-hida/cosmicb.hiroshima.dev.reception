package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HelloSpringWebController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index1(ModelAndView mv) {
		mv.setViewName("entry");
		return mv;
	}

	//valueにはhtmlで定義したアクション名
    @RequestMapping(value="/registration", method=RequestMethod.POST)
    public String post(@ModelAttribute EntryModel form, Model model) {
    	model.addAttribute("entry", form);
        return "entry";
    }

//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public ModelAndView index2(ModelAndView mv) {
//		mv.setViewName("exit");
//		return mv;
//	}

//	    @RequestMapping(value="/entry", method=RequestMethod.POST)
//	    public ModelAndView send(@RequestParam("inputCompany")String inputCompany, ModelAndView mv) {
//	        mv.setViewName("entry");
//	        mv.addObject("message", inputCompany);
//	        return mv;
//	    }

}

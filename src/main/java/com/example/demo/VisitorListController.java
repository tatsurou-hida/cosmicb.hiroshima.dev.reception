package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@EnableAutoConfiguration
@ComponentScan
public class VisitorListController {

	@Autowired
	private VisitorListService visitorListService;
	private SearchModel searchM = new SearchModel();


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Model model) {

		System.out.println("★★★★★ Controller called.");

//		System.out.println(visitorListService.getMinDate());
//		System.out.println(visitorListService.getMaxDate());
		visitorListService.startPageInitialize(searchM);

		model.addAttribute("searchM", searchM);

		//Thymeleafを表示する;
		return "VisitorList";
	}


	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@ModelAttribute SearchModel searchM, Model model) {


		//検索
		visitorListService.search(searchM);


		//form内の部品に値を入れる(入力内容の保持)
		model.addAttribute("searchM", searchM);
		//Thymeleafを表示する
		return "VisitorList";

	}

//	@RequestMapping(value = "/search", method = RequestMethod.POST)
//	public ModelAndView search(
//			@RequestParam("inputMinDate")String inputMinDate,
//			@RequestParam("inputMaxDate")String inputMaxDate,
//			@RequestParam("checked")Boolean checked,
//			ModelAndView mv) {
//
//
//		return mv;
//
//	}

//	//valueにはhtmlで定義したアクション名
//    @RequestMapping(value="/registration", method=RequestMethod.POST)
//    public String post(@ModelAttribute EntryModel form, Model model) {
//    	model.addAttribute("entry", form);
//        return "entry";
//    }





}

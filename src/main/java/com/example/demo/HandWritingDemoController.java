package com.example.demo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.config.SpringDataMongoDBConfig;

import net.sourceforge.tess4j.TesseractException;

@Controller
public class HandWritingDemoController {

	@Autowired
	private HandWritingDemoService service;

	// TODO: サンプルにつき後で消す
	@Autowired
	private SpringDataMongoDBConfig config;

	@RequestMapping(value = "/handwriting_demo", method = RequestMethod.GET)
	public String index(Model model) {

		model.addAttribute("form", new HandWritingDemoForm());

		// TODO: サンプルにつき後で消す
		System.out.println(config.getDatabase());
		System.out.println(config.getHost());
		System.out.println(config.getPassword());
		System.out.println(config.getPort());
		System.out.println(config.getUri());
		System.out.println(config.getUsername());

		return "handwriting_demo";
	}

	@RequestMapping(value = "/handwriting_demo", method = RequestMethod.POST)
	public String index(@ModelAttribute HandWritingDemoForm form, Model model) {

		String ocrResult = "";
		String ocrResultDetailURI = "";

		try {
			ocrResult = service.doOCR(form.getCanvasDataURL());
		} catch (TesseractException | IOException e) {
			e.printStackTrace();
		}

		try {
			ocrResultDetailURI = service.getOCRResultImageDataURL(form.getCanvasDataURL());
		} catch (IOException e) {
			e.printStackTrace();
		}

		form.setOcrResult(ocrResult);
		form.setCanvasDataURL(ocrResultDetailURI);

		model.addAttribute("form", form);
		return "handwriting_demo";
	}
}

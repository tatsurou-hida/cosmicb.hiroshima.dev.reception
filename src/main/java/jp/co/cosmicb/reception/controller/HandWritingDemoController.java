package jp.co.cosmicb.reception.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.cosmicb.reception.form.HandWritingDemoForm;
import jp.co.cosmicb.reception.service.HandWritingDemoService;

import net.sourceforge.tess4j.TesseractException;

@Controller
public class HandWritingDemoController {

	@Autowired
	private HandWritingDemoService service;

	@RequestMapping(value = "/handwriting_demo", method = RequestMethod.GET)
	public String index(Model model) {

		model.addAttribute("form", new HandWritingDemoForm());

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

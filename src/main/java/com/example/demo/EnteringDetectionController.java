package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnteringDetectionController {

	@Autowired
	private EnteringDetectionService service;

    /**
     *
     * @return
     */
    @RequestMapping(value="/api/enter/detect", method=RequestMethod.POST)
    public String registerEnterDetect() {
        return service.register();
    }

    /**
     * 前回入室検知からの経過時間（秒）を取得するAPI
     * @return
     */
    @RequestMapping(value="/api/enter/elapsed", method=RequestMethod.GET)
    public int getElapsedTimeFromLastEntered() {
    	return service.getElapsedTimeFromLastEntered();
    }

}

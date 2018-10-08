package com.scandocviewer.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexHandler {

	  @RequestMapping(method = RequestMethod.GET)
	    public String getIndexPage() {
	        return "index";
	    }

}
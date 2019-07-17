package com.zcky.learn.with.teacher.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
public class TestController extends BaseController{
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<String> index(HttpServletRequest request) throws Exception {
		//String auth = request.getHeader("uid");
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		responseHeaders.add("Cookie", "sadasdasdsadasdsadasd");
		return new ResponseEntity<String>( "Greetings from Spring Boot!", getResponseHeader(), HttpStatus.OK);
    }
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
    String uid(HttpSession session) {
		//System.out.println(session.isNew());
        return "test";
    }
}

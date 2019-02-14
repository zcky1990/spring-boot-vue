package com.projects.springboot.controller;

import org.springframework.http.HttpHeaders;

import com.google.gson.Gson;
import com.projects.springboot.environtment.Environtment;


public class BaseController {
	private Environtment env = new Environtment();

	public HttpHeaders getResponseHeader() {
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		return responseHeaders;
	}

	public String getEnvirontmentBuild() {
		return env.getEnv();
	}
	
	public String convertResponseToString(Object response) {
		Gson gson = new Gson();
		return gson.toJson(response);
	}
}

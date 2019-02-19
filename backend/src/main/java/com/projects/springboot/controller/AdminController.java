package com.projects.springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.projects.springboot.models.Response;
import com.projects.springboot.models.UserSession;
import com.projects.springboot.models.Users;

@RestController()
@RequestMapping("/api")
public class AdminController extends BaseController{
	
	@RequestMapping(value = "/is_session_active", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> isSessionActive(@RequestHeader MultiValueMap<String,String> headers){
		UserSession userSession = new UserSession();
		return new ResponseEntity<String>(convertResponseToString(userSession), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/sign_up", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> signUp(@RequestBody Users user,@RequestHeader MultiValueMap<String,String> headers){
		Response response = new Response();
		response.setErrorMessage("failed to login");
		return new ResponseEntity<String>(this.convertResponseToString(response), getResponseHeader(), HttpStatus.OK);
	}

}

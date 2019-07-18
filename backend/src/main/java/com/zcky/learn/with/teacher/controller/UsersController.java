package com.zcky.learn.with.teacher.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.zcky.learn.with.teacher.constant.Constant;
import com.zcky.learn.with.teacher.mongoDb.model.Users;
import com.zcky.learn.with.teacher.mongoDb.repository.UsersRepository;
import com.zcky.learn.with.teacher.mongoDb.serializer.UsersSerializer;
import com.zcky.learn.with.teacher.security.JwtTokenUtil;

@RestController
public class UsersController extends BaseController{

	@Autowired
	private UsersRepository repository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public UserDetails getUserDetails(Users user) {
		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}
	
	@RequestMapping(value = "/api/users/sign_up", method = RequestMethod.POST)
	public ResponseEntity<String> signUp(@Valid @RequestBody Users user, HttpServletRequest request) throws Exception {
		Users dataUser = repository.findByUserName(user.getUsername());
		JsonObject response;
		if(dataUser == null) {
			try {
				repository.save(user);
				Optional<Users> userResponse = repository.findById(user.getStringId());
				Users users = userResponse.get();
				response = getSuccessResponse();
				response.add(Constant.RESPONSE, toJSONObject(users));
			} catch(Exception e) {
				response = getFailedResponse();
				response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
			}
		} else {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, Constant.USER_ALREDY_EXISTS_ERROR_MESSAGE);
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/users/sign_in", method = RequestMethod.POST)
	public ResponseEntity<String> signIn(@Valid @RequestBody Users user, HttpServletResponse responseHeader) throws Exception {
		JsonObject response;
		try {
			Users userResponse = repository.signIn(user.getUsername(), user.getPassword());
			if(userResponse != null) {
				response = getSuccessResponse();
				final String token = jwtTokenUtil.generateToken(this.getUserDetails(userResponse));
				response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Users.class, new UsersSerializer(), userResponse) );
				response.addProperty("token", token);
				response.addProperty("exp_date", getExpiredDate());
			}else {
				response = getFailedResponse();
				response.addProperty(Constant.ERROR_MESSAGE,Constant.USER_NOT_FOUND_ERROR_MESSAGE);
			}	
		}catch (Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/users/edit_user", method = RequestMethod.PUT)
	public ResponseEntity<String> editUsers(@Valid @RequestBody Users user, HttpServletRequest request){
		JsonObject response;
		try {
			user.setModified_date(this.getTimeUtility().getCurrentDate("dd/MM/yyyy HH:mm:ss"));
			repository.save(user);
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE,Constant.UPDATE_USER_PROFILE_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/users/find_all_users", method = RequestMethod.GET)
	public ResponseEntity<String> finddAllUsers(HttpServletRequest request){
		JsonObject response;
		try {
			List<Users> users = repository.findAll();
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONArrayWithSerializer(Users.class, new UsersSerializer(), users) );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/users/delete_user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUsers(@PathVariable String id, HttpServletRequest request){
		JsonObject response;
		try {
			repository.delete(repository.findById(id).get());
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE, Constant.DELETE_USER_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/users/get_user_detail/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getUserDetails(@PathVariable String id, HttpServletRequest request){
		JsonObject response;
		try {
			Users user = repository.findById(id).get();
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Users.class, new UsersSerializer(), user) );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

}
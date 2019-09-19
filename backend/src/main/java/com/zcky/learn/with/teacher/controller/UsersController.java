package com.zcky.learn.with.teacher.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.zcky.learn.with.teacher.constant.Constant;
import com.zcky.learn.with.teacher.model.request.UsersFacebookRequest;
import com.zcky.learn.with.teacher.model.request.UsersGoogleRequest;
import com.zcky.learn.with.teacher.model.request.UsersRequest;
import com.zcky.learn.with.teacher.mongoDb.model.Roles;
import com.zcky.learn.with.teacher.mongoDb.model.Users;
import com.zcky.learn.with.teacher.mongoDb.repository.RolesRepository;
import com.zcky.learn.with.teacher.mongoDb.repository.UsersRepository;
import com.zcky.learn.with.teacher.mongoDb.serializer.UsersAdminSerializer;
import com.zcky.learn.with.teacher.mongoDb.serializer.UsersSerializer;
import com.zcky.learn.with.teacher.mongoDb.serializer.UsersWithPasswordSerializer;
import com.zcky.learn.with.teacher.security.JwtTokenUtil;
import com.zcky.learn.with.teacher.task.AsyncService;
import com.zcky.learn.with.teacher.util.TimeUtility;

@RestController
public class UsersController extends BaseController{

	@Autowired
	private UsersRepository repository;
	
	@Autowired
	private RolesRepository rolesRepository;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
    private AsyncService service;
	
	public UserDetails getUserDetails(Users user) {
		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}
	
	@RequestMapping(value = "/api/users/sign_up", method = RequestMethod.POST)
	public ResponseEntity<String> signUp(@Valid @RequestBody UsersRequest userRequest, HttpServletRequest request) throws Exception {
		Users dataUser = repository.findByUserName(userRequest.getUsername());
		JsonObject response;
		if(dataUser == null) {
			try {
				Users user = new Users();
				user.fromObject(userRequest);
				Roles role = rolesRepository.findByName(userRequest.getType());
				user.setRoles(role);
				user.setStatus(true);
				repository.save(user);
				service.sendSuccessSignUpMailService(user);
				response = getSuccessResponse();
				response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Users.class, new UsersSerializer(), user));
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
				if(userResponse.isValidated() && userResponse.isStatus()) {
					response = getSuccessResponse();
					final String token = jwtTokenUtil.generateToken(this.getUserDetails(userResponse));
					response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Users.class, new UsersSerializer(), userResponse) );
					response.addProperty("token", token);
					response.addProperty("exp_date", getExpiredDate());
				}
				else if(!userResponse.isStatus()) {
					response = getFailedResponse();
					response.addProperty(Constant.ERROR_MESSAGE,Constant.USER_FOUND_BUT_INACTIVE_ERROR_MESSAGE);
				}
				else if(!userResponse.isValidated()){
					response = getFailedResponse();
					response.addProperty(Constant.ERROR_MESSAGE,Constant.USER_NOT_VALIDATED_SUCCESS_MESSAGE);
				}else {
					response = getFailedResponse();
					response.addProperty(Constant.ERROR_MESSAGE,Constant.USER_NOT_FOUND_ERROR_MESSAGE);
				}
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
	
	@RequestMapping(value = "/api/users/sign_in_with_facebook", method = RequestMethod.POST)
	public ResponseEntity<String> signInFacebook(@Valid @RequestBody UsersFacebookRequest userFacebookRequest, HttpServletResponse responseHeader) throws Exception {
		JsonObject response;
		try {
			Users userResponse = repository.findByFaceBookId(userFacebookRequest.getId());
			if(userResponse != null) {
				if(userResponse.isValidated() && userResponse.isStatus()) {
					response = getSuccessResponse();
					final String token = jwtTokenUtil.generateTokenWithFaceBookAccounts(userResponse.getFacebookId());
					response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Users.class, new UsersSerializer(), userResponse) );
					response.addProperty("token", token);
					response.addProperty("exp_date", getExpiredDate());
				}
				else if(!userResponse.isStatus()) {
					response = getFailedResponse();
					response.addProperty(Constant.ERROR_MESSAGE,Constant.USER_FOUND_BUT_INACTIVE_ERROR_MESSAGE);
				}
				else if(!userResponse.isValidated()){
					response = getFailedResponse();
					response.addProperty(Constant.ERROR_MESSAGE,Constant.USER_NOT_VALIDATED_SUCCESS_MESSAGE);
				}else {
					response = getFailedResponse();
					response.addProperty(Constant.ERROR_MESSAGE,Constant.USER_NOT_FOUND_ERROR_MESSAGE);
				}
			}else {
				Users userLogin = new Users();
				userLogin.setFirstname(userFacebookRequest.getName());
				userLogin.setFacebookId(userFacebookRequest.getId());
				Roles role = rolesRepository.findByName(userFacebookRequest.getType());
				userLogin.setRoles(role);
				userLogin.setStatus(true);
				userLogin.setValidated(true);
				repository.save(userLogin);
				
				response = getSuccessResponse();
				final String token = jwtTokenUtil.generateTokenWithFaceBookAccounts(userLogin.getFacebookId());
				response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Users.class, new UsersSerializer(), userLogin) );
				response.addProperty("token", token);
				response.addProperty("exp_date", getExpiredDate());
			}	
		}catch (Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	@RequestMapping(value = "/api/users/sign_in_with_google", method = RequestMethod.POST)
	public ResponseEntity<String> signInGoogle(@Valid @RequestBody UsersGoogleRequest userGoogleRequest, HttpServletResponse responseHeader) throws Exception {
		JsonObject response;
		try {
			Users userResponse = repository.findByGoogleId(userGoogleRequest.getId());
			if(userResponse != null) {
				if(userResponse.isValidated() && userResponse.isStatus()) {
					response = getSuccessResponse();
					final String token = jwtTokenUtil.generateTokenWithGoogleAccounts(userResponse.getGoogleId());
					response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Users.class, new UsersSerializer(), userResponse) );
					response.addProperty("token", token);
					response.addProperty("exp_date", getExpiredDate());
				}
				else if(!userResponse.isStatus()) {
					response = getFailedResponse();
					response.addProperty(Constant.ERROR_MESSAGE,Constant.USER_FOUND_BUT_INACTIVE_ERROR_MESSAGE);
				}
				else if(!userResponse.isValidated()){
					response = getFailedResponse();
					response.addProperty(Constant.ERROR_MESSAGE,Constant.USER_NOT_VALIDATED_SUCCESS_MESSAGE);
				}else {
					response = getFailedResponse();
					response.addProperty(Constant.ERROR_MESSAGE,Constant.USER_NOT_FOUND_ERROR_MESSAGE);
				}
			}else {
				Users userLogin = new Users();
				userLogin.setFirstname(userGoogleRequest.getFirstname());
				userLogin.setLastname(userGoogleRequest.getLastname());
				userLogin.setDisplay_name(userGoogleRequest.getDisplay_name());
				userLogin.setEmail(userGoogleRequest.getEmail());
				userLogin.setGoogleId(userGoogleRequest.getId());
				userLogin.setImageUrl(userGoogleRequest.getImage_url());
				Roles role = rolesRepository.findByName(userGoogleRequest.getType());
				userLogin.setRoles(role);
				userLogin.setStatus(true);
				userLogin.setValidated(true);
				repository.save(userLogin);
				
				response = getSuccessResponse();
				final String token = jwtTokenUtil.generateTokenWithGoogleAccounts(userLogin.getGoogleId());
				response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Users.class, new UsersSerializer(), userLogin) );
				response.addProperty("token", token);
				response.addProperty("exp_date", getExpiredDate());
			}	
		}catch (Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/users/edit_user", method = RequestMethod.PUT)
	public ResponseEntity<String> editUsers(@Valid @RequestBody UsersRequest userRequest, HttpServletRequest request){
		JsonObject response;
		try {
			Users user = new Users();
			user.fromObject(userRequest);
			TimeUtility util = new TimeUtility();
			user.setModified_date(util.getCurrentDate("dd/MM/yyyy HH:mm:ss"));
			repository.save(user);
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE,Constant.UPDATE_USER_PROFILE_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/users/validate/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> editUsers(@PathVariable String id, HttpServletRequest request){
		JsonObject response;
		try {
			Users user = repository.findById(id).get();
			user.setValidated(true);
			repository.save(user);
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE,Constant.SUCCESS_VALIDATED_USER_PROFILE_SUCCESS_MESSAGE);
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
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Users.class, new UsersWithPasswordSerializer(), user) );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	/* user-admin END POINT 
	 * 
	 */
	@RequestMapping(value = "/api/admin/user/create", method = RequestMethod.POST)
	public ResponseEntity<String> create(@Valid @RequestBody UsersRequest userRequest, HttpServletRequest request) throws Exception {
		Users dataUser = repository.findByUserName(userRequest.getUsername());
		JsonObject response;
		if(dataUser == null) {
			try {
				Users user = new Users();
				user.fromObject(userRequest);
				repository.save(user);
				response = getSuccessResponse();
				response.add(Constant.RESPONSE, toJSONObject(user));
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
	
	@RequestMapping(value = "/api/admin/user/edit", method = RequestMethod.PUT)
	public ResponseEntity<String> editAdminUsers(@Valid @RequestBody UsersRequest userRequest, HttpServletRequest request){
		JsonObject response;
		try {
			TimeUtility util = new TimeUtility();
			Users user = new Users();
			user.fromObject(userRequest);
			user.setModified_date(util.getCurrentDate("dd/MM/yyyy HH:mm:ss"));
			repository.save(user);
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE,Constant.UPDATE_USER_PROFILE_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/admin/user/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteUserAdmin(@PathVariable String id, HttpServletRequest request){
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
	
	@RequestMapping(value = "/api/admin/user/find_all", method = RequestMethod.GET)
	public ResponseEntity<String> finddAllUsersAdmin(@RequestParam(value="roleId", required=false) String roleId, @RequestParam(value="page", required=false) String page,HttpServletRequest request){
		JsonObject response;
		try {
			List<Users> users = new ArrayList<>();
			if(page!= null) {
				Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").descending());
				if(roleId != null) {
					users = repository.findByRoleId( new ObjectId(roleId), pageableRequest);
				}else {
					Page<Users> pageUsers = repository.findAll(pageableRequest);
					users = pageUsers.getContent();
				}
			}else {
				if(roleId != null) {
					users = repository.findByRoleId( new ObjectId(roleId));
				}else {
					users = repository.findAll();
				}
			}
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONArrayWithSerializer(Users.class, new UsersAdminSerializer(), users) );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/admin/user/detail/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getUserDetailsAdmin(@PathVariable String id, HttpServletRequest request){
		JsonObject response;
		try {
			Users user = repository.findById(id).get();
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Users.class, new UsersAdminSerializer(), user) );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
}
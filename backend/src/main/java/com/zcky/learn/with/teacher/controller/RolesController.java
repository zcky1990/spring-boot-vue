package com.zcky.learn.with.teacher.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.zcky.learn.with.teacher.constant.Constant;
import com.zcky.learn.with.teacher.model.request.RolesRequest;
import com.zcky.learn.with.teacher.mongoDb.model.Category;
import com.zcky.learn.with.teacher.mongoDb.model.Roles;
import com.zcky.learn.with.teacher.mongoDb.repository.RolesRepository;
import com.zcky.learn.with.teacher.mongoDb.serializer.RolesSerializer;

@RestController
public class RolesController extends BaseController {
	@Autowired
	private RolesRepository repository;

	@RequestMapping(value = "/api/roles/create", method = RequestMethod.POST)
	public ResponseEntity<String> create(@Valid @RequestBody Roles roles, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			repository.save(roles);
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Roles.class, new RolesSerializer(), roles)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/roles/edit", method = RequestMethod.PUT)
	public ResponseEntity<String> edit(@Valid @RequestBody RolesRequest roles, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Roles updateRoles = new Roles();
			updateRoles.fromObject(roles);
			repository.save(updateRoles);
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE,Constant.UPDATE_ROLES_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/roles/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> edit(@PathVariable String id, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Optional<Roles> responseRoles = repository.findById(id);
			Roles roles = responseRoles.get();
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Roles.class, new RolesSerializer(), roles)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/roles/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable String id, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			repository.delete(repository.findById(id).get());
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE, Constant.DELETE_ROLES_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	
	@RequestMapping(value = "/api/roles/get_roles_list", method = RequestMethod.GET)
	public ResponseEntity<String> getRolesList(@RequestParam(value="status", required=true) Boolean status, @RequestParam(value="page", required=false) String page, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			List<Roles> roles  = new ArrayList<>();
			if(page!= null) {
				Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").descending());
				roles = repository.findAllByStatus( status, pageableRequest);
			}else {
				roles = repository.findAllByStatus(status);
			}
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONArrayWithSerializer(Roles.class, new RolesSerializer(), roles)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

}

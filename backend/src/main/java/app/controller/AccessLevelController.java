package app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import app.constants.Constant;
import app.model.request.AccessLevelRequest;
import app.mongo.model.AccessLevel;
import app.repository.AccessLevelRepository;
import app.serializer.AccessLevelSerializer;

@RestController
public class AccessLevelController extends BaseController {
	@Autowired
	private AccessLevelRepository repository;

	@RequestMapping(value = "/api/access_level/create", method = RequestMethod.POST)
	public ResponseEntity<String> create(@Valid @RequestBody AccessLevel accessLevel, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			repository.save(accessLevel);
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(AccessLevel.class, new AccessLevelSerializer(), accessLevel)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/access_level/edit", method = RequestMethod.PUT)
	public ResponseEntity<String> edit(@Valid @RequestBody AccessLevelRequest accessLevelRequest, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			AccessLevel accessLevel = new AccessLevel();
			accessLevel.fromObject(accessLevelRequest);
			repository.save(accessLevel);
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE,Constant.UPDATE_ROLES_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/access_level/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> edit(@PathVariable String id, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Optional<AccessLevel> accessLevel = repository.findById(id);
			AccessLevel accLevel = accessLevel.get();
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(AccessLevel.class, new AccessLevelSerializer(), accLevel)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/access_level/delete/{id}", method = RequestMethod.DELETE)
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

	
	@RequestMapping(value = "/api/access_level/get_access_level_list", method = RequestMethod.GET)
	public ResponseEntity<String> getRolesList(@RequestParam(value="page", required=false) String page, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			List<AccessLevel> accessLevel  = new ArrayList<>();
			if(page!= null) {
				Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").descending());
				Page<AccessLevel> pages = repository.findAll(pageableRequest);
				accessLevel = pages.getContent();
			}else {
				accessLevel = repository.findAll();
			}
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONArrayWithSerializer(AccessLevel.class, new AccessLevelSerializer(), accessLevel)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
}

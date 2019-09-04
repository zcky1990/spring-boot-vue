package com.zcky.learn.with.teacher.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.zcky.learn.with.teacher.model.request.CategoryRequest;
import com.zcky.learn.with.teacher.mongoDb.model.Category;
import com.zcky.learn.with.teacher.mongoDb.repository.CategoryRepository;
import com.zcky.learn.with.teacher.mongoDb.serializer.CategorySerializer;

@RestController
public class CategoryController extends BaseController {
	@Autowired
	private CategoryRepository repository;

	@RequestMapping(value = "/api/category/create", method = RequestMethod.POST)
	public ResponseEntity<String> create(@Valid @RequestBody Category category, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			repository.save(category);
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Category.class, new CategorySerializer(), category)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/category/edit", method = RequestMethod.PUT)
	public ResponseEntity<String> edit(@Valid @RequestBody CategoryRequest categoryReq, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Category category = new Category();
			category.fromObject(categoryReq);
			repository.save(category);
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE,Constant.UPDATE_CATEGORY_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/category/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> edit(@PathVariable String id, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Optional<Category> responseCategory = repository.findById(id);
			Category category = responseCategory.get();
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Category.class, new CategorySerializer(), category)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/category/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable String id, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			repository.delete(repository.findById(id).get());
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE, Constant.DELETE_CATEGORY_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

	
	@RequestMapping(value = "/api/category/get_category_list", method = RequestMethod.GET)
	public ResponseEntity<String> getCategoryList(@RequestParam(value="type", required=false) String type,@RequestParam(value="status", required=true) Boolean status, @RequestParam(value="page", required=false) String page, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			List<Category> categories  = new ArrayList<>();
			if(page!= null) {
				Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").descending());
				
				if(type != null) {
					categories = repository.findAllByTypeAndStatus(type, status, pageableRequest);
				}else{
					categories = repository.findAllByStatus(status, pageableRequest);
				}
			}else {
				if(type != null) {
					categories = repository.findAllByTypeAndStatus(type, status);
				}else {
					categories = repository.findAllByStatus(status);
				}
			}
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONArrayWithSerializer(Category.class, new CategorySerializer(), categories)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

}

package com.zcky.learn.with.teacher.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.bson.types.ObjectId;
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
import com.zcky.learn.with.teacher.constant.Constant;
import com.zcky.learn.with.teacher.model.ArticleList;
import com.zcky.learn.with.teacher.model.request.ArticleRequest;
import com.zcky.learn.with.teacher.mongoDb.model.Article;
import com.zcky.learn.with.teacher.mongoDb.model.Users;
import com.zcky.learn.with.teacher.mongoDb.repository.ArticleRepository;
import com.zcky.learn.with.teacher.mongoDb.repository.UsersRepository;
import com.zcky.learn.with.teacher.mongoDb.serializer.ArticleSerializer;
import com.zcky.learn.with.teacher.mongoDb.serializer.PageArticleSerializer;
import com.zcky.learn.with.teacher.util.TimeUtility;

@RestController
public class ArticleController extends BaseController {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private UsersRepository userRepository;
	
	@RequestMapping(value = "/api/article/get_article_list", method = RequestMethod.GET)
	public ResponseEntity<String> getArticleList(@RequestParam(value="page", required=false) String page, HttpServletRequest request) throws Exception {
		JsonObject response = new JsonObject();
		try {
			Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").descending());
			Page<Article> articleList = articleRepository.findAll(pageableRequest);
			response = getSuccessResponse();
			ArticleList article = gson.fromJson(ObjectToJSON(articleList), ArticleList.class);
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(ArticleList.class, new PageArticleSerializer(), article));
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString() , getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/article/search", method = RequestMethod.GET)
	public ResponseEntity<String> getArticleList(@RequestParam(value="query", required=false) String query,@RequestParam(value="page", required=false) String page, HttpServletRequest request) throws Exception {
		JsonObject response = new JsonObject();
		try {
			Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").descending());
			Page<Article> articleList = articleRepository.findArticleByRegexpTitleOrSlug(query,pageableRequest);
			response = getSuccessResponse();
			ArticleList article = gson.fromJson(ObjectToJSON(articleList), ArticleList.class);
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(ArticleList.class, new PageArticleSerializer(), article));
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString() , getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/article/add_article", method = RequestMethod.POST)
	public ResponseEntity<String> addArticle(@Valid @RequestBody ArticleRequest articleRequest, HttpServletRequest request) throws Exception {
		String auth = request.getHeader("x-uid");
		Users user = userRepository.findBy_id(new ObjectId(auth));
		JsonObject response;
		if(user != null) {
			Article article = new Article();
			article.fromObject(articleRequest);
			article.setAuthor(user);
			try {
				articleRepository.save(article);
				response = getSuccessResponse();
				response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Article.class, new ArticleSerializer(), article)  );
			} catch(Exception e) {
				response = getFailedResponse();
				response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
			}
		}else {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, Constant.USER_NOT_FOUND_ERROR_MESSAGE);
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/article/update_article", method = RequestMethod.PUT)
	public ResponseEntity<String> updateArticle(@Valid @RequestBody ArticleRequest articleRequest, HttpServletRequest request) throws Exception {
		String auth = request.getHeader("x-uid");
		TimeUtility util = new TimeUtility();
		Users user = userRepository.findBy_id(new ObjectId(auth));
		JsonObject response;
		if(user != null) {
			Article article = new Article();
			article.fromObject(articleRequest);
			article.setAuthor(user);
			article.setModified_date(util.getCurrentDate("dd/MM/yyyy HH:mm:ss"));
			try {
				articleRepository.save(article);
				response = getSuccessResponse();
				response.addProperty(Constant.RESPONSE,Constant.UPDATE_ARTICLE_SUCCESS_MESSAGE);
			} catch(Exception e) {
				response = getFailedResponse();
				response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
			}
		}else {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, Constant.USER_NOT_FOUND_ERROR_MESSAGE);
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/article/get_article/{slug}", method = RequestMethod.GET)
	public ResponseEntity<String> getArticle(@PathVariable String slug, HttpServletRequest request) throws Exception {
		JsonObject response;
		try {
			Article article = articleRepository.findBySlug(slug);
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONObjectWithSerializer(Article.class, new ArticleSerializer(), article)  );
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString() , getResponseHeader(), HttpStatus.OK);
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/api/article/get_list_user_article/{page}", method = RequestMethod.GET)
	public ResponseEntity<String> getListUserArticle(@PathVariable String page, HttpServletRequest request) throws Exception {
		String auth = request.getHeader("x-uid");
		Users user = userRepository.findBy_id(new ObjectId(auth));
		JsonObject response;
		int pages = Integer.parseInt(page);
		int startPage = getStartPage(pages);
		int endPage = getEndPage(startPage);
		if(user != null) {
			try {
				List<Article> article = articleRepository.findAllArticleByUserPaging(new ObjectId(auth), new PageRequest(startPage, endPage));
				response = getSuccessResponse();
				response.add(Constant.RESPONSE, toJSONArrayWithSerializer(Article.class, new ArticleSerializer(), article)  );
			} catch(Exception e) {
				response = getFailedResponse();
				response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
			}
		}else {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, Constant.USER_NOT_FOUND_ERROR_MESSAGE);
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/article/delete_article/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteArticle(@PathVariable String id, HttpServletRequest request){
		JsonObject response;
		try {
			articleRepository.delete(articleRepository.findById(id).get());
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE, Constant.DELETE_ARTICLE_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	
}

package com.zcky.learn.with.teacher.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.zcky.learn.with.teacher.constant.Constant;
import com.zcky.learn.with.teacher.mongoDb.model.Article;
import com.zcky.learn.with.teacher.mongoDb.model.Users;
import com.zcky.learn.with.teacher.mongoDb.repository.ArticleRepository;
import com.zcky.learn.with.teacher.mongoDb.repository.UsersRepository;
import com.zcky.learn.with.teacher.mongoDb.serializer.ArticleSerializer;

@RestController
public class ArticleController extends BaseController {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private UsersRepository userRepository;

	@RequestMapping(value = "/article/add_article", method = RequestMethod.POST)
	public ResponseEntity<String> addArticle(@Valid @RequestBody Article article, HttpServletRequest request) throws Exception {
		String auth = request.getHeader("uid");
		Users user = userRepository.findBy_id(new ObjectId(auth));
		JsonObject response;
		if(user != null) {
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
	
	@RequestMapping(value = "/article/update_article", method = RequestMethod.PUT)
	public ResponseEntity<String> updateArticle(@Valid @RequestBody Article article, HttpServletRequest request) throws Exception {
		String auth = request.getHeader("uid");
		Users user = userRepository.findBy_id(new ObjectId(auth));
		JsonObject response;
		if(user != null) {
			article.setAuthor(user);
			article.setModified_date(this.getTimeUtility().getCurrentDate("dd/MM/yyyy HH:mm:ss"));
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
	
	@RequestMapping(value = "/article/get_article/{slug}", method = RequestMethod.GET)
	public ResponseEntity<String> getArticle(@PathVariable String slug, HttpServletRequest request) throws Exception {
		String auth = request.getHeader("uid");
		Users user = userRepository.findBy_id(new ObjectId(auth));
		JsonObject response;
		if(user != null) {
			try {
				Article article = articleRepository.findByUrl(slug);
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
		return new ResponseEntity<String>( response.toString() , getResponseHeader(), HttpStatus.OK);
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/article/get_list_user_article/{page}", method = RequestMethod.GET)
	public ResponseEntity<String> getListUserArticle(@PathVariable String page, HttpServletRequest request) throws Exception {
		String auth = request.getHeader("uid");
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
	
	@RequestMapping(value = "/article/delete_article/{id}", method = RequestMethod.DELETE)
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

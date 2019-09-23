package app.controller;

import java.util.List;

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

import app.constants.Constant;
import app.model.request.BookmarksArticleRequest;
import app.mongo.model.BookmarksArticle;
import app.mongo.model.Users;
import app.repository.ArticleBookmarksRepository;
import app.repository.UsersRepository;
import app.serializer.BookmarksArticleSerializer;

@RestController
public class BookmarksArticleController extends BaseController {
	@Autowired
	private ArticleBookmarksRepository repository;

	@Autowired
	private UsersRepository userRepository;

	@RequestMapping(value = "/api/bookmarks/get_bookmarks_article_list", method = RequestMethod.GET)
	public ResponseEntity<String> getArticleList(@RequestParam(value="page", required=false) String page, HttpServletRequest request) throws Exception {
		JsonObject response = new JsonObject();
		String auth = request.getHeader("x-uid");
		Users user = userRepository.findBy_id(new ObjectId(auth));
		if(user != null) {
			try {
				Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").ascending());
				List<BookmarksArticle> bookmarksList = repository.findByUserId(user.get_id(), pageableRequest);
				response = getSuccessResponse();
				response.add(Constant.RESPONSE, toJSONArrayWithSerializer(BookmarksArticle.class, new BookmarksArticleSerializer(), bookmarksList));
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

	@RequestMapping(value = "/api/bookmarks/create", method = RequestMethod.POST)
	public ResponseEntity<String> addArticle(@Valid @RequestBody BookmarksArticleRequest bookmarkRequest, HttpServletRequest request) throws Exception {
		String auth = request.getHeader("x-uid");
		Users user = userRepository.findBy_id(new ObjectId(auth));
		JsonObject response;
		if(user != null) {
			BookmarksArticle bookmarksArticle = new BookmarksArticle();
			bookmarksArticle.fromObject(bookmarkRequest);
			try {
				repository.save(bookmarksArticle);
				response = getSuccessResponse();
				response.add(Constant.RESPONSE, toJSONObjectWithSerializer(BookmarksArticle.class, new BookmarksArticleSerializer(), bookmarksArticle)  );
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

	@RequestMapping(value = "/api/bookmarks/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteArticle(@PathVariable String id, HttpServletRequest request){
		JsonObject response;
		try {
			repository.delete(repository.findById(id).get());
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE, Constant.DELETE_BOOKMARK_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
}

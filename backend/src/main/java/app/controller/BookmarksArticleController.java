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
import app.mongo.model.FollowAuthors;
import app.mongo.model.Users;
import app.repository.ArticleBookmarksRepository;
import app.repository.FollowAuthorsRepository;
import app.repository.UsersRepository;
import app.serializer.BookmarksArticleSerializer;

@RestController
@RequestMapping("/api")
public class BookmarksArticleController extends BaseController {
	@Autowired
	private ArticleBookmarksRepository repository;

	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private FollowAuthorsRepository followRepository;
	

	@RequestMapping(value = "/bookmarks/get_bookmarks_article_list", method = RequestMethod.GET)
	public ResponseEntity<String> getArticleList(@RequestParam(value="page", required=true) String page, HttpServletRequest request) throws Exception {
		JsonObject response = new JsonObject();
		String auth = request.getHeader("x-uid");
		Users user = userRepository.findBy_id(new ObjectId(auth));
		if(user != null) {
			try {
				Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").ascending());
				List<BookmarksArticle> bookmarksList = repository.findBookmarkByUserId(user.get_id(), pageableRequest);
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

	@RequestMapping(value = "/bookmarks/create", method = RequestMethod.POST)
	public ResponseEntity<String> addArticle(@Valid @RequestBody BookmarksArticleRequest bookmarkRequest, HttpServletRequest request) throws Exception {
		String auth = request.getHeader("x-uid");
		Users user = userRepository.findBy_id(new ObjectId(auth));
		JsonObject response;
		if(user != null) {
			try {
				BookmarksArticle repo = repository.findByUserIdAndArticleId(new ObjectId(bookmarkRequest.getUserId()), new ObjectId(bookmarkRequest.getArticleId()));
				if(repo == null) {
					BookmarksArticle bookmarksArticle = new BookmarksArticle();
					bookmarksArticle.fromObject(bookmarkRequest);
					repository.save(bookmarksArticle);
					response = getSuccessResponse();
					response.add(Constant.RESPONSE, toJSONObjectWithSerializer(BookmarksArticle.class, new BookmarksArticleSerializer(), bookmarksArticle)  );
				}else {
					response = getFailedResponse();
					response.addProperty(Constant.ERROR_MESSAGE, Constant.BOOKMARKS_ALREADY_EXISTS_ERROR_MESSAGE);
				}
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

	@RequestMapping(value = "/bookmarks/delete/{id}", method = RequestMethod.DELETE)
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
	
	@RequestMapping(value = "/follow/authors/{id}", method = RequestMethod.POST)
	public ResponseEntity<String> followAuthors(@PathVariable String id, HttpServletRequest request){
		JsonObject response;
		String auth = request.getHeader("x-uid");
		Users user = userRepository.findBy_id(new ObjectId(auth));
		try {
			FollowAuthors fAuthors = new FollowAuthors();
			Users authors = new Users();
			authors.set_id(new ObjectId(id));
			fAuthors.setUser(user);
			fAuthors.setAuthor(authors);
			followRepository.save(fAuthors);
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE, Constant.SUCCESS_FOLLOW_AUHTORS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/unfollow/authors/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> unFollowAuthors(@PathVariable String id, HttpServletRequest request){
		JsonObject response;
		String auth = request.getHeader("x-uid");
		try {
			FollowAuthors fAuthors = followRepository.findByUserIdAndAuhtorsId(new ObjectId(auth) , new ObjectId(id));
			followRepository.delete(fAuthors);
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE, Constant.DELETE_UNFOLLOW_AUHTORS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
}

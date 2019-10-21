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
import app.model.request.CommentArticle;
import app.mongo.model.ArticleComment;
import app.mongo.model.Users;
import app.repository.ArticleCommentRepository;
import app.repository.UsersRepository;
import app.serializer.ArticleCommentSerializer;

@RestController
@RequestMapping("/api")
public class ArticleCommentController extends BaseController {
	
	@Autowired
	private ArticleCommentRepository articleCommentRepository;
	
	@Autowired
	private UsersRepository userRepository;
	
	
	@RequestMapping(value = "/article/get_comment/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getListComment(@PathVariable String id,@RequestParam(value="page", required=false) String page, HttpServletRequest request) throws Exception {
		JsonObject response = new JsonObject();
		Pageable pageableRequest = PageRequest.of(Integer.parseInt(page), 10, Sort.by("_id").descending());
		try {
		List<ArticleComment> articleComment = articleCommentRepository.findAllCommentArticleByArticleId(new ObjectId(id), pageableRequest);
			response = getSuccessResponse();
			response.add(Constant.RESPONSE, toJSONArrayWithSerializer(ArticleComment.class, new ArticleCommentSerializer(), articleComment)  );
		}
		catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/article/add_comment", method = RequestMethod.POST)
	public ResponseEntity<String> addComment(@Valid @RequestBody CommentArticle commentArticle, HttpServletRequest request) throws Exception {
		String auth = request.getHeader("x-uid");
		JsonObject response;
		Users user = userRepository.findBy_id(new ObjectId(auth));
		if(user != null) {
			ArticleComment articleComment = new ArticleComment();
			articleComment.fromObject(commentArticle);
			articleComment.setAuthor(user);
			try {
				articleCommentRepository.save(articleComment);
				response = getSuccessResponse();
				response.add(Constant.RESPONSE, toJSONObjectWithSerializer(ArticleComment.class, new ArticleCommentSerializer(), articleComment)  );
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

	@RequestMapping(value = "/article/update_article_comment", method = RequestMethod.PUT)
	public ResponseEntity<String> updateArticlComment(@Valid @RequestBody CommentArticle commentArticle, HttpServletRequest request) throws Exception {
		String auth = request.getHeader("x-uid");
		Users user = userRepository.findBy_id(new ObjectId(auth));
		JsonObject response;
		if(user != null) {
			ArticleComment articleComment = new ArticleComment();
			articleComment.fromObject(commentArticle);
			articleComment.setAuthor(user);
			try {
				articleCommentRepository.save(articleComment);
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
	
	@RequestMapping(value = "/article/delete_article_comment/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteArticleComment(@PathVariable String id, HttpServletRequest request){
		JsonObject response;
		try {
			articleCommentRepository.delete(articleCommentRepository.findById(id).get());
			response = getSuccessResponse();
			response.addProperty(Constant.RESPONSE, Constant.DELETE_ARTICLE_SUCCESS_MESSAGE);
		} catch(Exception e) {
			response = getFailedResponse();
			response.addProperty(Constant.ERROR_MESSAGE, e.getMessage().toString());
		}
		return new ResponseEntity<String>( response.toString(), getResponseHeader(), HttpStatus.OK);
	}

}

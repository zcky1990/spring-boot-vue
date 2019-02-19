package com.projects.springboot.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import com.projects.springboot.models.Comment;
import com.projects.springboot.models.Comments;
import com.projects.springboot.models.Reply;
import com.projects.springboot.models.Response;
import com.projects.springboot.models.UserComment;
import com.projects.springboot.models.UserSession;
import com.projects.springboot.models.Users;

@RestController()
@RequestMapping("/api")
public class AdminController extends BaseController{
	
	@RequestMapping(value = "/is_session_active", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> isSessionActive(@RequestHeader MultiValueMap<String,String> headers){
		UserSession userSession = new UserSession();
		return new ResponseEntity<String>(convertResponseToString(userSession), getResponseHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/sign_up", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> signUp(@RequestBody Users user,@RequestHeader MultiValueMap<String,String> headers){
		Response response = new Response();
		response.setErrorMessage("failed to login");
		return new ResponseEntity<String>(this.convertResponseToString(response), getResponseHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/get_comments", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getComment(@RequestHeader MultiValueMap<String,String> headers){
		UserComment userreply = new UserComment();
		userreply.setImageUrl("https://bulma.io/images/placeholders/96x96.png");
		userreply.setUserId("2");
		userreply.setUserName("test 2");
		
		Reply reply = new Reply();
		reply.setUser(userreply);
		reply.setCommentId("1");
		reply.setReplyId("1");
		reply.setReply("ini adalah reply contoh 1");
		
		Reply reply2 = new Reply();
		reply2.setUser(userreply);
		reply2.setCommentId("1");
		reply2.setReplyId("2");
		reply2.setReply("ini adalah reply contoh 2");
		
		UserComment usercomment = new UserComment();
		usercomment.setImageUrl("https://bulma.io/images/placeholders/96x96.png");
		usercomment.setUserId("1");
		usercomment.setUserName("test 1");
		
		Comment comment = new Comment();
		comment.setUser(usercomment);
		comment.setCommentId("1");
		comment.setPostId("post-1");
		comment.setComment("contoh comment 1");
		
		ArrayList<Reply> replyList = new ArrayList<Reply> ();
		replyList.add(reply);
		replyList.add(reply2);
		
		comment.setReplyList(replyList);
		
		Comment comment2 = new Comment();
		comment2.setUser(usercomment);
		comment2.setCommentId("2");
		comment2.setPostId("post-1");
		comment2.setComment("contoh comment 2");
		
		Comments commentList = new Comments();
		ArrayList<Comment> commentListExample = new ArrayList<Comment>();
		commentListExample.add(comment);
		commentListExample.add(comment2);
		
		commentList.setCommentList(commentListExample);
		
		Response response = new Response();
		response.setResponse(this.convertResponseToString(commentList));
		return new ResponseEntity<String>(this.convertResponseToString(response), getResponseHeader(), HttpStatus.OK);
	}

}

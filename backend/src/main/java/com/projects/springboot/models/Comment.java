package com.projects.springboot.models;

import java.util.ArrayList;

public class Comment {
	private String postId;
	private String commentId;
	private String comment;
	private ArrayList<Reply> replyList;
	private UserComment User;

	public UserComment getUser() {
		return User;
	}
	public void setUser(UserComment user) {
		User = user;
	}
	public ArrayList<Reply> getReplyList() {
		return replyList;
	}
	public void setReplyList(ArrayList<Reply> replyList) {
		this.replyList = replyList;
	}
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}

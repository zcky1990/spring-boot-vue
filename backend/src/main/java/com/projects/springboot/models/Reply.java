package com.projects.springboot.models;

public class Reply {
	private String commentId;
	private UserComment User;
	private String replyId;
	private String reply;
	
	public UserComment getUser() {
		return User;
	}
	public void setUser(UserComment user) {
		User = user;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getReplyId() {
		return replyId;
	}
	public void setReplyId(String replyId) {
		this.replyId = replyId;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	
	

}

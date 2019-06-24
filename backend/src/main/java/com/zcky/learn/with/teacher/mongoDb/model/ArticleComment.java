package com.zcky.learn.with.teacher.mongoDb.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class ArticleComment {
	@Id
	private ObjectId _id;
	
	@DBRef
	private Users user;
	
	@DBRef
	private Article article;
	
	private String content;
	
	public String getStringId() {
		return _id.toHexString();
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public Users getAuthor() {
		return user;
	}

	public void setAuthor(Users user) {
		this.user = user;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}

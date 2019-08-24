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
	private String comment;
	private String modified_date;
	
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getModified_date() {
		return modified_date;
	}

	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}
}

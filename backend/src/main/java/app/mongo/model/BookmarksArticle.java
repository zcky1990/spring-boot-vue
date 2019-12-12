package app.mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import app.model.request.BookmarksArticleRequest;

public class BookmarksArticle {
	@Indexed
	@Id
	private ObjectId _id;

	@DBRef
	private Users users;

	@DBRef
	private Article article;

	public String getStringId() {
		return _id.toHexString();
	}
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public Users getUser() {
		return users;
	}
	public void setUser(Users user) {
		this.users = user;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public void fromObject(BookmarksArticleRequest request) {
		if(request.getId() != null) {
			this.set_id(new ObjectId(request.getId()));
		}
		if(request.getArticleId() != null) {
			Article article = new Article();
			article.set_id(new ObjectId(request.getArticleId()));
			this.setArticle(article);
		}
		if(request.getUserId()!= null) {
			Users user = new Users();
			user.set_id(new ObjectId(request.getUserId()));
			this.setUser(user);
		}
	}
}

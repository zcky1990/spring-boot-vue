package app.model.response;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import app.mongo.model.Article;
import app.mongo.model.BookmarksArticle;
import app.mongo.model.Category;
import app.mongo.model.Users;

public class ArticleResponse {
	@Id
	private ObjectId _id;
	private Users author;
	private List<Category> categoryArticle;
	private String article_title;
	private String article_permalink;
	private String article_content;
	private String type;
	private String status;
	private String slug;
	private String modified_date;
	private List<String> reference_list;
	private boolean isBookmarked;
	private boolean isFollowed;
	
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
		return author;
	}
	public void setAuthor(Users author) {
		this.author = author;
	}
	public String getArticle_title() {
		return article_title;
	}
	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}
	public String getArticle_content() {
		return article_content;
	}
	public void setArticle_content(String article_content) {
		this.article_content = article_content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getModified_date() {
		return modified_date;
	}
	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}
	public String getArticle_permalink() {
		return article_permalink;
	}
	public void setArticle_permalink(String article_permalink) {
		this.article_permalink = article_permalink;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public List<Category> getCategoryArticle() {
		return categoryArticle;
	}
	public void setCategoryArticle(List<Category> categoryArticle) {
		this.categoryArticle = categoryArticle;
	}
	public List<String> getReference_list() {
		return reference_list;
	}
	public void setReference_list(List<String> reference_list) {
		this.reference_list = reference_list;
	}
	public boolean getIsFollowed() {
		return isFollowed;
	}
	public boolean isBookmarked() {
		return isBookmarked;
	}
	public void setBookmarked(boolean isBookmarked) {
		this.isBookmarked = isBookmarked;
	}
	public void setFollowed(boolean isFollowed) {
		this.isFollowed = isFollowed;
	}
	public void fromArticle(Article article) {
		this.set_id(article.get_id());
		this.setArticle_content(article.getArticle_content());
		this.setArticle_permalink(article.getArticle_permalink());
		this.setArticle_title(article.getArticle_title());
		this.setAuthor(article.getAuthor());
		this.setCategoryArticle(article.getCategoryArticle());
		this.setReference_list(article.getReference_list());
		this.setSlug(article.getSlug());
		this.setStatus(article.getStatus());
		this.setType(article.getType());
	}
}

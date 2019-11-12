package app.model.request;

import java.util.List;

public class ArticleRequest {
	private String id;
	private String authorId;
	private List<CategoryRequest> categoryArticle;
	private String article_title;
	private String article_permalink;
	private String article_content;
	private String type;
	private String status;
	private String slug;
	private String modified_date;
	private List<String> reference_list;
	private Boolean isPublish;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public List<CategoryRequest> getCategoryArticle() {
		return categoryArticle;
	}
	public void setCategoryArticle(List<CategoryRequest> categoryArticle) {
		this.categoryArticle = categoryArticle;
	}
	public String getArticle_title() {
		return article_title;
	}
	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}
	public String getArticle_permalink() {
		return article_permalink;
	}
	public void setArticle_permalink(String article_permalink) {
		this.article_permalink = article_permalink;
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
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getModified_date() {
		return modified_date;
	}
	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}
	public List<String> getReference_list() {
		return reference_list;
	}
	public void setReference_list(List<String> reference_list) {
		this.reference_list = reference_list;
	}
	public Boolean getIsPublish() {
		return isPublish;
	}
	public void setIsPublish(Boolean isPublish) {
		this.isPublish = isPublish;
	}
}

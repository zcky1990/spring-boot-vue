package app.mongo.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import app.model.request.ArticleRequest;

public class Article {
	@Id
	private ObjectId _id;
	
	@DBRef
	private Users author;
	@DBRef
	private List<Category> categoryArticle;
	private String article_title;
	private String article_permalink;
	private String article_content;
	private String article_short_content;
	private String type;
	private String status;
	private String slug;
	private String modified_date;
	private List<String> reference_list;
	private String imageHeader;
	
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
	public String getArticle_short_content() {
		return article_short_content;
	}
	public void setArticle_short_content(String article_short_content) {
		this.article_short_content = article_short_content;
	}
	public String getImageHeader() {
		return imageHeader;
	}
	public void setImageHeader(String imageHeader) {
		this.imageHeader = imageHeader;
	}
	
	public void setContentShort(String content) {
		String shortContent = "";
		int startPre = content.indexOf("<pre>");
		shortContent = content.substring(0, startPre);
		Document document = Jsoup.parse(content);
		shortContent = document.text();
		if(shortContent.length() > 150) {
			shortContent = shortContent.substring(0, 150)+"...";
		}
		this.setArticle_short_content(shortContent);
	}
	
	public void setHeaderImageFromContent(String articleContent) {
		String image = "";
		if(articleContent.contains("<figure")) {
			int start = articleContent.indexOf("<figure");
			int end = articleContent.indexOf("</figure>")+9;
			image = articleContent.substring(start, end);
			articleContent = articleContent.replace(image, "");

			int index = image.indexOf("src=")+5;
			int endIndex = 0;
			if(image.contains("<figcaption>")) {
				endIndex = image.indexOf("<figcaption>")-2;
			}else {
				endIndex = image.indexOf("</figure>")-2;
			}
			String imgSrc =image.substring(index, endIndex);
			this.setImageHeader(imgSrc);
		}
	}
	
	public void fromObject(ArticleRequest request) {
		if(request.getId() != null) {
			this.set_id(new ObjectId(request.getId()));
		}
		this.setArticle_content(request.getArticle_content());
		this.setArticle_permalink(request.getArticle_permalink());
		this.setArticle_title(request.getArticle_title());
		if(!request.getCategoryArticle().isEmpty() ) {
			List<Category> listCategory = new ArrayList<>();
			for(int i = 0 ; i < request.getCategoryArticle().size(); i++) {
				Category category = new Category();
				category.set_id(new ObjectId(request.getCategoryArticle().get(i).getId()));
				listCategory.add(category);
			}
			this.setCategoryArticle(listCategory);
		}		
		this.setSlug(request.getSlug());
		this.setStatus(request.getStatus());
		this.setType(request.getType());
		this.setReference_list(request.getReference_list());
		this.setContentShort(request.getArticle_content());
		this.setHeaderImageFromContent(request.getArticle_content());
	}
}

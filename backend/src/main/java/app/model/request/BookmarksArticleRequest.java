package app.model.request;


public class BookmarksArticleRequest {
	private String id;
	private String userId;
	private String articleId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleID) {
		this.articleId = articleID;
	}
}

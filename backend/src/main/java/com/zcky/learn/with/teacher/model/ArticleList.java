package com.zcky.learn.with.teacher.model;

import java.util.List;

public class ArticleList {
	private Integer total;
	private List<Article> content;
	private Pageable page;
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<Article> getContent() {
		return content;
	}
	public void setContent(List<Article> content) {
		this.content = content;
	}
	public Pageable getPage() {
		return page;
	}
	public void setPage(Pageable page) {
		this.page = page;
	}
	
}

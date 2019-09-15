package com.zcky.learn.with.teacher.mongoDb.serializer;

import com.google.gson.*;
import com.zcky.learn.with.teacher.model.ArticleList;
import java.lang.reflect.Type;

public class PageArticleSerializer implements JsonSerializer<ArticleList> {

	@Override
	public JsonElement serialize(ArticleList src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		JsonArray content = jsonObj.getAsJsonArray("content");
		for(int i = 0 ; i < content.size(); i++) {
			JsonObject article = content.get(i).getAsJsonObject();
	
			String articleContent = article.get("article_content").getAsString();
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
				article.addProperty("imageHeader", imgSrc);
				String lazyImage = imgSrc.replace("/upload/", "/upload/f_auto,q_auto,e_blur:300/");
				article.addProperty("imageHeaderLazy", lazyImage);
				
			}
			
			if(articleContent.length() > 200) {
				articleContent = articleContent.substring(0, 200)+"...";
			}
			
			if(articleContent.contains("<strong>")) {
				articleContent.replaceAll("<strong>", "");
				articleContent.replaceAll("</strong>", "");
			}

			if(articleContent.contains("<h2")) {
				int start = articleContent.indexOf("<h2");
				int end = articleContent.indexOf("</h2>")+5;
				String subtitle = articleContent.substring(start, end);
				articleContent = articleContent.replace(subtitle, "");
			}
			article.remove("article_content");
			if(!articleContent.isEmpty()) {
				article.addProperty("article_content", articleContent);
			}
		}
		return jsonObj;
	}
}

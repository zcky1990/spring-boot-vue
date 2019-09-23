package app.serializer;

import com.google.gson.*;

import app.mongo.model.Article;
import app.mongo.model.Category;
import app.util.TimeUtility;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

public class ArticleListSerializer implements JsonSerializer<Article> {

	@Override
	public JsonElement serialize(Article src, Type typeOfSrc, JsonSerializationContext context) {
		TimeUtility util = new TimeUtility();
		JsonObject jsonObj = (JsonObject)new GsonBuilder().create().toJsonTree(src);
		String id = src.getStringId();
		if(jsonObj.has("_id")) {
			jsonObj.remove("_id");
		}
		jsonObj.addProperty("id", id);


		if(jsonObj.has("author")) {
			jsonObj.remove("author");
		}

		String articleContent = src.getArticle_short_content();
		
		String imgSrc = src.getImageHeader();
		String lazyImage = imgSrc.replace("/upload/", "/upload/f_auto,q_auto,e_blur:300/");
		jsonObj.addProperty("imageHeaderLazy", lazyImage);

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
		jsonObj.remove("article_content");
		jsonObj.remove("article_short_content");
		if(!articleContent.isEmpty()) {
			jsonObj.addProperty("article_content", articleContent);
		}

		if(jsonObj.has("categoryArticle")) {
			jsonObj.remove("categoryArticle");
			List<Category> list = src.getCategoryArticle();
			JsonArray listCategory = new JsonArray();
			for(int i = 0 ; i < list.size(); i++) {
				Category categoryArticle = list.get(i);
				JsonObject category = new JsonObject();
				category.addProperty("id", categoryArticle.getStringId());
				category.addProperty("name", categoryArticle.getName());
				category.addProperty("status", categoryArticle.getStatus());
				category.addProperty("type", categoryArticle.getType());
				listCategory.add(category);
			}
			jsonObj.add("categoryArticle", listCategory);
		}

		if(jsonObj.has("reference_list")) {
			jsonObj.remove("reference_list");
		}

		Date time = src.get_id().getDate();
		String createDate = util.getFormatedDate(time, "dd/MM/yyyy HH:mm:ss");
		jsonObj.addProperty("created_date", createDate);

		return jsonObj;
	}
}

package app.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import app.mongo.model.Article;

public interface ArticleRepository extends MongoRepository<Article, String> {
	Article findBy_id(ObjectId _id);

	@Query(value = "{'slug' : ?0}")
	Article findBySlug(String slug);
	
	@Query(value = "{'author' : {'$ref' : 'users' , '$id' : ?0} }")
	List<Article> findAllArticleByUserPaging(ObjectId uid, Pageable pageable);
	
	@Query(value = "{'author' : {'$ref' : 'users' , '$id' : ?0} }")
	List<Article> findAllArticleByUser(ObjectId uid);
	
	Page<Article> findAll(Pageable pageable);
	
	@Query("{ '$or': [{'article_title': { $regex: ?0 } }, { 'slug' : { $regex: ?0} }]}")
	Page<Article> findArticleByRegexpTitleOrSlug(String regexp, Pageable pageable);
	
	@Query(value = "{'author' : {'$ref' : 'users' , '$id' : ?0} }")
	Page<Article> findAllArticleByUserId(ObjectId uid, Pageable pageable);
}
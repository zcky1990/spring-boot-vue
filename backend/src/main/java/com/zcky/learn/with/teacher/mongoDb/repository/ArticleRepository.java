package com.zcky.learn.with.teacher.mongoDb.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.zcky.learn.with.teacher.mongoDb.model.Article;

public interface ArticleRepository extends MongoRepository<Article, String> {
	Article findBy_id(ObjectId _id);

	@Query(value = "{'slug' : ?0}")
	Article findByUrl(String slug);
	
	@Query(value = "{'author' : {'$ref' : 'users' , '$id' : ?0} }")
	List<Article> findAllArticleByUserPaging(ObjectId uid, Pageable pageable);
	
	@Query(value = "{'author' : {'$ref' : 'users' , '$id' : ?0} }")
	List<Article> findAllArticleByUser(ObjectId uid);
	
	Page<Article> findAll(Pageable pageable);
}
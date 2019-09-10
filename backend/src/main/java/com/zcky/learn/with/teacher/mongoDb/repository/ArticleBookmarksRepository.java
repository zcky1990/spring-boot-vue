package com.zcky.learn.with.teacher.mongoDb.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.zcky.learn.with.teacher.mongoDb.model.BookmarksArticle;

public interface ArticleBookmarksRepository extends MongoRepository<BookmarksArticle, String> {
	BookmarksArticle findBy_id(ObjectId _id);

	@Query(value = "{'users' : {'$ref' : 'users' , '$id' : ?0} }")
	List<BookmarksArticle> findByUserId(Object userId, Pageable pageable);

	@Query(value = "{'users' : {'$ref' : 'users' , '$id' : ?0} }")
	List<BookmarksArticle> findByUserId(Object userId);	
	
	@Query(value = "{'users' : {'$ref' : 'users' , '$id' : ?0} , 'article' :  {'$ref' : 'article' , '$id' : ?1} }")
	BookmarksArticle findByUserIdAndArticleId(Object userId, Object articleId);	
}
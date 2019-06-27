package com.zcky.learn.with.teacher.mongoDb.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.zcky.learn.with.teacher.mongoDb.model.ArticleComment;

public interface ArticleCommentRepository extends MongoRepository<ArticleComment, String> {
	ArticleComment findBy_id(ObjectId _id);

	@Query(value = "{'user' : {'$ref' : 'users' , '$id' : ?0} }")
	List<ArticleComment> findAllCommentArticleByArticleId(ObjectId uid, Pageable pageable);
	
	@Query(value = "{'article' : {'$ref' : 'article' , '$id' : ?0} }")
	List<ArticleComment> findAllArticleByUser(ObjectId uid);
}
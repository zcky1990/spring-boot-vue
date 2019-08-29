package com.zcky.learn.with.teacher.mongoDb.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.zcky.learn.with.teacher.mongoDb.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
	Category findBy_id(ObjectId _id);

	@Query(value = "{'type' : ?0, 'status': ?1}")
	List<Category> findAllByTypeAndStatus(String type, Boolean status, Pageable pageable);
	
	@Query(value = "{'type' : ?0, 'status': ?1}")
	List<Category> findAllByTypeAndStatus(String type, Boolean status);
	
	@Query(value = "{'name' : ?0 }")
	Category findByName(String name);
}

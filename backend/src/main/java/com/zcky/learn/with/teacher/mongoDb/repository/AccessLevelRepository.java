package com.zcky.learn.with.teacher.mongoDb.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.zcky.learn.with.teacher.mongoDb.model.AccessLevel;

public interface AccessLevelRepository extends MongoRepository<AccessLevel, String> {
	@Query(value = "{ 'role': {'$ref' : 'role' , '$id' : ?0} }")
	List<AccessLevel> findAllAccessLevelByRoleId(ObjectId _Id);
	
	@Query(value = "{ 'role': {'$ref' : 'role' , '$id' : ?0} }")
	List<AccessLevel> findAllAccessLevelByRoleId(ObjectId _Id, Pageable pageable);
}

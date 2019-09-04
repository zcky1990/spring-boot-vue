package com.zcky.learn.with.teacher.mongoDb.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.zcky.learn.with.teacher.mongoDb.model.AccessLevel;

public interface AccessLevelRepository extends MongoRepository<AccessLevel, String> {
	AccessLevel findBy_Id(ObjectId _id);

	List<AccessLevel> findAllAccessLevel(Pageable pageable);

	@Query(value = "{ 'role': {'$ref' : 'role' , '$id' : ?0} }")
	List<AccessLevel> findAllAccessLevelByRoleId(ObjectId Id);
	
	@Query(value = "{ 'role': {'$ref' : 'role' , '$id' : ?0} }")
	List<AccessLevel> findAllAccessLevelByRoleId(ObjectId Id, Pageable pageable);
	
	@Query(value = "{ 'role': {'$ref' : 'role' , '$type' : ?0} }")
	List<AccessLevel> findAllAccessLevelByRoleType(String type);
	
	@Query(value = "{ 'role': {'$ref' : 'role' , '$type' : ?0} }")
	List<AccessLevel> findAllAccessLevelByRoleType(String type, Pageable pageable);
}

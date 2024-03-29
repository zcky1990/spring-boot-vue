package com.zcky.learn.with.teacher.mongoDb.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.zcky.learn.with.teacher.mongoDb.model.Users;

public interface UsersRepository extends MongoRepository<Users, String> {
	Users findBy_id(ObjectId _id);

	@Query(value = "{'username' : ?0}")
	Users findByUserName(String username);

	@Query(value = "{'username' : ?0 ,'password' : ?1}")
	Users signIn(String username, String password);
}
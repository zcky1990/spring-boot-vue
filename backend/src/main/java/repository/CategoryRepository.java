package repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import mongo.model.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
	Category findBy_id(ObjectId _id);

	@Query(value = "{'type' : ?0, 'status': ?1}")
	List<Category> findAllByTypeAndStatus(String type, Boolean status, Pageable pageable);
	
	@Query(value = "{'type' : ?0, 'status': ?1}")
	List<Category> findAllByTypeAndStatus(String type, Boolean status);
	
	@Query(value = "{'status': ?0}")
	List<Category> findAllByStatus( Boolean status);
	
	@Query(value = "{'status': ?0}")
	List<Category> findAllByStatus( Boolean status, Pageable pageable);
	
	@Query(value = "{'name' : ?0 }")
	Category findByName(String name);
}

package backend.repository;

import backend.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
public interface UserRepository extends MongoRepository<User,String> {
    User findByUsername(@Param("username") String username);
    void deleteByUsername(@Param("username") String username);
}
package backend.repository;

import backend.entity.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
public interface UserRepository extends MongoRepository<AppUser,String> {
    AppUser findByUsername(@Param("username") String username);
    void deleteByUsername(@Param("username") String username);
}
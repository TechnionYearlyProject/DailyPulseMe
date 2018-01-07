package backend.repository;

import backend.entity.Pulse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;

import java.util.List;

public interface PulseRepository extends MongoRepository<Pulse,String> {
    Pulse findById(@Param("id") String id);
    void deleteById(@Param("id") String id);

    @Override
    List<Pulse> findAll();
}

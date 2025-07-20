package co.com.bancolombia.mongo.repository;

import co.com.bancolombia.mongo.entity.BranchEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Flux;

public interface BranchMongoDBRepository extends ReactiveMongoRepository<BranchEntity, String>, ReactiveQueryByExampleExecutor<BranchEntity> {
    Flux<BranchEntity> findByFranchiseId(String franchiseId);
}

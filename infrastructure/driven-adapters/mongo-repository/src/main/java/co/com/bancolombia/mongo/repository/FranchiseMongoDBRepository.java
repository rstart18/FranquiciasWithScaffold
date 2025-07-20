package co.com.bancolombia.mongo.repository;

import co.com.bancolombia.mongo.entity.FranchiseEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Mono;

public interface FranchiseMongoDBRepository extends ReactiveMongoRepository<FranchiseEntity, String>, ReactiveQueryByExampleExecutor<FranchiseEntity> {
    Mono<FranchiseEntity> findByNitOrName(String nit, String name);
    Mono<FranchiseEntity> findByName(String name);
}

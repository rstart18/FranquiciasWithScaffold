package co.com.bancolombia.mongo.repository;

import co.com.bancolombia.mongo.entity.ProductEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Mono;

public interface ProductMongoDBRepository extends ReactiveMongoRepository<ProductEntity, String>, ReactiveQueryByExampleExecutor<ProductEntity> {
    Mono<String> getNameById(String id);
    Mono<ProductEntity> findByName(String name);
}

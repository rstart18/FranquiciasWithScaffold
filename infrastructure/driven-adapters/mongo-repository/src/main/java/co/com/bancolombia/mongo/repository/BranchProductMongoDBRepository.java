package co.com.bancolombia.mongo.repository;

import co.com.bancolombia.model.template.BranchProduct;
import co.com.bancolombia.mongo.entity.BranchProductEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchProductMongoDBRepository extends ReactiveMongoRepository<BranchProductEntity, String>, ReactiveQueryByExampleExecutor<BranchProductEntity> {
    Mono<BranchProductEntity> findByBranchIdAndProductId(String branchId, String productId);
    Flux<BranchProductEntity> findAllByBranchId(String branchId);
}

package co.com.bancolombia.mongo;

import co.com.bancolombia.model.branchproduct.BranchProduct;
import co.com.bancolombia.mongo.document.BranchProductDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchProductMongoDBRepository extends ReactiveMongoRepository<BranchProductDocument, String>, ReactiveQueryByExampleExecutor<BranchProductDocument> {
    Mono<BranchProductDocument> findByBranchIdAndProductId(String branchId, String productId);
    Flux<BranchProduct> findAllByBranchId(String branchId);
}

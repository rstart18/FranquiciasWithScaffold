package co.com.bancolombia.mongo;

import co.com.bancolombia.mongo.document.BranchDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Flux;

public interface BranchMongoDBRepository extends ReactiveMongoRepository<BranchDocument, String>, ReactiveQueryByExampleExecutor<BranchDocument> {
    Flux<BranchDocument> findByFranchiseId(String franchiseId);
}

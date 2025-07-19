package co.com.bancolombia.mongo;

import co.com.bancolombia.mongo.document.ProductDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Mono;

public interface ProductMongoDBRepository extends ReactiveMongoRepository<ProductDocument, String>, ReactiveQueryByExampleExecutor<ProductDocument> {
    Mono<String> getNameById(String id);
}

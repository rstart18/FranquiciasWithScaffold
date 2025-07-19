package co.com.bancolombia.mongo;

import co.com.bancolombia.mongo.document.FranchiseDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;

public interface FranchiseMongoDBRepository extends ReactiveMongoRepository<FranchiseDocument, String>, ReactiveQueryByExampleExecutor<FranchiseDocument> {
}

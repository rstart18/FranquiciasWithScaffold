package co.com.bancolombia.mongo;

import co.com.bancolombia.model.branchproduct.BranchProduct;
import co.com.bancolombia.model.branchproduct.gateways.BranchProductRepository;
import co.com.bancolombia.mongo.document.BranchProductDocument;
import co.com.bancolombia.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class BranchProductMongoRepositoryAdapter extends AdapterOperations<BranchProduct, BranchProductDocument, String, BranchProductMongoDBRepository>
    implements BranchProductRepository
{
    public BranchProductMongoRepositoryAdapter(BranchProductMongoDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, BranchProduct.class));
    }

    @Override
    public Mono<BranchProduct> findByBranchIdAndProductId(String branchId, String productId) {
        return repository.findByBranchIdAndProductId(branchId, productId)
            .map(doc -> mapper.map(doc, BranchProduct.class));
    }

    @Override
    public Flux<BranchProduct> findAllByBranchId(String branchId) {
        return repository.findAll()
            .filter(doc -> doc.getBranchId().equals(branchId))
            .map(doc -> mapper.map(doc, BranchProduct.class));
    }

    @Override
    public Mono<BranchProduct> findByBranchId(String branchId) {
        return repository.findAll()
            .filter(doc -> doc.getBranchId().equals(branchId))
            .next()
            .map(doc -> mapper.map(doc, BranchProduct.class));
    }


}

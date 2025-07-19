package co.com.bancolombia.mongo;

import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductRepository;
import co.com.bancolombia.mongo.document.ProductDocument;
import co.com.bancolombia.mongo.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class ProductMongoRepositoryAdapter extends AdapterOperations<Product, ProductDocument, String, ProductMongoDBRepository>
    implements ProductRepository
{

    public ProductMongoRepositoryAdapter(ProductMongoDBRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Product.class));
    }

    @Override
    public Mono<String> getNameById(String id) {
        return repository.findById(id)
            .map(doc -> doc.getName() != null ? doc.getName() : "");
    }
}

package co.com.bancolombia.model.product.gateways;

import co.com.bancolombia.model.product.Product;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> save(Product franchise);
    Mono<Product> findById(String id);
    Mono<String> getNameById(String productId);
}

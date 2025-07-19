package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.product.Product;
import reactor.core.publisher.Mono;

public interface ProductUseCase {
    Mono<Product> createProduct(Product product);
    Mono<Void> renameProduct(String productId, String newName);
    Mono<String> getNameById(String productId);
}

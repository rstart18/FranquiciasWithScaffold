package co.com.bancolombia.usecase.product;

import co.com.bancolombia.model.exception.ErrorCode;
import co.com.bancolombia.model.exception.NotFoundException;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.model.product.gateways.ProductRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ProductUseCaseImpl implements ProductUseCase{

    private final ProductRepository repository;

    @Override
    public Mono<Product> createProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Mono<Void> renameProduct(String productId, String newName) {
        return repository.findById(productId)
            .switchIfEmpty(Mono.error(new NotFoundException(
                ErrorCode.PRODUCT_NOT_FOUND.getMessage(), ErrorCode.PRODUCT_NOT_FOUND.getCode())))
            .flatMap(product -> {
                product.setName(newName);
                return repository.save(product);
            })
            .then();
    }

    @Override
    public Mono<String> getNameById(String productId) {
        return repository.findById(productId)
            .map(Product::getName);
    }

}

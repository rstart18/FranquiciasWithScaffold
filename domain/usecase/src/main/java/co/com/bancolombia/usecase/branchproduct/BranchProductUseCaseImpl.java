package co.com.bancolombia.usecase.branchproduct;

import co.com.bancolombia.model.branch.gateways.BranchRepository;
import co.com.bancolombia.model.branchproduct.BranchProduct;
import co.com.bancolombia.model.branchproduct.BranchProductWithInfo;
import co.com.bancolombia.model.branchproduct.gateways.BranchProductRepository;
import co.com.bancolombia.model.exception.ErrorCode;
import co.com.bancolombia.model.exception.NotFoundException;
import co.com.bancolombia.usecase.product.ProductUseCaseImpl;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Comparator;

@RequiredArgsConstructor
public class BranchProductUseCaseImpl implements BranchProductUseCase {
    private final BranchProductRepository repository;
    private final BranchRepository branchRepository;
    private final ProductUseCaseImpl productUseCase;

    @Override
    public Mono<BranchProduct> create(BranchProduct branchProduct) {
        return repository.save(branchProduct);
    }

    @Override
    public Mono<Void> updateStock(String branchId, String productId, int newStock) {
        return repository.findByBranchIdAndProductId(branchId, productId)
            .switchIfEmpty(Mono.error(
                new NotFoundException(ErrorCode.BRANCH_PRODUCT_NOT_FOUND.getMessage(), ErrorCode.BRANCH_PRODUCT_NOT_FOUND.getCode())))
            .flatMap(bp -> {
                bp.setStock(newStock);
                return repository.save(bp);
            })
            .then();
    }

    @Override
    public Mono<BranchProduct> softDelete(String branchId, String productId) {
        return repository.findByBranchIdAndProductId(branchId, productId)
            .switchIfEmpty(Mono.error(
                new NotFoundException(ErrorCode.BRANCH_PRODUCT_NOT_FOUND.getMessage(), ErrorCode.BRANCH_PRODUCT_NOT_FOUND.getCode())))
            .map(bp -> {
                bp.setDeletedAt(LocalDateTime.now());
                return bp;
            })
            .flatMap(repository::save);
    }

    @Override
    public Flux<BranchProductWithInfo> findTopStockProductPerBranchByFranchise(String franchiseId) {
        return branchRepository.findByFranchiseId(franchiseId)
            .flatMap(branch ->
                repository.findAllByBranchId(branch.getId())
                    .filter(bp -> bp.getDeletedAt() == null)
                    .collectList()
                    .flatMapMany(list ->
                        Mono.justOrEmpty(
                            list.stream()
                                .max(Comparator.comparingInt(BranchProduct::getStock))
                        )
                    )
                    .flatMap(max ->
                        productUseCase.getNameById(max.getProductId())
                            .switchIfEmpty(Mono.error(new RuntimeException("Product name not found")))
                            .map(productName ->
                                BranchProductWithInfo.builder()
                                    .branchId(branch.getId())
                                    .branchName(branch.getName())
                                    .productId(max.getProductId())
                                    .productName(productName)
                                    .stock(max.getStock())
                                    .build()
                            )
                    )
            );
    }
}

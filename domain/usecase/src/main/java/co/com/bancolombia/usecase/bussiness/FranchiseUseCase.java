package co.com.bancolombia.usecase.bussiness;

import co.com.bancolombia.model.template.Branch;
import co.com.bancolombia.model.template.BranchProduct;
import co.com.bancolombia.model.template.BranchProductWithInfo;
import co.com.bancolombia.model.exception.ErrorCode;
import co.com.bancolombia.model.exception.NotFoundException;
import co.com.bancolombia.model.template.Franchise;
import co.com.bancolombia.model.gateways.DbPort;
import co.com.bancolombia.model.template.Product;
import co.com.bancolombia.usecase.service.FranchiseService;
import co.com.bancolombia.usecase.validator.FranchisesValidator;
import co.com.bancolombia.usecase.validator.ProductValidator;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Comparator;

@RequiredArgsConstructor
public class FranchiseUseCase implements FranchiseService {
    private final DbPort repository;
    private final FranchisesValidator franchiseValidator;
    private final ProductValidator productValidator;

    @Override
    public Mono<Franchise> createFranchise(Franchise franchise) {
        return franchiseValidator.validateUniqueFranchise(franchise.getNit(), franchise.getName())
            .then(repository.saveFranchise(franchise));
    }

    @Override
    public Mono<Void> renameFranchise(String franchiseId, String newName) {
        return franchiseValidator.validateFranchiseNameNotTaken(newName)
            .then(repository.findByIdFranchise(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException(
                    ErrorCode.FRANCHISE_NOT_FOUND.getMessage(),
                    ErrorCode.FRANCHISE_NOT_FOUND.getCode())))
                .flatMap(franchise -> {
                    franchise.setName(newName);
                    return repository.saveFranchise(franchise).then();
                }));
    }

    @Override
    public Mono<Branch> createBranch(Branch branch) {
        return repository.saveBranch(branch);
    }

    @Override
    public Mono<Void> renameBranch(String id, String newName) {
        return repository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException(
                ErrorCode.BRANCH_NOT_FOUND.getMessage(), ErrorCode.BRANCH_NOT_FOUND.getCode())))
            .flatMap(branch -> {
                branch.setName(newName);
                return repository.saveProduct(branch);
            })
            .then();
    }

    @Override
    public Mono<BranchProduct> createBranchProduct(BranchProduct branchProduct) {
        return repository.saveBranchProduct(branchProduct);
    }

    @Override
    public Mono<Void> updateStock(String branchId, String productId, int newStock) {
        return repository.findByBranchIdAndProductId(branchId, productId)
            .switchIfEmpty(Mono.error(
                new NotFoundException(ErrorCode.BRANCH_PRODUCT_NOT_FOUND.getMessage(), ErrorCode.BRANCH_PRODUCT_NOT_FOUND.getCode())))
            .flatMap(bp -> {
                bp.setStock(newStock);
                return repository.saveBranchProduct(bp);
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
            .flatMap(repository::saveBranchProduct);
    }

    @Override
    public Flux<BranchProductWithInfo> findTopStockProductPerBranchByFranchise(String franchiseId) {
        return repository.findByFranchiseId(franchiseId)
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
                        repository.getNameById(max.getProductId())
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

    @Override
    public Mono<Product> createProduct(Product product) {
        return productValidator.validateProductNameNotTaken(product.getName())
            .then(repository.saveProduct(product));
    }

    @Override
    public Mono<Void> renameProduct(String productId, String newName) {
        return productValidator.validateProductNameNotTaken(newName) // validar antes de buscar el producto
            .then(repository.findById(productId)
                .switchIfEmpty(Mono.error(new NotFoundException(
                    ErrorCode.PRODUCT_NOT_FOUND.getMessage(),
                    ErrorCode.PRODUCT_NOT_FOUND.getCode())))
                .flatMap(product -> {
                    product.setName(newName);
                    return repository.saveProduct(product).then();
                }));
    }

    @Override
    public Mono<String> getNameById(String productId) {
        return repository.findById(productId)
            .map(Product::getName);
    }

}

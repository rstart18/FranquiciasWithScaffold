package co.com.bancolombia.usecase.service;

import co.com.bancolombia.model.template.Branch;
import co.com.bancolombia.model.template.BranchProduct;
import co.com.bancolombia.model.template.BranchProductWithInfo;
import co.com.bancolombia.model.template.Franchise;
import co.com.bancolombia.model.template.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseService {
    Mono<Franchise> createFranchise(Franchise franchise);
    Mono<Void> renameFranchise(String franchiseId, String newName);

    Mono<Branch> createBranch(Branch branch);
    Mono<Void> renameBranch(String id, String newName);

    Mono<BranchProduct> createBranchProduct(BranchProduct branchProduct);
    Mono<Void> updateStock(String branchId, String productId, int newStock);
    Mono<BranchProduct> softDelete(String branchId, String productId);
    Flux<BranchProductWithInfo> findTopStockProductPerBranchByFranchise(String franchiseId);

    Mono<Product> createProduct(Product product);
    Mono<Void> renameProduct(String productId, String newName);
    Mono<String> getNameById(String productId);
}

package co.com.bancolombia.model.gateways;

import co.com.bancolombia.model.template.Branch;
import co.com.bancolombia.model.template.BranchProduct;
import co.com.bancolombia.model.template.Franchise;
import co.com.bancolombia.model.template.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DbPort {
    Mono<Franchise> saveFranchise(Franchise franchise);
    Mono<Franchise> findFranchiseById(String id);
    Mono<Franchise> findFranchiseByNitOrName(String nit, String name);
    Mono<Franchise> findFranchiseByName(String name);

    Mono<Branch> saveBranch(Branch branch);
    Flux<Branch> findAllBranchByFranchiseId(String franchiseId);

    Mono<BranchProduct> saveBranchProduct(BranchProduct branchProduct);
    Mono<BranchProduct> findBranchProductByBranchIdAndProductId(String branchId, String productId);
    Flux<BranchProduct> findAllBranchProductByBranchId(String branchId);

    Mono<Product> saveProduct(Product product);
    Mono<Product> findProductById(String id);
    Mono<Product> findProductByName(String name);
    Mono<String> getProductNameById(String productId);
}


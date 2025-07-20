package co.com.bancolombia.model.gateways;

import co.com.bancolombia.model.template.Branch;
import co.com.bancolombia.model.template.BranchProduct;
import co.com.bancolombia.model.template.Franchise;
import co.com.bancolombia.model.template.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DbPort {
    Mono<Franchise> saveFranchise(Franchise franchise);
    Mono<Franchise> findByIdFranchise(String id);
    Mono<Franchise> findByNitOrName(String nit, String name);
    Mono<Franchise> findByName(String name);

    Mono<Branch> saveBranch(Branch branch);
    Flux<Branch> findByFranchiseId(String franchiseId);

    Mono<BranchProduct> saveBranchProduct(BranchProduct branchProduct);
    Mono<BranchProduct> findByBranchIdAndProductId(String branchId, String productId);
    Flux<BranchProduct> findAllByBranchId(String branchId);

    Mono<Product> saveProduct(Product product);
    Mono<Product> findById(String id);
    Mono<Product> findProductByName(String name);
    Mono<String> getNameById(String productId);
}


package co.com.bancolombia.model.branchproduct.gateways;

import co.com.bancolombia.model.branchproduct.BranchProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchProductRepository {
    Mono<BranchProduct> save(BranchProduct branchProduct);
    Mono<BranchProduct> findByBranchIdAndProductId(String branchId, String productId);
    Mono<BranchProduct> findByBranchId(String branchId);
    Flux<BranchProduct> findAllByBranchId(String branchId);
}

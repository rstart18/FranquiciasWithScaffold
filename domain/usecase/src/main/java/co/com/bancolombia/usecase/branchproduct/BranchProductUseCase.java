package co.com.bancolombia.usecase.branchproduct;

import co.com.bancolombia.model.branchproduct.BranchProduct;
import co.com.bancolombia.model.branchproduct.BranchProductWithInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchProductUseCase {
    Mono<BranchProduct> create(BranchProduct branchProduct);
    Mono<Void> updateStock(String branchId, String productId, int newStock);
    Mono<BranchProduct> softDelete(String branchId, String productId);
    Flux<BranchProductWithInfo> findTopStockProductPerBranchByFranchise(String franchiseId);
}

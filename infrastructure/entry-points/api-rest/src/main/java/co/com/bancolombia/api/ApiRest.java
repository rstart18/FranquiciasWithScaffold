package co.com.bancolombia.api;
import co.com.bancolombia.api.dto.ApiResponse;
import co.com.bancolombia.api.dto.branch.BranchRequest;
import co.com.bancolombia.api.dto.branch.BranchResponse;
import co.com.bancolombia.api.dto.branch.RenameBranchRequest;
import co.com.bancolombia.api.dto.branchProduct.BranchProductRequest;
import co.com.bancolombia.api.dto.branchProduct.BranchProductResponse;
import co.com.bancolombia.api.dto.branchProduct.BranchProductStockRequest;
import co.com.bancolombia.api.dto.franchise.FranchiseRequest;
import co.com.bancolombia.api.dto.franchise.FranchiseResponse;
import co.com.bancolombia.api.dto.franchise.RenameFranchiseRequest;
import co.com.bancolombia.api.dto.product.ProductRequest;
import co.com.bancolombia.api.dto.product.ProductResponse;
import co.com.bancolombia.api.dto.product.RenameProductRequest;
import co.com.bancolombia.model.branch.Branch;
import co.com.bancolombia.model.branchproduct.BranchProduct;
import co.com.bancolombia.model.branchproduct.BranchProductWithInfo;
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.usecase.branch.BranchUseCase;
import co.com.bancolombia.usecase.branchproduct.BranchProductUseCase;
import co.com.bancolombia.usecase.franchise.FranchiseUseCase;
import co.com.bancolombia.usecase.product.ProductUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * API Rest controller.
 *
 * Example of how to declare and use a use case:
 * <pre>
 * private final MyUseCase useCase;
 *
 * public String commandName() {
 *     return useCase.execute();
 * }
 * </pre>
 */
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ApiRest {

    private final FranchiseUseCase franchiseUseCase;
    private final BranchUseCase branchUseCase;
    private final ProductUseCase productUseCase;
    private final BranchProductUseCase branchProductUseCase;

    @PostMapping(path = "/franchise", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ApiResponse<FranchiseResponse>>> createFranchise(@RequestBody FranchiseRequest request) {
        Franchise franchise = Franchise.builder()
            .name(request.getName())
            .nit(request.getNit())
            .build();

        return franchiseUseCase.createFranchise(franchise)
            .map(created -> FranchiseResponse.builder()
                .id(created.getId())
                .name(created.getName())
                .nit(created.getNit())
                .build())
            .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(response)));
    }

    @PutMapping(path = "/franchise/{id}/rename", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Void>> renameFranchise(
        @PathVariable("id") String id,
        @RequestBody RenameFranchiseRequest request) {

        return franchiseUseCase.renameFranchise(id, request.getName())
            .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/franchise/{franchiseId}/top-products-by-branch")
    public Flux<BranchProductWithInfo> getTopProductsByBranch(@PathVariable("franchiseId") String franchiseId) {
        return branchProductUseCase.findTopStockProductPerBranchByFranchise(franchiseId);
    }

    @PostMapping(path = "/branch", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ApiResponse<BranchResponse>>> createBranch(@RequestBody BranchRequest request) {
        Branch branch = Branch.builder()
            .name(request.getName())
            .franchiseId(request.getFranchiseId())
            .build();

        return branchUseCase.createBranch(branch)
            .map(saved -> BranchResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .franchiseId(saved.getFranchiseId())
                .build())
            .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(response)));
    }

    @PutMapping(path = "/branch/{id}/rename", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Void>> renameBranch(
        @PathVariable("id") String id,
        @RequestBody RenameBranchRequest request) {

        return branchUseCase.renameBranch(id, request.getName())
            .thenReturn(ResponseEntity.noContent().build());
    }

    @PostMapping(path = "/product", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ApiResponse<ProductResponse>>> createProduct(@RequestBody ProductRequest request) {
        Product product = Product.builder()
            .name(request.getName())
            .build();

        return productUseCase.createProduct(product)
            .map(created -> ProductResponse.builder()
                .id(created.getId())
                .name(created.getName())
                .build())
            .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(response)));
    }

    @PutMapping(path = "/product/{id}/rename", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ApiResponse<Void>>> renameProduct(
        @PathVariable("id") String id,
        @RequestBody RenameProductRequest request) {

        return productUseCase.renameProduct(id, request.getName())
            .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @PostMapping(path = "/branch-product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ApiResponse<BranchProductResponse>>> create(@RequestBody BranchProductRequest request) {
        BranchProduct branchProduct = BranchProduct.builder()
            .productId(request.getProductId())
            .branchId(request.getBranchId())
            .stock(request.getStock())
            .build();

        return branchProductUseCase.create(branchProduct)
            .map(bp -> BranchProductResponse.builder()
                .id(bp.getId())
                .branchId(bp.getBranchId())
                .productId(bp.getProductId())
                .stock(bp.getStock())
                .build())
            .map(response -> ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.<BranchProductResponse>builder().data(response).build()));
    }

    @PutMapping(path = "/branch/{branchId}/product/{productId}/stock", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<ApiResponse<Void>>> updateStock(
        @PathVariable("branchId") String branchId,
        @PathVariable("productId") String productId,
        @RequestBody BranchProductStockRequest request) {

        return branchProductUseCase.updateStock(branchId, productId, request.getStock())
            .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @DeleteMapping("/branch/{branchId}/product/{productId}")
    public Mono<ResponseEntity<ApiResponse<Void>>> delete(
        @PathVariable("branchId") String branchId,
        @PathVariable("productId") String productId) {
        return branchProductUseCase.softDelete(branchId, productId)
            .then(Mono.just(ResponseEntity.noContent().build()));
    }
}

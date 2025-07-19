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
import co.com.bancolombia.model.franchise.Franchise;
import co.com.bancolombia.model.product.Product;
import co.com.bancolombia.usecase.branch.BranchUseCase;
import co.com.bancolombia.usecase.branchproduct.BranchProductUseCase;
import co.com.bancolombia.usecase.franchise.FranchiseUseCase;
import co.com.bancolombia.usecase.product.ProductUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final FranchiseUseCase franchiseUseCase;
    private final BranchUseCase branchUseCase;
    private final ProductUseCase productUseCase;
    private final BranchProductUseCase branchProductUseCase;

    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        return request.bodyToMono(FranchiseRequest.class)
            .map(req -> Franchise.builder().name(req.getName()).nit(req.getNit()).build())
            .flatMap(franchiseUseCase::createFranchise)
            .map(created -> FranchiseResponse.builder()
                .id(created.getId())
                .name(created.getName())
                .nit(created.getNit())
                .build())
            .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(new ApiResponse<>(response)));
    }

    public Mono<ServerResponse> renameFranchise(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(RenameFranchiseRequest.class)
            .flatMap(body -> franchiseUseCase.renameFranchise(id, body.getName()))
            .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getTopProductsByBranch(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        return ServerResponse.ok().body(branchProductUseCase.findTopStockProductPerBranchByFranchise(franchiseId), BranchProduct.class);
    }

    public Mono<ServerResponse> createBranch(ServerRequest request) {
        return request.bodyToMono(BranchRequest.class)
            .map(req -> Branch.builder().name(req.getName()).franchiseId(req.getFranchiseId()).build())
            .flatMap(branchUseCase::createBranch)
            .map(saved -> BranchResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .franchiseId(saved.getFranchiseId())
                .build())
            .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(new ApiResponse<>(response)));
    }

    public Mono<ServerResponse> renameBranch(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(RenameBranchRequest.class)
            .flatMap(body -> branchUseCase.renameBranch(id, body.getName()))
            .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(ProductRequest.class)
            .map(req -> Product.builder().name(req.getName()).build())
            .flatMap(productUseCase::createProduct)
            .map(created -> ProductResponse.builder()
                .id(created.getId())
                .name(created.getName())
                .build())
            .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(new ApiResponse<>(response)));
    }

    public Mono<ServerResponse> renameProduct(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(RenameProductRequest.class)
            .flatMap(body -> productUseCase.renameProduct(id, body.getName()))
            .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> createBranchProduct(ServerRequest request) {
        return request.bodyToMono(BranchProductRequest.class)
            .map(req -> BranchProduct.builder()
                .branchId(req.getBranchId())
                .productId(req.getProductId())
                .stock(req.getStock())
                .build())
            .flatMap(branchProductUseCase::create)
            .map(bp -> BranchProductResponse.builder()
                .id(bp.getId())
                .branchId(bp.getBranchId())
                .productId(bp.getProductId())
                .stock(bp.getStock())
                .build())
            .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(ApiResponse.<BranchProductResponse>builder().data(response).build()));
    }

    public Mono<ServerResponse> updateStock(ServerRequest request) {
        String branchId = request.pathVariable("branchId");
        String productId = request.pathVariable("productId");
        return request.bodyToMono(BranchProductStockRequest.class)
            .flatMap(body -> branchProductUseCase.updateStock(branchId, productId, body.getStock()))
            .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> deleteBranchProduct(ServerRequest request) {
        String branchId = request.pathVariable("branchId");
        String productId = request.pathVariable("productId");
        return branchProductUseCase.softDelete(branchId, productId)
            .then(ServerResponse.noContent().build());
    }
}

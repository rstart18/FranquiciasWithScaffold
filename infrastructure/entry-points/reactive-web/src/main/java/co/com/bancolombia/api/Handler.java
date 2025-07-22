package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.ApiResponse;
import co.com.bancolombia.api.dto.branch.BranchRequest;
import co.com.bancolombia.api.dto.branch.RenameBranchRequest;
import co.com.bancolombia.api.dto.branchProduct.BranchProductRequest;
import co.com.bancolombia.api.dto.branchProduct.BranchProductResponse;
import co.com.bancolombia.api.dto.branchProduct.BranchProductStockRequest;
import co.com.bancolombia.api.dto.franchise.FranchiseRequest;
import co.com.bancolombia.api.dto.franchise.RenameFranchiseRequest;
import co.com.bancolombia.api.dto.product.ProductRequest;
import co.com.bancolombia.api.dto.product.RenameProductRequest;
import co.com.bancolombia.api.mapper.BranchMapper;
import co.com.bancolombia.api.mapper.BranchProductMapper;
import co.com.bancolombia.api.mapper.FranchiseMapper;
import co.com.bancolombia.api.mapper.ProductMapper;
import co.com.bancolombia.model.template.BranchProduct;
import co.com.bancolombia.usecase.service.FranchiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final FranchiseService franchiseService;
    private final FranchiseMapper franchiseMapper;
    private final BranchMapper branchMapper;
    private final ProductMapper productMapper;
    private final BranchProductMapper branchProductMapper;

    public Mono<ServerResponse> createFranchise(ServerRequest request) {
        return request.bodyToMono(FranchiseRequest.class)
            .map(franchiseMapper::toDomain)
            .flatMap(franchiseService::createFranchise)
            .map(franchiseMapper::toResponseDto)
            .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(new ApiResponse<>(response)));
    }

    public Mono<ServerResponse> renameFranchise(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(RenameFranchiseRequest.class)
            .flatMap(body -> franchiseService.renameFranchise(id, body.getName()))
            .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> getTopProductsByBranch(ServerRequest request) {
        String franchiseId = request.pathVariable("franchiseId");
        return ServerResponse.ok().body(franchiseService.findTopStockProductPerBranchByFranchise(franchiseId), BranchProduct.class);
    }

    public Mono<ServerResponse> createBranch(ServerRequest request) {
        return request.bodyToMono(BranchRequest.class)
            .map(branchMapper::toDomain)
            .flatMap(franchiseService::createBranch)
            .map(branchMapper::toResponseDto)
            .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(new ApiResponse<>(response)));
    }

    public Mono<ServerResponse> renameBranch(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(RenameBranchRequest.class)
            .flatMap(body -> franchiseService.renameBranch(id, body.getName()))
            .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> createProduct(ServerRequest request) {
        return request.bodyToMono(ProductRequest.class)
            .map(productMapper::toDomain)
            .flatMap(franchiseService::createProduct)
            .map(productMapper::toResponseDto)
            .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(new ApiResponse<>(response)));
    }

    public Mono<ServerResponse> renameProduct(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(RenameProductRequest.class)
            .flatMap(body -> franchiseService.renameProduct(id, body.getName()))
            .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> createBranchProduct(ServerRequest request) {
        return request.bodyToMono(BranchProductRequest.class)
            .map(branchProductMapper::toDomain)
            .flatMap(franchiseService::createBranchProduct)
            .map(branchProductMapper::toResponseDto)
            .flatMap(response -> ServerResponse.status(HttpStatus.CREATED).bodyValue(ApiResponse.<BranchProductResponse>builder().data(response).build()));
    }

    public Mono<ServerResponse> updateStock(ServerRequest request) {
        String branchId = request.pathVariable("branchId");
        String productId = request.pathVariable("productId");
        return request.bodyToMono(BranchProductStockRequest.class)
            .flatMap(body -> franchiseService.updateStock(branchId, productId, body.getStock()))
            .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> deleteBranchProduct(ServerRequest request) {
        String branchId = request.pathVariable("branchId");
        String productId = request.pathVariable("productId");
        return franchiseService.softDelete(branchId, productId)
            .then(ServerResponse.noContent().build());
    }
}

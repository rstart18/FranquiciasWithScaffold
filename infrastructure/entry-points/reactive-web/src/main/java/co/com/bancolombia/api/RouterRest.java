package co.com.bancolombia.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(POST("/api/v1/franchise"), handler::createFranchise)
            .andRoute(PUT("/api/v1/franchise/{id}/rename"), handler::renameFranchise)
            .andRoute(GET("/api/v1/franchise/{franchiseId}/top-products-by-branch"), handler::getTopProductsByBranch)
            .andRoute(POST("/api/v1/branch"), handler::createBranch)
            .andRoute(PUT("/api/v1/branch/{id}/rename"), handler::renameBranch)
            .andRoute(POST("/api/v1/product"), handler::createProduct)
            .andRoute(PUT("/api/v1/product/{id}/rename"), handler::renameProduct)
            .andRoute(POST("/api/v1/branch-product"), handler::createBranchProduct)
            .andRoute(PUT("/api/v1/branch/{branchId}/product/{productId}/stock"), handler::updateStock)
            .andRoute(DELETE("/api/v1/branch/{branchId}/product/{productId}"), handler::deleteBranchProduct);
    }
}

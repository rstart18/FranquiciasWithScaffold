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
        return route(POST(ApiPaths.FRANCHISE), handler::createFranchise)
            .andRoute(PUT(ApiPaths.FRANCHISE_RENAME), handler::renameFranchise)
            .andRoute(GET(ApiPaths.FRANCHISE_TOP_PRODUCTS), handler::getTopProductsByBranch)
            .andRoute(POST(ApiPaths.BRANCH), handler::createBranch)
            .andRoute(PUT(ApiPaths.BRANCH_RENAME), handler::renameBranch)
            .andRoute(POST(ApiPaths.PRODUCT), handler::createProduct)
            .andRoute(PUT(ApiPaths.PRODUCT_RENAME), handler::renameProduct)
            .andRoute(POST(ApiPaths.BRANCH_PRODUCT), handler::createBranchProduct)
            .andRoute(PUT(ApiPaths.UPDATE_STOCK), handler::updateStock)
            .andRoute(DELETE(ApiPaths.DELETE_BRANCH_PRODUCT), handler::deleteBranchProduct);
    }
}

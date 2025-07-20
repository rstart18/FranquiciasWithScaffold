package co.com.bancolombia.api;

public class ApiPaths {

        private ApiPaths() {
        }

        public static final String FRANCHISE = "/api/v1/franchise";
        public static final String FRANCHISE_RENAME = FRANCHISE + "/{id}/rename";
        public static final String FRANCHISE_TOP_PRODUCTS = FRANCHISE + "/{franchiseId}/top-products-by-branch";

        public static final String BRANCH = "/api/v1/branch";
        public static final String BRANCH_RENAME = BRANCH + "/{id}/rename";

        public static final String PRODUCT = "/api/v1/product";
        public static final String PRODUCT_RENAME = PRODUCT + "/{id}/rename";

        public static final String BRANCH_PRODUCT = "/api/v1/branch-product";
        public static final String UPDATE_STOCK = BRANCH + "/{branchId}/product/{productId}/stock";
        public static final String DELETE_BRANCH_PRODUCT = BRANCH + "/{branchId}/product/{productId}";
}

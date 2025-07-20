package co.com.bancolombia.api.config;

import co.com.bancolombia.usecase.branch.BranchUseCase;
import co.com.bancolombia.usecase.branchproduct.BranchProductUseCase;
import co.com.bancolombia.usecase.franchise.FranchiseUseCase;
import co.com.bancolombia.usecase.product.ProductUseCase;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MockConfig {

    @Bean
    public FranchiseUseCase franchiseUseCase() {
        return Mockito.mock(FranchiseUseCase.class);
    }

    @Bean
    public BranchUseCase branchUseCase() {
        return Mockito.mock(BranchUseCase.class);
    }

    @Bean
    public ProductUseCase productUseCase() {
        return Mockito.mock(ProductUseCase.class);
    }

    @Bean
    public BranchProductUseCase branchProductUseCase() {
        return Mockito.mock(BranchProductUseCase.class);
    }
}

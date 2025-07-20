package co.com.bancolombia.config;

import co.com.bancolombia.model.gateways.DbPort;
import co.com.bancolombia.usecase.validator.FranchisesValidator;
import co.com.bancolombia.usecase.validator.ProductValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.bancolombia.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {
    @Bean
    public FranchisesValidator franchisesValidator(DbPort repository) {
        return new FranchisesValidator(repository);
    }

    @Bean
    public ProductValidator productValidator(DbPort repository) {
        return new ProductValidator(repository);
    }
}

package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.franchise.FranchiseRequest;
import co.com.bancolombia.api.dto.franchise.FranchiseResponse;
import co.com.bancolombia.model.template.Franchise;
import co.com.bancolombia.usecase.bussiness.FranchiseUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {RouterRest.class, Handler.class})
@WebFluxTest
@Import(co.com.bancolombia.api.config.MockConfig.class)
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private FranchiseUseCase franchiseUseCase;

    @Test
    void whenValidRequestIsSent_shouldReturnCreatedFranchise_then200OK() {
        FranchiseRequest request = new FranchiseRequest();
        request.setName("Test Franchise");
        request.setNit("123456789");

        Franchise response = Franchise.builder()
            .name("Test Franchise")
            .nit("123456789")
            .build();

        Mockito.when(franchiseUseCase.createFranchise(Mockito.any()))
            .thenReturn(Mono.just(response));

        FranchiseResponse expected = FranchiseResponse.builder()
            .name("Test Franchise")
            .nit("123456789")
            .build();

        FranchiseResponse actual = webTestClient.post()
            .uri(ApiPaths.FRANCHISE)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(FranchiseResponse.class)
            .returnResult()
            .getResponseBody();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }


    @Test
    void testListenGETOtherUseCase() {
        webTestClient.get()
                .uri("/api/otherusercase/path")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(userResponse -> {
                            assertThat(userResponse).isEmpty();
                        }
                );
    }

    @Test
    void testListenPOSTUseCase() {
        webTestClient.post()
                .uri("/api/usecase/otherpath")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue("")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .value(userResponse -> {
                            assertThat(userResponse).isEmpty();
                        }
                );
    }
}

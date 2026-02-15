package com.inditex.similar.client;

import com.inditex.similar.dto.ProductDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ProductApiClient {

    private final WebClient webClient;

    public ProductApiClient(
            WebClient.Builder builder,
            @Value("${app.mocksBaseUrl}") String baseUrl
    ) {
        this.webClient = builder.baseUrl(baseUrl).build();
    }

    public Mono<List<String>> getSimilarIds(String productId) {
        return webClient.get()
                .uri("/product/{productId}/similarids", productId)
                .retrieve()
                .onStatus(status -> status == HttpStatus.NOT_FOUND,
                        resp -> Mono.error(new ProductNotFoundException(productId)))
                .bodyToFlux(Integer.class)
                .map(String::valueOf)
                .collectList();
    }

    public Mono<ProductDetail> getProductDetail(String id) {
        return webClient.get()
                .uri("/product/{id}", id)
                .retrieve()
                .onStatus(status -> status == HttpStatus.NOT_FOUND,
                        resp -> Mono.error(new ProductNotFoundException(id)))
                .bodyToMono(ProductDetail.class);
    }

    public static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String id) {
            super("Product not found: " + id);
        }
    }
}

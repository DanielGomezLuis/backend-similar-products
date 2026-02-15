package com.inditex.similar.service;

import com.inditex.similar.client.ProductApiClient;
import com.inditex.similar.dto.ProductDetail;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
public class SimilarProductsService {

    private final ProductApiClient client;

    private static final int CONCURRENCY = 8;
    private static final Duration DETAIL_TIMEOUT = Duration.ofSeconds(2);

    public SimilarProductsService(ProductApiClient client) {
        this.client = client;
    }

    public Mono<List<ProductDetail>> getSimilarProducts(String productId) {
        return client.getSimilarIds(productId)
                .flatMapMany(ids ->
                        Flux.fromIterable(ids)
                                .flatMapSequential(id ->
                                                client.getProductDetail(id)
                                                        .timeout(DETAIL_TIMEOUT)
                                                        .onErrorResume(ex -> Mono.empty()),
                                        CONCURRENCY
                                )
                )
                .collectList();
    }
}

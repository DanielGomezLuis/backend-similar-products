package com.inditex.similar.controller;

import com.inditex.similar.dto.ProductDetail;
import com.inditex.similar.service.SimilarProductsService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/product")
public class SimilarProductsController {

    private final SimilarProductsService service;

    public SimilarProductsController(SimilarProductsService service) {
        this.service = service;
    }

    @GetMapping("/{productId}/similar")
    public Mono<List<ProductDetail>> similar(@PathVariable String productId) {
        return service.getSimilarProducts(productId);
    }
}

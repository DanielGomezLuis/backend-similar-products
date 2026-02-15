package com.inditex.similar.dto;

public record ProductDetail(
        String id,
        String name,
        double price,
        boolean availability
) {}
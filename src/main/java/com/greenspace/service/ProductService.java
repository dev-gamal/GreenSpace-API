package com.greenspace.service;

import com.greenspace.dto.request.ProductRequest;
import com.greenspace.dto.response.ProductResponse;
import com.greenspace.enums.ExchangeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request, Long publisherId, String imageUrl);
    ProductResponse getProductById(Long id);
    Page<ProductResponse> getProductsByPublisher(Long publisherId, Pageable pageable);
    Page<ProductResponse> getLocalMarketProducts(ExchangeType exchangeType, String city, Pageable pageable);
    void deleteProduct(Long id, Long publisherId);
}
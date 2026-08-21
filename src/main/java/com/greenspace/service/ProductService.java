package com.greenspace.service;

import com.greenspace.dto.request.ProductRequest;
import com.greenspace.dto.response.ProductResponse;
import com.greenspace.enums.ExchangeType;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request, Long publisherId, String imageUrl);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getProductsByPublisher(Long publisherId);
    List<ProductResponse> getLocalMarketProducts(ExchangeType exchangeType, String city);
    void deleteProduct(Long id, Long publisherId);
}
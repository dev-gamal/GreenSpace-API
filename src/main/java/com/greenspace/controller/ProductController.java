package com.greenspace.controller;

import com.greenspace.dto.request.ProductRequest;
import com.greenspace.dto.response.ProductResponse;
import com.greenspace.enums.ExchangeType;
import com.greenspace.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest request,
            @RequestParam Long publisherId,
            @RequestParam String imageUrl) {
        ProductResponse response = productService.createProduct(request, publisherId, imageUrl);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/market")
    public ResponseEntity<Page<ProductResponse>> getMarketProducts(
            @RequestParam ExchangeType exchangeType,
            @RequestParam String city,
            @PageableDefault(size = 12, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(productService.getLocalMarketProducts(exchangeType, city, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id, @RequestParam Long publisherId) {
        productService.deleteProduct(id, publisherId);
        return ResponseEntity.noContent().build();
    }

}

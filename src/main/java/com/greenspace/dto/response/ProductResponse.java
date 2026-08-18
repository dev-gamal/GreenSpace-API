package com.greenspace.dto.response;

import com.greenspace.enums.ExchangeType;
import com.greenspace.enums.ProductStatus;
import com.greenspace.enums.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private Long publisherId;
    private String publisherName;
    private String publisherCity;
    private String title;
    private String description;
    private Double quantityKgOrUnits;
    private Double price;
    private ProductType productType;
    private ExchangeType exchangeType;
    private ProductStatus status;
    private String imageUrl;
    private LocalDateTime createdAt;
}
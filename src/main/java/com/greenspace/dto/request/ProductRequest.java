package com.greenspace.dto.request;

import com.greenspace.enums.ExchangeType;
import com.greenspace.enums.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity cannot be negative")
    private Double quantityKgOrUnits;

    @PositiveOrZero(message = "Price cannot be negative")
    private Double price;

    @NotNull(message = "Product type is required")
    private ProductType productType;

    @NotNull(message = "Exchange type is required")
    private ExchangeType exchangeType;
}

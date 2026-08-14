package com.greenspace.entity;

import com.greenspace.enums.ExchangeType;
import com.greenspace.enums.ProductStatus;
import com.greenspace.enums.ProductType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data @NoArgsConstructor @AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id", nullable = false)
    private User publisher;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "quantity_kg_or_units", nullable = false)
    private Double quantityKgOrUnits;

    private Double price = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false, length = 20, columnDefinition = "varchar(20)")
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    @Column(name = "exchange_type", nullable = false, length = 20, columnDefinition = "varchar(20)")
    private ExchangeType exchangeType;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, columnDefinition = "varchar(20)")
    private ProductStatus status = ProductStatus.AVAILABLE;

    @Column(name = "image_url")
    private String imageUrl;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
package com.greenspace.repository;

import com.greenspace.entity.Product;
import com.greenspace.enums.ExchangeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByPublisherId(Long publisherId);

    @Query("SELECT p FROM Product p JOIN p.publisher u WHERE p.status = 'AVAILABLE' " +
            "AND p.exchangeType = :exchangeType " +
            "AND LOWER(u.city) LIKE LOWER(CONCAT('%', :city, '%')) ORDER BY p.createdAt DESC")
    List<Product> findLocalMarketProducts(@Param("exchangeType") ExchangeType exchangeType,
                                          @Param("city") String city);
}

package com.greenspace.service.impl;

import com.greenspace.dto.request.ProductRequest;
import com.greenspace.dto.response.ProductResponse;
import com.greenspace.entity.Product;
import com.greenspace.entity.User;
import com.greenspace.enums.ExchangeType;
import com.greenspace.enums.ProductStatus;
import com.greenspace.mapper.ProductMapper;
import com.greenspace.repository.ProductRepository;
import com.greenspace.repository.UserRepository;
import com.greenspace.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductRequest request, Long publisherId, String imageUrl) {
        User publisher = userRepository.findById(publisherId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Product product = productMapper.toEntity(request);
        product.setPublisher(publisher);
        product.setImageUrl(imageUrl);
        product.setStatus(ProductStatus.AVAILABLE);

        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    @Transactional
    public List<ProductResponse> getProductsByPublisher(Long publisherId) {
        return productRepository.findByPublisherId(publisherId).stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public List<ProductResponse> getLocalMarketProducts(ExchangeType exchangeType, String city) {
        return productRepository.findLocalMarketProducts(exchangeType, city).stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteProduct(Long id, Long publisherId) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (!product.getPublisher().getId().equals(publisherId)) {
            throw new IllegalArgumentException("Action not authorized.");
        }
        productRepository.delete(product);
    }
}
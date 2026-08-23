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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getProductsByPublisher(Long publisherId, Pageable pageable) {
        return productRepository.findByPublisherId(publisherId, pageable)
                .map(productMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getLocalMarketProducts(ExchangeType exchangeType, String city, Pageable pageable) {
        return productRepository.findLocalMarketProducts(exchangeType, city, pageable)
                .map(productMapper::toResponse);
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
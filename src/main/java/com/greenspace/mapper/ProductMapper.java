package com.greenspace.mapper;

import com.greenspace.dto.request.ProductRequest;
import com.greenspace.dto.response.ProductResponse;
import com.greenspace.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "publisher.id", target = "publisherId")
    @Mapping(target = "publisherName", expression = "java(product.getPublisher() != null ? product.getPublisher().getFirstName() + \" \" + product.getPublisher().getLastName() : null)")
    @Mapping(source = "publisher.city", target = "publisherCity")
    ProductResponse toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Product toEntity(ProductRequest request);
}

package com.epam.productmanagement.api.mapper;

import com.epam.productmanagement.api.dto.ProductDTO;
import com.epam.productmanagement.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category.name", source = "productDTO.categoryName")
    Product productDTOtoProduct(ProductDTO productDTO);

    @Mapping(target = "categoryName", source = "product.category.name")
    ProductDTO productToProductDTO(Product product);

    List<ProductDTO> productToProductDTOList(List<Product> products);
}
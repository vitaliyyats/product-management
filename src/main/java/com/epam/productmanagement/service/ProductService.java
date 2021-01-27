package com.epam.productmanagement.service;

import com.epam.productmanagement.api.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll(Long categoryId);

    ProductDTO getProduct(Long id, Long categoryId);

    ProductDTO saveProduct(ProductDTO productDTO, Long categoryId);

    void removeProduct(Long id);
}

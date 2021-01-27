package com.epam.productmanagement.api.controller;

import com.epam.productmanagement.api.dto.ProductDTO;
import com.epam.productmanagement.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories/{categoryId}/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductDTO> getAll(@PathVariable Long categoryId) {
        return productService.getAll(categoryId);
    }

    @GetMapping("/{id}")
    public ProductDTO getProduct(@PathVariable Long id, @PathVariable Long categoryId) {
        return productService.getProduct(id, categoryId);
    }

    @PreAuthorize("hasRole('SUPER_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ProductDTO createProduct(@PathVariable Long categoryId, @RequestBody ProductDTO productDTO) {
        return productService.saveProduct(productDTO, categoryId);
    }

    @PreAuthorize("hasRole('SUPER_USER')")
    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id,
                              @PathVariable Long categoryId,
                              @RequestBody ProductDTO productDTO) {
        if (!Objects.equals(id, productDTO.getId())) {
            throw new IllegalStateException("Invalid product id");
        }
        return productService.saveProduct(productDTO, categoryId);
    }

    @PreAuthorize("hasRole('SUPER_USER')")
    @DeleteMapping("/{id}")
    public void removeProduct(@PathVariable Long id) {
        productService.removeProduct(id);
    }
}

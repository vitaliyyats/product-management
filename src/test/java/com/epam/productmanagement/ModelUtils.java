package com.epam.productmanagement;

import com.epam.productmanagement.api.dto.CategoryDTO;
import com.epam.productmanagement.api.dto.ProductDTO;
import com.epam.productmanagement.model.Category;
import com.epam.productmanagement.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ModelUtils {
    public static final Long PRODUCT_ID = 1L;
    public static final String PRODUCT_NAME = "Test product name";
    public static final String PRODUCT_DESCRIPTION = "Test product description";
    public static final BigDecimal PRODUCT_PRICE = BigDecimal.TEN;

    public static final Long CATEGORY_ID = 1L;
    public static final String CATEGORY_NAME = "Test category name";
    public static final String CATEGORY_DESCRIPTION = "Test category description";

    public static Product createProduct() {
        return Product.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .price(PRODUCT_PRICE)
                .build();
    }

    public static ProductDTO createProductDTO() {
        return ProductDTO.builder()
                .id(PRODUCT_ID)
                .name(PRODUCT_NAME)
                .description(PRODUCT_DESCRIPTION)
                .categoryName(CATEGORY_NAME)
                .price(PRODUCT_PRICE)
                .build();
    }

    public static Category createCategory() {
        return Category.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .description(CATEGORY_DESCRIPTION)
                .products(new ArrayList<>())
                .build();
    }

    public static CategoryDTO createCategoryDTO() {
        return CategoryDTO.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .description(CATEGORY_DESCRIPTION)
                .build();
    }
}

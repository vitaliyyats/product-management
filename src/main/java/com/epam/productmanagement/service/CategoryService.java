package com.epam.productmanagement.service;

import com.epam.productmanagement.api.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAll();

    CategoryDTO getCategory(Long id);

    CategoryDTO saveCategory(CategoryDTO categoryDTO);

    void removeCategory(Long id);
}

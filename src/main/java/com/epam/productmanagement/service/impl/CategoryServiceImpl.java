package com.epam.productmanagement.service.impl;

import com.epam.productmanagement.api.dto.CategoryDTO;
import com.epam.productmanagement.api.mapper.CategoryMapper;
import com.epam.productmanagement.dao.CategoryRepository;
import com.epam.productmanagement.model.Category;
import com.epam.productmanagement.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        log.info("Fetching all categories. Found {} records.", categories.size());
        return categoryMapper.categoryToCategoryDTOList(categories);
    }

    @Override
    public CategoryDTO getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Category with id = " + id + " not found."));
        log.info("Found category with id = {}: {}", id, category);
        return categoryMapper.categoryToCategoryDTO(category);
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.categoryDTOtoCategory(categoryDTO);
        if (categoryDTO.getParentId() != null) {
            Category parentCategory = categoryRepository.getOne(categoryDTO.getParentId());
            category.setParentCategory(parentCategory);
        }
        log.info("Saving category: {}", category);
        category = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDTO(category);
    }

    @Override
    public void removeCategory(Long id) {
        log.info("Removing category with id = {}", id);
        categoryRepository.deleteById(id);
    }
}
package com.epam.productmanagement.api.mapper;

import com.epam.productmanagement.api.dto.CategoryDTO;
import com.epam.productmanagement.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category categoryDTOtoCategory(CategoryDTO categoryDTO);

    @Mapping(target = "parentId", source = "category.parentCategory.id")
    CategoryDTO categoryToCategoryDTO(Category category);

    List<CategoryDTO> categoryToCategoryDTOList(List<Category> categories);
}
package com.epam.productmanagement.api.controller;

import com.epam.productmanagement.api.dto.CategoryDTO;
import com.epam.productmanagement.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public CategoryDTO getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    @PreAuthorize("hasRole('SUPER_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CategoryDTO createCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.saveCategory(categoryDTO);
    }

    @PreAuthorize("hasRole('SUPER_USER')")
    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        if (!Objects.equals(id, categoryDTO.getId())) {
            throw new IllegalStateException("Invalid category id");
        }
        return categoryService.saveCategory(categoryDTO);
    }

    @PreAuthorize("hasRole('SUPER_USER')")
    @DeleteMapping("/{id}")
    public void removeCategory(@PathVariable Long id) {
        categoryService.removeCategory(id);
    }
}

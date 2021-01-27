package com.epam.productmanagement.integration;

import com.epam.productmanagement.ModelUtils;
import com.epam.productmanagement.api.dto.CategoryDTO;
import com.epam.productmanagement.api.mapper.CategoryMapper;
import com.epam.productmanagement.dao.CategoryRepository;
import com.epam.productmanagement.model.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "User", roles = {"SUPER_USER"})
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @AfterEach
    public void resetDb() {
        categoryRepository.deleteAll();
    }

    @Test
    void getAll() throws Exception {
        Category category = categoryRepository.save(ModelUtils.createCategory());

        mockMvc
                .perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(category.getId()))
                .andExpect(jsonPath("$[0].name").value(category.getName()));
    }

    @Test
    void getCategory() throws Exception {
        Category category = categoryRepository.save(ModelUtils.createCategory());

        mockMvc
                .perform(get("/categories/" + category.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(category.getId()))
                .andExpect(jsonPath("$.name").value(category.getName()));
    }

    @Test
    void createCategory() throws Exception {
        CategoryDTO categoryDTO = ModelUtils.createCategoryDTO();

        mockMvc.perform(
                post("/categories")
                        .content(objectMapper.writeValueAsString(categoryDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(categoryDTO.getName()));
    }

    @Test
    void updateCategory() throws Exception {
        Category category = categoryRepository.save(ModelUtils.createCategory());
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
        categoryDTO.setName("Updated name");

        mockMvc.perform(
                put("/categories/" + categoryDTO.getId())
                        .content(objectMapper.writeValueAsString(categoryDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(categoryDTO.getName()));
    }

    @Test
    void removeCategory() throws Exception {
        Category category = categoryRepository.save(ModelUtils.createCategory());

        mockMvc.perform(
                delete("/categories/{id}", category.getId()))
                .andExpect(status().isOk());
    }
}
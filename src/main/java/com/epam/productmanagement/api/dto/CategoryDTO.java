package com.epam.productmanagement.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private String description;
    private Long parentId;
}
package com.wholesaler.backend.dto;

import java.util.List;

public class CategoryDTO {
    private Integer categoryId;
    private String categoryName;
    private String categoryDescription;

    private List<PartDTO> parts;

    public CategoryDTO() {
    }

    public CategoryDTO(Integer categoryId, String categoryName, String categoryDescription, List<PartDTO> parts) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDescription = categoryDescription;
        this.parts = parts;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public List<PartDTO> getParts() {
        return parts;
    }

    public void setParts(List<PartDTO> parts) {
        this.parts = parts;
    }
}

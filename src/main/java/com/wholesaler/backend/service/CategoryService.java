package com.wholesaler.backend.service;

import com.wholesaler.backend.dto.CategoryDTO;
import com.wholesaler.backend.dto.PartDTO;
import com.wholesaler.backend.model.Category;
import com.wholesaler.backend.model.Part;
import com.wholesaler.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private CategoryDTO convertCategoryToDTO(Category category) {
        List<PartDTO> partDTOs = category.getParts() != null ? category.getParts().stream()
                .map(this::convertPartToDTO)
                .collect(Collectors.toList()) : null;

        return new CategoryDTO(
                category.getCategoryId(),
                category.getCategoryName(),
                category.getCategoryDescription(),
                partDTOs
        );
    }

    private PartDTO convertPartToDTO(Part part) {
        PartDTO partDTO = new PartDTO();
        partDTO.setPartId(part.getPartId());
        partDTO.setPartName(part.getPartName());
        partDTO.setUnitPrice(part.getUnitPrice());
        partDTO.setQuantityPerUnit(part.getQuantityPerUnit());
        partDTO.setLeftOnStock(part.getLeftOnStock());
        partDTO.setAvailable(part.getAvailable());
        partDTO.setPartDescription(part.getPartDescription());
        return partDTO;
    }

    // get all
    public List<CategoryDTO> getAllCategoriesAsDTO() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertCategoryToDTO)
                .collect(Collectors.toList());
    }

    // get by id
    public Optional<CategoryDTO> getCategoryByIdAsDTO(Integer categoryId) {
        return categoryRepository.findById(categoryId).map(this::convertCategoryToDTO);
    }

    // post new category
    public Category addCategory(String categoryName, String categoryDescription) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setCategoryDescription(categoryDescription);
        return categoryRepository.save(category);
    }

    // post updated category
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    // put
    public Optional<Category> updateCategory(Integer categoryId, Category updatedCategory) {
        return categoryRepository.findById(categoryId).map(category -> {
            category.setCategoryName(updatedCategory.getCategoryName());
            category.setCategoryDescription(updatedCategory.getCategoryDescription());
            return categoryRepository.save(category);
        });
    }

    // delete
    public void deleteCategory(Integer categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            categoryRepository.deleteById(categoryId);
        }
    }
}

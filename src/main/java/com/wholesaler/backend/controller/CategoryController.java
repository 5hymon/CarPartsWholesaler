package com.wholesaler.backend.controller;

import com.wholesaler.backend.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.wholesaler.backend.dto.CategoryDTO;
import com.wholesaler.backend.service.CategoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // GET /categories/all - get all categories
    @GetMapping("/all")
    public List<CategoryDTO> getAllCategories() {
        return categoryService.getAllCategoriesAsDTO();
    }

    // GET /categories/{categoryId} - get category by ID
    @GetMapping("{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("categoryId") Integer categoryId) {
        Optional<CategoryDTO> category = categoryService.getCategoryByIdAsDTO(categoryId);
        if (category.isPresent()) {
            return new ResponseEntity<>(category.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // POST - add new category
    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    // PUT - update category by ID
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable("categoryId") Integer categoryId, @RequestBody Category updatedCategory) {
        Optional<Category> categoryOptional = categoryService.updateCategory(categoryId, updatedCategory);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            return ResponseEntity.ok(categoryService.saveCategory(category));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE - delete category by ID
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        Optional<CategoryDTO> categoryOptional = categoryService.getCategoryByIdAsDTO(categoryId);
        if (categoryOptional.isPresent()) {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

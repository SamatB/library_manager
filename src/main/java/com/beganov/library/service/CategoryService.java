package com.beganov.library.service;

import com.beganov.library.model.Category;

import java.util.List;

public interface CategoryService {

    Long save(Category category);

    Category getById(Long id);

    List<Category> getAllCategories();

    Category update(Long id, String newName);

    String delete(Long id);
}

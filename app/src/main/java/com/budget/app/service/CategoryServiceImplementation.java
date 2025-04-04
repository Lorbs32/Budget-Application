package com.budget.app.service;

import com.budget.app.entity.Category;
import com.budget.app.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImplementation  implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void saveCategory(Category category) {categoryRepository.updateOrInsert(category);}
}

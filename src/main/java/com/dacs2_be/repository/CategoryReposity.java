package com.dacs2_be.repository;

import com.dacs2_be.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryReposity extends JpaRepository<Category, Long> {
    Category findByName(String name);
    Category findById(long id);
    void deleteById(long id);
    Category save(Category category);
}

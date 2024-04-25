package com.coolvetclinicpumb.vetclinicapp.repository;

import com.coolvetclinicpumb.vetclinicapp.model.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByTypeCategory(Category.TypeCategory typeCategory);
}

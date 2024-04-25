package com.coolvetclinicpumb.vetclinicapp.repository.criteriaquery.animal;

import com.coolvetclinicpumb.vetclinicapp.mapper.CategoryMapper;
import com.coolvetclinicpumb.vetclinicapp.model.Animal;
import com.coolvetclinicpumb.vetclinicapp.model.Category;
import com.coolvetclinicpumb.vetclinicapp.repository.CategoryRepository;
import com.coolvetclinicpumb.vetclinicapp.repository.criteriaquery.SpecificationProvider;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategorySpecificationProvider implements SpecificationProvider<Animal> {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    public Specification<Animal> getSpecification(String[] params) {
        List<Category> listTypeCategory
                = Arrays.stream(params)
                .map(String::toUpperCase)
                .map(categoryMapper::toCategory)
                .map(categoryRepository::findAllByTypeCategory)
                .flatMap(List::stream)
                .toList();
        return (root, query, criteriaBuilder) -> root.get("category")
                .in(listTypeCategory.toArray());
    }

    @Override
    public String getKey() {
        return "category";
    }
}
